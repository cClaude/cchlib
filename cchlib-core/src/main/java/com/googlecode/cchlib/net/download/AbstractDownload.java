package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Abstract Downloader
 * @since 4.1.5
 */
//public
abstract class AbstractDownload implements RunnableDownload
{
    private final Proxy proxy;
    private final URL   url;

    /**
     * Create a download task using a proxy
     *
     * @param proxy {@link Proxy} to use for download (could be null)
     * @param url   {@link URL} for download
     */
    public AbstractDownload( final Proxy proxy, final URL url )
    {
        this.proxy  = proxy;
        this.url    = url;
    }

//    /**
//     * Create a download task with no proxy
//     *
//     * @param url   {@link URL} for download
//     */
//    public AbstractDownload( final URL url )
//    {
//        this( null, url );
//    }

    /**
     * Returns {@link InputStream} for internal URL
     * @return {@link InputStream} for internal URL
     * @throws IOException if any
     */
    final
    protected InputStream getInputStream() throws IOException
    {
        if( proxy == null ) {
            return url.openStream();
            }
        else {
            HttpURLConnection uc = HttpURLConnection.class.cast( url.openConnection( proxy ) );
            uc.connect();

            return uc.getInputStream();
        }
    }

    @Override
    final
    public URL getURL()
    {
        return url;
    }
}
