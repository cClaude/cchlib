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
 *
 * @since 4.1.5
 */
public class DownloadToString extends AbstractDownload
{
    /**
     * Create a download task for {@link String}
     * @param downloadURL   DownloadURL to find this string.
     * @param eventHandler  Event to use for notifications
     */
    public DownloadToString(
            final DownloadStringURL downloadURL,
            final DownloadEvent     eventHandler
            )
    {
        super( downloadURL, eventHandler );
    }

    @Override
    protected void download( final InputStream inputStream )
            throws IOException, DownloadIOException // $codepro.audit.disable unnecessaryExceptions
    {
        final CharArrayWriter buffer = new CharArrayWriter();

        try( final Reader r = new InputStreamReader( inputStream ) ) {
            IOHelper.copy( r, buffer );
            }

        final DownloadStringURL dURL = DownloadStringURL.class.cast( getDownloadURL() );

        dURL.setResultAsString( buffer.toString() );
    }
}
