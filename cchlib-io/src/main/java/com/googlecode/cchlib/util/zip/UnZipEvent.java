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
    private final File file;
    private transient ZipEntry zipEntry; // NOT SERIALISABLE !
    //TODO: should be serialisable, really not urgent...

    /**
     * Create UnZipEvent
     * @param source {@link ZipEntry} for this event
     * @param file {@link File} for this event
     */
    public UnZipEvent( final ZipEntry source, final File file )
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
