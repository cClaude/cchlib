package com.googlecode.cchlib.tools.downloader.connector;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;
import com.googlecode.cchlib.tools.downloader.AbstractDownloaderAppInterface;
import com.googlecode.cchlib.tools.downloader.DefaultComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;


/**
 * http://www.gifmash.com/
 *
 * http://picasion.com/gallery/24151868/
 * 4gifs.com
 */
public class DownloadI_www_gifmash_com
    extends AbstractDownloaderAppInterface
{
    private static final long serialVersionUID = 1L;
    private static final String SITE_NAME = "www.gifmash.com";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 10;
    private static final int DEFAULT_MAX_PAGES = 100;
    private final GenericDownloaderAppComboBoxConfig mainComboBoxConfig;

    public DownloadI_www_gifmash_com()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        final String[] comboBoxValues = { "http://www.gifmash.com/blog/page/%d" };
        final String[] labelStrings   = { "pics from blog" };

        this.mainComboBoxConfig =  new DefaultComboBoxConfig( "Main page", comboBoxValues, labelStrings );

        super.addComboBoxConfig( this.mainComboBoxConfig );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "com.gifmash.www";
    }

    @Override
    public DownloadStringURL getDownloadStringURL( final int pageNumber ) throws MalformedURLException, URISyntaxException
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
    public Collection<DownloadFileURL> getURLToDownloadCollection(
            final GenericDownloaderAppUIResults gdauir,
            final DownloadStringURL             content2Parse
            ) throws MalformedURLException
    {
        // <img class="event-item-lol-image" src="https://chzgifs.files.wordpress.com/2012/06/bald-eagle-in-slow-motion.gif" a
        final RegExgSplitter[] regexps = {
            new DefaultRegExgSplitter( "\\<img class=\"event-item-lol-image\" src=\"", '"' ),
            new DefaultRegExgSplitter( "\\<img class='event-item-lol-image' src='", '\'' ),
            };
        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps   );
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( final String src, final int regexpIndex )
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
    public void setSelectedItems( final List<Item> selectedItems )
    {
        // TODO Auto-generated method stub

    }
}
