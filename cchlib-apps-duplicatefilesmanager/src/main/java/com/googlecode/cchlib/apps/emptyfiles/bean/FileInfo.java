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
    private Date lastModifiedDate;
    private boolean deleted;
    private String lengthString;
    private String fileAttributsString;

     /**
     * 
     */
    public FileInfo( File file, boolean selected, FileInfoFormater fileInfoFormater )
    {
        this.selected = selected;
        this.lastModifiedDate    = new Date( file.lastModified() );
        this.fileAttributsString = fileInfoFormater.formatAttributs( file );
        this.lengthString        = fileInfoFormater.formatLength( file );
        this.deleted = false;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected( boolean selected )
    {
        this.selected = selected;
    }

    public Date getLastModifiedDate()
    {
        return this.lastModifiedDate;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

    public String getLengthString()
    {
        return lengthString;
    }

    public void setLengthString( String lengthString )
    {
        this.lengthString = lengthString;
    }

    public String getFileAttributsString()
    {
        return fileAttributsString;
    }
    
    public void setFileAttributsString( String fileAttributsString )
    {
        this.fileAttributsString = fileAttributsString;
    }
}
