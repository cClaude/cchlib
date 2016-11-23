package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.google.GoogleReadConfigFactory;

/**
 *
 *
 */
public class PhoneRecordSorterCLIApp
{
    private static final Logger LOGGER = Logger.getLogger( PhoneRecordSorterCLIApp.class );
    private final ConfigFactory configFactory;

    private PhoneRecordSorterCLIApp()
    {
        this.configFactory = GoogleReadConfigFactory.getInstance();
    }

    /**
     * Launch the application.
     * @throws IOException
     * @throws UnsupportedOperationException
     * @throws FileNotFoundException
     */
    public static void main( final String[] args ) throws FileNotFoundException, UnsupportedOperationException, IOException
    {
        //final File sourceFolderFile   = args.length > 0 ? new File( args[ 0 ] ) : null;
        //final File destinationFolders = args.length > 1 ? new File( args[ 1 ] ) : null;
        //final File googleContactFile  = args.length > 1 ? new File( args[ 2 ] ) : null;

        //        final File sourceFolderFile   = new File( "C:/Users/Claude/Dropbox/#CallRecorder/_SortStep1_" );
        //final File sourceFolderFile   = new File( "C:/Users/Claude/Dropbox/#CallRecorder/" );
        final File sourceFolderFile   = new File( "E:/tmp/#CallRecorder" );
        final File destinationFolder = new File( "E:/tmp/OUTPUT/" );
        final File googleContactFile  = new File( "C:/Users/Claude/Dropbox/#CallRecorder/#Config/google-contacts.csv" );
//                                                   C:\Users\Claude\Dropbox\#CallRecorder\_SortStep1_
        LOGGER.info( "sourceFolderFile    = " + sourceFolderFile );
        LOGGER.info( "destinationFolder   = " + destinationFolder );
        LOGGER.info( "googleContactFile   = " + googleContactFile );

        if( ! isDirectory( sourceFolderFile ) ) {
             throw new IllegalArgumentException( "Parameter sourceFolderFile not valid : " + sourceFolderFile.isDirectory() );
        }
        if( ! isValid( destinationFolder ) ) {
            throw new IllegalArgumentException( "Parameter destinationFolders not valid : " + destinationFolder );
        }
        if( ! isFile( googleContactFile ) ) {
            throw new IllegalArgumentException( "Parameter googleContactFile not valid : " + googleContactFile.getAbsolutePath() );
        }

        new PhoneRecordSorterCLIApp().run( sourceFolderFile,destinationFolder, googleContactFile );
    }

    private static boolean isValid( final File file )
    {
        return file != null;
    }

    private static boolean isDirectory( final File file )
    {
        if( isValid( file ) ) {
            return file.isDirectory();
        }
        return false;
    }

    private static boolean isFile( final File file )
    {
        if( isValid( file ) ) {
            return file.isFile();// && file.canRead();
        }
        return false;
    }

    public void run(
        final File sourceFolderFile,
        final File destinationFolder,
        final File googleContactFile
        ) throws FileNotFoundException, UnsupportedOperationException, IOException
    {
        final Config config = this.configFactory.load( googleContactFile );

        try {
            Files.walkFileTree( sourceFolderFile.toPath(), createVisitor( config, destinationFolder ) );
        } catch( final IOException e ) {
            LOGGER.fatal( "Fail while walking in : " + sourceFolderFile, e );
            throw e;
        }
    }

    private FileVisitor<Path> createVisitor(
            final Config config,
            final File   destinationFolder
            ) throws CreateDestinationFolderException
    {
        return new PhoneRecordSorterFileVisitor( config, destinationFolder );
    }
}
