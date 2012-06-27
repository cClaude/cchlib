package com.googlecode.cchlib.net.download;

import java.io.Serializable;
import java.net.URL;

/**
 * Default implementation of {@link DownloadURL}
 *
 * @since 4.1.7
 */
public abstract class AbstractDownloadURL implements DownloadURL, Serializable
{
    private static final long serialVersionUID = 1L;
    private final URL url;

    /**
     * Create an AbstractDownloadURL using giving {@link URL}
     *
     * @param url {@link URL} for this AbstractDownloadURL
     */
    public AbstractDownloadURL( final URL url )
    {
        this.url = url;
    }

    @Override
    public URL getURL()
    {
        return url;
    }

}
