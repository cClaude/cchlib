package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.io.DirectoryIterator;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

public class HashCompute
{
    private final FileDigestFactory fileDigestFactory;
    private final File directortFile;
    private final HashComputeListener listener;
    private final FileFilter fileFilter;

    /**
     *
     * @param cliHelper Helper for CLI parameters
     */
    public HashCompute( final CLIHelper cliHelper )
    {
        this.fileDigestFactory = cliHelper.getFileDigestFactory();
        this.directortFile     = cliHelper.getDirectory();
        this.listener          = cliHelper.getHashComputeListener();
        this.fileFilter        = cliHelper.getFileFilter();
    }

    public List<HashFile> getAllHash() throws NoSuchAlgorithmException
    {
        final List<HashFile>    listHashFile = new ArrayList<>();
        final DirectoryIterator iter         = new DirectoryIterator( this.directortFile );

        while( iter.hasNext() ) {
            final File   dir   = iter.next();
            final File[] files = dir.listFiles( this.fileFilter );

            if( files != null ) {
                for( final File file : files ) {
                    if( file.isFile() ) {
                        try {
                            final String hash = computeHash( file );

                            listHashFile.add( new HashFile( hash, file ) );
                            this.listener.printCurrentFile( hash, file );
                        }
                        catch( IOException | CancelRequestException e ) {
                            Console.printError( "Access error", file, e );
                        }
                    }
                }
            }
        }

        return listHashFile;
    }

    private String computeHash( //
            final File file
            ) throws NoSuchAlgorithmException, FileNotFoundException, IOException, CancelRequestException
    {
        final FileDigest fileDigest = this.fileDigestFactory.newInstance();

        /*final byte[] hash = */fileDigest.computeFile( file, this.listener );
        return fileDigest.digestString();
    }
}
