package com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist.model;

import java.io.File;
import java.io.IOException;

public class CanonicalFileInfo extends AbstractFileInfo
{
    private static final long serialVersionUID = 1L;

    private String canonicalPath;

    public CanonicalFileInfo( final File file ) throws IOException
    {
        super( file );

        this.canonicalPath = normalize( file.getCanonicalPath() );
    }

    public CanonicalFileInfo()
    {
        // JSON
    }

    public String getCanonicalPath()
    {
        return this.canonicalPath;
    }

    public void setCanonicalPath( final String canonicalPath )
    {
        this.canonicalPath = canonicalPath;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "CanonicalFileInfo [canonicalPath=" );
        builder.append( this.canonicalPath );
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
