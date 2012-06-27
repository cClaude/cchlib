package samples.downloader;

import java.net.CookieHandler;
import java.net.Proxy;

/**
 *
 */
public interface GenericDownloaderAppUIResults
{
    /**
     *
     * @return
     */
    public int getDownloadThreadCount();

    /**
     *
     * @return
     */
    public Proxy getProxy();

    /**
     * @return a {@link CookieHandler} if site need it, null otherwise
     */
    //public Map<URI,Map<String,List<String>>> getCookieHandlerMap();
    public CookieHandler getCookieHandler();
    /**
     *
     * @return
     */
    public LoggerListener getAbstractLogger();
}
