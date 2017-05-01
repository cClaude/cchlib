package com.googlecode.cchlib.tools.com.flickr;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.EnumSet;
import java.util.Set;
import org.apache.log4j.Logger;

public class FlickrSorter
{
    private static final Logger LOGGER = Logger.getLogger( FlickrSorter.class );
    private static final String S_IS_NOT_A_FOLDER = "%s is not a folder : ";

    private final File sourceFolderFile;
    private final File destinationFolderFile;
    private final Set<FileVisitOption> walkOptions;

    private FlickrSorter(
        final File sourceFolderFile,
        final File destinationFolderFile
        ) throws FlickrSorterException
    {
        if( ! sourceFolderFile.isDirectory() ) {
            throw new FlickrSorterException( String.format( S_IS_NOT_A_FOLDER, sourceFolderFile ) );
        }

        if( ! destinationFolderFile.isDirectory() ) {
            throw new FlickrSorterException( String.format( S_IS_NOT_A_FOLDER, destinationFolderFile ) );
        }

        this.sourceFolderFile      = sourceFolderFile;
        this.destinationFolderFile = destinationFolderFile;
        this.walkOptions           = EnumSet.noneOf( FileVisitOption.class );
    }

    private void sort() throws IOException
    {
        LOGGER.info( "walk in " + this.sourceFolderFile );

        Files.walkFileTree(
            this.sourceFolderFile.toPath(),
            this.walkOptions,
            Integer.MAX_VALUE,
            new FlickrSorterFileVisitor( this.sourceFolderFile, this.destinationFolderFile )
            );

        LOGGER.info( "walk done" );
    }

    public static void main( final String[] args ) throws FlickrSorterException, IOException
    {
        final File sourceFolderFile      = new File( args[ 0 ] );
        final File destinationFolderFile = new File( args[ 1 ] );

        final FlickrSorter instance = new FlickrSorter( sourceFolderFile, destinationFolderFile );

        instance.sort();
    }
}
