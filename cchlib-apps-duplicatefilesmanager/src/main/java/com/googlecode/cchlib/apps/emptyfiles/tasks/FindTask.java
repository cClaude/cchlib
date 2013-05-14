package com.googlecode.cchlib.apps.emptyfiles.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.Collection;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptyfiles.WorkingTableModel;
import java.nio.file.attribute.BasicFileAttributes;

public class FindTask
{
    private static final Logger logger = Logger.getLogger( FindTask.class );

    private WorkingTableModel tableModel;
    private Set<FileVisitOption> fileVisitOption;
    private int maxDepth;
    private LinkOption linkOption;

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

    public void start( Collection<File> directoryFiles )
    {
        for( File directoryFile : directoryFiles ) {
            try {
                findFiles( directoryFile.toPath() );
                }
            catch( IOException e ) {
                logger.error( "FindTask.start(): " + directoryFile, e );
                }
            }
    }

    private void findFiles( final Path directoryPath ) throws IOException
    {
        Files.walkFileTree( directoryPath, fileVisitOption, maxDepth, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory( Path dir, BasicFileAttributes attrs )
                throws IOException
            {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile( Path file, BasicFileAttributes attrs )
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
            public FileVisitResult visitFileFailed( Path file, IOException exc )
                throws IOException
            {
                logger.error( "visitFileFailed", exc );

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory( Path dir, IOException exc )
                throws IOException
            {
                return FileVisitResult.CONTINUE;
            }} );
    }
}