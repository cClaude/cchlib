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
    //private final static transient Logger logger = Logger.getLogger( DownloadToFile.class );
    private DownloadFilterInputStreamBuilder downloadFilterBuilder;

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

    @SuppressWarnings("resource")
    @Override
    protected void download( final InputStream inputStream )
            throws DownloadIOException, IOException
    {
        final File                  file = DownloadFileEvent.class.cast( getDownloadEvent() ).createDownloadTmpFile();
        final DownloadFileURL       dURL = DownloadFileURL.class.cast( getDownloadURL() );
        final InputStream           is;
        final FilterInputStream     filter;
        
        if( downloadFilterBuilder != null ) {
            is = filter = downloadFilterBuilder.createFilterInputStream( inputStream );
            }
        else {
            is     = inputStream;
            filter = null;
            }

        try {
            IOHelper.copy( is, file );
            
            if( filter != null ) {
                filter.close(); // Needed ???
                
                downloadFilterBuilder.storeFilterResult( filter, dURL );
                }
            }
        catch( IOException e ) {
            throw new DownloadIOException( getDownloadURL(), file, e );
            }
        finally {
            filter.close(); // Needed ???
            }

        dURL.setResultAsFile( file );
    }
}
