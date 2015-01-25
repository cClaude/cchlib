package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.annotation.Nullable;
import com.googlecode.cchlib.Beta;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.net.download.fis.DownloadFilterInputStreamBuilder;

/**
 * Download {@link URL} and put result into a {@link File}
 *
 * @since 4.1.5
 */
@Beta
public class DownloadToFile extends AbstractDownload
{
    private static final class MyInputStream extends InputStream {

        private final DownloadFilterInputStreamBuilder downloadFilterBuilder;
        private final FilterInputStream filter;
        private final InputStream inputStream;

        public MyInputStream( @Nullable final DownloadFilterInputStreamBuilder downloadFilterBuilder, final InputStream inputStream )
        {
            this.downloadFilterBuilder = downloadFilterBuilder;

            if( downloadFilterBuilder != null ) {
                this.filter = downloadFilterBuilder.createFilterInputStream( inputStream );
                this.inputStream = this.filter;
                }
            else {
                this.filter = null;
                this.inputStream = inputStream;
               }
       }

        @Override
        public int read() throws IOException
        {
            return this.inputStream.read();
        }

        @Override
        public int read(final byte[] b ) throws IOException
        {
            return this.inputStream.read( b );
        }

        @Override
        public int read(final byte[] b, final int off, final int len ) throws IOException
        {
            return this.inputStream.read( b, off, len );
        }

        @Override
        public void close() throws IOException
        {
            if( this.filter != null ) {
                this.filter.close();
            }
        }

        public void storeFilterResult( final DownloadFileURL dURL )
        {
            if( this.downloadFilterBuilder != null ) {
                this.downloadFilterBuilder.storeFilterResult( this.filter, dURL );
            }
        }
    }

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

        // TODO : To test

        final MyInputStream closedMis;

        try( final MyInputStream mis = new MyInputStream( this.downloadFilterBuilder, inputStream )) {
            IOHelper.copy( mis, file );
            closedMis = mis;
            }
        catch( final IOException e ) {
            throw new DownloadIOException( getDownloadURL(), file, e );
            }

        storeLocalFile( dURL, closedMis );

        dURL.setResultAsFile( file );
    }

    private void storeLocalFile( final DownloadFileURL dURL, final MyInputStream closedMis )
    {
        // InputStream must be close to be store.
        closedMis.storeFilterResult( dURL );
    }
}
