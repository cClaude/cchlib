package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.io.DirectoryIterator;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * Compute hash code of all files (recursive)
 */
public class HashCompute
{
    private final FileDigestFactory fileDigestFactory;
    private final File directortFile;
    private final HashComputeListener listener;
    private final FileFilter fileFilter;

    /**
     *
     * @param cliHelper Helper for CLI parameters
     * @throws CLIParametersException
     */
    public HashCompute( final CLIParameters cli ) throws CLIParametersException
    {
        this.fileDigestFactory = cli.getFileDigestFactory();
        this.directortFile     = cli.getDirectory();
        this.listener          = cli.getHashComputeListener();
        this.fileFilter        = cli.getFileFilter();

        if( cli.isVerbose() ) {
            CLIHelper.trace( this.fileFilter );
        }
    }

    public List<HashFile> getAllHash() throws NoSuchAlgorithmException
    {
        final List<HashFile>    listHashFile = new ArrayList<>();
        final DirectoryIterator iter         = new DirectoryIterator( this.directortFile );

        while( iter.hasNext() ) {
            final File   dir   = iter.next();
            final File[] files = dir.listFiles( this.fileFilter );

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
