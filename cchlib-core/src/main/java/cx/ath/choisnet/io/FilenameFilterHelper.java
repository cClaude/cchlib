package cx.ath.choisnet.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * FileFilterHelper provide more efficient filter.
 * Theses FilenameFilter are provide for compatibility only
 * @author Claude CHOISNET
 * @see FileFilterHelper
 */
public class FilenameFilterHelper
{
    private static abstract class PrivateFilenameFilter implements FilenameFilter, java.io.Serializable
    {
        private static final long serialVersionUID = 1L;
    }
    
    private FilenameFilterHelper()
    { // ALL Static!
    }

    /**
     * 
     * @return FilenameFilter which return only Directories
     * @see FileFilterHelper#directoryFileFilter()
     */
    public static FilenameFilter directoryFileFilter()
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
            {
                return new File(dir,filename).isDirectory();
            }
        };
    }

    /**
     * 
     * @return FilenameFilter which does not filter
     *  @see FileFilterHelper#trueFileFilter()
     */
    public static FilenameFilter trueFileFilter()
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
            {
                return true;
            }
        };
    }

    /**
     * 
     * @param aFileFilter
     * @return FilenameFilter which return "not" result from giving FilenameFilter
     * @see FileFilterHelper#not(java.io.FileFilter)
     */
    public static FilenameFilter not(final FilenameFilter aFileFilter)
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
            {
                return !aFileFilter.accept(dir,filename);
            }
        };
    }

    /**
     * 
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a FilenameFilter
     * @see FileFilterHelper#and(java.io.FileFilter, java.io.FileFilter)
     */
    public static FilenameFilter and(
                final FilenameFilter firstFileFilter,
                final FilenameFilter secondFileFilter
                )
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
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
     * @param fileFilters
     * @return a FilenameFilter
     * @see FileFilterHelper#and(java.io.FileFilter...)
     */
    public static FilenameFilter and(
            final FilenameFilter...fileFilters
            )
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
            {
                for(FilenameFilter ff : fileFilters ) {
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
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a FilenameFilter
     * @see FileFilterHelper#or(java.io.FileFilter, java.io.FileFilter)
     */
    public static FilenameFilter or(
            final FilenameFilter firstFileFilter,
            final FilenameFilter secondFileFilter
            )
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
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
     * @param fileFilters
     * @return a FilenameFilter
     * @see FileFilterHelper#or(java.io.FileFilter...)
     */
    public static FilenameFilter or(
            final FilenameFilter...fileFilters
            )
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
            {
                for(FilenameFilter ff : fileFilters) {
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
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a FilenameFilter
     * @see FileFilterHelper#xor(java.io.FileFilter, java.io.FileFilter)
     */
    public static FilenameFilter xor(
            final FilenameFilter firstFileFilter,
            final FilenameFilter secondFileFilter
            )
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
            {
                // TODO Auto-generated method stub
                return  firstFileFilter.accept(dir,filename)
                        ^ 
                        secondFileFilter.accept(dir,filename);
            }
        };
    }

    /**
     * 
     * @param length
     * @return a FilenameFilter
     * @see FileFilterHelper#fileLengthFileFilter(long)
     */
    public static FilenameFilter fileLengthFileFilter(final long length)
    {
        return new PrivateFilenameFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept( File dir, String filename )
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
