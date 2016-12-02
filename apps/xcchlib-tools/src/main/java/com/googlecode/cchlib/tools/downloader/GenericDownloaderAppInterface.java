package com.googlecode.cchlib.tools.downloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;

public interface GenericDownloaderAppInterface
{
    /**
     * Returns the site name (for UI)
     * @return the site name
     */
    String getSiteName();

    /**
     * Returns average pictures by page (for UI)
     * @return average pictures by page.
     */
    int getNumberOfPicturesByPage();

    /**
     * Returns number of page to download (for UI). Value must be greater than 0 and
     * less or equal than value return by {@link #getMaxPageCount()}.
     * @return number of page to download
     */
    int getPageCount();

    /**
     * Set number of page to download
     * @see #getPageCount()
     */
    void setPageCount( int pageCount );

    /**
     * Returns max number of page to download (for UI)
     * @return max number of page to download
     */
    int getMaxPageCount();

    /**
     * @return relative directory cache name
     */
    String getCacheRelativeDirectoryCacheName();

    /**
     * Returns a list of {@link DownloadStringURL} to parse
     * @return a list of {@link DownloadStringURL} to parse
     * @throws MalformedURLException if any
     * @throws URISyntaxException  if any
     */
    @SuppressWarnings("squid:S1160")
    Collection<ContentDownloadURI<String>> getURLDownloadAndParseCollection()
        throws MalformedURLException, URISyntaxException;

    @SuppressWarnings("squid:S1160")
    Collection<ContentDownloadURI<File>> getURLToDownloadCollection(
        GenericDownloaderAppUIResults   gdauir,
        ContentDownloadURI<String>      content2Parse
        ) throws MalformedURLException, URISyntaxException;

    Collection<GenericDownloaderAppComboBoxConfig> getComboBoxConfigCollection();

    Proxy getProxy();

    void setProxy( Proxy proxy );

    GenericDownloaderAppButton getButtonConfig();

    void setSelectedItems( List<Item> selectedItems );
}
