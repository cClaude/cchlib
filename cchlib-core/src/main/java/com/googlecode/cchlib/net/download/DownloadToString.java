package com.googlecode.cchlib.net.download;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
     * @param eventHandler  Event to use for notifications
     * @param proxy
     * @param downloadURL
     */
    public DownloadToString(
            final DownloadURL           downloadURL,
            final DownloadEvent         eventHandler
//            final Map<String,String>    requestPropertyMap,
//            final Proxy                 proxy
            )
    {
        super( downloadURL, eventHandler/*, requestPropertyMap, proxy*/);
    }

    @Override
    protected void download( InputStream inputStream )
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

        getDownloadURL().setResultAsString( buffer.toString() );
    }
}
