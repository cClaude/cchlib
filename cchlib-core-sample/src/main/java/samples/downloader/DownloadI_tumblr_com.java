package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;
import com.googlecode.cchlib.util.properties.Populator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;

/**
 *
 *
 *
 */
public abstract class DownloadI_tumblr_com
    extends AbstractDownloadInterface
        implements GenericDownloaderAppInterface
{
    private final static Logger logger = Logger.getLogger( DownloadI_tumblr_com.class );
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

    private static final String SITE_NAME_ALL     = "www.tumblr.com";
    private static final String SITE_NAME_GENERIC = "*.tumblr.com";
    /*
     * [NAME].tumblr.com
     *
     * Use for label AND for cache directory name
     */
    private static final String SITE_NAME_FMT = "%s.tumblr.com";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = -1;
    /** number of pages to explore */
    private final static int DEFAULT_MAX_PAGES_BLOGS = 32;
    private final static int DEFAULT_MAX_PAGES_ALL = 16;
    
    private static class DownloadI_tumblr_com_ForHost extends DownloadI_tumblr_com
    {
        private DefaultComboBoxConfig   comboBoxConfig;
        private String                  _hostname;
        
        public DownloadI_tumblr_com_ForHost( final String hostname )
        {
            super(
                    String.format( SITE_NAME_FMT, hostname ),
                    NUMBER_OF_PICTURES_BY_PAGE,
                    DEFAULT_MAX_PAGES_BLOGS
                    );

            this.comboBoxConfig = null;
            this._hostname      = hostname;
        }        

        public DownloadI_tumblr_com_ForHost( DefaultComboBoxConfig comboBoxConfig )
        {
            super( SITE_NAME_GENERIC, DEFAULT_MAX_PAGES_BLOGS );
            
            this.comboBoxConfig = comboBoxConfig;
            super.addComboBoxConfig( comboBoxConfig );
        }
        
        protected String getCurrentHostName()
        {
            return ( comboBoxConfig == null ) ?
                    _hostname
                    :
                    comboBoxConfig.getComboBoxSelectedValue();
        }

        @Override
        public DownloadStringURL getDownloadStringURL( int pageNumber )
                throws MalformedURLException
        {
            return getDownloadStringURL( getCurrentHostName(), pageNumber, getProxy() );
        }
    }    

    private DownloadI_tumblr_com( 
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

    protected DownloadI_tumblr_com(
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
            GenericDownloaderAppUIResults gdauir, String content2Parse )
            throws MalformedURLException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "<img src=\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( String src, int regexpIndex )
            throws MalformedURLException
    {
        return new DefaultDownloadFileURL( src, null, getProxy() );
    }
    
    public final static DownloadI_tumblr_com createForHost( final String hostname )
    {
        return new DownloadI_tumblr_com_ForHost( hostname );
    }
    
    public final static DownloadI_tumblr_com createAllEntries()
    {
        final String[] blogsNames;
        {
            final Config_DownloadI_tumblr_com config = new Config_DownloadI_tumblr_com();

            blogsNames = config.getBlogNames();
        }
        
        return new DownloadI_tumblr_com_ForHost(
                new DefaultComboBoxConfig(
                        "Name",
                        blogsNames, // hostname
                        blogsNames  // description
                        )
                );
    }
    
    public  final static DownloadI_tumblr_com createAllEntriesInOnce()
    {
        final String[] blogsNames;
        {
            final Config_DownloadI_tumblr_com config = new Config_DownloadI_tumblr_com();

            blogsNames = config.getBlogNames();
        }

        return new DownloadI_tumblr_com( SITE_NAME_ALL, DEFAULT_MAX_PAGES_ALL )
        {
            @Override
            protected String getCurrentHostName()
            {
                return "www";
            }

            @Override
            public DownloadStringURL getDownloadStringURL( int pageNumber )
                    throws MalformedURLException
            {
                throw new UnsupportedOperationException();
            }

            private DownloadStringURL getStringDownloadURL(
                    final String    hostname,
                    final int       pageNumber 
                    )
                    throws MalformedURLException
            {
                return getDownloadStringURL( hostname, pageNumber, getProxy() );
            }
            
            @Override// GenericDownloaderAppInterface
            public Collection<DownloadStringURL> getURLDownloadAndParseCollection()
                    throws MalformedURLException
            {
                final List<DownloadStringURL>   sdURLList = new ArrayList<DownloadStringURL>();
                final int                       pageCount = getPageCount();
                
                for( String hostname : blogsNames ) {
                    logger.debug( "HostName = " + hostname );
                    for( int i=1; i<=pageCount; i++ ) {
                        logger.debug( "HostName:i = " + hostname + ":" + i );
                        sdURLList.add( getStringDownloadURL( hostname, i ) );
                        }
                    }

                return sdURLList;
            }
        };
    };
    
    private static DownloadStringURL getDownloadStringURL(
            final String    hostname,
            final int       pageNumber,
            final Proxy     proxy
            )
            throws MalformedURLException
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


/**
 * 
 *
 */
class Config_DownloadI_tumblr_com
{
    private final static Logger logger = Logger.getLogger( Config_DownloadI_tumblr_com.class );
    private PropertiesPopulator<Config_DownloadI_tumblr_com> pp = new PropertiesPopulator<Config_DownloadI_tumblr_com>( this.getClass() );
    
    @Populator
    private String[] blogsNames;
    
    public Config_DownloadI_tumblr_com()
    {
        try {
            Properties properties = PropertiesHelper.loadProperties( getConfigFile() );
            
            pp.populateBean( properties , this );
            }
        catch( IOException e ) {
            logger.error( "Can't load config", e );
            }
    }
    
    public Config_DownloadI_tumblr_com( final String[] blogsNames )
    {
        this.blogsNames = blogsNames;
    }
    
    private static File getConfigFile()
    {
        return FileHelper.getUserHomeDirFile( DownloadI_tumblr_com.class.getName() + ".properties" );
    }
    
    public String[] getBlogNames()
    {
        return blogsNames;
    }
    
    public void storeConfig() throws IOException
    {
        Properties properties = new Properties();
        
        pp.populateProperties( this, properties );
        
        PropertiesHelper.saveProperties( getConfigFile(), properties );
    }
}
