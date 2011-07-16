package cx.ath.choisnet.io.testcase;

import java.util.List;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.IOHelper;
import cx.ath.choisnet.io.FileIterator;
import junit.framework.TestCase;

/**
 * 
 * @author Claude CHOISNET
 *
 */
public class FileIteratorTest extends TestCase 
{
    final private static Logger slogger = Logger.getLogger(FileIteratorTest.class);

    public static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );
    //public static final File ROOT_FILE = new File( "C:/" );
    public static final File ROOT_FILE = TEMP_DIR_FILE;
    public static final File SYSTEM_ROOT_FILE = new File( "/" );
    public static final File NOT_EXIST_FILE =  new File( "thisFileShoundNotExists" );
    private File currentFile;
    
//    public static final int ITER_GETCOUNT = 19;
//    public static final int ITER_XX = 20;
    
    public void setUp() throws java.io.IOException
    {
        currentFile = new File( "." ).getCanonicalFile();
    }
    
    public void testNotExist()
    {
        try {
            new FileIterator( NOT_EXIST_FILE );
           
            fail( "Should crash here" );
            }
        catch( IllegalArgumentException e ) {
            slogger.info( "Ok: does not exist" );
        }
    }
    /* OLD VERSION
    public void testNotExist()
    {
        Iterator<File> iter = new FileIterator( NOT_EXIST_FILE );

        if( iter.hasNext() ) {
            StringBuilder msg = new StringBuilder()
                    .append( "Hum! : File '" )
                    .append( NOT_EXIST_FILE )
                    .append( " should not exist, so file: " )
                    .append( iter.next() )
                    .append( "' should not exist..." );
            
            slogger.error( msg );
            fail(msg.toString());
        }

        if( iter.hasNext() ) {
            String msg = "*** error: this Iterator should be empty";
            
            slogger.error( (new StringBuilder())
                    .append( "*** error: " )
                    .append( iter.next() )
                    .toString()
                    );
            fail( msg );
        }
    }
    */
    public void testFileIteratorCounter()
    {
        File rootFile = TEMP_DIR_FILE;
        FileIterator fi = new FileIterator( rootFile );
        int countFile = 0;
        int countDir = 0;
        int countOther = 0;
        final int displayMax = 5;
        int displayCount = 0;
        
        slogger.info( "---------------------" );
        slogger.info( rootFile );
        slogger.info( "* testFileIteratorCounter( <<no filter>> )" );
        slogger.info( "---------------------" );
        long begin  = System.currentTimeMillis();
        for( File f : fi ) {
            if( displayCount++<displayMax ) {
                slogger.info( String.format( "f %d:%s\n", displayCount, f ) );
            }
            if( f.isFile() ) {
                countFile++;
            }
            else if( f.isDirectory() ) {
                countDir++;
            }
            else {
                countOther++; // well
                slogger.info( "unkown file type: " + f );
            }
        }
        long end = System.currentTimeMillis();
        slogger.info( "---------------------" );
        slogger.info( "dir        : " + rootFile );
        slogger.info( "file  count: " + countFile );
        slogger.info( "dir   count: " + countDir );
        slogger.info( "other count: " + countOther );
        slogger.info( "ms         : " + (end-begin) );
        slogger.info( "---------------------" );
    }
    
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
        
        slogger.info( "---------------------" );
        slogger.info( rootFile );
        slogger.info( "* testFileIteratorFileFilter( *.java )" );
        slogger.info( "---------------------" );
        long begin  = System.currentTimeMillis();
        for( File f : fi ) {
            if( displayCount++<displayMax ) {
                slogger.info( String.format( "f %d:%s\n", displayCount, f ) );
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
                slogger.info( "unkown file type: " + f );
            }
        }
        long end = System.currentTimeMillis();
        slogger.info( "---------------------" );
        slogger.info( "dir        : " + rootFile );
        slogger.info( "file  count: " + countFile );
        slogger.info( "dir   count: " + countDir );
        slogger.info( "other count: " + countOther );
        slogger.info( "ms         : " + (end-begin) );
        slogger.info( "---------------------" );
        
    }

    public void testDirStruct() throws IOException
    {
        File dirRootFile = new File(TEMP_DIR_FILE, getClass().getName());

        IOHelper.deleteTree(dirRootFile);
        
        boolean res = dirRootFile.exists();
        assertFalse( "Already exists (Can't delete): " + dirRootFile, res);
        
        res = dirRootFile.mkdirs();
        assertTrue( "Can't mkdirs(): " + dirRootFile, res);

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
        
        // TODO: check if it should be in iterator or not ! 
        // allFiles.add(dirRootFile);
        
        for( File d : dirs ) {
            res = d.mkdirs();
            assertTrue( "Can't mkdirs(): " + d, res);
            allFiles.add(d);
        }
        for( File f : files ) {
            IOHelper.toFile(f,f.getPath());
            allFiles.add(f);
        }
        
        List<File> notFoundInFileIterator = new ArrayList<File>(allFiles);
        List<File> foundInFileIterator    = new ArrayList<File>();
        
        FileIterator fi = new FileIterator( dirRootFile );
        
        for( File f : fi ) {
            foundInFileIterator.add( f );
            
            boolean oldFound = notFoundInFileIterator.remove( f );
            assertTrue( "File should not be here: " + f, oldFound);
        }
        
        slogger.info( "allFiles # " + allFiles.size() );
        slogger.info( "foundInFileIterator # " + foundInFileIterator.size() );
        slogger.info( "notFoundInFileIterator # " + notFoundInFileIterator.size() );

        for( File f : notFoundInFileIterator ) {
            slogger.info( "  > not found by Iterator: " + f );
        }
        
        assertEquals("File count not equals !",allFiles.size(),foundInFileIterator.size());
        assertEquals("Somes files not founds !",0,notFoundInFileIterator.size());
        
        // cleanup !
        IOHelper.deleteTree(dirRootFile);
        
        res = dirRootFile.exists();
        assertFalse( "Can't delete(): " + dirRootFile, res);
    }
}
