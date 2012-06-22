package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 *
 *
 */
public class DownloaderSample_gifpal
    implements GenericDownloaderAppInterface
{
    private final static Logger logger = Logger.getLogger( DownloaderSample_gifpal.class );

    //private final static Proxy PROXY = Proxy.NO_PROXY;
    private final static Proxy  __PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("55.37.80.2", 3128));
    private final static int    __DOWNLOAD_THREAD = 10;

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

    // sort parameter values
    /*
    private final static String[] __FMT_PARAM_SORT_VALUES = {
        "id",
        "top",
        "top-today",
        "top-week",
        };
    */
    private final static String[][] __FMT_PARAM_SORT_STRVALUE_COMMENT_VALUE = {
        {"All items", "id", "All items !" },
        {"top items", "top", "xx top" },
        {"top of the day", "top-today", "xx top-today" },
        {"top of the week", "top-week", "xx top-week" },
        };

    /** number of pages to explore */
    private final static int DEFAULT_MAX_PAGES = 3;
    /** number of pages to explore */
    private int pageCount = DEFAULT_MAX_PAGES;

    /**
     * param1 = image_id
     *
     * http://www.gifpal.com/uimages/WVrkTTeOoI.gif
     */
    private final static String IMG_URL_BASE_FMT   = __SERVER_ROOT_URL_STR + "/uimages/%s.gif";

    private static final String CACHE_FOLDER_NAME = "output/www.gifpal.com";
    private List<StringDownloadURL> _htmlURLList = null;

    private Collection<String> extraStringValuesCollection = null;
    private int extraStringSelectedIndex;

    /**
     * Start Sample here !
     */
    public static void main( String...args )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        File destinationFolderFile = new File( new File(".").getAbsoluteFile(), "output/www.gifpal.com" ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        final DownloaderSample_gifpal downloadConfig = new DownloaderSample_gifpal();

        final LoggerListener mylogger = new MyLoggerListener( logger );

        final GenericDownloaderAppUIResults gdauir = new GenericDownloaderAppUIResults()
        {
            @Override
            public int getDownloadThreadCount()
            {
                return __DOWNLOAD_THREAD;
            }
            @Override
            public Proxy getProxy()
            {
                return __PROXY;
            }

            @Override
            public LoggerListener getAbstractLogger()
            {
                return mylogger;
            }
        };

        GenericDownloader instance
            = new GenericDownloader(
                destinationFolderFile,
                __DOWNLOAD_THREAD,
                __PROXY,
                mylogger
                )
        {

            @Override
            protected Collection<URL> collectURLs() throws IOException
            {
              String allContent;
              {
                  List<String>    contentList = loads( downloadConfig.getURLDownloadAndParseCollection() );
                  StringBuilder   sb          = new StringBuilder();

                  for( String s: contentList ) {
                      sb.append( s );
                      }

                  allContent = sb.toString();
                  contentList.clear();
                  sb.setLength( 0 );
              }

                return downloadConfig.getURLToDownloadCollection( gdauir, allContent );

//                final String[] regexps = {
//                        "\\{\"image\"\\:\"",
//                        };
//
//                Set<URL> imagesURLCollection = new HashSet<URL>();
//
//                for( String regexp : regexps ) {
//                    String[] strs = allContent.toString().split( regexp );
//                    mylogger.info( "> img founds = " + (strs.length - 1));
//
//                    for( int i=1; i<strs.length; i++ ) {
//                        String  s   = strs[ i ];
//                        int     end = s.indexOf( '"' );
//                        String  src = s.substring( 0, end );
//
//                        imagesURLCollection.add( new URL( String.format( IMG_URL_BASE_FMT, src ) ) );
//                        }
//                    }
//
//                mylogger.info( "> URL founds = " + imagesURLCollection.size() );
//
//                return imagesURLCollection;
            }
        };

        mylogger.info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        mylogger.info( "done" );
    }

    @Override
    public String getSiteName()
    {
        return "gifpal.com";
    }

    @Override
    public int getNumberOfPicturesByPage()
    {
        return 16;
    }

//    @Override
//    public int getDefaultPageCount()
//    {
//        return 5;
//    }

    @Override
    public int getPageCount()
    {
        return pageCount;
    }

    @Override
    public void setPageCount( int pageCount )
    {
        this.pageCount = pageCount;
    }

    @Override
    public int getMaxPageCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return CACHE_FOLDER_NAME;
    }

    @Override
    public Collection<StringDownloadURL> getURLDownloadAndParseCollection() throws MalformedURLException
    {
        if( _htmlURLList == null ) {
            _htmlURLList = new ArrayList<StringDownloadURL>();
            //_htmlURLList.add( new URL( "http://www.google.com/" ) );

            for( int i=1; i< getPageCount(); i++ ) {
                //_htmlURLList.add( new URL( String.format( __HTML_URL_BASE_FMT, __FMT_PARAM_SORT_VALUES[ 0 ], i ) ) );
                _htmlURLList.add( new StringDownloadURL(
                    String.format(
                        __HTML_URL_BASE_FMT,
                        //__FMT_PARAM_SORT_VALUES[ getExtraStringSelectedIndex() ],
                        __FMT_PARAM_SORT_STRVALUE_COMMENT_VALUE[ getExtraStringSelectedIndex() ][ 2 ],
                        i )
                        )
                    );
                }
            }

        return _htmlURLList;
    }

    @Override
    public Collection<URL> getURLToDownloadCollection(
        final GenericDownloaderAppUIResults gdauir,
        final String                        allContent
        ) throws MalformedURLException
    {
        final String[] regexps = {
            "\\{\"image\"\\:\"",
            };

        Set<URL> imagesURLCollection = new HashSet<URL>();

        for( String regexp : regexps ) {
            String[] strs = allContent.toString().split( regexp );
            gdauir.getAbstractLogger().info( "> img founds = " + (strs.length - 1));

            for( int i=1; i<strs.length; i++ ) {
                String  s   = strs[ i ];
                int     end = s.indexOf( '"' );
                String  src = s.substring( 0, end );

                imagesURLCollection.add( new URL( String.format( IMG_URL_BASE_FMT, src ) ) );
                }
            }

        gdauir.getAbstractLogger().info( "> URL founds = " + imagesURLCollection.size() );

        return imagesURLCollection;
    }

    @Override
    public boolean isExtraStringValue()
    {
        return true;
    }

    @Override
    public String getExtraStringLabel()
    {
        return "XXXXX";
    }

    @Override
    public Collection<String> getExtraStringValues()
    {
        if( extraStringValuesCollection == null ) {
            extraStringValuesCollection = new ArrayList<>( __FMT_PARAM_SORT_STRVALUE_COMMENT_VALUE.length );

            for( int i = 0; i<__FMT_PARAM_SORT_STRVALUE_COMMENT_VALUE.length; i++ ) {
                extraStringValuesCollection.add( __FMT_PARAM_SORT_STRVALUE_COMMENT_VALUE[ i ][ 0 ] );
                }
            }

        return Collections.unmodifiableCollection( extraStringValuesCollection );
    }

    @Override
    public int getExtraStringSelectedIndex()
    {
        return extraStringSelectedIndex;
    }

    @Override
    public void setExtraStringSelectedIndex( final int index )
    {
        extraStringSelectedIndex = index;
    }

    @Override
    public String getExtraStringLabels( int index )
    {
//        final String[] comments = __FMT_PARAM_SORT_VALUES /*{
//            "xxx"
//            } FIXME */;
//        return comments[ index ];
        return __FMT_PARAM_SORT_STRVALUE_COMMENT_VALUE[ index ][ 2 ];

    }

}
