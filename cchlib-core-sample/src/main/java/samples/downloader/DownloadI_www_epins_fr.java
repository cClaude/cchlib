package samples.downloader;

import java.net.MalformedURLException;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
//import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.FileDownloadURL;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 *
 *http://www.photofunia.com/output/4/1/I/V/c/IVcOXqVdzyhMjNamUoG1IA_s.jpg
 */
public class DownloadI_www_epins_fr
    extends AbstractDownloadInterface
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
    public Collection<StringDownloadURL> getURLDownloadAndParseCollection()
            throws MalformedURLException
    {
        return Collections.emptyList();
    }

    @Override
    public Collection<FileDownloadURL> getURLToDownloadCollection(
            GenericDownloaderAppUIResults   gdauir,
            String                          content2Parse
            )
            throws MalformedURLException
    {
        return new AbstractCollection<FileDownloadURL>()
        {
            @Override
            public Iterator<FileDownloadURL> iterator()
            {
                return new Iterator<FileDownloadURL>()
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
                    public FileDownloadURL next()
                    {
                        try {
                            return buildDownloadURL( i++ );
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
                    private FileDownloadURL buildDownloadURL( final int i ) throws MalformedURLException
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

                        return new FileDownloadURL( buildURL_sb1.toString(), null, getProxy() );
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
    public FileDownloadURL getDownloadURLFrom( String src, int regexpIndex )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();// NOT USE
    }
}
