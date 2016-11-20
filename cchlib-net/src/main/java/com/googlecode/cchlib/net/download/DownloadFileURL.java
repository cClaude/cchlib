package com.googlecode.cchlib.net.download;

import java.io.File;

/**
 * a {@link DownloadURL} with a result into {@link File}
 *
 * @since 4.1.7
 */
public interface DownloadFileURL extends DownloadURL
{
    /**
     * Returns {@link DownloadURL} result stored in {@link File}
     * @return {@link File} object for {@link DownloadURL} result
     */
    File getResultAsFile();

    /**
     * Set {@link File} for this {@link DownloadURL}
     * @param file {@link File} to set
     */
    void setResultAsFile( File file );

    /**
     * Set custom property on this {@link DownloadURL}
     *
     * @param name  Name of property to set
     * @param value Property value
     */
    void setProperty( String name, Object value );

    /**
     * Get custom property
     *
     * @param name  Name of property to retrieve
     * @return property value or null if property does not exist
     */
    Object getProperty( String name );
}
