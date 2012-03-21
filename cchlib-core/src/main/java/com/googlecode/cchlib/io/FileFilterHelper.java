package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
** Build commons {@link FileFilter} that are {@link java.io.Serializable}
** @since 4.1.7
*/
public class FileFilterHelper
{
    private FileFilterHelper()
    {
        // All static
    }

    /**
     * Returns a filter that select only directories
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see File#isDirectory()
     */
    public static SerializableFileFilter directoryFileFilter()
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
                return file.isDirectory();
            }
        };
    }

    /**
     * Returns a filter that select only files
     * @return a {@link java.io.Serializable} {@link FileFilter}
     * @see File#isFile()
     */
    public static SerializableFileFilter fileFileFilter()
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
                return file.isFile();
            }
        };
    }

    /**
     * Returns a {@link java.io.Serializable} {@link FileFilter} with an
     * accept(File) method that always return true
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter trueFileFilter()
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
                return true;
            }
        };
    }

    /**
     * Returns a {@link java.io.Serializable} {@link FileFilter} with an
     * accept(File) method that always return false
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter falseFileFilter()
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept( File file )
            {
                return false;
            }
        };
    }

    /**
     * TODOC
     * @param aFileFilter
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter not(final FileFilter aFileFilter)
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
                return !aFileFilter.accept(file);
            }
        };
    }

    /**
     * TODOC
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter and(
                final FileFilter firstFileFilter,
                final FileFilter secondFileFilter
                )
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
                if(firstFileFilter.accept(file)) {
                    return secondFileFilter.accept(file);
                    }
                else {
                    return false;
                    }
            }
        };
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
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
              for(FileFilter ff : fileFilters ) {
                    if(!ff.accept(file)) {
                        return false;
                        }
                    }
                return true;
            }
        };
    }

    /**
     * TODOC
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter or(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept( File pathname )
            {
                if( firstFileFilter.accept( pathname ) ) {
                    return true;
                    }
                else {
                    return secondFileFilter.accept( pathname );
                    }
            }
        };
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
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept( final File pathname )
            {
                for( FileFilter ff : fileFilters ) {
                    if( ff.accept( pathname ) ) {
                        return true;
                        }
                    }
                return false;
            }
        };
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
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept( File file )
            {
                return firstFileFilter.accept(file) ^ secondFileFilter.accept(file);
            }
        };
    }

    /**
     * TODOC
     * @param length
     * @return a {@link java.io.Serializable} {@link FileFilter}
     */
    public static SerializableFileFilter fileLengthFileFilter( final long length )
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
                return file.length() == length;
            }
        };
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
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean accept(File file)
            {
                return file.length() != 0;
            }
        };
    }
}
