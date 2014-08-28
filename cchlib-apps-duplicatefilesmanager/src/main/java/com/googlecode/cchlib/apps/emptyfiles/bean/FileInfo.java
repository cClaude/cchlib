package com.googlecode.cchlib.apps.emptyfiles.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import com.googlecode.cchlib.apps.emptyfiles.interfaces.FileInfoFormater;

/**
 *
 */
public class FileInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private boolean selected;
    private final File file;
    private final FileInfoFormater fileInfoFormater;

     /**
     *
     */
    public FileInfo( final File file, final boolean selected, final FileInfoFormater fileInfoFormater )
    {
        this.file             = file;
        this.selected         = selected;
        this.fileInfoFormater = fileInfoFormater;
    }

    public boolean isSelected()
    {
        if( isDeleted() ) {
            return false;
            }
        else {
            return selected;
            }
    }

    public void setSelected( final boolean selected )
    {
        this.selected = selected;
    }

    public Date getLastModifiedDate()
    {
        return new Date( file.lastModified() );
    }

    public boolean isDeleted()
    {
        return ! file.exists();
    }

    public String getLengthString()
    {
        return fileInfoFormater.formatLength( file );
    }

    public String getFileAttributsString()
    {
        return fileInfoFormater.formatAttributs( file );
    }
}
