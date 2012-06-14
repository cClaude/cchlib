package samples.downloader;

import java.net.Proxy;

/**
 *
 */
public interface GenericDownloaderAppUIResults
{
    public int getDownloadThreadCount();

    public Proxy getProxy();

    public LoggerListener getAbstractLogger();
}
