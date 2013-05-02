package samples.downloader;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;

/**
 *
 *
 */
public abstract class AbstractDownloaderAppInterface
    implements GenericDownloaderAppInterface, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger( AbstractDownloaderAppInterface.class );
    public static final String DownloadFileURL_PARENT_URL_PROPERTY = "parent";
    private String  siteName;
    private int     numberOfPicturesByPage;
    private int     pageCount;
    private Proxy   proxy;
    private List<GenericDownloaderAppInterface.ComboBoxConfig> comboBoxConfigList;

    /**
     * @param siteName
     * @param numberOfPicturesByPage
     * @param pageCount
     */
    protected AbstractDownloaderAppInterface(
        final String    siteName,
        final int       numberOfPicturesByPage,
        final int       pageCount
        )
    {
        this.siteName = siteName;
        this.numberOfPicturesByPage = numberOfPicturesByPage;

        setPageCount( pageCount );
    }

    @Override
    final public String getSiteName() { return this.siteName; }

    @Override
    final public int getNumberOfPicturesByPage() { return this.numberOfPicturesByPage; }

    @Override// GenericDownloaderAppInterface
    final// FIXME: remove this
    public int getPageCount()
    {
        return this.pageCount;
    }

    @Override// GenericDownloaderAppInterface
    final// FIXME: remove this
    public void setPageCount( final int pageCount )
    {
        // TODO  >= 1; // Min value ???
        this.pageCount = pageCount;
    }

    @Override// GenericDownloaderAppInterface
    final// FIXME: remove this
    public int getMaxPageCount()
    {
        return Integer.MAX_VALUE; //Default value !
    }

    @Override// GenericDownloaderAppInterface
    final// FIXME: remove this
    public Proxy getProxy()
    {
        return this.proxy;
    }

    @Override// GenericDownloaderAppInterface
    final// FIXME: remove this
    public void setProxy( final Proxy proxy )
    {
        this.proxy = proxy;
    }

    /**
     * TODOC !
     * @param pageNumber
     * @return TODOC
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    abstract public DownloadStringURL getDownloadStringURL( final int pageNumber )
            throws MalformedURLException, URISyntaxException;

    /**
     * Default implementation based on
     * {@link #getPageCount()} and on
     * {@link #getDownloadStringURL(int)}
     */
    @Override// GenericDownloaderAppInterface
    public Collection<DownloadStringURL> getURLDownloadAndParseCollection()
            throws MalformedURLException, URISyntaxException
    {
        final List<DownloadStringURL> sdURLList = new ArrayList<DownloadStringURL>();

        for( int i=1; i<= getPageCount(); i++ ) {
            sdURLList.add( getDownloadStringURL( i ) );
            }

        return sdURLList;
    }

    /**
     * TODOC
     *
     * @param src
     * @param regexpIndex
     * @return TODOC
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    abstract
    public DownloadFileURL getDownloadURLFrom( String src, int regexpIndex )
        throws MalformedURLException, URISyntaxException;

    public interface RegExgSplitter
    {
        public String getBeginRegExp();
        public char getLastChar();
    }

    public class DefaultRegExgSplitter implements RegExgSplitter
    {
        private String beginRegExg;
        private char lastChar;
        public DefaultRegExgSplitter( final String beginRegExg, final char lastChar )
        {
            this.beginRegExg = beginRegExg;
            this.lastChar = lastChar;
        }
        @Override
        public String getBeginRegExp() { return beginRegExg; }
        @Override
        public char getLastChar() { return lastChar; }
    }
    
    /*
     * Default implementation of {@link GenericDownloaderAppInterface#getURLToDownloadCollection(GenericDownloaderAppUIResults, String)},
     * that use {@link AbstractDownloadInterface#getDownloadURLFrom(String, int)}
     *
     * @param gdauir
     * @param content2Parse
     * @param regexps
     * @return TODOC
     * @see GenericDownloaderAppInterface#getURLToDownloadCollection(GenericDownloaderAppUIResults, String)
     * @see AbstractDownloadInterface#getDownloadURLFrom(String, int)
     */
    /**
     * TODOC
     * 
     * @param gdauir
     * @param content2Parse
     * @param regexps
     * @return a {@link Collection} of {@link DownloadFileURL}
     */
    final//FIXME remove this
    public Collection<DownloadFileURL> getURLToDownloadCollection(
        final GenericDownloaderAppUIResults gdauir,
        final DownloadStringURL             content2Parse,
        final RegExgSplitter[]              regexps
        )
    {
        final Set<DownloadFileURL> imagesURLCollection = new HashSet<DownloadFileURL>();

        for( RegExgSplitter regexp : regexps ) {
            final String[] strs = content2Parse.getResultAsString().split( regexp.getBeginRegExp() );

            if( logger.isDebugEnabled() ) {
                logger.debug( "> img founds = " + (strs.length - 1));
                }

            for( int i=1; i<strs.length; i++ ) {
                final String    strPart = strs[ i ];
                final int       end = strPart.indexOf( regexp.getLastChar() /*'"'*/ );
                final String    src = strPart.substring( 0, end );

                try {
                    //imagesURLCollection.add( getDownloadURLFrom( src, i ) );
                    DownloadFileURL dfURL = getDownloadURLFrom( src, i );

                    dfURL.setProperty( DownloadFileURL_PARENT_URL_PROPERTY, content2Parse.getURL() );

                    imagesURLCollection.add( dfURL );
                    }
                catch( MalformedURLException | URISyntaxException e ) {
                    // TODO Auto-generated catch block
                    logger.warn( "URL Exception src = [" + src + "]" );
                    logger.warn( "URL Exception", e );
                    logger.warn( "URL Exception strPart:\n------->>\n"
                            + strPart
                            + "\n<<-------"
                            );
                    logger.warn( "URL Exception content2Parse:\n------->>\n"
                            + content2Parse.getResultAsString()
                            + "\n<<-------"
                            );
                    }
                }
            }

        if( logger.isDebugEnabled() ) {
            logger.debug( "> URL founds = " + imagesURLCollection.size() );
            }

        return imagesURLCollection;
    }

    /**
     *
     * @param entry
     */
    protected void addComboBoxConfig( final GenericDownloaderAppInterface.ComboBoxConfig entry )
    {
        if( this.comboBoxConfigList == null ) {
            this.comboBoxConfigList = new ArrayList<>();
            }

        this.comboBoxConfigList.add( entry );
    }

    /**
     * @see DefaultComboBoxConfig
     */
    @Override// GenericDownloaderAppInterface
    public Collection<GenericDownloaderAppInterface.ComboBoxConfig> getComboBoxConfigCollection()
    {
        if( this.comboBoxConfigList == null ) {
            return Collections.emptyList();
            }

        return this.comboBoxConfigList;
    }
}
