package com.googlecode.cchlib.io;

import com.googlecode.cchlib.io.filefilter.ANDFileFilter;
import com.googlecode.cchlib.io.filefilter.DirectoryFileFilter;
import com.googlecode.cchlib.io.filefilter.FalseFileFilter;
import com.googlecode.cchlib.io.filefilter.FileFileFilter;
import com.googlecode.cchlib.io.filefilter.FileLengthFileFilter;
import com.googlecode.cchlib.io.filefilter.NOTFileFilter;
import com.googlecode.cchlib.io.filefilter.NoneZeroLengthFileFilter;
import com.googlecode.cchlib.io.filefilter.ORFileFilter;
import com.googlecode.cchlib.io.filefilter.TrueFileFilter;
import com.googlecode.cchlib.io.filefilter.XORFileFilter;
import java.io.File;
import java.io.FileFilter;

/**
 * Build commons {@link FileFilter} that are {@link java.io.Serializable}
 * <p>
 * You must also provide {@link java.io.Serializable} {@link FileFilter} when
 * required.
 * </p>
 * @since 4.1.7
 * @see SerializableFileFilter
 * @see com.googlecode.cchlib.io.filefilter.PatternFileFilter
 * @see com.googlecode.cchlib.io.filefilter.EndsWithFileFilter
*/
public final class FileFilterHelper
{
    private FileFilterHelper()
    {
        // All static
    }

    /**
     * Returns a file filter that select only directories
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see File#isDirectory()
     * @see DirectoryFileFilter
     */
    public static SerializableFileFilter directoryFileFilter()
    {
        return new DirectoryFileFilter();
    }

    /**
     * Returns a filter that select only files
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see File#isFile()
     * @see FileFileFilter
     */
    public static SerializableFileFilter fileFileFilter()
    {
        return new FileFileFilter();
    }

    /**
     * Returns a {@link java.io.Serializable} {@link FileFilter} with an
     * accept(File) method that always return true
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see TrueFileFilter
     */
    public static SerializableFileFilter trueFileFilter()
    {
        return new TrueFileFilter();
    }

    /**
     * Returns a {@link java.io.Serializable} {@link FileFilter} with an
     * accept(File) method that always return false
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see FalseFileFilter
     */
    public static SerializableFileFilter falseFileFilter()
    {
        return new FalseFileFilter();
    }

    /**
     * Returns a {@link java.io.Serializable} {@link FileFilter} with an
     * accept(File) method that return not operation result of giving
     * {@link FileFilter}
     * @param aFileFilter
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see NOTFileFilter
     */
    public static SerializableFileFilter not(
        final FileFilter aFileFilter
        )
    {
        return new NOTFileFilter( aFileFilter );
    }

    /**
     * TODOC
     * @param fileFilters
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter and(
            final FileFilter...fileFilters
            )
    {
        return new ANDFileFilter( fileFilters );
    }

    /**
     * TODOC
     * @param fileFilters
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter or(
            final FileFilter...fileFilters
            )
    {
        return new ORFileFilter( fileFilters );
    }

    /**
     * TODOC
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter xor(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    {
        return new XORFileFilter( firstFileFilter, secondFileFilter );
    }

    /**
     * TODOC
     * @param length
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter fileLengthFileFilter( final long length )
    {
        return new FileLengthFileFilter( length );
    }

    /**
     * TODOC
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter zeroLengthFileFilter()
    {
        return fileLengthFileFilter( 0L );
    }

    /**
     * TODOC
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter noneZeroLengthFileFilter()
    {
        return new NoneZeroLengthFileFilter();
    }
}
