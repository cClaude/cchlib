package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;

class CustomFileFilterImpl implements Serializable, FileFilter
{
    private static final long serialVersionUID = 1L;

    private final Collection<String> excludeNames;
    private final Collection<String> excludePaths;
    private final boolean            verbose;

    CustomFileFilterImpl(
            final Collection<String> excludeNames,
            final Collection<String> excludePaths,
            final boolean            verbose
            )
    {
        this.excludeNames = unmodifiableCollection( excludeNames );
        this.excludePaths = unmodifiableCollection( excludePaths );

        // For performance this is evaluate only once
        this.verbose = verbose;
    }

    private static <T> Collection<T> unmodifiableCollection( final Collection<T> c )
    {
        if( c == null ) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection( c );
    }

    @Override
    public boolean accept( @Nonnull final File file )
    {
        if( this.excludeNames.contains( file.getName() ) ) {
            if( this.verbose ) {
                CLIHelper.printMessage( "Ignore " + file + " because " + file.getName() + " is in excludeNames values" );
            }
            return false;
        }

        if( this.excludePaths.contains( file.getPath() ) ) {
            if( this.verbose ) {
                CLIHelper.printMessage( "Ignore " + file + " because " + file.getPath() + " is in excludePaths values" );
            }
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
