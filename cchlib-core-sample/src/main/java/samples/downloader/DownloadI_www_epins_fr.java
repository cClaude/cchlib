package samples.downloader;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;

/**
 *
 *
 *http://www.photofunia.com/output/4/1/I/V/c/IVcOXqVdzyhMjNamUoG1IA_s.jpg
 */
public class DownloadI_www_epins_fr
    extends AbstractDownloaderAppInterface
{
    //private final static Logger logger = Logger.getLogger( DownloaderSample1.class );

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

    private static final String SITE_NAME = "www.epins.fr";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 10; // FIXME
    private static final int DEFAULT_MAX_PAGES = 10; // FIXME

    protected DownloadI_www_epins_fr()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "fr.epins";
    }

    @Override
    public Collection<DownloadStringURL> getURLDownloadAndParseCollection()
            throws MalformedURLException
    {
        return Collections.emptyList();
    }

    @Override
    public Collection<DownloadFileURL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults   gdauir,
            DownloadStringURL               content2Parse
            )
            throws MalformedURLException
    {
        return new AbstractCollection<DownloadFileURL>()
        {
            @Override
            public Iterator<DownloadFileURL> iterator()
            {
                return new Iterator<DownloadFileURL>()
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
                    public DownloadFileURL next()
                    {
                        try {
                            return buildDownloadURL( i++ );
                            }
                        catch( MalformedURLException | URISyntaxException e ) {
                            throw new RuntimeException( e );
                            }
                    }
                    @Override
                    public void remove()
                    {
                        throw new UnsupportedOperationException();
                    }
                    private DownloadFileURL buildDownloadURL( final int i ) throws MalformedURLException, URISyntaxException
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

                        return new DefaultDownloadFileURL( buildURL_sb1.toString(), null, getProxy() );
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
    public DownloadStringURL getDownloadStringURL( int pageNumber )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();// NOT USE
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( String src, int regexpIndex )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();// NOT USE
    }
}
