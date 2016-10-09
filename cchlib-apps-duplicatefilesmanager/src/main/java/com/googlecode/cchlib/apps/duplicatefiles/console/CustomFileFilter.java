package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

class CustomFileFilter implements FileFilter
{
    private final Collection<String> excludeName;

    CustomFileFilter( final Collection<String> excludeName )
    {
        this.excludeName = excludeName;
    }

    @Override
    public boolean accept( final File file )
    {
        final String name = file.getName();

        if( this.excludeName.contains( name ) ) {
            return false;
        }

        return true;
    }

}
