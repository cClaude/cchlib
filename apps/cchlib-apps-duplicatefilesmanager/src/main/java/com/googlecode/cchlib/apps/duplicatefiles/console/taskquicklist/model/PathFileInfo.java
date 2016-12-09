package com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist.model;

import java.io.File;
import java.io.IOException;

public class PathFileInfo extends AbstractFileInfo
{
    private static final long serialVersionUID = 1L;

    private String path;

    public PathFileInfo( final File file ) throws IOException
    {
        super( file );

        this.path = normalize( file.getPath() );
    }

    public PathFileInfo()
    {
        // JSON
    }

    public String getPath()
    {
        return this.path;
    }

    public void setPath( final String path )
    {
        this.path = path;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "PathFileInfo [path=" );
        builder.append( this.path );
        builder.append( ", getLength()=" );
        builder.append( getLength() );
        builder.append( ", getLastModified()=" );
        builder.append( getLastModified() );
        builder.append( ", getAttributes()=" );
        builder.append( getAttributes() );
        builder.append( ", getFilename()=" );
        builder.append( getFilename() );
        builder.append( "]" );

        return builder.toString();
    }
}
