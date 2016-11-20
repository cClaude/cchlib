package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.Serializable;

/**
 * Global configuration for {@link CustomFileFilterConfig}
 *
 * Allow to configure directories and files
 */
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

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "FileFiltersConfig [files=" );
        builder.append( this.files );
        builder.append( ", directories=" );
        builder.append( this.directories );
        builder.append( "]" );

        return builder.toString();
    }
}
