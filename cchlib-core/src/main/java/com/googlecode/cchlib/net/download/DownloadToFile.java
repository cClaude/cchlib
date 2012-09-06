package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.io.checksum.MD5FilterInputStream;

/**
 * Download {@link URL} and put result into a {@link File}
 * 
 * @since 4.1.5
 */
public class DownloadToFile extends AbstractDownload
{
    //private final static transient Logger logger = Logger.getLogger( DownloadToFile.class );
    private MD5FilterInputStreamBuilder downloadFilterBuilder;

    /**
     * Create a new DownloadToFile
     * 
     * @param downloadURL
     * @param fileEventHandler
     * @param downloadFilterBuilder
     */
    public DownloadToFile(
        final DownloadFileURL               downloadURL,
        final DownloadFileEvent             fileEventHandler,
        final MD5FilterInputStreamBuilder   downloadFilterBuilder
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
        final InputStream           is;
        final MD5FilterInputStream  filter;
        final String                md5HashString;
        
        if( downloadFilterBuilder != null ) {
            is = filter = downloadFilterBuilder.createMD5FilterInputStream( inputStream );
            }
        else {
            is     = inputStream;
            filter = null;
            }

        try {
            IOHelper.copy( is, file );
            
            if( filter != null ) {
                filter.close(); // Needed ???
                md5HashString = filter.getHashString();
                }
            else {
                md5HashString = null;
                }
            }
        catch( IOException e ) {
            throw new DownloadIOException( getDownloadURL(), file, e );
            }
        finally {
            filter.close(); // Needed ???
            }

        final DownloadFileURL dURL = DownloadFileURL.class.cast( getDownloadURL() );
        
        dURL.setResultAsFile( file );
        dURL.setContentHashCode( md5HashString );
    }
}
