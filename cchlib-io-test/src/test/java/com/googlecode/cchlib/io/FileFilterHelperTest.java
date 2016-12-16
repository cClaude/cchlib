package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
*/
public class FileFilterHelperTest
{
    private static final Logger LOGGER = Logger.getLogger(FileFilterHelperTest.class);

    @Test
    public void test_directoryFileFilter()
    {
        LOGGER.info( "test_directoryFileFilter()" );
        final File        dir = new File( FileHelper.getTmpDirFile(), this.getClass().getSimpleName() );
        final boolean     isDirCreated = dir.mkdir();
        Assert.assertTrue( isDirCreated );

        final FileFilter     ff     = FileFilterHelper.directoryFileFilter();

        final boolean isDir = ff.accept( dir );
        Assert.assertTrue( isDir );

        final boolean isDirDeleted = dir.delete();
        Assert.assertTrue( isDirDeleted );
    }

    @Test
    public void test_fileFileFilter() throws IOException
    {
        final File        file = File.createTempFile( this.getClass().getSimpleName(), ".tmp" );
        final boolean     isFileCreated = file.isFile();
        Assert.assertTrue( isFileCreated );

        final FileFilter     ff     = FileFilterHelper.fileFileFilter();

        final boolean isFile = ff.accept( file );
        Assert.assertTrue( isFile );

        final boolean isFileDeleted = file.delete();
        Assert.assertTrue( isFileDeleted );
    }

    @Test
    public void test_trueFileFilter()
    {
        final FileFilter ff = FileFilterHelper.trueFileFilter();

        Assert.assertTrue( ff.accept( null ) );
    }

    @Test
    public void test_falseFileFilter()
    {
        final FileFilter ff = FileFilterHelper.falseFileFilter();

        Assert.assertFalse( ff.accept( null ) );
    }

    @Test
    public void test_not()
    {
        final FileFilter ff = FileFilterHelper.not(
            FileFilterHelper.falseFileFilter()
            );

        Assert.assertTrue( ff.accept( null ) );
    }

    @Test
    public void test_and()
    {
        final FileFilter ff1 = FileFilterHelper.and(
                FileFilterHelper.trueFileFilter(),
                FileFilterHelper.directoryFileFilter()
                );
        final FileFilter ff2 = FileFilterHelper.and(
                FileFilterHelper.trueFileFilter(),
                FileFilterHelper.fileFileFilter()
                );
        final FileFilter ff3 = FileFilterHelper.and(
                FileFilterHelper.falseFileFilter(),
                FileFilterHelper.directoryFileFilter()
                );

        final File        dir = new File( FileHelper.getTmpDirFile(), this.getClass().getSimpleName() );
        final boolean     isDirCreated = dir.mkdir();
        Assert.assertTrue( isDirCreated );

        final boolean isDir1 = ff1.accept( dir );
        Assert.assertTrue( isDir1 );

        final boolean isDir2 = ff2.accept( dir );
        Assert.assertFalse( isDir2 );

        final boolean isDir3 = ff3.accept( dir );
        Assert.assertFalse( isDir3 );

        final boolean isDirDeleted = dir.delete();
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
    */
    @Test
    public void test_zeroLengthFileFilter()
        throws IOException
    {
        // Create a empty file !
        final File f = File.createTempFile( this.getClass().getName(), "tmp" );

        final FileFilter ff1 = FileFilterHelper.zeroLengthFileFilter();
        final FileFilter ff2 = FileFilterHelper.noneZeroLengthFileFilter();

        Assert.assertTrue( "Should be empty", ff1.accept( f ) );
        Assert.assertFalse( "Should be empty", ff2.accept( f ));

        // Now file is not empty
        IOHelper.toFile( "not empty", f );

        Assert.assertFalse( "Should not be empty", ff1.accept( f ) );
        Assert.assertTrue( "Should not be empty", ff2.accept( f ));

        final boolean isDel = f.delete();
        Assert.assertTrue( "Can not delete file", isDel );
    }
}
