package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;
import com.googlecode.cchlib.NeedDoc;
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
    /**
     * @since 4.2
     */
    @NeedDoc
    @FunctionalInterface
    public interface ErrorHandler {
        @NeedDoc
        boolean handleIOException( File file, IOException ioe );
    }

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
     * NEEDDOC
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
     * NEEDDOC
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
     * NEEDDOC
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
     * NEEDDOC
     * @param length
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter fileLengthFileFilter( final long length )
    {
        return new FileLengthFileFilter( length );
    }

    /**
     * NEEDDOC
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter zeroLengthFileFilter()
    {
        return fileLengthFileFilter( 0L );
    }

    /**
     * NEEDDOC
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter noneZeroLengthFileFilter()
    {
        return new NoneZeroLengthFileFilter();
    }

    @NeedDoc
    public static FileFilter toFileFilter( final Filter<Path> filter, final ErrorHandler errorHandler )
    {
        return pathname -> {
            try {
                return filter.accept( pathname.toPath() );
            }
            catch( final IOException e ) {
                return errorHandler.handleIOException( pathname, e );
            }
        };
    }

    public static ErrorHandler returnFalseOnError()
    {
        return ( file, ioe ) -> false;
    }

}
