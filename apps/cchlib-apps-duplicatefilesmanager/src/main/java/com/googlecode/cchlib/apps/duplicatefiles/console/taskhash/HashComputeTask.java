package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.TaskCommon;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.tools.CollectFileInternalTask;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * Compute hash code of all files (recursive)
 */
public class HashComputeTask
    extends TaskCommon<HashFiles>
        implements CommandTask<HashFiles>, CollectFileInternalTask.Config
{
    private final FileDigestFactory       fileDigestFactory;
    private final File                    directoryFile;
    private final FileComputeTaskListener listener;

    /**
     * Create {@link HashComputeTask} based on <code>cli</code>
     *
     * @param cliHelper Helper for CLI parameters
     * @throws CLIParametersException
     */
    public HashComputeTask( final CLIParameters cli ) throws CLIParametersException
    {
        super( cli );

        this.fileDigestFactory     = cli.getFileDigestFactory();
        this.directoryFile         = cli.getDirectory();
        this.listener              = cli.getHashComputeListener();
    }

    @Override // CollectFileInternalTask.Config
    public FileFilter getDirectoriesFileFilter()
    {
        return super.getDirectoriesFileFilter();
    }

    @Override // CollectFileInternalTask.Config
    public FileFilter getFilesFileFilter()
    {
        return super.getFilesFileFilter();
    }

    @Override // CollectFileInternalTask.Config
    public File getDirectoryFile()
    {
        return this.directoryFile;
    }

    @Override // CommandTask
    public List<HashFiles> doTask() throws CLIParametersException
    {
        final List<HashFile> hashList = runDirectories();

        final List<HashFiles> result = HashFileHelper.convert( hashList, isVerbose() );

        if( isOnlyDuplicates() ) {
            removeNonDuplicates( result );
        }

        return result;
    }

    private List<HashFile> runDirectories()
    {
        final CollectFileInternalTask<HashFile> task
            = new CollectFileInternalTask<>( this, this::handleFile );

        return task.runDirectories();
    }

    private HashFile handleFile( final File file )
    {
        try {
            final String   hash   = computeHash( file );
            final HashFile result = new HashFile( hash, file );

            this.listener.printCurrentFile( hash, file );

            return result;
        }
        catch( IOException | CancelRequestException | NoSuchAlgorithmException e ) {
            CLIHelper.printError( "Access error", file, e );

            return null;
        }
    }

    private String computeHash( final File file )
        throws NoSuchAlgorithmException, IOException, CancelRequestException
    {
        final FileDigest fileDigest = this.fileDigestFactory.newInstance();

        /*final byte[] hash = */fileDigest.computeFile( file, HashComputeTask.this.listener );
        return fileDigest.digestString();
    }
}
