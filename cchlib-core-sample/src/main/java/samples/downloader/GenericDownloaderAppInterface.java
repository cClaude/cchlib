package samples.downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 *
 */
public interface GenericDownloaderAppInterface
{
    /**
     * Returns the site name (for UI)
     * @return the site name
     */
    public String getSiteName();

    /**
     * Returns average pictures by page (for UI)
     * @return average pictures by page.
     */
    public int getNumberOfPicturesByPage();

    /**
     * Returns default number of page to download (for UI)
     * @return default number of page to download
     */
    public int getDefaultPageCount();

    /**
     * Returns max number of page to download (for UI)
     * @return max number of page to download
     */
    public int getMaxPageCount();

    /**
     *
     * @return relative directory cache name
     */
    public String getCacheRelativeDirectoryCacheName();

    /**
     * Returns a list of URL of json values to parse
     * @return a list of URL of json values to parse
     * @throws MalformedURLException
     */
    public Collection<URL> getURLDownloadAndParseCollection()
        throws MalformedURLException;

    /**
     *
     * @param gdauir
     * @param content2Parse
     * @return
     * @throws MalformedURLException
     */
    public Collection<URL> getURLToDownloadCollection(
        GenericDownloaderAppUIResults   gdauir,
        String                          content2Parse
        ) throws MalformedURLException;
}
