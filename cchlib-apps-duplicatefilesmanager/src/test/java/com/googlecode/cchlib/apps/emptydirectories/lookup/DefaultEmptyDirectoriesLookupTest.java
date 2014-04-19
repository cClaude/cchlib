package com.googlecode.cchlib.apps.emptydirectories.lookup;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultEmptyDirectoriesLookupTest
{
    private static final Logger LOGGER = Logger.getLogger( DefaultEmptyDirectoriesLookupTest.class );

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    private LoggerEmptyDirectoriesListener emptyDirectoriesListener;
    private final List<Path>                     pathToCleanUpList = new ArrayList<>();
    private final List<Path>                     isEmptyList       = new ArrayList<>();
    private final List<Path>                     couldBeEmptyList  = new ArrayList<>();
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
            LOGGER.trace( "isEmptyList: " + p );
            }
        for( Path p : couldBeEmptyList ) {
            LOGGER.trace( "couldBeEmptyList: " + p );
            }

        Iterator<EmptyFolder> iter =  emptyDirectoriesListener.getEmptyFolderList().iterator();

        while( iter.hasNext() ) {
            final EmptyFolder ef   = iter.next();
            final Path        path = ef.getPath();

            LOGGER.info( "check pass1: " + ef + " : path=" + path );

            if( isEmptyList.contains( path ) ) {
                Assert.assertTrue( ef.isEmpty() );
                Files.delete( path );
                iter.remove();
                }
            else if( couldBeEmptyList.contains( path ) ) {
                Assert.assertFalse( ef.isEmpty() );
                }
            else {
                LOGGER.warn( "Unexpected entry: " + ef );

                File[] subFiles = ef.getFile().listFiles();
                LOGGER.warn( "subFiles=" + subFiles );

                if( subFiles != null ) {
                    LOGGER.warn( "subFiles.length=" + subFiles.length );
                    }

                Assert.fail( "Unexpected entry: " + ef );
                }
            }

        iter =  emptyDirectoriesListener.getEmptyFolderList().iterator();

        while( iter.hasNext() ) {
            EmptyFolder ef   = iter.next();
            Path        path = ef.getPath();

            LOGGER.info( "check pass2: " + ef );

            if( isEmptyList.contains( path ) ) {
                Assert.fail( "Should be delete: " + ef );
                }
            else if( couldBeEmptyList.contains( path ) ) {
                Assert.assertFalse( ef.isEmpty() );
                ef.check();
                Assert.assertTrue( ef.isEmpty() );

                Files.delete( path );
                iter.remove();
                }
            else {
                Assert.fail( "Unexpected entry: " + ef );
                }
            }

        Assert.assertEquals( 0, emptyDirectoriesListener.getEmptyFolderList().size() );

        LOGGER.info( "done" );
    }

    private static class LoggerEmptyDirectoriesListener implements EmptyDirectoriesListener
    {
        private final List<EmptyFolder> emptyFolderList = new ArrayList<>();

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
            LOGGER.info( "emptyFolder: " + emptyFolder );

            File f = emptyFolder.getPath().toFile();
            Assert.assertTrue( "Not a directory", f.isDirectory() );

            File[] files = f.listFiles();
            Assert.assertNotNull( "Not a valid directory", files );

            if( emptyFolder.isEmpty() ) {
                LOGGER.warn( "Warn found " + files.length + " file(s)" );

                for( File file : files ) {
                    LOGGER.warn( "> " + file + " (" + file.length() + ") D:" + file.isDirectory() );
                    }
                Assert.assertEquals( "Not empty: " + emptyFolder, 0, files.length );
                }
            else {
                Assert.assertTrue( "Is empty", files.length > 0);

                for( File file : files ) {
                    Assert.assertTrue( "found a none directory object: " + file, file.isDirectory() );
                    }
                }

            emptyFolderList.add( emptyFolder );
        }

        @Override
        public void findStarted()
        {
            LOGGER.info( "findStarted()" );
        }

        @Override
        public void findDone()
        {
            LOGGER.info( "findDone()" );
        }
    }

}
