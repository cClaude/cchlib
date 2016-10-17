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
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFilterFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.io.DirectoryIterator;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * Compute hash code of all files (recursive)
 */
public class HashComputeTask implements CommandTask
{
    private final FileDigestFactory fileDigestFactory;
    private final File directortFile;
    private final HashComputeTaskListener listener;
    private final FileFilter filesFileFilter;
    private final FileFilter directoriesFileFilter;

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

        final FileFiltersConfig ffc     = cli.getFileFiltersConfig();
        final boolean           verbose = cli.isVerbose();

        this.filesFileFilter       = FileFilterFactory.getFileFilterForFiles( ffc, verbose );
        this.directoriesFileFilter = FileFilterFactory.getFileFilterForDirectories( ffc, verbose );

        if( verbose ) {
            CLIHelper.trace( "Files FileFilter", this.filesFileFilter );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
    }

    @Override
    public List<HashFile> doTask()
    {
        try {
            return internalDoTask();
        }
        catch( final NoSuchAlgorithmException e ) {
            // Should not occur
            throw new CommandTaskException( "Internal error", e );
        }
    }

    private List<HashFile> internalDoTask() throws NoSuchAlgorithmException
    {
        final List<HashFile>    listHashFile = new ArrayList<>();
        final DirectoryIterator iter         = new DirectoryIterator( this.directortFile, this.directoriesFileFilter );

        while( iter.hasNext() ) {
            final File   dir   = iter.next();
            final File[] files = dir.listFiles( this.filesFileFilter );

            if( files != null ) {
                handleHashes( listHashFile, files );
            }
        }

        return listHashFile;
   }

    private void handleHashes( //
        final List<HashFile> listHashFile, //
        final File[]         files //
        ) throws NoSuchAlgorithmException
    {
        for( final File file : files ) {
            if( file.isFile() ) {
                handleHash( listHashFile, file );
            }
        }
    }

    private void handleHash(
        final List<HashFile> listHashFile,
        final File           file
        ) throws NoSuchAlgorithmException
    {
        try {
            final String hash = computeHash( file );

            listHashFile.add( new HashFile( hash, file ) );
            this.listener.printCurrentFile( hash, file );
        }
        catch( IOException | CancelRequestException e ) {
            CLIHelper.printError( "Access error", file, e );
        }
    }

    private String computeHash( //
            final File file
            ) throws NoSuchAlgorithmException, IOException, CancelRequestException
    {
        final FileDigest fileDigest = this.fileDigestFactory.newInstance();

        /*final byte[] hash = */fileDigest.computeFile( file, this.listener );
        return fileDigest.digestString();
    }
}
