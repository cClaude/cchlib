package com.googlecode.cchlib.tools.downloader.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.tools.downloader.comboconfig.GenericDownloaderAppComboBoxConfig;

public interface DownloaderHandler
{
    void doSelectedItems( List<Item> selectedItems );

    @SuppressWarnings("squid:S1160")
    Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader(
        GenericDownloaderAppUIResults   gdauir,
        ContentDownloadURI<String>      content2Parse
        ) throws MalformedURLException, URISyntaxException;

    GenericDownloaderAppButton getButtonConfig();

    Collection<GenericDownloaderAppComboBoxConfig> getComboBoxConfigCollection();

    /**
     * Returns a list of {@link ContentDownloadURI} to parse
     * @return a list of {@link ContentDownloadURI} to parse
     * @throws MalformedURLException if any
     * @throws URISyntaxException  if any
     */
    @SuppressWarnings("squid:S1160")
    Collection<ContentDownloadURI<String>> getURLDownloadAndParseCollection()
        throws MalformedURLException, URISyntaxException;
}
