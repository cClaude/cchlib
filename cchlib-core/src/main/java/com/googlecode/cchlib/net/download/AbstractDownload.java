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
    private DownloadEvent event;
    private final Proxy proxy;
    private final URL   url;

    /**
     * Create a download task using a proxy
     * @param event
     *
     * @param proxy {@link Proxy} to use for download (could be null)
     * @param url   {@link URL} for download
     */
    public AbstractDownload( DownloadEvent event, final Proxy proxy, final URL url )
    {
        this.event  = event;
        this.proxy  = proxy;
        this.url    = url;
    }

    /**
     * Returns {@link InputStream} for internal URL
     * @return {@link InputStream} for internal URL
     * @throws IOException if any
     */
    final // FIXME: remove this
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
    final // FIXME: remove this
    public void run()
    {
        event.downloadStart( getURL() );

        try {
            InputStream is = getInputStream();

            try {
                DownloadResult result_string_or_file = download( is );

                event.downloadDone( getURL(), result_string_or_file );
                }
            finally {
                is.close();
                }
            }
        catch( DownloadIOException e ) {
            event.downloadFail( e );
            }
        catch( Exception e ) {
            event.downloadFail( new DownloadIOException( getURL(), e ) );
            }
    }

    /**
     * TODOC
     *
     * @param inputStream
     * @return
     * @throws DownloadIOException
     */
    abstract protected DownloadResult download( InputStream inputStream )
            throws IOException, DownloadIOException;

    /**
     *
     * @return
     */
    final // FIXME: remove this
    protected DownloadEvent getDownloadEvent() { return event; };

    @Override
    final // FIXME: remove this
    public URL getURL()
    {
        return url;
    }
}
