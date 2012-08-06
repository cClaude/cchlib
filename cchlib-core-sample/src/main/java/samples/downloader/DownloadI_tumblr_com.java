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
public class DownloadI_tumblr_com
    extends AbstractDownloadInterface
        implements GenericDownloaderAppInterface
{
    /*
     * http://[NAME].tumblr.com
     */
    private final static String SERVER_ROOT_URL_STR_FMT = "http://%s.tumblr.com";

    /*
     * http://[NAME].tumblr.com/page/[NUMBER]
     */
    private final static String HTML_URL_BASE_FMT = SERVER_ROOT_URL_STR_FMT + "/page/%d";

    private static final String SITE_NAME_GEN = "*.tumblr.com";
    /*
     * [NAME].tumblr.com
     *
     * Use for label AND for cache directory name
     */
    private static final String SITE_NAME_FMT = "%s.tumblr.com";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = -1;
    /** number of pages to explore */
    private final static int DEFAULT_MAX_PAGES = 15;

    private DefaultComboBoxConfig comboBoxConfig;

    private String hostname;

    public DownloadI_tumblr_com()
    {
        super(
                SITE_NAME_GEN,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        String[] comboBoxValues = {
                "milfgalore",
                "nopantiesinpublic",
                "vacationfun",
                "pssshclothes",
                "shavednudistamateurs",
                "beach-ball",
                "clothes-free",
                "topless-beach-girls",
                "ultra555",
                "milfsgilfs",
                };
        this.comboBoxConfig = new DefaultComboBoxConfig(
            "Name",
            comboBoxValues, // hostname
            comboBoxValues  // description
            );
        this.hostname = "";

        super.addComboBoxConfig( comboBoxConfig );
    }

    public DownloadI_tumblr_com( final String hostname )
    {
        super(
                String.format( SITE_NAME_FMT, hostname ),
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        this.comboBoxConfig = null;
        this.hostname       = hostname;
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        final String[]      fullHostName = String.format( SITE_NAME_FMT, getCurrentHostName() ).split( "\\." );
        final StringBuilder sb           = new StringBuilder();
        int                 i            = fullHostName.length - 1;

        while( i >= 0 ) {
            sb.append( fullHostName[ i ] );
            --i;

            if( i < 0 ) {
                break;
                }
            sb.append( '.' );
            }

        return sb.toString();
    }

    private String getCurrentHostName()
    {
        return ( comboBoxConfig == null ) ?
                hostname
                :
                comboBoxConfig.getComboBoxSelectedValue();
    }

    @Override
    public StringDownloadURL getStringDownloadURL( final int pageNumber )
            throws MalformedURLException
    {
        return new StringDownloadURL(
            String.format(
                HTML_URL_BASE_FMT,
                getCurrentHostName(),
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
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "<img src=\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public FileDownloadURL getDownloadURLFrom( String src, int regexpIndex )
            throws MalformedURLException
    {
        return new FileDownloadURL( src, null, getProxy() );
    }
}
