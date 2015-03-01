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
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppInterface;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;


/**
 *
 *
 *
 */
public class DownloadI_www_gifpal_com
    extends AbstractDownloaderAppInterface
        implements GenericDownloaderAppInterface
{
    private static final long serialVersionUID = 1L;
    private final static String __SERVER_ROOT_URL_STR = "http://www.gifpal.com";

    /**
     * param1 = sort
     * param2 = page_number
     *
     * http://www.gifpal.com/gallery-contents-json.php?sort=id&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top-today&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top-week&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top&page=2
     */
    private final static String __HTML_URL_BASE_FMT   = __SERVER_ROOT_URL_STR + "/gallery-contents-json.php?sort=%s&page=%d";

    private static final String SITE_NAME = "www.gifpal.com";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 16;
    /** number of pages to explore */
    private final static int DEFAULT_MAX_PAGES = 3;

    /**
     * param1 = image_id
     *
     * http://www.gifpal.com/uimages/WVrkTTeOoI.gif
     */
    private final static String IMG_URL_BASE_FMT   = __SERVER_ROOT_URL_STR + "/uimages/%s.gif";

    private static final String CACHE_FOLDER_NAME = "com.gifpal.www";


    private final DefaultComboBoxConfig comboBoxConfig;

    public DownloadI_www_gifpal_com()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        final String[]    comboBoxValues  = { "id"        , "top"         , "top-today"       , "top of the week"};
        final String[]    labelStrings    = { "All items" , "top items"   , "top of the day"  , "top-week"};
        this.comboBoxConfig = new DefaultComboBoxConfig(
            "Filter",
            comboBoxValues,
            labelStrings
            );
        super.addComboBoxConfig( this.comboBoxConfig );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return CACHE_FOLDER_NAME;
    }

    @Override
    public DownloadStringURL getDownloadStringURL( final int pageNumber )
            throws MalformedURLException, URISyntaxException
    {
        return new DefaultDownloadStringURL(
            String.format(
                __HTML_URL_BASE_FMT,
                this.comboBoxConfig.getComboBoxSelectedValue(),
                Integer.valueOf( pageNumber )
                ),
            null,
            getProxy()
            );
    }

    @Override
    public Collection<DownloadFileURL> getURLToDownloadCollection(
            final GenericDownloaderAppUIResults   gdauir,
            final DownloadStringURL               content2Parse
            )
            throws MalformedURLException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "\\{\"image\"\\:\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( final String src, final int regexpIndex )
            throws MalformedURLException, URISyntaxException
    {
        return new DefaultDownloadFileURL( String.format( IMG_URL_BASE_FMT, src ), null, getProxy() );
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