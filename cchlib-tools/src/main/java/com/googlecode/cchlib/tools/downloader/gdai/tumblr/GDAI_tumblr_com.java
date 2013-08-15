package samples.downloader.gdai.tumblr;

import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import samples.downloader.AbstractDownloaderAppInterface;
import samples.downloader.DefaultComboBoxConfig;
import samples.downloader.GenericDownloaderAppInterface;
import samples.downloader.GenericDownloaderAppUIResults;
import samples.downloader.PolyURLDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;

/**
 *
 *
 *
 */
public abstract class GDAI_tumblr_com
    extends AbstractDownloaderAppInterface
        implements GenericDownloaderAppInterface
{
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger( GDAI_tumblr_com.class );
    /*
     * http://[NAME].tumblr.com
     */
    private final static String SERVER_ROOT_URL_STR_FMT = "http://%s.tumblr.com";

    /*
     * http://[NAME].tumblr.com/
     * http://[NAME].tumblr.com/page/[NUMBER]
     */
    private final static String HTML_URL_BASE1_FMT = SERVER_ROOT_URL_STR_FMT + "/page/%d";
    private final static String HTML_URL_BASEx_FMT = SERVER_ROOT_URL_STR_FMT + "/page/%d";

    //private static final String SITE_NAME_ALL     = "www.tumblr.com";
    /*
     * [NAME].tumblr.com
     *
     * Use for label AND for cache directory name
     */
    static final String SITE_NAME_FMT = "%s.tumblr.com";
    static final int NUMBER_OF_PICTURES_BY_PAGE = -1;
    
    /** number of pages to explore */
    final static int DEFAULT_MAX_PAGES_BLOGS = 32;
    //private final static int DEFAULT_MAX_PAGES_ALL = 16;
    
    private final static  int[] TUMBLR_COM_KNOWN_SIZES = {
            1280, 
             500,
             400,
             250,
             100
           };

    protected GDAI_tumblr_com( 
        final String displaySiteName,
        final int    maxPages
        )
    {
        this(
                displaySiteName,
                NUMBER_OF_PICTURES_BY_PAGE,
                maxPages
                );
    }

    protected GDAI_tumblr_com(
            final String    format,
            final int       numberOfPicturesByPage,
            final int       defaultMaxPages
            )
    {
        super( format, numberOfPicturesByPage, defaultMaxPages );
    }


    protected abstract String getCurrentHostName();
    
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
    
    @Override
    public Collection<DownloadFileURL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults   gdauir, 
            DownloadStringURL               content2Parse 
            )
            throws MalformedURLException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "<img src=\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( 
        final String src, 
        final int    regexpIndex 
        ) throws MalformedURLException, URISyntaxException
    {
        {
            // Ignore avatars
            URL    urlBase = new URL( src );
            String path    = urlBase.getPath();

            if( path.startsWith( "avatar_" ) ) {
                // Don't want to download avatars !
                return null;
                }
        }

        // Find extension.
        int pos = src.lastIndexOf( '.' );
        
        if( pos < 0 ) {
            // <0  - No extension: Try to download any way.
            return new DefaultDownloadFileURL( src, null, getProxy() );
            }
        
        final String extension = src.substring( pos );
        final String prefix1   = src.substring( 0, pos );
        
        // Find picture size
        pos = prefix1.lastIndexOf( '_' );
        
        if( pos < 0 ) {
            // Unknown format: Try to download any way.
            return new DefaultDownloadFileURL( src, null, getProxy() );
            }

        final int size;
        try {
            final String sizeStr = prefix1.substring( pos + 1 );
            
            size = Integer.parseInt( sizeStr );
            }
        catch( Exception e ) {
            logger.warn( "size of " + src, e );
            // Can not find picture size: Try to download any way.
            return new DefaultDownloadFileURL( src, null, getProxy() );
            }
        final String prefix2 = prefix1.substring( 0, pos + 1 );

        final URL       defaultURL;
        final List<URL> alternateURL = new ArrayList<URL>();
        {
            // Build list of URL
            final List<String> urls = new ArrayList<String>();

            for( int i = 0; i<TUMBLR_COM_KNOWN_SIZES.length; i++ ) {
                if( size >= TUMBLR_COM_KNOWN_SIZES[ i ] ) {
                    break;
                    }
                urls.add( 
                    prefix2 + TUMBLR_COM_KNOWN_SIZES[ i ] + extension
                    );
                }
            
            urls.add( 
                prefix2 + size + extension
                );
            
            boolean first       = true;
            String  firstURLStr = null;
            
            for( String urlStr : urls ) {
                if( first ) {
                    firstURLStr = urlStr;
                    first       = false;
                    }
                else {
                    alternateURL.add( new URL( urlStr ) );
                    }
                }
            
            defaultURL = new URL( firstURLStr );
        }
        
        return new PolyURLDownloadFileURL( defaultURL, null, getProxy(), alternateURL, src );
    }
    
    public final static GDAI_tumblr_com createForHost( 
        final Frame     ownerFrame,
        final String    hostname 
        )
    {
        return new GDAI_tumblr_com_ForHost( ownerFrame, hostname );
    }
    
    public final static GDAI_tumblr_com createAllEntries(
        final Frame ownerFrame
        )
    {
        final String[] blogNames;
        final String[] blogDescriptions;
        final GDAI_tumblr_com_Config config = new GDAI_tumblr_com_Config();
        {
            Collection<GDAI_tumblr_com_Config.Entry> entries = config.getEntriesCollection();
            
            blogNames        = new String[ entries.size() ];
            blogDescriptions = new String[ entries.size() ];

            int i = 0;
            for( GDAI_tumblr_com_Config.Entry entry : entries ) {
                blogNames[ i ]          = entry.getName();
                blogDescriptions[ i++ ] = entry.getDescription();
                }

            logger.info( "Found " + entries.size() + " count." );
        }
        
        return new GDAI_tumblr_com_ForHost(
                ownerFrame,
                new DefaultComboBoxConfig(
                        "Name",
                        blogNames,
                        blogDescriptions
                        ),
                config
                );
    }
    
    static DownloadStringURL getDownloadStringURL(
            final String    hostname,
            final int       pageNumber,
            final Proxy     proxy
            )
            throws MalformedURLException, URISyntaxException
    {
        final String fmt;
        
        if( pageNumber == 1 ) {
            fmt = HTML_URL_BASE1_FMT;
            }
        else {
            fmt = HTML_URL_BASEx_FMT;
            }
        return new DefaultDownloadStringURL(
                String.format(
                    fmt,
                    hostname,
                    pageNumber
                    ),
                null,
                proxy
                );
    }   

}
