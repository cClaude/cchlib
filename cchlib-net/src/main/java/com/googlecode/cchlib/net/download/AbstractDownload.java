package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 * Abstract Downloader for {@link DownloadURI}
 *
 * @param <R>
 *            Result expected type for download
 *
 * @since 4.1.5
 */
//NOT public
abstract class AbstractDownload<R> implements RunnableDownload<R>
{
    private static final Logger LOGGER = Logger.getLogger( AbstractDownload.class );

    // Use getDownloadURL() to access to this object
    private final ContentDownloadURI<R> downloader;
    // Use getDownloadEvent() to access to this object
    private final DownloadEvent event;

    /**
     * Create a download task using a proxy
     *
     * @param downloader            A valid {@link ContentDownloadURI}.
     * @param event                 A valid {@link DownloadEvent}.
     * @throws NullPointerException if one of event or downloadURL parameters is null.
     */
    public AbstractDownload(
            final ContentDownloadURI<R>   downloader,
            final DownloadEvent           event
            )
    {
        this.event          = event;
        this.downloader     = downloader;

        if( event == null ) {
            throw new NullPointerException( "Not valid DownloadEvent" );
            }
        if( downloader == null ) {
            throw new NullPointerException( "Not DownloadURL DownloadEvent" );
            }
    }

    @Override
    public final void run()
    {
        try {
            getDownloadEvent().downloadStart( getDownloadURL() );

            download();
            }
        catch( final Exception e ) {
            LOGGER.fatal( "Unhandled exception", e );
            }
    }

    private void download()
    {
        try( final InputStream is = getDownloadURL().getInputStream() ) {
            download( is );

            getDownloadEvent().downloadDone( getDownloadURL() );
            }
        catch( final DownloadIOException e ) {
            getDownloadEvent().downloadFail( e );
            }
        catch( final Exception e ) {
            getDownloadEvent().downloadFail( new DownloadIOException( getDownloadURL(), e ) );
            }
    }

    @Override
    public final ContentDownloadURI<R> getDownloadURL()
    {
        return this.downloader;
    }

   /**
     * Must update {@link #getDownloadURL()} content to set result
     *
     * @param inputStream {@link InputStream} based on URL.
     * @throws DownloadIOException if any I/O error occur on download stream
     */
    protected abstract void download( InputStream inputStream )
            throws IOException, DownloadIOException;

    /**
     * Returns {@link DownloadEvent} for this {@link AbstractDownload}
     * @return {@link DownloadEvent} for this {@link AbstractDownload}
     */
    protected final DownloadEvent getDownloadEvent()
    {
        return this.event;
    }
}
