package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.util.EventObject;
import java.util.zip.ZipEntry;

/**
 * TODOC
 */
public class UnZipEvent extends EventObject
{
    private static final long serialVersionUID = 1L;
    private File file;
    private ZipEntry zipEntry;

    public UnZipEvent( ZipEntry source, File file )
    {
        super( source );
        
        this.zipEntry = source;
        this.file     = file;
    }

    /**
     * TODOC
     * @return TODOC
     */
    public File getFile()
    {
        return this.file;
    }

    /**
     * TODOC
     * @return TODOC
     */
    public ZipEntry getZipEntry()
    {
        return this.zipEntry;
    }
}
