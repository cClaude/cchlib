package samples.downloader;

import java.net.MalformedURLException;
import java.util.Collection;
import com.googlecode.cchlib.net.download.FileDownloadURL;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 *
 *
 */
public class DownloadI_www_gifpal_com
    extends AbstractDownloadInterface
        implements GenericDownloaderAppInterface
{
    //private final static Logger logger = Logger.getLogger( DownloadI_gifpal.class );
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


    private DefaultComboBoxConfig comboBoxConfig;

    public DownloadI_www_gifpal_com()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        String[]    comboBoxValues  = { "id"        , "top"         , "top-today"       , "top of the week"};
        String[]    labelStrings    = { "All items" , "top items"   , "top of the day"  , "top-week"};
        comboBoxConfig = new DefaultComboBoxConfig(
            "Filter",
            comboBoxValues,
            labelStrings
            );
        super.addComboBoxConfig( comboBoxConfig );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return CACHE_FOLDER_NAME;
    }

    @Override
    public StringDownloadURL getStringDownloadURL( final int pageNumber )
            throws MalformedURLException
    {
        return new StringDownloadURL(
            String.format(
                __HTML_URL_BASE_FMT,
                comboBoxConfig.getComboBoxSelectedValue(),
                pageNumber
                ),
            null,
            getProxy()
            );
    }

    @Override
    public Collection<FileDownloadURL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults gdauir, String content2Parse )
            throws MalformedURLException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "\\{\"image\"\\:\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public FileDownloadURL getDownloadURLFrom( String src, int regexpIndex )
            throws MalformedURLException
    {
        return new FileDownloadURL( String.format( IMG_URL_BASE_FMT, src ), null, getProxy() );
    }
}
