package com.googlecode.cchlib.net.download;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.net.URL;
import cx.ath.choisnet.net.URLHelper;

/**
 * Download {@link URL} and put result into a {@link String}
 * @since 4.1.5
 */
public class DownloadToString implements Runnable
{
    private final DownloadStringEvent   event;
    private final URL                   url;

    /**
     * Create a download task for {@link String}
     *
     * @param event Event to use for notifications
     * @param url   {@link URL} for download
     */
    public DownloadToString( final DownloadStringEvent event, final URL url )
    {
        this.event  = event;
        this.url     = url;
    }

    @Override
    public void run()
    {
        CharArrayWriter buffer = new CharArrayWriter();

        this.event.downloadStart( url );

        try {
            URLHelper.copy( url, buffer );

            this.event.downloadDone( url, buffer.toString() );
            }
        catch( IOException e ) {
            this.event.downloadFail( url, e );
            }
    }
}