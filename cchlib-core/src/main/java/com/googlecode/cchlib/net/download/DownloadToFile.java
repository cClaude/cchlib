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
    /**
     * @throws UnsupportedDownloadEventTypeException
     */
    public DownloadToFile( final DownloadEvent event, final Proxy proxy, final URL url )
    {
        super( event, proxy, url );

        if( ! event.getDownloadResultType().equals( DownloadResultType.FILE ) ) {
            throw new UnsupportedDownloadEventTypeException();
            }

        if( ! (event instanceof DownloadFileEvent) ) {
            throw new UnsupportedDownloadEventTypeException();
            }
    }

    protected DownloadResult download( InputStream inputStream )
            throws DownloadIOException, IOException
    {
        final File file = DownloadFileEvent.class.cast( getDownloadEvent() ).getDownloadTmpFile();

        try {
            IOHelper.copy( inputStream, file );
            }
        catch( IOException e ) {
            throw new DownloadIOException( getURL(), file, e );
            }

        return new DefaultDownloadResult( file );
    }
}
