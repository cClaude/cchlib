package com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist.model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnull;

public abstract class AbstractFileInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final String DATE_ISO_FORMAT = "yyyy-MM-dd.HH-mm-ss";

    private String filename;
    private String lastModified;
    private String attributes;

    private long length;

    public AbstractFileInfo( @Nonnull final File file )
    {
        this.filename     = file.getName();
        this.attributes   = getAttributes( file );

        this.lastModified = getLastModifiedDate( file );
        this.length       = file.length();
    }

    public AbstractFileInfo()
    {
        // JSON
    }

    protected String normalize( @Nonnull final String path )
    {
        if( File.separatorChar == '\\' ) {
            return path.replace( File.separatorChar, '/' );
        }

        return path;
    }

    private static String getLastModifiedDate( final File file )
    {
        return newISODateFormat().format( new Date( file.lastModified() ) );
    }

    private static SimpleDateFormat newISODateFormat()
    {
        return new SimpleDateFormat( DATE_ISO_FORMAT );
    }

    private static String getAttributes( final File file )
    {
        final char[] attrs = new char[ 7 ];

        attrs[ 0 ] = file.canExecute()  ? 'x' : '_';
        attrs[ 1 ] = file.canRead()     ? 'r' : '_';
        attrs[ 2 ] = file.canWrite()    ? 'w' : '_';
        attrs[ 3 ] = file.isAbsolute()  ? 'A' : '_';
        attrs[ 4 ] = file.isDirectory() ? 'D' : '_';
        attrs[ 5 ] = file.isFile()      ? 'F' : '_';
        attrs[ 6 ] = file.isHidden()    ? 'H' : '_';

        return new String( attrs );
    }

    public long getLength()
    {
        return this.length;
    }

    public void setLength( final long length )
    {
        this.length = length;
    }

    public String getLastModified()
    {
        return this.lastModified;
    }

    public void setLastModified( final String lastModified )
    {
        this.lastModified = lastModified;
    }

    public String getAttributes()
    {
        return this.attributes;
    }

    public void setAttributes( final String attributes )
    {
        this.attributes = attributes;
    }

    public String getFilename()
    {
        return this.filename;
    }
}
