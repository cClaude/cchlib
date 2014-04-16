package com.googlecode.cchlib.util.duplicate.stream;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.log4j.Logger;

final class Helper {
    private static final Logger LOGGER = Logger.getLogger( Helper.class );

    private Helper() {
        // all static
    }

    public static FileVisitor<Path> newFileVisitor()
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

                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "visitFile:" + file );
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed( final Path file, final IOException exc ) throws IOException
            {
                LOGGER.warn( "*** visitFileFailed * Can not visit " + file + " cause " + exc );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory( final Path dir, final IOException exc ) throws IOException
            {
                if( exc != null ) {
                    LOGGER.warn( "*** postVisitDirectory * Can not visit " + dir + " cause " + exc );
                }
                return FileVisitResult.CONTINUE;
            }};
    }

//    public static MessageDigestFileBuilder newMessageDigestFileBuilder( final String algorithm, final int bufferSize ) throws NoSuchAlgorithmException {
//        return ( ) -> new MessageDigestFile( algorithm, bufferSize );
//    }
}
