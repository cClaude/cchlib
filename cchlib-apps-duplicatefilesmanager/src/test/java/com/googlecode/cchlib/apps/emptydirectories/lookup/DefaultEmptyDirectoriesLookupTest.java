package com.googlecode.cchlib.apps.emptydirectories.lookup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.ScanIOException;
import com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup;
import com.googlecode.cchlib.util.CancelRequestException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultEmptyDirectoriesLookupTest
{
    private static final Logger logger = Logger.getLogger( DefaultEmptyDirectoriesLookupTest.class );

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    private LoggerEmptyDirectoriesListener emptyDirectoriesListener;
    private List<Path>                     pathToCleanUpList = new ArrayList<>();
    private List<Path>                     isEmptyList       = new ArrayList<>();
    private List<Path>                     couldBeEmptyList  = new ArrayList<>();
    private Path rootPath;

    @Before
    public void setUp() throws Exception
    {
        this.emptyDirectoriesListener = new LoggerEmptyDirectoriesListener();

        this.rootPath = Files.createTempDirectory( getClass().getSimpleName() );
        pathToCleanUpList.add( rootPath );

        // emptyPath1 (is empty, should be deleted)
        Path dirPath1 = rootPath.resolve( "empty1" );
        Files.createDirectory( dirPath1 );
        isEmptyList.add( dirPath1 );

        // path2
        // +--- folder2_1
        //      +--- file2_1_1
        // +--- couldBeEmpty2_2
        //      +--- couldBeEmpty2_2_1
        //           +--- empty2_2_1_1
        Path dirPath2   = rootPath.resolve( "dirPath2" );
        Files.createDirectory( dirPath2 );
        pathToCleanUpList.add( dirPath2 );

        Path folder2_1 = dirPath2.resolve( "folder2_1" );
        Files.createDirectory( folder2_1 );
        pathToCleanUpList.add( folder2_1 );

        Path file2_1_1 = folder2_1.resolve( "file2_1_1" );
        Files.copy( getSomeData(), file2_1_1 );
        pathToCleanUpList.add( file2_1_1 );

        Path dirPath2_2 = dirPath2.resolve( "couldBeEmpty2_2" );
        Files.createDirectory( dirPath2_2 );
        couldBeEmptyList.add( dirPath2_2 );

        Path dirPath2_2_1 = dirPath2_2.resolve( "couldBeEmpty2_2_1" );
        Files.createDirectory( dirPath2_2_1 );
        couldBeEmptyList.add( dirPath2_2_1 );

        Path dirPath2_2_1_1 = dirPath2_2_1.resolve( "empty2_2_1_1" );
        Files.createDirectory( dirPath2_2_1_1 );
        isEmptyList.add( dirPath2_2_1_1 );
    }

    private InputStream getSomeData()
    {
        final String data = "Hello world";
        return new ByteArrayInputStream( data.getBytes() );
    }

    @After
    public void tearDown() throws IOException
    {
    }

    @Test
    public void testLookup() throws CancelRequestException, IOException, ScanIOException
    {
        EmptyDirectoriesLookup edl = new DefaultEmptyDirectoriesLookup( rootPath );

        edl.addListener( emptyDirectoriesListener );
        edl.lookup();

        for( Path p : isEmptyList ) {
            logger.trace( "isEmptyList: " + p );
            }
        for( Path p : couldBeEmptyList ) {
            logger.trace( "couldBeEmptyList: " + p );
            }

        Iterator<EmptyFolder> iter =  emptyDirectoriesListener.getEmptyFolderList().iterator();

        while( iter.hasNext() ) {
            final EmptyFolder ef   = iter.next();
            final Path        path = ef.getPath();

            logger.info( "check pass1: " + ef + " : path=" + path );

            if( isEmptyList.contains( path ) ) {
                assertTrue( ef.isEmpty() );
                Files.delete( path );
                iter.remove();
                }
            else if( couldBeEmptyList.contains( path ) ) {
                assertFalse( ef.isEmpty() );
                }
            else {
                logger.warn( "Unexpected entry: " + ef );

                File[] subFiles = ef.getFile().listFiles();
                logger.warn( "subFiles=" + subFiles );

                if( subFiles != null ) {
                    logger.warn( "subFiles.length=" + subFiles.length );
                    }

                fail( "Unexpected entry: " + ef );
                }
            }

        iter =  emptyDirectoriesListener.getEmptyFolderList().iterator();

        while( iter.hasNext() ) {
            EmptyFolder ef   = iter.next();
            Path        path = ef.getPath();

            logger.info( "check pass2: " + ef );

            if( isEmptyList.contains( path ) ) {
                fail( "Should be delete: " + ef );
                }
            else if( couldBeEmptyList.contains( path ) ) {
                assertFalse( ef.isEmpty() );
                ef.check();
                assertTrue( ef.isEmpty() );

                Files.delete( path );
                iter.remove();
                }
            else {
                fail( "Unexpected entry: " + ef );
                }
            }

        assertEquals( 0, emptyDirectoriesListener.getEmptyFolderList().size() );

        logger.info( "done" );
    }

    private static class LoggerEmptyDirectoriesListener implements EmptyDirectoriesListener
    {
        private List<EmptyFolder> emptyFolderList = new ArrayList<>();

        public List<EmptyFolder> getEmptyFolderList()
        {
            return emptyFolderList;
        }

        @Override
        public boolean isCancel()
        {
            return false;
        }

        @Override
        public void newEntry( EmptyFolder emptyFolder )
        {
            logger.info( "emptyFolder: " + emptyFolder );

            File f = emptyFolder.getPath().toFile();
            assertTrue( "Not a directory", f.isDirectory() );

            File[] files = f.listFiles();
            assertNotNull( "Not a valid directory", files );

            if( emptyFolder.isEmpty() ) {
                logger.warn( "Warn found " + files.length + " file(s)" );

                for( File file : files ) {
                    logger.warn( "> " + file + " (" + file.length() + ") D:" + file.isDirectory() );
                    }
                assertEquals( "Not empty: " + emptyFolder, 0, files.length );
                }
            else {
                assertTrue( "Is empty", files.length > 0);

                for( File file : files ) {
                    assertTrue( "found a none directory object: " + file, file.isDirectory() );
                    }
                }

            emptyFolderList.add( emptyFolder );
        }

        @Override
        public void findStarted()
        {
            logger.info( "findStarted()" );
        }

        @Override
        public void findDone()
        {
            logger.info( "findDone()" );
        }
    }

}
