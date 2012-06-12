package samples.downloader;

/**
 *
 */
public interface GenericDownloaderAppInterface
{
    /**
     * Returns the site name
     * @return the site name
     */
    public String getSiteName();

    /**
     * Returns average pictures by page.
     * @return average pictures by page.
     */
    public int getNumberOfPicturesByPage();

    /**
     * Returns default number of page to download
     * @return default number of page to download
     */
    public int getDefaultPageCount();

    /**
     * Returns max number of page to download
     * @return max number of page to download
     */
    public int getMaxPageCount();
}
