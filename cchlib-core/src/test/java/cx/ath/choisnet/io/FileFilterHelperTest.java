package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
*/
public class FileFilterHelperTest
{
    final private static Logger logger = Logger.getLogger(FileFilterHelperTest.class);
    public static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );

    @Test
    public void test_directoryFileFilter()
    {
        logger.info( "test_directoryFileFilter()" );
        File		dir = new File( TEMP_DIR_FILE, this.getClass().getSimpleName() );
        boolean 	isDirCreated = dir.mkdir();
        Assert.assertTrue( isDirCreated );

        FileFilter 	ff 	= FileFilterHelper.directoryFileFilter();

        boolean isDir = ff.accept( dir );
        Assert.assertTrue( isDir );

        boolean isDirDeleted = dir.delete();
        Assert.assertTrue( isDirDeleted );
    }

    @Test
    public void test_fileFileFilter() throws IOException
    {
        File		file = File.createTempFile( this.getClass().getSimpleName(), ".tmp" );
        boolean 	isFileCreated = file.isFile();
        Assert.assertTrue( isFileCreated );

        FileFilter 	ff 	= FileFilterHelper.fileFileFilter();

        boolean isFile = ff.accept( file );
        Assert.assertTrue( isFile );

        boolean isFileDeleted = file.delete();
        Assert.assertTrue( isFileDeleted );
    }

    @Test
    public void test_trueFileFilter()
    {
        FileFilter 	ff 	= FileFilterHelper.trueFileFilter();

        Assert.assertTrue( ff.accept( null ) );
    }

    @Test
    public void test_falseFileFilter()
    {
        FileFilter 	ff 	= FileFilterHelper.falseFileFilter();

        Assert.assertFalse( ff.accept( null ) );
    }

    @Test
    public void test_not()
    {
        FileFilter 	ff 	= FileFilterHelper.not(
            FileFilterHelper.falseFileFilter()
            );

        Assert.assertTrue( ff.accept( null ) );
    }

    @Test
    public void test_and()
    {
        FileFilter 	ff1	= FileFilterHelper.and(
                FileFilterHelper.trueFileFilter(),
                FileFilterHelper.directoryFileFilter()
                );
        FileFilter 	ff2 = FileFilterHelper.and(
                FileFilterHelper.trueFileFilter(),
                FileFilterHelper.fileFileFilter()
                );
        FileFilter 	ff3 	= FileFilterHelper.and(
                FileFilterHelper.falseFileFilter(),
                FileFilterHelper.directoryFileFilter()
                );

        File		dir = new File( TEMP_DIR_FILE, this.getClass().getSimpleName() );
        boolean 	isDirCreated = dir.mkdir();
        Assert.assertTrue( isDirCreated );

        boolean isDir1 = ff1.accept( dir );
        Assert.assertTrue( isDir1 );

        boolean isDir2 = ff2.accept( dir );
        Assert.assertFalse( isDir2 );

        boolean isDir3 = ff3.accept( dir );
        Assert.assertFalse( isDir3 );

        boolean isDirDeleted = dir.delete();
        Assert.assertTrue( isDirDeleted );
    }
/*
    public static FileFilter and(
            final FileFilter...fileFilters
            )
    public static FileFilter or(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    public static FileFilter or(
            final FileFilter...fileFilters
            )
    public static FileFilter xor(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    public static FileFilter fileLengthFileFilter( final long length )
    public static FileFilter zeroLengthFileFilter()
    */
}
