package cx.ath.choisnet.io;

import java.util.List;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;

/**
 * @deprecated
 */
@Deprecated
public class FileIteratorTest
{
    private static final Logger LOGGER = Logger.getLogger(FileIteratorTest.class);

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
            FileIterator fi = new FileIterator( NOT_EXIST_FILE );

            Assert.fail( "Should crash here" );
            }
        catch( IllegalArgumentException e ) {
            LOGGER.info( "Ok: does not exist" );
        }
    }

    @Test
    public void testFileIteratorCounter()
    {
        File rootFile = TEMP_DIR_FILE;
        FileIterator fi = new FileIterator( rootFile );
        int countFile = 0;
        int countDir = 0;
        int countOther = 0;
        final int displayMax = 5;
        int displayCount = 0;

        LOGGER.info( "---------------------" );
        LOGGER.info( rootFile );
        LOGGER.info( "* testFileIteratorCounter( <<no filter>> )" );
        LOGGER.info( "---------------------" );
        long begin  = System.currentTimeMillis();
        for( File f : fi ) {
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
        long end = System.currentTimeMillis();
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
        File rootFile = currentFile;
        FileFilter fileFilter = new FileFilter(){
            @Override
            public boolean accept( File f )
            {
                return f.getName().endsWith( ".java" );
            }
        };
        FileIterator fi = new FileIterator( rootFile, fileFilter );
        int countFile = 0;
        int countDir = 0;
        int countOther = 0;
        final int displayMax = 5;
        int displayCount = 0;

        LOGGER.info( "---------------------" );
        LOGGER.info( rootFile );
        LOGGER.info( "* testFileIteratorFileFilter( *.java )" );
        LOGGER.info( "---------------------" );
        long begin  = System.currentTimeMillis();
        for( File f : fi ) {
            if( displayCount++<displayMax ) {
                LOGGER.info( String.format( "f %d:%s\n", displayCount, f ) );
            }

            Assert.assertTrue( "file should be a java file :" + f, fileFilter.accept( f ) );

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
        long end = System.currentTimeMillis();
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
        File dirRootFile = new File(TEMP_DIR_FILE, getClass().getName());

        IOHelper.deleteTree(dirRootFile);

        boolean res = dirRootFile.exists();
        Assert.assertFalse( "Already exists (Can't delete): " + dirRootFile, res);

        res = dirRootFile.mkdirs();
        Assert.assertTrue( "Can't mkdirs(): " + dirRootFile, res);

        File[] dirs = {
                new File(dirRootFile, "dir1"),
                new File(dirRootFile, "dir2"),
                new File(dirRootFile, "dir2/dir21"),
                new File(dirRootFile, "dir2/dir22"),
                new File(dirRootFile, "dir2/dir23"),
        };
        File[] files = {
                new File(dirRootFile, "a.txt"),
                new File(dirRootFile, "ab"),
                new File(dirRootFile, "dir2/dir21/b.txt"),
                new File(dirRootFile, "dir2/dir21/b.tmp"),
        };

        List<File> allFiles = new ArrayList<File>();

        // TO DO: check if it should be in iterator or not !
        // allFiles.add(dirRootFile);

        for( File d : dirs ) {
            res = d.mkdirs();
            Assert.assertTrue( "Can't mkdirs(): " + d, res);
            allFiles.add(d);
        }
        for( File f : files ) {
            IOHelper.toFile(f.getPath(),f);
            allFiles.add(f);
        }

        List<File> notFoundInFileIterator = new ArrayList<File>(allFiles);
        List<File> foundInFileIterator    = new ArrayList<File>();

        FileIterator fi = new FileIterator( dirRootFile );

        for( File f : fi ) {
            foundInFileIterator.add( f );

            boolean oldFound = notFoundInFileIterator.remove( f );
            Assert.assertTrue( "File should not be here: " + f, oldFound);
        }

        LOGGER.info( "allFiles # " + allFiles.size() );
        LOGGER.info( "foundInFileIterator # " + foundInFileIterator.size() );
        LOGGER.info( "notFoundInFileIterator # " + notFoundInFileIterator.size() );

        for( File f : notFoundInFileIterator ) {
            LOGGER.info( "  > not found by Iterator: " + f );
        }

        Assert.assertEquals("File count not equals !",allFiles.size(),foundInFileIterator.size());
        Assert.assertEquals("Somes files not founds !",0,notFoundInFileIterator.size());

        // cleanup !
        IOHelper.deleteTree(dirRootFile);

        res = dirRootFile.exists();
        Assert.assertFalse( "Can't delete(): " + dirRootFile, res);
    }
}
