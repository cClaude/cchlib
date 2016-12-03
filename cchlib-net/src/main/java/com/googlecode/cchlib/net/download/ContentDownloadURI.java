package com.googlecode.cchlib.net.download;

import java.io.File;

/**
 * a {@link DownloadURI} with a result into {@link File}
 *
 * @param <R>
 *            Result expected type for download
 *
 * @since 4.1.7
 */
public interface ContentDownloadURI<R> extends DownloadURI
{
    /**
     * Returns content type
     * @return content type
     */
    Class<R> getType();

    /**
     * Returns {@link DownloadURI} result stored in {@link File}
     *
     * @return {@link File} object for {@link DownloadURI} result
     */
    R getResult();

    /**
     * Setter result for this {@link DownloadURI}
     *
     * @param resultContener
     *            Container to set
     */
    void setResult( R resultContener );

    /**
     * Set custom property on this {@link DownloadURI}
     *
     * @param name
     *            Name of property to set
     * @param value
     *            Property value
     * @throws UnsupportedOperationException
     *             if not supported
     */
    void setProperty( String name, Object value );

    /**
     * Get custom property
     *
     * @param name
     *            Name of property to retrieve
     * @return property value or null if property does not exist
     * @throws UnsupportedOperationException
     *             if not supported
     */
    Object getProperty( String name );

    /**
     * Get custom property has a String value
     *
     * @param name
     *            Name of property to retrieve
     * @return property value or null if property does not exist
     * @throws UnsupportedOperationException
     *             if not supported
     */
    String getStringProperty( String name );
}
