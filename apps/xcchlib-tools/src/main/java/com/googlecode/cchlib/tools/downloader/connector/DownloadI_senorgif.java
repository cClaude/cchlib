package com.googlecode.cchlib.tools.downloader.connector;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.tools.downloader.DefaultComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface;


/**
 * http://senorgif.memebase.com/page/5/ 1600
 */
public class DownloadI_senorgif
    extends AbstractDownloaderAppInterface
{
    private static final long serialVersionUID = 1L;
    private static final String SITE_NAME = "senorgif";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 5;
    private static final int DEFAULT_MAX_PAGES = 15;

    private final GenericDownloaderAppComboBoxConfig mainComboBoxConfig;

    public DownloadI_senorgif()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        final String[] comboBoxValues = { "http://senorgif.memebase.com/page/%d/" , "http://senorgif.memebase.com/vote/" };
        final String[] labelStrings   = { "pages type 1"                          , "pages type 2"};

        this.mainComboBoxConfig = new DefaultComboBoxConfig(
             "Page type",
             comboBoxValues,
             labelStrings
             );
        super.addComboBoxConfig( this.mainComboBoxConfig );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "com.memebase.senorgif";
    }

    @Override
    public ContentDownloadURI<String> getDownloadStringURL( final int pageNumber ) throws MalformedURLException, URISyntaxException
    {
        return new DefaultDownloadStringURL(
            String.format(
                this.mainComboBoxConfig.getComboBoxSelectedValue(),
                Integer.valueOf( pageNumber )
                ),
            null,
            getProxy()
            );
    }

    @Override
    public Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader(
            final GenericDownloaderAppUIResults gdauir,
            final ContentDownloadURI<String>    content2Parse
            ) throws MalformedURLException
    {
        final RegExgSplitter[] regexps = {
            new DefaultRegExgSplitter( "\\<img class=\"event-item-lol-image\" src=\"", '"' ),
            new DefaultRegExgSplitter( "\\<img class='event-item-lol-image' src='", '\'' ),
            };
        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps   );
    }

    @Override
    public ContentDownloadURI<File> getDownloadURLFrom( final String src, final int regexpIndex )
            throws MalformedURLException, URISyntaxException
    {
        return new DefaultDownloadFileURL( src, null, getProxy() );
    }

    @Override
    public GenericDownloaderAppButton getButtonConfig()
    {
        return null;
    }

    @Override
    public void doSelectedItems( final List<Item> selectedItems )
    {
        // TODO Not implemented
        Logger.getLogger( getClass() ).warn( "NOT IMPLEMENTED" );
    }
}
