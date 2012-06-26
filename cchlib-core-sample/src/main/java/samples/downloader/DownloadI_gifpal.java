package samples.downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 *
 *
 */
public class DownloadI_gifpal
    extends AbstractExtentedDownloadInterface//AbstractDownloadInterface
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

    /** number of pages to explore */
    private final static int DEFAULT_MAX_PAGES = 3;

    /**
     * param1 = image_id
     *
     * http://www.gifpal.com/uimages/WVrkTTeOoI.gif
     */
    private final static String IMG_URL_BASE_FMT   = __SERVER_ROOT_URL_STR + "/uimages/%s.gif";

    private static final String CACHE_FOLDER_NAME = "www.gifpal.com";

    private DefaultComboBoxConfig comboBoxConfig;

    public DownloadI_gifpal()
    {
        super(
            "www.gifpal.com",
            16,                 //numberOfPicturesByPage
            DEFAULT_MAX_PAGES   // defaultPageCount
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
                )
            );
    }

    @Override
    public Collection<URL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults gdauir, String content2Parse )
            throws MalformedURLException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "\\{\"image\"\\:\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public URL getURLToDownload( String src, int regexpIndex )
            throws MalformedURLException
    {
        return new URL( String.format( IMG_URL_BASE_FMT, src ) );
    }
}
