package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
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
 *
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
     * Returns a {@link java.io.Serializable} {@link SerializableFileFilter} with an
     * accept(File) method that return not operation result of giving
     * {@link SerializableFileFilter}
     *
     * @param aFileFilter Original {@link SerializableFileFilter}
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see NOTFileFilter
     */
    public static SerializableFileFilter not(
        final SerializableFileFilter aFileFilter
        )
    {
        return new NOTFileFilter( aFileFilter );
    }

    /**
     * Create a file filter that need to satisfy all
     * child file filters
     *
     * @param fileFilters Array of {@link FileFilter}
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter and(
            final SerializableFileFilter...fileFilters
            )
    {
        return new ANDFileFilter( fileFilters );
    }

    /**
     * Create a file filter that need to satisfy at least
     * on child file filters
     *
     * @param fileFilters Array of {@link SerializableFileFilter}
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter or(
            final SerializableFileFilter...fileFilters
            )
    {
        return new ORFileFilter( fileFilters );
    }

    /**
     * Binary XOR operation on file filter
     *
     * @param firstFileFilter  first {@link SerializableFileFilter}
     * @param secondFileFilter second {@link SerializableFileFilter}
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter xor(
            final SerializableFileFilter firstFileFilter,
            final SerializableFileFilter secondFileFilter
            )
    {
        return new XORFileFilter( firstFileFilter, secondFileFilter );
    }

    /**
     * Create {@link FileLengthFileFilter} for the giving {@code length}
     *
     * @param length File length to expect
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see FileLengthFileFilter
     */
    public static SerializableFileFilter fileLengthFileFilter( final long length )
    {
        return new FileLengthFileFilter( length );
    }

    /**
     * Create a file filter for empty files
     *
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter zeroLengthFileFilter()
    {
        return fileLengthFileFilter( 0L );
    }

    /**
     * Create a file filter for none empty files
     *
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter noneZeroLengthFileFilter()
    {
        return new NoneZeroLengthFileFilter();
    }

    /**
     * Convert a {@link DirectoryStream.Filter} into a
     * {@link FileFilter}
     *
     * @param filter Filter to convert
     * @param errorHandler Error handler during conversion.
     * @return a {@link FileFilter}
     */
    public static FileFilter toFileFilter(
        final DirectoryStream.Filter<Path> filter,
        final ErrorHandler                 errorHandler
        )
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
