package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.Collection;

/**
 * Configuration to create {@link FileFilter} or {@link FilenameFilter} objects
 */
public class CustomFileFilterConfig implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Collection<String> excludeNames;
    private Collection<String> excludePaths;

    /**
     * Create a FileFilter based on current configuration
     *
     * @return a FileFilter
     */
    public FileFilter newInstance()
    {
        if( isEmpty( this.excludeNames ) && isEmpty( this.excludePaths ) ) {
            return pathname -> true;
        } else {
            return new CustomFileFilter( this.excludeNames, this.excludePaths );
        }
    }

    /**
     * Create a FilenameFilter based on current configuration
     *
     * @return a FilenameFilter
     */
    public FilenameFilter newFilenameFilterInstance()
    {
        if( isEmpty( this.excludeNames ) && isEmpty( this.excludePaths ) ) {
            return ( dir, name ) -> false;
        } else {
            return new CustomFileFilter( this.excludeNames, this.excludePaths );
        }
    }

    private static <T> boolean isEmpty( final Collection<T> collection )
    {
        return (collection == null) || collection.isEmpty();
    }

    public void setExcludeNames( final Collection<String> excludeNames )
    {
        this.excludeNames = excludeNames;
    }

    public Collection<String> getExcludeNames()
    {
        return this.excludeNames;
    }

    public void setExcludePaths( final Collection<String> excludePaths )
    {
        this.excludePaths = excludePaths;
    }

    public Collection<String> getExcludePaths()
    {
        return this.excludePaths;
    }
}
