package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URLConnection;
import org.apache.log4j.Logger;

/**
 * Abstract Downloader for {@link DownloadURL}
 *
 * @since 4.1.5
 */
//NOT public
abstract class AbstractDownload implements RunnableDownload
{
    private final static transient Logger logger = Logger.getLogger( AbstractDownload.class );
    private DownloadEvent event;
    private final Proxy proxy;
    private DownloadURL downloadURL;

    /**
     * Create a download task using a proxy
     *
     * @param event         A valid {@link DownloadEvent}.
     * @param proxy         {@link Proxy} to use for download (could be null)
     * @param downloadURL   A valid {@link DownloadURL}.
     * @throws NullPointerException if one of event or downloadURL parameters is null.
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

        if( event == null ) {
            throw new NullPointerException( "Not valid DownloadEvent" );
            }
        if( downloadURL == null ) {
            throw new NullPointerException( "Not DownloadURL DownloadEvent" );
            }
    }

//    /**
//     * Returns {@link InputStream} for internal URL
//     * @return {@link InputStream} for internal URL
//     * @throws IOException if any
//     */
//    final protected InputStream getInputStream() throws IOException
//    {
//        if( proxy == null ) {
//            return downloadURL.getURL().openStream();
//            }
//        else {
//            HttpURLConnection uc = HttpURLConnection.class.cast( downloadURL.getURL().openConnection( proxy ) );
//            uc.connect();
//
//            return uc.getInputStream();
//        }
//    }

    /**
     * Returns {@link URLConnection} for {@link DownloadURL}
     * @return {@link URLConnection} for {@link DownloadURL}
     * @throws IOException if any
     */
    final protected URLConnection getURLConnection() throws IOException
    {
        if( proxy == null ) {
            //return downloadURL.getURL().openStream();
            return downloadURL.getURL().openConnection();
            }
        else {
            return downloadURL.getURL().openConnection( proxy );
//            HttpURLConnection uc = HttpURLConnection.class.cast( downloadURL.getURL().openConnection( proxy ) );
//            uc.connect();
//
//
//            return uc.getInputStream();
            }
    }

    @Override
    final public void run()
    {
        event.downloadStart( downloadURL );

        try {
            //InputStream is = getInputStream();
            URLConnection uc = getURLConnection();

            if( logger.isTraceEnabled() ) {
                logger.trace( "URLConnection: " + uc );
                }

            uc.connect();
            InputStream is = uc.getInputStream();

            try {
                download( is );

                event.downloadDone( downloadURL );
                }
            finally {
                is.close();
                }
            }
        catch( DownloadIOException e ) {
            event.downloadFail( e );
            }
        catch( Exception e ) {
            event.downloadFail( new DownloadIOException( getDownloadURL(), e ) );
            }
    }

    @Override
    final public DownloadURL getDownloadURL()
    {
        return this.downloadURL;
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
     * @return {@link DownloadEvent} for this
     */
    protected final DownloadEvent getDownloadEvent() { return event; };
}
