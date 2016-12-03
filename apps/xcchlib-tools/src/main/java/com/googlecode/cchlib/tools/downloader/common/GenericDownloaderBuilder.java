package com.googlecode.cchlib.tools.downloader.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppInterface;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;

@Deprecated
public class GenericDownloaderBuilder implements GenericDownloaderAppInterface
{
    private final DownloaderData    data;
    private final DownloaderHandler handler;

    private GenericDownloaderBuilder(
            final DownloaderData    data,
            final DownloaderHandler handler
            )
    {
        this.data    = data;
        this.handler = handler;
    }

    public static GenericDownloaderAppInterface newInstance(
        final DownloaderHandler handler
        )
    {
        final DownloaderData data = new DownloaderDataImpl();

        return new GenericDownloaderBuilder( data, handler );
    }

    @Override // DownloaderData
    public String getSiteName()
    {
        return this.data.getSiteName();
    }

    @Override // DownloaderData
    public int getNumberOfPicturesByPage()
    {
        return this.data.getNumberOfPicturesByPage();
    }

    @Override // DownloaderData
    public int getPageCount()
    {
        return this.data.getPageCount();
    }

    @Override // DownloaderData
    public void setPageCount( final int pageCount )
    {
        this.data.setPageCount( pageCount );
    }

    @Override // DownloaderData
    public int getMaxPageCount()
    {
        return this.data.getMaxPageCount();
    }

    @Override // DownloaderData
    public String getCacheRelativeDirectoryCacheName()
    {
        return this.data.getCacheRelativeDirectoryCacheName();
    }

    @Override // DownloaderData
    public Proxy getProxy()
    {
        return this.data.getProxy();
    }

    @Override // DownloaderData
    public void setProxy( final Proxy proxy )
    {
        this.data.setProxy( proxy );
    }

    @Override // DownloaderHandler
    public void doSelectedItems( final List<Item> selectedItems )
    {
        this.handler.doSelectedItems( selectedItems );
    }

    @Override // DownloaderHandler
    public Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader( final GenericDownloaderAppUIResults gdauir, final ContentDownloadURI<String> content2Parse )
            throws MalformedURLException, URISyntaxException
    {
        return this.handler.computeURLsAndGetDownloader( gdauir, content2Parse );
    }

    @Override // DownloaderHandler
    public GenericDownloaderAppButton getButtonConfig()
    {
        return this.handler.getButtonConfig();
    }

    @Override // DownloaderHandler
    public Collection<GenericDownloaderAppComboBoxConfig> getComboBoxConfigCollection()
    {
        return this.handler.getComboBoxConfigCollection();
    }

    @Override // DownloaderHandler
    public Collection<ContentDownloadURI<String>> getURLDownloadAndParseCollection() throws MalformedURLException, URISyntaxException
    {
        return this.handler.getURLDownloadAndParseCollection();
    }
}
