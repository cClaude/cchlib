package samples.downloader;

import java.io.File;
import java.net.URL;
import java.util.EventListener;

/**
 *
 */
public interface LoggerListener extends EventListener
{
    public void warn( String msg );
    public void info( String msg );
    public void error( URL url, File file, Throwable cause );
    //public void error( URL url, Throwable cause );
    public void downloadStateInit( DownloadStateEvent event );
    public void downloadStateChange( DownloadStateEvent event );
}
