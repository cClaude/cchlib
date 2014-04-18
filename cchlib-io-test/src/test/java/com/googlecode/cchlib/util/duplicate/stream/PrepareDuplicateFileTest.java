package com.googlecode.cchlib.util.duplicate.stream;

import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.util.duplicate.DuplicateHelpers;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class PrepareDuplicateFileTest {
    private static final Logger LOGGER = Logger.getLogger( PrepareDuplicateFileTest.class );

    @Test
    public void test_using_walk_and_stream() throws IOException
    {
        final Path startPath = FileHelper.getUserHomeDirFile().toPath();
        final FileVisitOption[] options = {};

        try (final Stream<Path> streamPath = Files.walk( startPath, options )) {
            final PrepareDuplicateFile dff = DuplicateFileBuilder.createFromStreamOfPaths( streamPath, true );

            final Map<Long, Set<File>> files = dff.compute();

            LOGGER.info( "files.size() = " + files.size() );
            LOGGER.info( "files = " + files );
        }
        catch( final UncheckedIOException e ) {
            final IOException cause = e.getCause();
            if( cause instanceof AccessDeniedException ) {
                LOGGER.warn( "Abort since can not read file " + cause.getMessage() );
            } else {
                throw e;
            }
        }
    }

    @Test
    public void test_using_walker() throws IOException
    {
        final Path[] startPaths = { FileHelper.getUserHomeDirFile().toPath() };
        final Set<FileVisitOption> options = EnumSet.noneOf( FileVisitOption.class );
        final int maxDepth = Integer.MAX_VALUE;
        final FileVisitor<Path> visitor = Helper.newFileVisitor();

        final Map<Long, Set<File>> files = DuplicateFileBuilder.createFromFileVisitor( visitor, options, maxDepth, startPaths ).compute();

        LOGGER.info( "files.size() = " + files.size() );

        files.entrySet().forEach( entry -> {
            final long fileLength = entry.getKey().longValue();

            entry.getValue().forEach( f -> {
                if( fileLength != f.length() ) {
                    LOGGER.warn( "File length has changed : " + f + " + " + fileLength + '/' + f.length() );
                }
                Assert.assertTrue( "No empty files", fileLength > 0 );
            } );
        } );

        DuplicateHelpers.removeNonDuplicate( files );

        files.entrySet().forEach( entry -> {
            Assert.assertTrue( "No empty files : " + entry, entry.getKey().longValue() > 0 );
            Assert.assertTrue( "Not a duplicate : " + entry, entry.getValue().size() > 1 );
        } );

    }
}
