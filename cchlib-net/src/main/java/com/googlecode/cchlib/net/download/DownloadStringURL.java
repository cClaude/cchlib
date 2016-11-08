package com.googlecode.cchlib.net.download;

/**
 * a {@link DownloadURL} with a result into {@link String}
 *
 * @since 4.1.7
 */
public interface DownloadStringURL extends DownloadURL
{
    /**
     * Returns {@link DownloadURL} result stored in string
     * @return {@link String} with content of this {@link DownloadURL}
     */
    String getResultAsString();

    /**
     * Set {@link String} for this {@link DownloadURL}
     * @param string with download content
     */
    void setResultAsString( String string );
}
