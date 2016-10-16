package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.Collections;

class CustomFileFilter implements FileFilter, FilenameFilter
{
    private final Collection<String> excludeNames;
    private final Collection<String> excludePaths;

    CustomFileFilter(
            final Collection<String> excludeNames,
            final Collection<String> excludePaths
            )
    {
        this.excludeNames = unmodifiableCollection( excludeNames );
        this.excludePaths = unmodifiableCollection( excludePaths );
    }

    private static <T> Collection<T> unmodifiableCollection( final Collection<T> c )
    {
        if( c == null ) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection( c );
    }

    @Override
    public boolean accept( final File file )
    {
        if( this.excludeNames.contains( file.getName() ) ) {
            return false;
        }

        if( this.excludePaths.contains( file.getPath() ) ) {
            return false;
        }

        return true;
    }

    @Override
    public boolean accept( final File dir, final String name )
    {
        if( this.excludeNames.contains( name ) ) {
            return false;
        }

        if( this.excludePaths.contains( name ) ) {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "CustomFileFilter [excludeNames=" + this.excludeNames + ", excludePaths=" + this.excludePaths + "]";
    }
}
