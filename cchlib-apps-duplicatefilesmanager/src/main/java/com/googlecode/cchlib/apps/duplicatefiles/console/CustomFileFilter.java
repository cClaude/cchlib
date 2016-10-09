package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

class CustomFileFilter implements FileFilter
{
    private final Collection<String> excludeNames;

    CustomFileFilter( final Collection<String> excludeNames )
    {
        this.excludeNames = excludeNames;
    }

    @Override
    public boolean accept( final File file )
    {
        final String name = file.getName();

        if( this.excludeNames.contains( name ) ) {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "CustomFileFilter [excludeNames=" + this.excludeNames + "]";
    }
}
