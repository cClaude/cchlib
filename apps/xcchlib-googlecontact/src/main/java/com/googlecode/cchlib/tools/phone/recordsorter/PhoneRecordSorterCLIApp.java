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
     *
     * @param args
     *            CLI parameters, expected : sourceFolderFile destinationFolder googleContactFile
     * @throws IOException
     *             if any
     * @throws UnsupportedOperationException
     *             if any
     * @throws FileNotFoundException
     *             if any
     */
    public static void main( final String[] args ) throws Exception
    {
        final File sourceFolderFile  = getSourceFolderFile( args );
        final File destinationFolder = getDestinationFolderFile( args );
        final File googleContactFile = getGoogleContactFile( args );

        LOGGER.info( "sourceFolderFile  = " + sourceFolderFile );
        LOGGER.info( "destinationFolder = " + destinationFolder );
        LOGGER.info( "googleContactFile = " + googleContactFile );

        if( ! isDirectory( sourceFolderFile ) ) {
             throw new IllegalArgumentException( "Parameter sourceFolderFile not valid : " + sourceFolderFile );
        }
        if( ! isValid( destinationFolder ) ) {
            throw new IllegalArgumentException( "Parameter destinationFolders not valid : " + destinationFolder );
        }
        if( ! isFile( googleContactFile ) ) {
            final String absolutePath = googleContactFile == null ? null : googleContactFile.getAbsolutePath();
            throw new IllegalArgumentException( "Parameter googleContactFile not valid : " + absolutePath );
        }

        new PhoneRecordSorterCLIApp().run( sourceFolderFile,destinationFolder, googleContactFile );
    }

    private static File getGoogleContactFile( final String[] args )
    {
        return args.length > 2 ? new File( args[ 2 ] ) : null;
    }

    private static File getDestinationFolderFile( final String[] args )
    {
        return args.length > 1 ? new File( args[ 1 ] ) : null;
    }

    private static File getSourceFolderFile( final String[] args )
    {
        return args.length > 0 ? new File( args[ 0 ] ) : null;
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
            return file.isFile();
        }

        return false;
    }

    @SuppressWarnings({"squid:S1160","squid:RedundantThrowsDeclarationCheck"})
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
