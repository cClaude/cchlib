package samples.downloader;

import java.io.File;
import java.net.URL;
import com.googlecode.cchlib.net.download.DownloadEvent;
import com.googlecode.cchlib.net.download.DownloadURL;

/**
 *
 */
public interface LoggerListener extends DownloadEvent
{
    /**
     * 
     * @param msg
     */
    public void warn( String msg );

    /**
     * 
     * @param msg
     */
    public void info( String msg );

    /**
     * 
     * @param url
     * @param file
     * @param cause
     */
    public void error( URL url, File file, Throwable cause );

    /**
     * 
     * @param event
     */
    public void downloadStateInit( DownloadStateEvent event );
    
    /**
     * 
     * @param event
     */
    public void downloadStateChange( DownloadStateEvent event );

    /**
     * 
     * @param dURL
     * @param tmpFile
     * @param expectedCacheFile
     */
    public void downloadCantRename( DownloadURL dURL, File tmpFile, File expectedCacheFile );
    
    /**
     * 
     * @param dURL
     */
    public void downloadStored( DownloadURL dURL );
}
