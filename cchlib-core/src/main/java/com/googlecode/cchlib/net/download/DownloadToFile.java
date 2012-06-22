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
    public DownloadToFile(
            DownloadEvent eventHandler,
            Proxy proxy,
            DownloadURL downloadURL )
    {
        super( eventHandler, proxy, downloadURL );
    }

    @Override
    protected void download( final InputStream inputStream )
            throws DownloadIOException, IOException
    {
        final File file = DownloadFileEvent.class.cast( getDownloadEvent() ).createDownloadTmpFile();

        try {
            IOHelper.copy( inputStream, file );
            }
        catch( IOException e ) {
            throw new DownloadIOException( getDownloadURL(), file, e );
            }

        getDownloadURL().setResultAsFile( file );
    }
}
