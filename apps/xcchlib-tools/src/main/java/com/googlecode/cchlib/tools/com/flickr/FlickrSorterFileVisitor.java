package com.googlecode.cchlib.tools.com.flickr;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.IOHelper;

class FlickrSorterFileVisitor implements FileVisitor<Path>
{
    private static final Logger LOGGER = Logger.getLogger( FlickrSorterFileVisitor.class );

    private final File sourceFolderFile;
    private final File destinationFolderFile;

    private final String[] endOfFolderNames;
    private final String[] endOfFileNames;

    private final String[] extensions;

    public FlickrSorterFileVisitor(
        final File sourceFolderFile,
        final File destinationFolderFile
        )
    {
        this(
            sourceFolderFile,
            destinationFolderFile,
            new String[] { "_fichiers", "_files", },
            new String[] { ".htm"     , ".html" , }
            );
    }

    public FlickrSorterFileVisitor(
        final File     sourceFolderFile,
        final File     destinationFolderFile,
        final String[] endOfFolderNames,
        final String[] endOfFileNames
        )
    {
        this.sourceFolderFile      = sourceFolderFile;
        this.destinationFolderFile = destinationFolderFile;
        this.endOfFolderNames      = endOfFolderNames;
        this.endOfFileNames        = endOfFileNames;
        this.extensions            = new String[] { ".gif", ".jpeg", ".jpg", ".png", ".mp4" };
    }

    @Override
    public FileVisitResult preVisitDirectory( final Path dir, final BasicFileAttributes attrs )
        throws IOException
    {
        for( final String endOfFolderName : this.endOfFolderNames ) {
            if( dir.endsWith( endOfFolderName ) ) {
                return FileVisitResult.SKIP_SIBLINGS;
            }
        }

        LOGGER.trace( "Visite directory : " + dir );

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs )
        throws IOException
    {
        for( int i = 0; i<this.endOfFileNames.length; i++ ) {
            final String fileName = file.getFileName().toString();
            if( fileName.endsWith( this.endOfFileNames[ i ] ) ) {
                visitHtmlFile( file, this.endOfFileNames[ i ] );
            } else {
                LOGGER.debug( "ignore file : " + file );
            }
        }

        return FileVisitResult.CONTINUE;
    }

    private void visitHtmlFile( final Path file, final String endOfFileName ) throws IOException
    {
        final String fileName = file.getFileName().toString();
        final String prefix   = fileName.substring( 0, fileName.length() - endOfFileName.length() );
        final File   folder   = findRelatedFolder( file.getParent().toFile(), prefix );

        if( folder != null ) {
            handleFile( file, folder );
        } else {
            final boolean deleted = file.toFile().delete();
            LOGGER.warn( "No folder for html: deleted["+deleted+"] " + file );
        }
    }

    private File findRelatedFolder( final File sourceFolderFile, final String prefix )
    {
        for( final String endOfFolderName : this.endOfFolderNames ) {
            final String folderName = prefix + endOfFolderName;
            final File   folder     = new File( sourceFolderFile, folderName );

            if( folder.isDirectory() ) {
                return folder;
            }
        }

        return null;
    }

    private void handleFile( final Path file, final File folder )
        throws IOException
    {
        LOGGER.trace( "folder found for: " + file );
        final String id = findId( file );

        if( id != null ) {
            moveFiles( folder, id );
        } else {
            LOGGER.error( "No ID for file: " + file );
        }
     }

    private String findId( final Path file ) throws IOException
    {
        final String content = IOHelper.toString( file.toFile() );

        final String str = "<link id=\"canonicalurl\" rel=\"canonical\" href=\"https://www.flickr.com/photos/";
        final int pos = content.indexOf( str );

        if( pos != -1 ) {
            final int beginIndex = pos + str.length();
            final int endIndex   = content.indexOf( '/', beginIndex );

            if( endIndex != -1 ) {
                final String id = content.substring( beginIndex, endIndex );
                LOGGER.warn( "id: " + id );

                return id;
            } else {
                LOGGER.error( "Can not find END for id in : " + file );
            }
        } else {
            LOGGER.error( "Can not find pattern in : " + file );
        }

        return null;
    }

    @SuppressWarnings("squid:S134")
    private boolean moveFiles( final File folder, final String id )
    {
        final File   folderFile = new File( this.destinationFolderFile, id );
        final File[] files      = folder.listFiles( this::isMedia );

        if( ! folderFile.exists() ) {
            folderFile.mkdirs();
        }

        boolean moveMissing = false;

        if( files != null ) {
            for( final File file : files ) {
                if( isMedia( file ) ) {
                    final File    newFile = new File( folderFile, file.getName() );
                    final boolean moved   = file.renameTo( newFile );

                    if( !moved ) {
                        moveMissing  = true;
                        LOGGER.warn( String.format(
                            "Can not move: (exists:%b) %s to %s",
                            newFile.exists(),
                            file,
                            newFile
                            ) );
                    }
                }
            }
        }

        return !moveMissing;
    }

    private boolean isMedia( final File file )
    {
        if( file.isDirectory() ) {
            return false;
        }
        for( final String extension : this.extensions ) {
            if( file.getName().endsWith( extension ) ) {
                return true;
            }
        }
         return false;
    }

    @Override
    public FileVisitResult visitFileFailed( final Path file, final IOException ioe )
        throws IOException
    {
        LOGGER.warn( "Fail : " + file, ioe );

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory( final Path dir, final IOException ioe )
        throws IOException
    {
        return FileVisitResult.CONTINUE;
    }
}
