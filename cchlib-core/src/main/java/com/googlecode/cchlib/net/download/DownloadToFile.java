package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import com.googlecode.cchlib.io.IOHelper;

/**
 * Download {@link URL} and put result into a {@link File}
 * @since 4.1.5
 */
public class DownloadToFile extends AbstractDownload
{
    private final DownloadFileEvent event;

//    /**
//     * Create a download task for {@link File}
//     *
//     * @param event Event to use for notifications
//     * @param url   {@link URL} for download
//     */
//    public DownloadToFile( final DownloadFileEvent event, final URL url )
//    {
//        this( event, null,  url );
//    }

    /**
     * Create a download task for {@link File}
     *
     * @param event Event to use for notifications
     * @param proxy {@link Proxy} to use for download (could be null)
     * @param url   {@link URL} for download
     */
    public DownloadToFile( final DownloadFileEvent event, final Proxy proxy, final URL url )
    {
        super( proxy, url );

        this.event  = event;
    }

    @Override
    public void run()
    {
        download();
    }

    private void download()
    {
        try {
            final File file = event.downloadStart( getURL() );

            try {
                download( file );
                }
            catch( IOException e ) {
                event.downloadFail( getURL(), file, e );
                }
            }
        catch( IOException e ) {
            event.downloadFail( getURL(), e );
            }
    }

    private void download( final File file ) throws IOException
    {
        InputStream is = super.getInputStream();

        try {
            IOHelper.copy( is, file );
            }
        finally {
            is.close();
            }

        this.event.downloadDone( getURL(), file );
    }
}
