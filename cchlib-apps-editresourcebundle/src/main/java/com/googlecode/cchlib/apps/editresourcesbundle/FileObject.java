package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.File;
import java.io.Serializable;

public class FileObject implements Serializable
{
    private static final long serialVersionUID = 2L;
    private File file;
    private boolean readOnly;

    public FileObject()
    {
        this(null,true);
    }

    public FileObject(File file)
    {
        this(file,true);
    }

    public FileObject(File file, boolean readOnly)
    {
        this.file       = file;
        this.readOnly   = readOnly;
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        result = prime * result + (readOnly ? 1231 : 1237);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        FileObject other = (FileObject)obj;
        if( file == null ) {
            if( other.file != null ) {
                return false;
            }
        } else if( !file.equals( other.file ) ) {
            return false;
        }
        if( readOnly != other.readOnly ) {
            return false;
        }
        return true;
    }
}
