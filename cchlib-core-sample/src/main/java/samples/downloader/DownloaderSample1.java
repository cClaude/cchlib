package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 *
 *
 */
public class DownloaderSample1
{
    //
    private final static Proxy PROXY = Proxy.NO_PROXY;
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("195.168.109.60", 8080));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("195.168.109.60", 8080));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.orange.fr", 8080));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("unblockinschool.com", 3128));
    // private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("122.194.5.154", 80));
    private final static int    DOWNLOAD_THREAD = 10;

    private final static String serverRootURLString = "http://www.bloggif.com";
    private final static String htmlURLBase         = serverRootURLString + "/creations?page=";

    /**
     * Start Sample here !
     */
    public static void main( String...args )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        File destinationFolderFile = new File( new File(".").getAbsoluteFile(), "output/www.bloggif.com" ).getCanonicalFile();
        destinationFolderFile.mkdirs();
/*
        DownloaderSample1 instance0 = new DownloaderSample1( destinationFolderFile );

        instance.println( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        instance.println( "done" );
*/
        final GenericDownloader.Logger logger = new GenericDownloader.Logger()
        {
            @Override
            public void warn( String msg )
            {
                System.out.println( msg );
            }
            @Override
            public void info( String msg )
            {
                System.out.println( msg );
            }
            @Override
            public void error( URL url, Throwable cause )
            {
                System.err.println( "*ERROR:" + url + " - " + cause.getMessage() );
            }
        };

        GenericDownloader instance
            = new GenericDownloader(
                destinationFolderFile,
                destinationFolderFile,
                DOWNLOAD_THREAD,
                PROXY,
                logger
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
            }

            /**
            *
            * @return
            * @throws MalformedURLException
            */
           Iterable<URL> collectURLPrepare() throws MalformedURLException
           {
               if( _htmlURLList == null ) {
                   _htmlURLList = new ArrayList<URL>();

                   for( int i=1; i<23; i++ ) {
                       _htmlURLList.add( new URL( htmlURLBase + i ) );
                       }
                   }

               return _htmlURLList;
           }
        };

        logger.info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        logger.info( "done" );
    }
}
