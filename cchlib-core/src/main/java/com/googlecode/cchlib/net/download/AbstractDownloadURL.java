package com.googlecode.cchlib.net.download;

import java.net.URL;

/**
 *
 * @since 4.1.7
 */
public abstract class AbstractDownloadURL implements DownloadURL
{
    private final URL url;

    /**
     *
     * @param file
     */
    public AbstractDownloadURL( final URL url )
    {
        this.url = url;
    }

    @Override
    final // FIXME remove this
    public URL getURL()
    {
        return url;
    }
}
