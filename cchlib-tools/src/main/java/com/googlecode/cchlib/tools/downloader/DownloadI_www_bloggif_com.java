package samples.downloader;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import samples.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;

/**
 *
 *
 *
 */
public class DownloadI_www_bloggif_com
    extends AbstractDownloaderAppInterface
{
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger( DownloadI_www_bloggif_com.class );

    /** number of pages to explore */
    private final static int DEFAULT_MAX_PAGES = 25;
    /** Average number of pictures on a page */
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 10;
    private static final String SITE_NAME = "www.bloggif.com";

    private final static String serverRootURLString = "http://www.bloggif.com";
    private final static String htmlURLBase         = serverRootURLString + "/creations?page=";

    //private List<StringDownloadURL> _htmlURLList = null;

    public DownloadI_www_bloggif_com()
    {
        super(
            SITE_NAME,
            NUMBER_OF_PICTURES_BY_PAGE,
            DEFAULT_MAX_PAGES
            );
    }

    /* *
     * Start Sample here !
     * /
    public static void main( String...args )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        File destinationFolderFile = new File( new File(".").getAbsoluteFile(), "output" ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        final DownloaderSample1 downloadConfig = new DownloaderSample1();
        downloadConfig.setProxy( PROXY );

        final LoggerListener mylogger = new MyLoggerListener( logger );
        final GenericDownloaderAppUIResults gdauir = new GenericDownloaderAppUIResults()
        {
            @Override
            public int getDownloadThreadCount()
            {
                return DOWNLOAD_THREAD;
            }
            @Override
            public LoggerListener getAbstractLogger()
            {
                return mylogger;
            }
        };

        GenericDownloader instance
            = new GenericDownloader(downloadConfig, gdauir
//                destinationFolderFile,
//                downloadConfig.getCacheRelativeDirectoryCacheName(),
//                gdauir.getDownloadThreadCount(), //DOWNLOAD_THREAD,
//                mylogger
                )/*
        {
            @Override
            protected Collection<FileDownloadURL> collectDownloadURLs() throws IOException
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
            }
        }* /;

        mylogger.info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        mylogger.info( "done" );
    }*/

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "com.bloggif.www";
    }

    @Override
    public Collection<DownloadStringURL> getURLDownloadAndParseCollection()
            throws MalformedURLException, URISyntaxException
    {
        ArrayList<DownloadStringURL> list = new ArrayList<DownloadStringURL>();

        for( int i=1; i<getPageCount(); i++ ) {
            list.add( new DefaultDownloadStringURL( htmlURLBase + i, null, getProxy() ) );
            }

        return list;
    }

    @Override
    public Collection<DownloadFileURL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults   gdauir,
            DownloadStringURL               content2Parse
            )
            throws MalformedURLException, URISyntaxException
    {
        final String[] regexps = {
                "<img class=\"img_progress ...\" src=\"",
                "<img class=\"img_progress ....\" src=\""
                };

        final Collection<DownloadFileURL> imagesURLCollection = new HashSet<DownloadFileURL>();

        for( String regexp : regexps ) {
            String[] strs = content2Parse.toString().split( regexp );

            if( logger.isDebugEnabled() ) {
                logger.debug( "> img founds = " + (strs.length - 1));
                }

            for( int i=1; i<strs.length; i++ ) {
                String  s   = strs[ i ];
                int     end = s.indexOf( '"' );
                String  src = s.substring( 0, end );

                //imagesURLCollection.add( new URL( serverRootURLString + src ) );
                imagesURLCollection.add( new DefaultDownloadFileURL( src, null, getProxy() ) );
                }
            }

        if( logger.isDebugEnabled() ) {
            logger.debug( "> URL founds = " + imagesURLCollection.size() );
            }

        return imagesURLCollection;
    }

    @Override
    public DownloadStringURL getDownloadStringURL( int pageNumber )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( String src, int regexpIndex )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Button getButtonConfig()
    {
        return null;
    }
    
    @Override
    public void setSelectedItems( final List<Item> selectedItems )
    {
        // TODO Auto-generated method stub
        
    }
}
