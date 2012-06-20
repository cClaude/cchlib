package samples.downloader;

import java.io.File;
import java.net.URL;
import com.googlecode.cchlib.net.download.DownloadEvent;
import com.googlecode.cchlib.net.download.DownloadURL;

/**
 *
 */
interface LoggerListener extends DownloadEvent//EventListener
{
    public void warn( String msg );
    public void info( String msg );
    public void error( URL url, File file, Throwable cause );
    //public void error( URL url, Throwable cause );
    public void downloadStateInit( DownloadStateEvent event );
    public void downloadStateChange( DownloadStateEvent event );
    //public void downloadStoreAs( URL url, File cacheFile );
    public void downloadCantRename( DownloadURL dURL, File tmpFile, File expectedCacheFile );
    public void downloadStored( DownloadURL dURL );
}
