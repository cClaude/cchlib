package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 *
 *http://www.photofunia.com/output/4/1/I/V/c/IVcOXqVdzyhMjNamUoG1IA_s.jpg
 */
public class DownloaderSample2
    extends AbstractDownloadInterface
        implements GenericDownloaderAppInterface
{
    private final static Logger logger = Logger.getLogger( DownloaderSample1.class );
    private final static int    DOWNLOAD_THREAD = 20;
    //private final static Proxy PROXY = Proxy.NO_PROXY;
    private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("55.37.80.2", 3128));
    //private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("195.168.109.60", 8080));
    //private final static Proxy  PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("122.194.5.154", 80));

    /* http://www.epins.fr/pins/98699.png */
    /* http://www.epins.fr/pins/104639.png */
    private final static String serverRootURLString = "http://www.epins.fr";
    private final static String htmlURLFmt0         = serverRootURLString + "/pins/";
    //private final static String htmlURLFmt1         = "00000";
    private final static String htmlURLFmt2         = ".png";
    //private final static int    MIN = 0;
    //private final static int    MAX = 1000000;
    private final static int    MIN = 1000000;
    private final static int    MAX = 1100000;
    //private final static int    MAX = 110;

    //private final static String CACHE_FOLDER_NAME = "output/";

    /**
     * Start Sample here !
     */
    public static void main( String...args )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        File destinationFolderFile = new File( new File(".").getAbsoluteFile(), "output" ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        final DownloaderSample2 downloadConfig = new DownloaderSample2();
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
            @Override
            public CookieHandler getCookieHandler()
            {
                return null;
            }
        };

        GenericDownloader instance
            = new GenericDownloader(
                destinationFolderFile,
                downloadConfig.getCacheRelativeDirectoryCacheName(),
                gdauir.getDownloadThreadCount(),//DOWNLOAD_THREAD,
                gdauir.getProxy(),//PROXY,
                gdauir.getCookieHandler(),
                mylogger
                )
        {
            @Override
            protected Collection<URL> collectURLs() throws IOException
            {
                return downloadConfig.getURLToDownloadCollection( gdauir, null );
            }
        };

        mylogger.info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        mylogger.info( "done" );
    }

    protected DownloaderSample2()
    {
        super(
            "epins.fr",
            10 /* FIXME */,
            10 /* FIXME */
            );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "epins.fr";
    }

    @Override
    public Collection<StringDownloadURL> getURLDownloadAndParseCollection()
            throws MalformedURLException
    {
        return Collections.emptyList();
    }

    @Override
    public Collection<URL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults   gdauir,
            String                          content2Parse
            )
            throws MalformedURLException
    {
        return new AbstractCollection<URL>()
        {
            @Override
            public Iterator<URL> iterator()
            {
                return new Iterator<URL>()
                {
                    private StringBuilder buildURL_sb1 = new StringBuilder();
                    //private StringBuilder buildURL_sb2 = new StringBuilder();
                    private int i = MIN;

                    @Override
                    public boolean hasNext()
                    {
                        return i<MAX;
                    }
                    @Override
                    public URL next()
                    {
                        try {
                            return buildURL( i++ );
                            }
                        catch( MalformedURLException e ) {
                            throw new RuntimeException( e );
                            }
                    }
                    @Override
                    public void remove()
                    {
                        throw new UnsupportedOperationException();
                    }
                    private URL buildURL( final int i ) throws MalformedURLException
                    {

                        buildURL_sb1.setLength( 0 );
                        //buildURL_sb2.setLength( 0 );

                        buildURL_sb1.append( htmlURLFmt0 );

                        buildURL_sb1.append( i );
                        //buildURL_sb2.append( htmlURLFmt1 ).append( i );
                        //int end     = buildURL_sb2.length();
                        //int start   = end - htmlURLFmt1.length();

                        //buildURL_sb1.append( buildURL_sb2.substring( start, end ) );
                        buildURL_sb1.append( htmlURLFmt2 );

                        return new URL( buildURL_sb1.toString() );
                    }
                };
            }
            @Override
            public int size()
            {
                return MAX - MIN;
            }
        };
    }

    @Override
    public StringDownloadURL getStringDownloadURL( int pageNumber )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();// NOT USE
    }

    @Override
    public URL getURLToDownload( String src, int regexpIndex )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();// NOT USE
    }
}
