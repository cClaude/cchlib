package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;

/**
** Build commons {@link FileFilter} that are {@link Serializable}
*/
public class FileFilterHelper
{
    private FileFilterHelper()
    {
        // All static
    }

    /**
     * Returns a filter that keep only directories
     * @return a {@link Serializable} {@link FileFilter}
     * @see File#isDirectory()
     */
    public static FileFilter directoryFileFilter()
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean accept(File file)
            {
                return file.isDirectory();
            }
        };
    }

    /**
     * Returns a filter that keep only file
     * @return a {@link Serializable} {@link FileFilter}
     * @see File#isFile()
     */
    public static FileFilter fileFileFilter()
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean accept(File file)
            {
                return file.isFile();
            }
        };
    }

    /**
     * Returns a {@link Serializable} {@link FileFilter} with an
     * accept(File) method that always return true
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter trueFileFilter()
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean accept(File file)
            {
                return true;
            }
        };
    }

    /**
     * Returns a {@link Serializable} {@link FileFilter} with an
     * accept(File) method that always return false
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter falseFileFilter()
    {
        return new SerializableFileFilter()
        {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean accept( File file )
            {
                return false;
            }
        };
    }

    /**
     *
     * @param aFileFilter
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter not(final FileFilter aFileFilter)
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean accept(File file)
            {
                return !aFileFilter.accept(file);
            }
        };
    }

    /**
     *
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter and(
                final FileFilter firstFileFilter,
                final FileFilter secondFileFilter
                )
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
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
     *
     * @param fileFilters
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter and(
            final FileFilter...fileFilters
            )
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
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
     *
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter or(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
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
     *
     * @param fileFilters
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter or(
            final FileFilter...fileFilters
            )
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
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
     *
     * @param firstFileFilter
     * @param secondFileFilter
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter xor(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter
            )
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean accept( File file )
            {
                return firstFileFilter.accept(file) ^ secondFileFilter.accept(file);
            }
        };
    }

    /**
     *
     * @param length
     * @return a {@link Serializable} {@link FileFilter}
     */
    public static FileFilter fileLengthFileFilter( final long length )
    {
        return new SerializableFileFilter() {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean accept(File file)
            {
                return file.length() == length;
            }
        };
    }

    /**
    *
    * @return a {@link Serializable} {@link FileFilter}
    */
   public static FileFilter zeroLengthFileFilter()
   {
       return fileLengthFileFilter( 0L );
   }

   /**
   *
   * @return a {@link Serializable} {@link FileFilter}
   * @since 4.1.6
   */
  public static FileFilter noneZeroLengthFileFilter()
  {
      return new SerializableFileFilter() {
          private static final long serialVersionUID = 1L;
          @Override
          public boolean accept(File file)
          {
              return file.length() != 0;
          }
      };
  }
}
