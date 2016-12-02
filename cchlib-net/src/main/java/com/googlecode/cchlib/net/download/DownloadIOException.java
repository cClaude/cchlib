package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

/**
 * Exception generate when a error occur during download process
 *
 * @since 4.1.7
 */
public class DownloadIOException extends Exception
{
    private static final long serialVersionUID = 1L;

    private final DownloadURI downloadURL;
    private final File        file;

    /**
     * Create a DownloadIOException
     *
     * @param downloadURL
     *            The related {@link DownloadURI}
     * @param cause
     *            The cause
     */
    public DownloadIOException(
            final DownloadURI downloadURL,
            final Exception   cause
            )
    {
        super( downloadURL.getURL().toExternalForm(), cause );

        this.downloadURL = downloadURL;
        this.file        = null;
    }

    /**
     * Create a DownloadIOException
     *
     * @param downloadURL
     *            The related {@link DownloadURI}
     * @param file
     *            The local {@link File}
     * @param cause
     *            The cause
     */
    public DownloadIOException(
        final DownloadURI downloadURL,
        final File        file,
        final IOException cause
        )
    {
        super( downloadURL.getURL().toExternalForm(), cause );

        this.downloadURL = downloadURL;
        this.file        = file;
    }

    /**
     * @return the {@link DownloadURI} that cause this exception
     */
    public DownloadURI getDownloadURL()
    {
        return this.downloadURL;
    }

    /**
     * @return the related file (may be a temporary file) or null
     */
    @Nullable
    public File getFile()
    {
        return this.file;
    }
}
