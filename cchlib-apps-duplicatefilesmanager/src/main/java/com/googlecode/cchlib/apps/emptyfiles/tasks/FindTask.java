package com.googlecode.cchlib.apps.emptyfiles.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingTableModel;

public class FindTask
{
    private static final Logger LOGGER = Logger.getLogger( FindTask.class );

    private final WorkingTableModel tableModel;
    private final Set<FileVisitOption> fileVisitOption;
    private final int maxDepth;
    private final LinkOption linkOption;

    public FindTask(
        final WorkingTableModel    tableModel,
        final Set<FileVisitOption> fileVisitOption,
        final int                  maxDepth,
        final LinkOption           linkOption
        )
    {
        this.tableModel      = tableModel;
        this.fileVisitOption = fileVisitOption;
        this.maxDepth        = maxDepth;
        this.linkOption      = linkOption;
    }

    public void start( final Collection<File> directoryFiles )
    {
        for( final File directoryFile : directoryFiles ) {
            try {
                findFiles( directoryFile.toPath() );
                }
            catch( final IOException e ) {
                LOGGER.error( "FindTask.start(): " + directoryFile, e );
                }
            }
    }

    private void findFiles( final Path directoryPath ) throws IOException
    {
        Files.walkFileTree( directoryPath, fileVisitOption, maxDepth, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory( final Path dir, final BasicFileAttributes attrs )
                throws IOException
            {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs )
                throws IOException
            {
                if( ! Files.isDirectory( file, linkOption ) ) {
                    if( Files.size( file ) == 0 ) {
                        tableModel.add( file.toFile() );
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
            }} );
    }
}
