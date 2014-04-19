package com.googlecode.cchlib.apps.emptyfiles.bean;

import com.googlecode.cchlib.apps.emptyfiles.interfaces.FileInfoFormater;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class FileInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private boolean selected;
    //private Date lastModifiedDate;
    //private boolean deleted;
    //private String lengthString;
    //private String fileAttributsString;
    private final File file;
    private final FileInfoFormater fileInfoFormater;

     /**
     * 
     */
    public FileInfo( File file, boolean selected, FileInfoFormater fileInfoFormater )
    {
        this.file             = file;
        this.selected         = selected;
        this.fileInfoFormater = fileInfoFormater;
        //this.lastModifiedDate    = new Date( file.lastModified() );
        //this.fileAttributsString = fileInfoFormater.formatAttributs( file );
        //this.lengthString        = fileInfoFormater.formatLength( file );
        //this.deleted = false;
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

    public void setSelected( boolean selected )
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

//    public void setDeleted( boolean deleted )
//    {
//        this.deleted = deleted;
//    }

    public String getLengthString()
    {
        return fileInfoFormater.formatLength( file );
    }

//    public void setLengthString( String lengthString )
//    {
//        this.lengthString = lengthString;
//    }

    public String getFileAttributsString()
    {
        return fileInfoFormater.formatAttributs( file );
    }
    
//    public void setFileAttributsString( String fileAttributsString )
//    {
//        this.fileAttributsString = fileAttributsString;
//    }
}
