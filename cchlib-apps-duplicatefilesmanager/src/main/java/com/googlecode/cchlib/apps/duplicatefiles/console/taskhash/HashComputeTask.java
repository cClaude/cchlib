package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTaskException;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.TaskCommon;
import com.googlecode.cchlib.io.DirectoryIterator;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * Compute hash code of all files (recursive)
 */
public class HashComputeTask extends TaskCommon implements CommandTask
{
    private class InternalTask
    {
        private final List<HashFile> listHashFile;

        InternalTask()
        {
            this.listHashFile = new ArrayList<>();
        }

        List<HashFile> runDirectories() throws NoSuchAlgorithmException
        {
            final DirectoryIterator iter = new DirectoryIterator(
                    HashComputeTask.this.directortFile,
                    getDirectoriesFileFilter()
                    );

            while( iter.hasNext() ) {
                final File   dir   = iter.next();
                final File[] files = dir.listFiles( getFilesFileFilter() );

                if( files != null ) {
                    runFiles( files );
                }
            }

            return this.listHashFile;
        }

        private void runFiles( final File[] files )
            throws NoSuchAlgorithmException
        {
            for( final File file : files ) {
                if( file.isFile() ) {
                    handleHash( file );
                }
            }
        }

        private void handleHash( final File file )
            throws NoSuchAlgorithmException
        {
            try {
                final String hash = computeHash( file );

                this.listHashFile.add( new HashFile( hash, file ) );

                HashComputeTask.this.listener.printCurrentFile( hash, file );
            }
            catch( IOException | CancelRequestException e ) {
                CLIHelper.printError( "Access error", file, e );
            }
        }
    }

    private final FileDigestFactory       fileDigestFactory;
    private final File                    directortFile;
    private final HashComputeTaskListener listener;

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
        this.directortFile         = cli.getDirectory();
        this.listener              = cli.getHashComputeListener();
    }

    @Override
    public List<HashFiles> doTask()
    {
        try {
            final List<HashFile> hashList = runDirectories();

            final List<HashFiles> result = HashFileHelper.convert( hashList, isVerbose() );

            if( isOnlyDuplicates() ) {
                removeNonDuplicates( result );
            }

            return result;
        }
        catch( final NoSuchAlgorithmException e ) {
            // Should not occur
            throw new CommandTaskException( "Internal error", e );
        }
    }

    private List<HashFile> runDirectories() throws NoSuchAlgorithmException
    {
        final InternalTask task = new InternalTask();

        return task.runDirectories();
   }

    private String computeHash( // NOSONAR
            final File file
            ) throws NoSuchAlgorithmException, IOException, CancelRequestException
    {
        final FileDigest fileDigest = this.fileDigestFactory.newInstance();

        /*final byte[] hash = */fileDigest.computeFile( file, this.listener );
        return fileDigest.digestString();
    }
}
