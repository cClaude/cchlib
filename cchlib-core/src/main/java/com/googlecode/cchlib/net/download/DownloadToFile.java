package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import cx.ath.choisnet.net.URLHelper;

/**
 * Download {@link URL} and put result into a {@link File}
 * @since 4.1.5
 */
public class DownloadToFile implements RunnableDownload
{
    private final DownloadFileEvent event;
    private final URL url;

    /**
     * Create a download task for {@link File}
     *
     * @param event Event to use for notifications
     * @param url   {@link URL} for download
     */
    public DownloadToFile( final DownloadFileEvent event, final URL url )
    {
        this.event  = event;
        this.url    = url;
    }

    @Override
    public void run()
    {
        try {
            download();
            }
        catch( IOException e ) {
            event.downloadFail( url, e );
            }
    }

    private void download() throws IOException
    {
        final File file = event.downloadStart( url );

        URLHelper.copy( url, file );

        this.event.downloadDone( url, file );
    }

    @Override
    public URL getURL()
    {
        return url;
    }
}