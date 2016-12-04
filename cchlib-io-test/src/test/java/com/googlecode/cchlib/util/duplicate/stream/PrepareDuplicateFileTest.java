package com.googlecode.cchlib.util.duplicate.stream;

import static org.fest.assertions.api.Assertions.assertThat;
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
import org.junit.Test;
import com.googlecode.cchlib.util.duplicate.DuplicateHelpers;

public class PrepareDuplicateFileTest {
    private static final Logger LOGGER = Logger.getLogger( PrepareDuplicateFileTest.class );

    private static final boolean IGNORE_EMPTY_FILES = true;
    private static final int     MAX_DEPTH          = Integer.MAX_VALUE;

    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Test // could take to much time / memory
    public void test_using_walk_and_stream() throws IOException
    {
        final Path startPath = StartPathsHelper.getStartPath();
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

    @Test // could take to much time / memory
    public void test_using_walker() throws IOException
    {
        final Path[] startPaths = StartPathsHelper.getStartPaths();

        final Set<FileVisitOption>  options = EnumSet.noneOf( FileVisitOption.class );
        final FileVisitor<Path>     visitor = FileVisitorHelper.newFileVisitor( getLogger() );

        final Map<Long, Set<File>> files = DuplicateFileBuilder.createFromFileVisitor( visitor, options, MAX_DEPTH, IGNORE_EMPTY_FILES, startPaths ).compute();

        LOGGER.info( "files.size() = " + files.size() );

        files.entrySet().forEach( entry -> {
            final long fileLength = entry.getKey().longValue();

            entry.getValue().forEach( f -> {
                if( fileLength != f.length() ) {
                    LOGGER.warn( "File length has changed : " + f + " + " + fileLength + '/' + f.length() );
                }

                assertThat( fileLength ).isGreaterThan( 0L );
            } );
        } );

        DuplicateHelpers.removeNonDuplicate( files );

        files.entrySet().forEach( entry -> {
            assertThat( entry.getKey() )
                .describedAs( "No empty files : " + entry )
                .isGreaterThan( 0L );
            assertThat( entry.getValue().size() )
                .as( "Not a duplicate : " + entry )
                .isGreaterThan( 1 );
        } );

    }
}
