package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import static com.googlecode.cchlib.util.CollectionHelper.isEmpty;
import java.io.FileFilter;

/**
 * Factory class to create {@link FileFilter}
 * according to {@link FileFiltersConfig}
 */
public class FileFilterFactory
{
    private class FileFilterFor
    {
        private final CustomFileFilterConfig targetConfig;

        FileFilterFor( final CustomFileFilterConfig targetConfig )
        {
            this.targetConfig = targetConfig;
        }

        FileFilter newFileFilter()
        {
            if( this.targetConfig != null ) {
                if( isEmpty( this.targetConfig.getExcludeNames() ) && isEmpty( this.targetConfig.getExcludePaths() ) ) {
                    return DEFAULT_FILE_FILTER;
                } else {
                    return internalNewInstance( this.targetConfig );
                }
            }
            return DEFAULT_FILE_FILTER;
        }

        private CustomFileFilterImpl internalNewInstance(
                final CustomFileFilterConfig config
                )
        {
            return new CustomFileFilterImpl(
                    config.getExcludeNames(),
                    config.getExcludePaths(),
                    config.getExcludeRegexNames(),
                    config.getExcludeRegexPaths(),
                    config.getIncludeRegexNames(),
                    config.getIncludeRegexPaths(),
                    FileFilterFactory.this.verbose
                    );
        }
    }

    private static final FileFilter DEFAULT_FILE_FILTER = pathname -> true;

    private final FileFiltersConfig ffc;
    private final boolean           verbose;

    private FileFilterFactory( final FileFiltersConfig ffc, final boolean verbose )
    {
        this.ffc     = ffc;
        this.verbose = verbose;
    }

    private FileFilterFor forDirectories()
    {
        return new FileFilterFor( this.ffc == null ? null : this.ffc.getDirectories() );
    }

    private FileFilterFor forFiles()
    {
        return new FileFilterFor( this.ffc == null ? null : this.ffc.getFiles() );
    }

    /**
    *
    * @param ffc
    * @param verbose
    * @return
    */
   public static FileFilter getFileFilterForDirectories( final FileFiltersConfig ffc, final boolean verbose  )
   {
       return new FileFilterFactory( ffc, verbose ).forDirectories().newFileFilter();
   }

   /**
    *
    * @param ffc
    * @param verbose
    * @return
    */
   public static FileFilter getFileFilterForFiles( final FileFiltersConfig ffc, final boolean verbose  )
   {
       return new FileFilterFactory( ffc, verbose ).forFiles().newFileFilter();
   }
}
