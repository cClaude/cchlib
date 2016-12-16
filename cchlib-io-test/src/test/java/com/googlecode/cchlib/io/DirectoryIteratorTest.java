package com.googlecode.cchlib.io;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DirectoryIteratorTest
{
    final private static Logger LOGGER = Logger.getLogger( DirectoryIteratorTest.class );

    public static final File TEMP_DIR_FILE_    = FileHelper.getTmpDirFile();
    public static final File SYSTEM_ROOT_FILE = new File( "/" );
    public static final File NOT_EXIST_FILE   = new File( "thisFileShoundNotExists" );

    @Test
    public void testNotExist()
    {
        final Iterator<File> iter = new DirectoryIterator( NOT_EXIST_FILE );

        if( iter.hasNext() ) {
            final String msg = "Hum! : File '" + NOT_EXIST_FILE
                    + " should not exist, so file: " + iter.next()
                    + "' should not exist...";

            LOGGER.error( msg );
            fail( msg );
        }

        if( iter.hasNext() ) {
            final String msg = "*** error: this Iterator should be empty";

            LOGGER.error( "*** error: " + iter.next() );
            fail( msg );
        }
    }

    @Test
    public void testDirectoryIterator()
    {
        final long              begin0      = System.currentTimeMillis();
        final File              rootFile     = FileHelper.getTmpDirFile();
        final DirectoryIterator dirsIterator = new DirectoryIterator( rootFile );

        final long end0 = System.currentTimeMillis();

        LOGGER.info( "---------------------" );
        LOGGER.info( "Root    : "  + rootFile );
        LOGGER.info( "ms      : "  + (end0 - begin0) );
        LOGGER.info( "---------------------" );

        final long begin1 = System.currentTimeMillis();

        int  fCount = 0;
        for( final File dirFile : dirsIterator ) {
            fCount++;
            LOGGER.info( "dir "  + dirFile );

            assertThat( dirFile )
                .as( "Is not a directory : " + dirFile  )
                .isDirectory();
        }

        final long end1 = System.currentTimeMillis();

        LOGGER.info( "---------------------" );
        LOGGER.info( "Count   : "  + fCount );
        LOGGER.info( "ms      : "  + (end1-begin1) );
        LOGGER.info( "---------------------" );
    }

    @Test
    public void testDirStruct() throws IOException
    {
        final File dirRootFile = FileHelper.createTempDir();

        IOHelper.deleteTree( dirRootFile);

        assertThat( dirRootFile )
            .as( "Already exists (Can't delete): " + dirRootFile )
            .doesNotExist();

        boolean res = dirRootFile.mkdirs();

        assertThat( res )
            .as( "Can't mkdirs(): " + dirRootFile )
            .isTrue();

        final File[] dirs = {
            new File( dirRootFile, "dir1" ),
            new File( dirRootFile, "dir2" ),
            new File( dirRootFile, "dir2/dir21" ),
            new File( dirRootFile, "dir2/dir22" ),
            new File( dirRootFile, "dir2/dir23" ),
        };

        final File[] files = {
            new File( dirRootFile, "a.txt" ),
            new File( dirRootFile, "ab" ),
            new File( dirRootFile, "dir2/dir21/b.txt" ),
            new File( dirRootFile, "dir2/dir21/b.tmp" ),
        };

        final List<File> allFiles = new ArrayList<>();

        allFiles.add(dirRootFile);

        for( final File dir : dirs ) {
            res = dir.mkdirs();

            assertThat( res )
                .as( "Can't mkdirs(): " + dir )
                .isTrue();
            allFiles.add( dir );
            }

        for( final File file : files ) {
            // Build some files and put something in it
            IOHelper.toFile( file.getPath(), file );
            }

        final List<File> notFoundInFileIterator = new ArrayList<>( allFiles );
        final List<File> foundInFileIterator    = new ArrayList<>();

        final DirectoryIterator di = new DirectoryIterator( dirRootFile );

        for( final File dir : di ) {
            assertThat( dir )
                .as( "Is not a directory : " + dir )
                .isDirectory();

            foundInFileIterator.add( dir );

            final boolean oldFound = notFoundInFileIterator.remove( dir );

            assertThat( oldFound )
                .as( "File should not be here: " + dir )
                .isTrue();
        }

        LOGGER.info( "allFiles # " + allFiles.size() );
        LOGGER.info( "foundInFileIterator # " + foundInFileIterator.size() );
        LOGGER.info( "notFoundInFileIterator # " + notFoundInFileIterator.size() );

        for( final File file : notFoundInFileIterator ) {
            LOGGER.info( "  > not found by Iterator: " + file );
        }

        assertThat( foundInFileIterator )
            .as( "File count not equals !" )
            .hasSize( allFiles.size() );

        assertThat( notFoundInFileIterator )
            .as( "Somes files not founds !" )
            .hasSize( 0 );

        //
        // Test FileFilter !
        //
        final FileFilter        dirFF = (final File f) -> f.getName().endsWith( "2" );
        final DirectoryIterator diFF  = new DirectoryIterator(
                dirRootFile,
                dirFF
                );
        int diFFcount = 0;

        for( final File file : diFF ) {
            LOGGER.info( String.format( ">%s\n", file ) );
            diFFcount++;
        }

        assertThat( diFFcount )
            .as( "Must find 2 directories (+1 rootdir)" )
            .isEqualTo( 3 );

        // cleanup !
        IOHelper.deleteTree( dirRootFile );

        assertThat( dirRootFile )
            .as( "Can't delete(): " + dirRootFile )
            .doesNotExist();
    }
}
