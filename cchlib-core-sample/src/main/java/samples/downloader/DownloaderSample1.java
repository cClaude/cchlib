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
public class DownloaderSample1
    implements GenericDownloaderAppInterface
{
    private final static Logger logger = Logger.getLogger( DownloaderSample1.class );
    //private final static Proxy PROXY = Proxy.NO_PROXY;
    private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("55.37.80.2", 3128));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("195.168.109.60", 8080));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("195.168.109.60", 8080));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.orange.fr", 8080));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("unblockinschool.com", 3128));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("122.194.5.154", 80));
    private final static int    DOWNLOAD_THREAD = 10;

    /** number of pages to explore */
    private final static int DEFAULT_MAX_PAGES = 25;
    /** number of pages to explore */
    private int pageCount = DEFAULT_MAX_PAGES;

    private final static String serverRootURLString = "http://www.bloggif.com";
    private final static String htmlURLBase         = serverRootURLString + "/creations?page=";
    private List<StringDownloadURL> _htmlURLList = null;

    /**
     * Start Sample here !
     */
    public static void main( String...args )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        File destinationFolderFile = new File( new File(".").getAbsoluteFile(), "output/www.bloggif.com" ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        final DownloaderSample1 downloadConfig = new DownloaderSample1();

        final LoggerListener mylogger = new MyLoggerListener( logger );
        final GenericDownloaderAppUIResults gdauir = new GenericDownloaderAppUIResults()
        {
            @Override
            public int getDownloadThreadCount()
            {
                return DOWNLOAD_THREAD;
            }
            @Override
            public Proxy getProxy()
            {
                return PROXY;
            }

            @Override
            public LoggerListener getAbstractLogger()
            {
                return mylogger;
            }
        };

        GenericDownloader instance
            = new GenericDownloader(
                //destinationFolderFile,
                destinationFolderFile,
                DOWNLOAD_THREAD,
                PROXY,
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
/*
                String allContent;
                {
                    List<String>    contentList = loads( collectURLPrepare() );
                    StringBuilder   sb          = new StringBuilder();

                    for( String s: contentList ) {
                        sb.append( s );
                        }

                    allContent = sb.toString();
                    contentList.clear();
                    sb.setLength( 0 );
                }

                final String[] regexps = {
                    "<img class=\"img_progress ...\" src=\"",
                    "<img class=\"img_progress ....\" src=\""
                    };

                Set<URL> imagesURLCollection = new HashSet<URL>();

                for( String regexp : regexps ) {
                    String[] strs = allContent.toString().split( regexp );
                    logger.info( "> img founds = " + (strs.length - 1));

                    for( int i=1; i<strs.length; i++ ) {
                        String  s   = strs[ i ];
                        int     end = s.indexOf( '"' );
                        String  src = s.substring( 0, end );

                        //imagesURLCollection.add( new URL( serverRootURLString + src ) );
                        imagesURLCollection.add( new URL( src ) );
                        }
                    }

                logger.info( "> URL founds = " + imagesURLCollection.size() );

                return imagesURLCollection;
*/
            }
        };

        mylogger.info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        mylogger.info( "done" );
    }

    @Override
    public String getSiteName()
    {
        return "bloggif.com";
    }
    @Override
    public int getNumberOfPicturesByPage()
    {
        return 10; // FIXME
    }

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
        return "output/www.bloggif.com";
    }

    @Override
    public Collection<StringDownloadURL> getURLDownloadAndParseCollection()
            throws MalformedURLException
    {
        if( _htmlURLList == null ) {
            _htmlURLList = new ArrayList<StringDownloadURL>();

            for( int i=1; i<getPageCount(); i++ ) {
                _htmlURLList.add( new StringDownloadURL( htmlURLBase + i ) );
                }
            }

        return _htmlURLList;
    }

    @Override
    public Collection<URL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults   gdauir,
            String                          content2Parse
            )
            throws MalformedURLException
    {
        final String[] regexps = {
                "<img class=\"img_progress ...\" src=\"",
                "<img class=\"img_progress ....\" src=\""
                };

            Set<URL> imagesURLCollection = new HashSet<URL>();

        for( String regexp : regexps ) {
            String[] strs = content2Parse.toString().split( regexp );
            gdauir.getAbstractLogger().info( "> img founds = " + (strs.length - 1));

            for( int i=1; i<strs.length; i++ ) {
                String  s   = strs[ i ];
                int     end = s.indexOf( '"' );
                String  src = s.substring( 0, end );

                //imagesURLCollection.add( new URL( serverRootURLString + src ) );
                imagesURLCollection.add( new URL( src ) );
                }
            }

        gdauir.getAbstractLogger().info( "> URL founds = " + imagesURLCollection.size() );

        return imagesURLCollection;
    }

}
