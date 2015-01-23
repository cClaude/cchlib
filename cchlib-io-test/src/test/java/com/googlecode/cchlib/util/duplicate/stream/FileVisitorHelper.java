package com.googlecode.cchlib.util.duplicate.stream;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.log4j.Logger;

final class FileVisitorHelper {

    private FileVisitorHelper() {
        // all static
    }

    public static FileVisitor<Path> newFileVisitor( final Logger logger )
    {
        return new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory( final Path dir, final BasicFileAttributes attrs ) throws IOException
            {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs )
            {
                try {
                    if( Files.size( file ) == 0 ) {
                        return FileVisitResult.SKIP_SUBTREE; // Does not collect file
                    }
                }
                catch( final IOException e ) {
                    // Add Notification for GUI
                    return FileVisitResult.SKIP_SUBTREE; // Does not collect file
                }

                if( logger.isTraceEnabled() ) {
                    logger.trace( "visitFile:" + file );
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed( final Path file, final IOException exc ) throws IOException
            {
                logger.warn( "*** visitFileFailed * Can not visit " + file + " cause " + exc );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory( final Path dir, final IOException exc ) throws IOException
            {
                if( exc != null ) {
                    logger.warn( "*** postVisitDirectory * Can not visit " + dir + " cause " + exc );
                }
                return FileVisitResult.CONTINUE;
            }};
    }

}
