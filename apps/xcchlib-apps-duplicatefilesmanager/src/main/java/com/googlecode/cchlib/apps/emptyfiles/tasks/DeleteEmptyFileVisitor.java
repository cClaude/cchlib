package com.googlecode.cchlib.apps.emptyfiles.tasks;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingTableModel;

final class DeleteEmptyFileVisitor extends SimpleFileVisitor<Path>
{
    private static final Logger LOGGER = Logger.getLogger( DeleteEmptyFileVisitor.class );

    private final LinkOption        linkOption;
    private final WorkingTableModel tableModel;

    DeleteEmptyFileVisitor( final LinkOption linkOption, final WorkingTableModel tableModel )
    {
        this.linkOption = linkOption;
        this.tableModel = tableModel;
    }

    @Override
    public FileVisitResult preVisitDirectory( final Path dir, final BasicFileAttributes attrs )
        throws IOException
    {
        return FileVisitResult.CONTINUE;
    }

    @Override
    @SuppressWarnings("squid:S1066") // Collapse "if"
    public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs )
        throws IOException
    {
        if( ! Files.isDirectory( file, this.linkOption ) ) {
            if( Files.size( file ) == 0 ) {
                this.tableModel.add( file.toFile() );
                }
            }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed( final Path file, final IOException exc )
        throws IOException
    {
        if( exc instanceof AccessDeniedException ) {
            LOGGER.warn( "visitFileFailed : " + exc.getMessage() );
        } else {
            LOGGER.error( "visitFileFailed", exc );
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory( final Path dir, final IOException exc )
        throws IOException
    {
        return FileVisitResult.CONTINUE;
    }
}
