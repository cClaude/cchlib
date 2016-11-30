package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.zip.ZipEntry;
import com.googlecode.cchlib.VisibleForTesting;

/**
 * UnZip event
 */
public class UnZipEvent extends EventObject
{
    private static final long serialVersionUID = 1L;

    private final File file;

    private transient ZipEntry zipEntry;

    /**
     * Create UnZipEvent
     *
     * @param source {@link ZipEntry} for this event
     * @param file Related {@link File} for this event
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
     * <b>Warning</b> if {@link UnZipEvent} need to be serialized
     * could return null, use {@link #getZipEntry(ZipEntryBuilder)} if
     * {@link ZipEntry} is really needed.
     *
     * @return {@link ZipEntry} for this event
     */
    @VisibleForTesting
    ZipEntry getZipEntry()
    {
        return this.zipEntry;
    }

    /**
     * @return {@link ZipEntry} for this event
     * @throws IOException if any I/O occur
     * @see ZipEntryBuilder#computeReference(File)
     * @see ZipEntryBuilder
     */
    @VisibleForTesting
    ZipEntry getZipEntry( final ZipEntryBuilder builder )
        throws IOException
    {
        if( this.zipEntry == null ) {
            this.zipEntry = builder.newZipEntry( this.file );
        }
        return this.zipEntry;
    }
}
