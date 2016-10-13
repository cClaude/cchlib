package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;

public class FileFiltersConfig implements Serializable
{
    private static final long serialVersionUID = 1L;

    private CustomFileFilterConfig files;
    private CustomFileFilterConfig directories;

    public CustomFileFilterConfig getDirectories()
    {
        return this.directories;
    }

    public void setDirectories( final CustomFileFilterConfig directories )
    {
        this.directories = directories;
    }

    public CustomFileFilterConfig getFiles()
    {
        return this.files;
    }

    public void setFiles( final CustomFileFilterConfig files )
    {
        this.files = files;
    }

    public static FileFilter getFileFilterForDirectories( final FileFiltersConfig ffc )
    {
        if( ffc != null ) {
            final CustomFileFilterConfig forDirectories = ffc.getDirectories();

            if( forDirectories != null ) {
                return forDirectories.newInstance();
            }
        }

        return pathname -> true;
    }

    public static FilenameFilter getFilenameFilterForDirectories( final FileFiltersConfig ffc )
    {
        if( ffc != null ) {
            final CustomFileFilterConfig forDirectories = ffc.getDirectories();

            if( forDirectories != null ) {
                return forDirectories.newFilenameFilterInstance();
            }
        }

        return ( dir, name ) -> false;
    }

    public static FileFilter getFileFilterForFiles( final FileFiltersConfig ffc )
    {
        if( ffc != null ) {
            final CustomFileFilterConfig forFiles = ffc.getFiles();

            if( forFiles != null ) {
                return forFiles.newInstance();
            }
        }

        return pathname -> true;
    }

    public static FilenameFilter getFilenameFilterForFiles( final FileFiltersConfig ffc )
    {
        if( ffc != null ) {
            final CustomFileFilterConfig forFiles = ffc.getFiles();

            if( forFiles != null ) {
                return forFiles.newFilenameFilterInstance();
            }
        }

        return ( dir, name ) -> false;
    }

}
