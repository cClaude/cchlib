package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;

/**
 * Exception generate when a error occur during download process
 *
 * @since 4.1.7
 */
public class DownloadIOException extends Exception
{
    private static final long serialVersionUID = 1L;
    private final DownloadURL downloadURL;
    private final File file;

    /**
     * NEEDDOC
     *
     * @param downloadURL
     * @param cause
     */
    public DownloadIOException( final DownloadURL downloadURL, final Throwable cause )
    {
        super( downloadURL.getURL().toExternalForm(), cause );

        this.downloadURL = downloadURL;
        this.file        = null;
    }

    /**
     * NEEDDOC
     *
     * @param downloadURL
     * @param file
     * @param cause
     */
    public DownloadIOException( final DownloadURL downloadURL, final File file, final IOException cause )
    {
        super( downloadURL.getURL().toExternalForm(), cause );

        this.downloadURL = downloadURL;
        this.file        = file;
    }

    /**
     * @return the {@link DownloadURL} that cause this exception
     */
    public DownloadURL getDownloadURL()
    {
        return this.downloadURL;
    }

    /**
     * @return the temporary file if exist or null
     */
    public File getFile()
    {
        return this.file;
    }
}
