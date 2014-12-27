package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.net.download.fis.DownloadFilterInputStreamBuilder;

/**
 * Download {@link URL} and put result into a {@link File}
 *
 * @since 4.1.5
 */
public class DownloadToFile extends AbstractDownload
{
    private final DownloadFilterInputStreamBuilder downloadFilterBuilder;

    /**
     * Create a new DownloadToFile
     *
     * @param downloadURL
     * @param fileEventHandler
     * @param downloadFilterBuilder
     */
    public DownloadToFile(
        final DownloadFileURL                   downloadURL,
        final DownloadFileEvent                 fileEventHandler,
        final DownloadFilterInputStreamBuilder  downloadFilterBuilder
        )
    {
        super( downloadURL, fileEventHandler );

        this.downloadFilterBuilder = downloadFilterBuilder;
    }

    @Override
    protected void download( final InputStream inputStream )
            throws DownloadIOException, IOException
    {
        final File                  file = DownloadFileEvent.class.cast( getDownloadEvent() ).createDownloadTmpFile();
        final DownloadFileURL       dURL = DownloadFileURL.class.cast( getDownloadURL() );
        final InputStream           is;
        final FilterInputStream     filter;

        if( this.downloadFilterBuilder != null ) {
            is = filter = this.downloadFilterBuilder.createFilterInputStream( inputStream );
            }
        else {
            is     = inputStream;
            filter = null;
            }

        try {
            IOHelper.copy( is, file );

            if( filter != null ) {
                filter.close(); // Needed ???

                this.downloadFilterBuilder.storeFilterResult( filter, dURL );
                }
            }
        catch( final IOException e ) {
            throw new DownloadIOException( getDownloadURL(), file, e );
            }
        finally {
            if( filter != null ) {
                filter.close(); // Needed ???
                }
            }

        dURL.setResultAsFile( file );
    }
}
