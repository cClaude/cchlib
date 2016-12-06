package com.googlecode.cchlib.tools.downloader.connector;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.FileDownloader;
import com.googlecode.cchlib.net.download.StringDownloader;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppInterface;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.tools.downloader.comboconfig.DefaultComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface;


/**
 *
 * http://www.gifpal.com/gallery/top-week/3/
 *
 */
public class DownloadI_www_gifpal_com
    extends AbstractDownloaderAppInterface
        implements GenericDownloaderAppInterface
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( DownloadI_www_gifpal_com.class );

    private static final String ROOT_URL_STR = "http://www.gifpal.com";

    /**
     * param1 = sort
     * param2 = page_number
     *
     * http://www.gifpal.com/gallery-contents-json.php?sort=id&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top-today&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top-week&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top&page=2
     *
     * http://www.gifpal.com/gallery/top-week/3/
     */
    private static final String HTML_URL_BASE_FMT
        // = ROOT_URL_STR + "/gallery-contents-json.php?sort=%s&page=%d";
        = ROOT_URL_STR + "/gallery/%s/%d/";

    private static final String SITE_NAME = "www.gifpal.com";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 16;

    /** number of pages to explore */
    private static final int DEFAULT_MAX_PAGES = 5;

    /**
     * param1 = image_id
     *
     * http://www.gifpal.com/uimages/WVrkTTeOoI.gif
     */
    private static final String IMG_URL_BASE_FMT   = ROOT_URL_STR + "/uimages/%s.gif";

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
    public ContentDownloadURI<String> getDownloadStringURL( final int pageNumber )
            throws MalformedURLException, URISyntaxException
    {
        return new StringDownloader(
            String.format(
                HTML_URL_BASE_FMT,
                this.comboBoxConfig.getComboBoxSelectedValue(),
                Integer.valueOf( pageNumber )
                ),
            null,
            getProxy()
            );
    }

    @Override
    public Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader(
            final GenericDownloaderAppUIResults   gdauir,
            final ContentDownloadURI<String>      content2Parse
            )
            throws MalformedURLException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "\\{\"image\"\\:\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public ContentDownloadURI<File> getDownloadURLFrom( final String src, final int regexpIndex )
            throws MalformedURLException, URISyntaxException
    {
        return new FileDownloader( String.format( IMG_URL_BASE_FMT, src ), null, getProxy() );
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
        LOGGER.warn( "NOT IMPLEMENTED" );
    }
}
