package com.googlecode.cchlib.apps.editresourcesbundle.files;

import java.io.File;
import java.io.Serializable;

public class FileObject implements Serializable
{
    private static final long serialVersionUID = 2L;
    private File file;
    private boolean readOnly;
    private CustomProperties customProperties;

    public FileObject( final File file, final boolean readOnly )
    {
        this.file             = file;
        this.readOnly         = readOnly;
    }

    /**
     * @return the file
     */
    public File getFile()
    {
        return file;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly()
    {
        return readOnly;
    }

    void setCustomProperties( final CustomProperties customProperties )
    {
        this.customProperties = customProperties;
    }

    public CustomProperties getCustomProperties()
    {
        if( customProperties == null ) {
            throw new IllegalStateException( "CustomProperties not initialized" );
            }
        
        return customProperties;
    }

    public String getDisplayName( final String txtNoFile )
    {
        if( file == null ) {
            return txtNoFile;
            }
        else {
            return file.getName();
            }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "FileObject [file=" );
        builder.append( file );
        builder.append( ", readOnly=" );
        builder.append( readOnly );
        builder.append( ']' );
        return builder.toString();
    }
}
