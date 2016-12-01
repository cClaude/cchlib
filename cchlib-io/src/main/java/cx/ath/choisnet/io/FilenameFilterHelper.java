package cx.ath.choisnet.io;

import java.io.File;
import java.io.FilenameFilter;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.SerializableFileFilter;
import com.googlecode.cchlib.io.SerializableFilenameFilter;

/**
 * FileFilterHelper provide more efficient filter.
 * Theses FilenameFilter are provide for compatibility only
 *
 * @see FileFilterHelper
 *
 * @deprecated use Lambda instead or {@link SerializableFileFilter}
 */
@Deprecated
public class FilenameFilterHelper
{
    private FilenameFilterHelper()
    { // ALL Static!
    }

    /**
     * @return FilenameFilter which return only Directories
     * @see FileFilterHelper#directoryFileFilter()
     */
    public static FilenameFilter directoryFileFilter()
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                return new File(dir,filename).isDirectory();
            }
        };
    }

    /**
     * @return FilenameFilter which does not filter
     * @see FileFilterHelper#trueFileFilter()
     */
    public static FilenameFilter trueFileFilter()
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                return true;
            }
        };
    }

    /**
     *
     * @param aFileFilter source {@link FilenameFilter}
     * @return FilenameFilter which return "not" result from giving FilenameFilter
     * @see FileFilterHelper#not(SerializableFileFilter)
     */
    public static FilenameFilter not(final FilenameFilter aFileFilter)
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                return !aFileFilter.accept(dir,filename);
            }
        };
    }

    /**
     *
     * @param firstFileFilter source {@link FilenameFilter}
     * @param secondFileFilter source {@link FilenameFilter}
     * @return a FilenameFilter
     * @see FileFilterHelper#and(SerializableFileFilter...)
     */
    public static FilenameFilter and(
                final FilenameFilter firstFileFilter,
                final FilenameFilter secondFileFilter
                )
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                if(firstFileFilter.accept(dir,filename)) {
                    return secondFileFilter.accept(dir,filename);
                }
                else {
                    return false;
                }
            }
        };
    }

    /**
     *
     * @param fileFilters source {@link FilenameFilter}
     * @return a FilenameFilter
     * @see FileFilterHelper#and(SerializableFileFilter...)
     */
    public static FilenameFilter and(
            final FilenameFilter...fileFilters
            )
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                for(final FilenameFilter ff : fileFilters ) {
                    if(!ff.accept(dir,filename)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    /**
     *
     * @param firstFileFilter source {@link FilenameFilter}
     * @param secondFileFilter source {@link FilenameFilter}
     * @return a FilenameFilter
     * @see FileFilterHelper#or(SerializableFileFilter...)
     */
    public static FilenameFilter or(
            final FilenameFilter firstFileFilter,
            final FilenameFilter secondFileFilter
            )
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                if(firstFileFilter.accept(dir,filename)) {
                    return true;
                }
                else {
                    return secondFileFilter.accept(dir,filename);
                }
            }
        };
    }

    /**
     *
     * @param fileFilters source {@link FilenameFilter}
     * @return a FilenameFilter
     * @see FileFilterHelper#or(SerializableFileFilter...)
     */
    public static FilenameFilter or(
            final FilenameFilter...fileFilters
            )
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                for(final FilenameFilter ff : fileFilters) {
                    if(ff.accept(dir,filename)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     *
     * @param firstFileFilter source {@link FilenameFilter}
     * @param secondFileFilter source {@link FilenameFilter}
     * @return a FilenameFilter
     * @see FileFilterHelper#xor(SerializableFileFilter, SerializableFileFilter)
     */
    public static FilenameFilter xor(
            final FilenameFilter firstFileFilter,
            final FilenameFilter secondFileFilter
            )
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                return  firstFileFilter.accept(dir,filename)
                        ^
                        secondFileFilter.accept(dir,filename);
            }
        };
    }

    /**
     *
     * @param length Length of expected files
     * @return a FilenameFilter
     * @see FileFilterHelper#fileLengthFileFilter(long)
     */
    public static FilenameFilter fileLengthFileFilter(final long length)
    {
        return new SerializableFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( final File dir, final String filename )
            {
                return (new File(dir,filename)).length() == length;
            }
        };
    }

    /**
     *
     * @return a FilenameFilter
     * @see FileFilterHelper#zeroLengthFileFilter()
     */
    public static FilenameFilter zeroLengthFileFilter()
    {
        return fileLengthFileFilter( 0L );
    }
}
