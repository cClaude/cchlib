package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 * Abstract Downloader for {@link DownloadURL}
 *
 * @since 4.1.5
 */
//NOT public
abstract class AbstractDownload implements RunnableDownload
{
    private static final Logger logger = Logger.getLogger( AbstractDownload.class );
    // Use getDownloadURL() to access to this object
    private final DownloadURL downloadURL;
    // Use getDownloadEvent() to access to this object
    private final DownloadEvent event;

    /**
     * Create a download task using a proxy
     *
     * @param downloadURL           A valid {@link DownloadURL}.
     * @param event                 A valid {@link DownloadEvent}.
     * @throws NullPointerException if one of event or downloadURL parameters is null.
     */
    public AbstractDownload(
            final DownloadURL   downloadURL,
            final DownloadEvent event
            )
    {
        this.event          = event;
        this.downloadURL    = downloadURL;

        if( event == null ) {
            throw new NullPointerException( "Not valid DownloadEvent" );
            }
        if( downloadURL == null ) {
            throw new NullPointerException( "Not DownloadURL DownloadEvent" );
            }
    }

    @Override
    final public void run()
    {
        try {
            getDownloadEvent().downloadStart( getDownloadURL() );

            try {
                final InputStream is = getDownloadURL().getInputStream();

                try {
                    download( is );

                    getDownloadEvent().downloadDone( getDownloadURL() );
                    }
                finally {
                    is.close();
                    }
                }
            catch( DownloadIOException e ) {
                getDownloadEvent().downloadFail( e );
                }
            catch( Exception e ) {
                getDownloadEvent().downloadFail( new DownloadIOException( getDownloadURL(), e ) );
                }
            }
        catch( Exception e ) {
            logger.fatal( "Unhandled exception", e );
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
    protected abstract void download( InputStream inputStream )
            throws IOException, DownloadIOException;

    /**
     * Returns {@link DownloadEvent} for this {@link AbstractDownload}
     * @return {@link DownloadEvent} for this {@link AbstractDownload}
     */
    protected final DownloadEvent getDownloadEvent() 
    {
        return event; 
    }
}
