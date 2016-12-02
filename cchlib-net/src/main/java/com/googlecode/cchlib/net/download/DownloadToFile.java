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
public class DownloadToFile extends AbstractDownload<File>
{
    private static final class MyInputStream extends InputStream
    {
        private final DownloadFilterInputStreamBuilder<File> downloadFilterBuilder;
        private final FilterInputStream                filter;
        private final InputStream                      inputStream;

        public MyInputStream(
            @Nullable
            final DownloadFilterInputStreamBuilder<File> downloadFilterBuilder,
            final InputStream inputStream
            )
        {
            this.downloadFilterBuilder = downloadFilterBuilder;

            if( downloadFilterBuilder != null ) {
                this.filter      = downloadFilterBuilder.createFilterInputStream( inputStream );
                this.inputStream = this.filter;
                }
            else {
                this.filter      = null;
                this.inputStream = inputStream;
               }
       }

        @Override
        public int read() throws IOException
        {
            return this.inputStream.read();
        }

        @Override
        public int read( final byte[] b ) throws IOException
        {
            return this.inputStream.read( b );
        }

        @Override
        public int read( final byte[] b, final int off, final int len )
            throws IOException
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

        public void storeFilterResult( final ContentDownloadURI<File> dURL )
        {
            if( this.downloadFilterBuilder != null ) {
                this.downloadFilterBuilder.storeFilterResult( this.filter, dURL );
            }
        }
    }

    private final DownloadFilterInputStreamBuilder<File> downloadFilterBuilder;

    /**
     * Create a new DownloadToFile
     *
     * @param downloadURL NEEDDOC
     * @param fileEventHandler NEEDDOC
     * @param downloadFilterBuilder NEEDDOC
     */
    public DownloadToFile(
        final ContentDownloadURI<File>          downloadURL,
        final DownloadFileEvent                 fileEventHandler,
        final DownloadFilterInputStreamBuilder<File>  downloadFilterBuilder
        )
    {
        super( downloadURL, fileEventHandler );

        this.downloadFilterBuilder = downloadFilterBuilder;
    }

    @Override
    protected void download( final InputStream inputStream )
            throws DownloadIOException, IOException
    {
        final File  file = DownloadFileEvent.class.cast( getDownloadEvent() ).createDownloadTmpFile();

        // TODO : To test

        final MyInputStream closedMis;

        try( final MyInputStream mis = new MyInputStream( this.downloadFilterBuilder, inputStream )) {
            IOHelper.copy( mis, file );
            closedMis = mis;
            }
        catch( final IOException e ) {
            throw new DownloadIOException( getDownloadURL(), file, e );
            }

        final ContentDownloadURI<File> dURL = getDownloadURL();

        storeLocalFile( dURL, closedMis );

        dURL.setResult( file );
    }

    private void storeLocalFile(
        final ContentDownloadURI<File> dURL,
        final MyInputStream            closedMis
        )
    {
        // InputStream must be close to be store.
        closedMis.storeFilterResult( dURL );
    }
}
