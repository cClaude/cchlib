package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.util.EventObject;
import java.util.zip.ZipEntry;

/**
 * UnZip event
 */
public class UnZipEvent extends EventObject
{
    private static final long serialVersionUID = 1L;
    private File file;
    private ZipEntry zipEntry; // NOT SERIALISABLE !

    /**
     * Create UnZipEvent
     * @param source {@link ZipEntry} for this event
     * @param file     {@link File} for this event
     */
    public UnZipEvent( ZipEntry source, File file )
    {
        super( source );

        this.zipEntry = source;
        this.file     = file;
    }

    /**
     * @return {@link File} for this event
     */
    public File getFile()
    {
        return this.file;
    }

    /**
     * @return {@link ZipEntry} for this event
     */
    public ZipEntry getZipEntry()
    {
        return this.zipEntry;
    }
}
