package samples.downloader;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.FileDownloadURL;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 *
 */
public abstract class AbstractDownloadInterface
    implements GenericDownloaderAppInterface
{
    private final static transient Logger logger = Logger.getLogger( AbstractDownloadInterface.class );
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
    protected AbstractDownloadInterface(
        final String    siteName,
        final int       numberOfPicturesByPage,
        final int       pageCount
        )
    {
        this.siteName = siteName;
        this.numberOfPicturesByPage = numberOfPicturesByPage;

        setPageCount( pageCount );
    }

//    /**
//     * @return always null (default implementation)
//     */
//    @Override
//    public CookieHandler getCookieHandler()
//    {
//        return null;
//    }

//    /**
//     * @return always null (default implementation)
//     */
//    @Override
//    public Map<String, String> getRequestPropertyMap()
//    {
//        return null;
//    }

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
     * @param i
     * @return
     * @throws MalformedURLException
     */
    abstract public StringDownloadURL getStringDownloadURL( final int pageNumber )
            throws MalformedURLException;

    @Override// GenericDownloaderAppInterface
    public Collection<StringDownloadURL> getURLDownloadAndParseCollection()
            throws MalformedURLException
    {
        final List<StringDownloadURL> sdURLList = new ArrayList<StringDownloadURL>();

        for( int i=1; i< getPageCount(); i++ ) {
            sdURLList.add( getStringDownloadURL( i ) );
            }

        return sdURLList;
    }

    /**
     * TODOC
     *
     * @param src
     * @param regexpIndex
     * @return
     * @throws MalformedURLException
     */
    abstract
    public FileDownloadURL getDownloadURLFrom( String src, int regexpIndex )
        throws MalformedURLException;

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
        public String getBeginRegExp() { return beginRegExg; }
        public char getLastChar() { return lastChar; }
    }

    /**
     * Default implementation of {@link GenericDownloaderAppInterface#getURLToDownloadCollection(GenericDownloaderAppUIResults, String)},
     * that use {@link AbstractDownloadInterface#getURLToDownload(String, int)}
     *
     * @param gdauir
     * @param allContent
     * @param regexps
     * @return
     * @see GenericDownloaderAppInterface#getURLToDownloadCollection(GenericDownloaderAppUIResults, String)}
     * @see AbstractDownloadInterface#getURLToDownload(String, int)
     */
    final//FIXME remove this
    public Collection<FileDownloadURL> getURLToDownloadCollection(
        final GenericDownloaderAppUIResults gdauir,
        final String                        content2Parse,
        final RegExgSplitter[]              regexps
        )
    {
        final Set<FileDownloadURL> imagesURLCollection = new HashSet<FileDownloadURL>();

        for( RegExgSplitter regexp : regexps ) {
            final String[] strs = content2Parse.toString().split( regexp.getBeginRegExp() );
            gdauir.getAbstractLogger().info( "> img founds = " + (strs.length - 1));

            for( int i=1; i<strs.length; i++ ) {
                final String    strPart = strs[ i ];
                final int       end = strPart.indexOf( regexp.getLastChar() /*'"'*/ );
                final String    src = strPart.substring( 0, end );

                //imagesURLCollection.add( new URL( String.format( IMG_URL_BASE_FMT, src ) ) );
                try {
                    imagesURLCollection.add( getDownloadURLFrom( src, i ) );
                    }
                catch( MalformedURLException e ) {
                    // TODO Auto-generated catch block
                    logger.warn( "MalformedURLException src = [" + src + "]" );
                    logger.warn( "MalformedURLException", e );
                    logger.warn( "MalformedURLException strPart:\n------->>\n"
                            + strPart
                            + "\n<<-------"
                            );
                    logger.warn( "MalformedURLException content2Parse:\n------->>\n"
                            + content2Parse
                            + "\n<<-------"
                            );
                    }
                }
            }

        gdauir.getAbstractLogger().info( "> URL founds = " + imagesURLCollection.size() );

        return imagesURLCollection;
    }

    /**
     *
     * @param entry
     */
    final//FIXME remove this
    protected void addComboBoxConfig( final GenericDownloaderAppInterface.ComboBoxConfig entry )
    {
        if( this.comboBoxConfigList == null ) {
            this.comboBoxConfigList = new ArrayList<>();
            };

        this.comboBoxConfigList.add( entry );
    }

    /**
     * @see DefaultComboBoxConfig
     */
    @Override// GenericDownloaderAppInterface
    final//FIXME remove this
    public Collection<GenericDownloaderAppInterface.ComboBoxConfig> getComboBoxConfigCollection()
    {
        if( this.comboBoxConfigList == null ) {
            return Collections.emptyList();
            }

        return this.comboBoxConfigList;
    }
}
