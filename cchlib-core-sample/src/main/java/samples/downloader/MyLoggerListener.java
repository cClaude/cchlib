package samples.downloader;

import java.io.File;
import java.net.URL;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURL;

/**
 *
 *
 */
public final class MyLoggerListener implements LoggerListener
{
    private final Logger logger;

    /**
     *
     * @param logger
     */
    public MyLoggerListener( final Logger logger )
    {
        this.logger = logger;
    }

    @Override
    public void warn( final String msg )
    {
        //System.out.println( msg );
        logger.warn( msg );
    }

    @Override
    public void info( final String msg )
    {
        //System.out.println( msg );
        logger.info( msg );
    }

    @Override
    public void error( final URL url, final File file, final Throwable cause )
    {
        //String msg = "*ERROR: " + url + " - " + cause.getMessage() );
        //System.err.println( "*ERROR: " + url + " - " + cause.getMessage() );
        logger.error( "URL: " + url + " File: " + file, cause );
    }

    @Override
    public void downloadStateInit( final DownloadStateEvent event )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void downloadStateChange( final DownloadStateEvent event )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void downloadFail( final DownloadIOException e )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadStart( DownloadURL url )
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void downloadDone( DownloadURL url )
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void downloadCantRename( DownloadURL dURL, File tmpFile,
            File expectedCacheFile )
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void downloadStored( DownloadURL dURL )
    {
        // TODO Auto-generated method stub
        
    }
}
