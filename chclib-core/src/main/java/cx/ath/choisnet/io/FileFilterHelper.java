package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class FileFilterHelper
{
    private static abstract class PrivateFileFilterHelper implements FileFilter, java.io.Serializable
    {
        private static final long serialVersionUID = 1L;
    }

    private FileFilterHelper()
    {
    }

    public static FileFilter directoryFileFilter()
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File file)
            {
                return file.isDirectory();
            }
        };
    }
    
    public static FileFilter fileFileFilter()
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File file)
            {
                return file.isFile();
            }
        };
    }

    public static FileFilter trueFileFilter()
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File file)
            {
                return true;
            }
        };
    }

    public static FileFilter not(final FileFilter aFileFilter)
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File file)
            {
                return !aFileFilter.accept(file);
            }
        };
    }

    public static FileFilter and(
                final FileFilter firstFileFilter,
                final FileFilter secondFileFilter
                )
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

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

    public static FileFilter and(
            final FileFilter...fileFilters
            )
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

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

    public static FileFilter or(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File pathname)
            {
                if(firstFileFilter.accept(pathname)) {
                    return true;
                }
                else {
                    return secondFileFilter.accept(pathname);
                }
            }
        };
    }

    public static FileFilter or(
            final FileFilter...fileFilters
            )
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File pathname)
            {
                for(FileFilter ff : fileFilters) {
                    if(ff.accept(pathname)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static FileFilter xor(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File file)
            {
                return firstFileFilter.accept(file) ^ secondFileFilter.accept(file);
            }
        };
    }

    public static FileFilter fileLengthFileFilter(final long length)
    {
        return new PrivateFileFilterHelper() {
            private static final long serialVersionUID = 1L;

            public boolean accept(File file)
            {
                return file.length() == length;
            }
        };
    }

    public static FileFilter zeroLengthFileFilter()
    {
        return fileLengthFileFilter( 0L );
    }
}
