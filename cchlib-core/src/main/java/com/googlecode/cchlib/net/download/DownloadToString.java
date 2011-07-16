package com.googlecode.cchlib.net.download;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Proxy;
import java.net.URL;
import com.googlecode.cchlib.io.IOHelper;

/**
 * Download {@link URL} and put result into a {@link String}
 * @since 4.1.5
 */
public class DownloadToString extends AbstractDownload
{
    private final DownloadStringEvent event;

//    /**
//     * Create a download task for {@link String}
//     *
//     * @param event Event to use for notifications
//     * @param url   {@link URL} for download
//     */
//    public DownloadToString( final DownloadStringEvent event, final URL url )
//    {
//        this( event, null, url );
//    }

    /**
     * Create a download task for {@link String}
     *
     * @param event Event to use for notifications
     * @param url   {@link URL} for download
     */
    public DownloadToString( final DownloadStringEvent event, final Proxy proxy, final URL url )
    {
        super( proxy, url );

        this.event  = event;
    }

    @Override
    public void run()
    {
        CharArrayWriter buffer = new CharArrayWriter();

        this.event.downloadStart( getURL() );

        try {
            Reader r = new InputStreamReader( super.getInputStream() );

            try {
                IOHelper.copy( r, buffer );
                }
            finally {
                r.close();
                }

            this.event.downloadDone( getURL(), buffer.toString() );
            }
        catch( IOException e ) {
            this.event.downloadFail( getURL(), e );
            }
    }
}
