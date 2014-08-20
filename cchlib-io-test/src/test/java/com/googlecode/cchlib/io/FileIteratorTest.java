package com.googlecode.cchlib.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("boxing")
public class FileIteratorTest
{
    final private static Logger LOGGER = Logger.getLogger(FileIteratorTest.class);

    public static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );
    public static final File ROOT_FILE = TEMP_DIR_FILE;
    public static final File SYSTEM_ROOT_FILE = new File( "/" );
    public static final File NOT_EXIST_FILE =  new File( "thisFileShoundNotExists" );
    private File currentFile;

    @Before
    public void setUp() throws java.io.IOException
    {
        currentFile = new File( "." ).getCanonicalFile();
    }

    @Test
    public void testNotExist()
    {
        try {
            @SuppressWarnings("unused")
            final
            FileIterator iter = new FileIterator( NOT_EXIST_FILE );

            fail( "Should crash here" );
            }
        catch( final IllegalArgumentException e ) { // $codepro.audit.disable logExceptions
            LOGGER.info( "Ok: does not exist" );
        }
    }


    @Test
    public void testFileIteratorCounter()
    {
        final File rootFile = TEMP_DIR_FILE;
        final FileIterator fi = new FileIterator( rootFile );
        int countFile = 0;
        int countDir = 0;
        int countOther = 0;
        final int displayMax = 5;
        int displayCount = 0;

        LOGGER.info( "---------------------" );
        LOGGER.info( rootFile );
        LOGGER.info( "* testFileIteratorCounter( <<no filter>> )" );
        LOGGER.info( "---------------------" );
        final long begin  = System.currentTimeMillis();

        while( fi.hasNext() ) {
            final File f = fi.next();
            if( displayCount++<displayMax ) {
                LOGGER.info( String.format( "f %d:%s\n", displayCount, f ) );
            }
            if( f.isFile() ) {
                countFile++;
            }
            else if( f.isDirectory() ) {
                countDir++;
            }
            else {
                countOther++; // well
                LOGGER.info( "unkown file type: " + f );
            }
        }
        final long end = System.currentTimeMillis();
        LOGGER.info( "---------------------" );
        LOGGER.info( "dir        : " + rootFile );
        LOGGER.info( "file  count: " + countFile );
        LOGGER.info( "dir   count: " + countDir );
        LOGGER.info( "other count: " + countOther );
        LOGGER.info( "ms         : " + (end-begin) );
        LOGGER.info( "---------------------" );
    }

    @Test
    public void testFileIteratorFileFilter()
    {
        final File rootFile = currentFile;
        final FileFilter fileFilter = f -> f.getName().endsWith( ".java" );
        final FileIterator fi = new FileIterator( rootFile, fileFilter );
        int countFile = 0;
        int countDir = 0;
        int countOther = 0;
        final int displayMax = 5;
        int displayCount = 0;

        LOGGER.info( "---------------------" );
        LOGGER.info( rootFile );
        LOGGER.info( "* testFileIteratorFileFilter( *.java )" );
        LOGGER.info( "---------------------" );
        final long begin  = System.currentTimeMillis();

        while( fi.hasNext() ) {
            final File f = fi.next();
            if( displayCount++<displayMax ) {
                LOGGER.info( String.format( "f %d:%s\n", displayCount, f ) );
            }

            assertTrue( "file should be a java file :" + f, fileFilter.accept( f ) );

            if( f.isFile() ) {
                countFile++;
            }
            else if( f.isDirectory() ) {
                countDir++;
            }
            else {
                countOther++; // well
                LOGGER.info( "unkown file type: " + f );
            }
        }
        final long end = System.currentTimeMillis();
        LOGGER.info( "---------------------" );
        LOGGER.info( "dir        : " + rootFile );
        LOGGER.info( "file  count: " + countFile );
        LOGGER.info( "dir   count: " + countDir );
        LOGGER.info( "other count: " + countOther );
        LOGGER.info( "ms         : " + (end-begin) );
        LOGGER.info( "---------------------" );

    }

    @Test
    public void testDirStruct() throws IOException
    {
        final File dirRootFile = new File(TEMP_DIR_FILE, getClass().getName());

        IOHelper.deleteTree(dirRootFile);

        boolean res = dirRootFile.exists();
        assertFalse( "Already exists (Can't delete): " + dirRootFile, res);

        res = dirRootFile.mkdirs();
        assertTrue( "Can't mkdirs(): " + dirRootFile, res);

        final File[] dirs = {
                new File(dirRootFile, "dir1"),
                new File(dirRootFile, "dir2"),
                new File(dirRootFile, "dir2/dir21"),
                new File(dirRootFile, "dir2/dir22"),
                new File(dirRootFile, "dir2/dir23"),
        };
        final File[] files = {
                new File(dirRootFile, "a.txt"),
                new File(dirRootFile, "ab"),
                new File(dirRootFile, "dir2/dir21/b.txt"),
                new File(dirRootFile, "dir2/dir21/b.tmp"),
        };

        final List<File> allFiles = new ArrayList<>();

        // TODO: check if it should be in iterator or not !
        // allFiles.add(dirRootFile);

        for( final File d : dirs ) {
            res = d.mkdirs();
            assertTrue( "Can't mkdirs(): " + d, res);
            allFiles.add(d);
            }

        for( final File f : files ) {
            IOHelper.toFile( f.getPath(), f );
            allFiles.add( f );
            }

        final List<File> notFoundInFileIterator = new ArrayList<>(allFiles);
        final List<File> foundInFileIterator    = new ArrayList<>();

        final FileIterator fi = new FileIterator( dirRootFile );

        while( fi.hasNext() ) {
            final File f = fi.next();
            foundInFileIterator.add( f );

            final boolean oldFound = notFoundInFileIterator.remove( f );
            assertTrue( "File should not be here: " + f, oldFound);
        }

        LOGGER.info( "allFiles # " + allFiles.size() );
        LOGGER.info( "foundInFileIterator # " + foundInFileIterator.size() );
        LOGGER.info( "notFoundInFileIterator # " + notFoundInFileIterator.size() );

        for( final File f : notFoundInFileIterator ) {
            LOGGER.info( "  > not found by Iterator: " + f );
        }

        assertEquals( "File count not equals !", allFiles.size(), foundInFileIterator.size() );
        assertEquals( "Somes files not founds !", 0, notFoundInFileIterator.size() );

        // cleanup !
        IOHelper.deleteTree( dirRootFile );

        res = dirRootFile.exists();
        assertFalse( "Can't delete(): " + dirRootFile, res);
    }
}
