package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 *
 *
 */
public class DownloaderSample_gifpal
{
    private final static Logger logger = Logger.getLogger( DownloaderSample_gifpal.class );

    //private final static Proxy PROXY = Proxy.NO_PROXY;
    private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("55.37.80.2", 3128));
    private final static int    DOWNLOAD_THREAD = 10;

    private final static String SERVER_ROOT_URL_STR = "http://www.gifpal.com";

    /**
     * param1 = sort
     * param2 = page_number
     *
     * http://www.gifpal.com/gallery-contents-json.php?sort=id&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top-today&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top-week&page=2
     * http://www.gifpal.com/gallery-contents-json.php?sort=top&page=2
     */
    private final static String HTML_URL_BASE_FMT   = SERVER_ROOT_URL_STR + "/gallery-contents-json.php?sort=%s&page=%d";

    // sort parameter values
    private final static String[] FMT_PARAM_SORT_VALUES = {
        "id",
        "top",
        "top-today",
        "top-week",
        };

    // number of pages to explore
    private final static int MAX_PAGES = 50;

    /**
     * param1 = image_id
     *
     * http://www.gifpal.com/uimages/WVrkTTeOoI.gif
     */
    private final static String IMG_URL_BASE_FMT   = SERVER_ROOT_URL_STR + "/uimages/%s.gif";

    /**
     * Start Sample here !
     */
    public static void main( String...args )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        File destinationFolderFile = new File( new File(".").getAbsoluteFile(), "output/www.gifpal.com" ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        final GenericDownloader.AbstractLogger mylogger = new GenericDownloader.AbstractLogger()
        {
            @Override
            public void warn( String msg )
            {
                //System.out.println( msg );
                logger.warn( msg );
            }
            @Override
            public void info( String msg )
            {
                //System.out.println( msg );
                logger.info( msg );
            }
            @Override
            public void error( URL url, Throwable cause )
            {
                //String msg = "*ERROR: " + url + " - " + cause.getMessage() );
                //System.err.println( "*ERROR: " + url + " - " + cause.getMessage() );
                logger.error( url, cause );
            }
        };

        GenericDownloader instance
            = new GenericDownloader(
                destinationFolderFile,
                destinationFolderFile,
                DOWNLOAD_THREAD,
                PROXY,
                mylogger
                )
        {
            private List<URL> _htmlURLList = null;

            @Override
            protected Iterable<URL> collectURLs() throws IOException
            {
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
                    "\\{\"image\"\\:\"",
                    };

                Set<URL> imagesURLCollection = new HashSet<URL>();

                for( String regexp : regexps ) {
                    String[] strs = allContent.toString().split( regexp );
                    mylogger.info( "> img founds = " + (strs.length - 1));

                    for( int i=1; i<strs.length; i++ ) {
                        String  s   = strs[ i ];
                        int     end = s.indexOf( '"' );
                        String  src = s.substring( 0, end );

                        imagesURLCollection.add( new URL( String.format( IMG_URL_BASE_FMT, src ) ) );
                        }
                    }

                mylogger.info( "> URL founds = " + imagesURLCollection.size() );

                return imagesURLCollection;
            }

            /**
            * Returns a list of URL of json values to parse
            * @return a list of URL of json values to parse
            * @throws MalformedURLException
            */
           Iterable<URL> collectURLPrepare() throws MalformedURLException
           {
               if( _htmlURLList == null ) {
                   _htmlURLList = new ArrayList<URL>();
                   //_htmlURLList.add( new URL( "http://www.google.com/" ) );

                   for( int i=1; i<MAX_PAGES; i++ ) {
                       _htmlURLList.add( new URL( String.format( HTML_URL_BASE_FMT, FMT_PARAM_SORT_VALUES[ 0 ], i ) ) );
                       }
                   }

               return _htmlURLList;
           }
        };

        mylogger.info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        mylogger.info( "done" );
    }
}