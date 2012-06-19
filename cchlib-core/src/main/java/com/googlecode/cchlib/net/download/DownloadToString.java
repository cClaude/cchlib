package com.googlecode.cchlib.net.download;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
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
    /**
     * Create a download task for {@link String}
     *
     * @param event Event to use for notifications
     * @param url   {@link URL} for download
     */
    public DownloadToString( final DownloadEvent event, final Proxy proxy, final URL url )
    {
        super( event, proxy, url );
    }

    @Override
    protected DownloadResult download( InputStream inputStream )
            throws IOException, DownloadIOException
    {
        CharArrayWriter buffer = new CharArrayWriter();
        Reader          r      = new InputStreamReader( inputStream );

        try {
            IOHelper.copy( r, buffer );
            }
        finally {
            r.close();
            }

        return new DefaultDownloadResult( buffer.toString() );
    }
}
