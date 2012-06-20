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
//NOT public
abstract class AbstractDownload implements RunnableDownload
{
    private DownloadEvent event;
    private final Proxy proxy;
    //private final URL   url;
    private DownloadURL downloadURL;

    /*
     * Create a download task using a proxy
     * @param event
     *
     * @param proxy {@link Proxy} to use for download (could be null)
     * @param url   {@link URL} for download
     *
    public AbstractDownload( DownloadEvent event, final Proxy proxy, final URL url )
    {
        this.event  = event;
        this.proxy  = proxy;
        this.url    = url;
    }*/

    /**
     * Create a download task using a proxy
     *
     * @param event
     * @param proxy     {@link Proxy} to use for download (could be null)
     * @param downloadURL
     */
    public AbstractDownload(
            final DownloadEvent event,
            final Proxy         proxy,
            final DownloadURL   downloadURL
            )
    {
        this.event          = event;
        this.proxy          = proxy;
        this.downloadURL    = downloadURL;
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
            return downloadURL.getURL().openStream();
            }
        else {
            HttpURLConnection uc = HttpURLConnection.class.cast( downloadURL.getURL().openConnection( proxy ) );
            uc.connect();

            return uc.getInputStream();
        }
    }

    @Override
    final // FIXME: remove this
    public void run()
    {
        event.downloadStart( downloadURL );

        try {
            InputStream is = getInputStream();

            try {
                /*DownloadResult result_string_or_file =*/ download( is );

                event.downloadDone( downloadURL /* getURL(), result_string_or_file*/ );
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
     *
     */
    @Override
    public final URL getURL()
    {
        return this.downloadURL.getURL();
    }

   /**
     * Must update {@link #getDownloadURL()} content to set result
     *
     * @param inputStream {@link InputStream} based on URL.
     * @throws DownloadIOException
     */
    abstract protected void download( InputStream inputStream )
            throws IOException, DownloadIOException;

    /**
     *
     * @return
     */
    protected final DownloadEvent getDownloadEvent() { return event; };

    /**
     *
     * @return
     */
    public final DownloadURL getDownloadURL()
    {
        return this.downloadURL;
    }
}
