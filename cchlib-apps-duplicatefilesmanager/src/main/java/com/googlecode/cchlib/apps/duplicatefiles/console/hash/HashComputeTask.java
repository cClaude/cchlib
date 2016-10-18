package com.googlecode.cchlib.apps.duplicatefiles.console.hash;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskException;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFilterFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.io.DirectoryIterator;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * Compute hash code of all files (recursive)
 */
public class HashComputeTask implements CommandTask
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
                    HashComputeTask.this.directoriesFileFilter
                    );

            while( iter.hasNext() ) {
                final File   dir   = iter.next();
                final File[] files = dir.listFiles( HashComputeTask.this.filesFileFilter );

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
    private final FileFilter              filesFileFilter;
    private final FileFilter              directoriesFileFilter;
    private final boolean                 verbose;

    /**
     * Create {@link HashComputeTask} based on <code>cli</code>
     *
     * @param cliHelper Helper for CLI parameters
     * @throws CLIParametersException
     */
    public HashComputeTask( final CLIParameters cli ) throws CLIParametersException
    {
        this.fileDigestFactory     = cli.getFileDigestFactory();
        this.directortFile         = cli.getDirectory();
        this.listener              = cli.getHashComputeListener();
        this.verbose               = cli.isVerbose();

        final FileFiltersConfig ffc     = cli.getFileFiltersConfig();

        this.filesFileFilter       = FileFilterFactory.getFileFilterForFiles( ffc, this.verbose );
        this.directoriesFileFilter = FileFilterFactory.getFileFilterForDirectories( ffc, this.verbose );

        if( this.verbose ) {
            CLIHelper.trace( "Files FileFilter", this.filesFileFilter );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
    }

    @Override
    public List<HashFiles> doTask()
    {
        try {
            final List<HashFile> hashList = runDirectories();

            return HashFileHelper.convert( hashList, this.verbose );
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
