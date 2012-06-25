package samples.downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 *
 */
public abstract class AbstractDownloadInterface
    implements GenericDownloaderAppInterface
{
    private int pageCount = 1; // Min value
    private int extraStringSelectedIndex;

    /**
     *
     * @param pageCount
     */
    protected AbstractDownloadInterface( final int pageCount )
    {
        setPageCount( pageCount );
    }

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
        this.pageCount = pageCount;
    }

    @Override// GenericDownloaderAppInterface
    public int getMaxPageCount()
    {
        return Integer.MAX_VALUE; //Default value !
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
    //final// FIXME: remove this
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
    public URL getURL( String src, int regexpIndex )
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
     * TODOC
     *
     * @param gdauir
     * @param allContent
     * @param regexps
     * @return
     * @throws MalformedURLException
     */
    final//FIXME remove this
    public Collection<URL> getURLToDownloadCollection(
        final GenericDownloaderAppUIResults gdauir,
        final String                        allContent,
        //final String[]                      regexps
        final RegExgSplitter[]              regexps
        ) throws MalformedURLException
    {
        final Set<URL> imagesURLCollection = new HashSet<URL>();

        for( RegExgSplitter regexp : regexps ) {
            String[] strs = allContent.toString().split( regexp.getBeginRegExp() );
            gdauir.getAbstractLogger().info( "> img founds = " + (strs.length - 1));

            for( int i=1; i<strs.length; i++ ) {
                String  s   = strs[ i ];
                int     end = s.indexOf( regexp.getLastChar() /*'"'*/ );
                String  src = s.substring( 0, end );

                //imagesURLCollection.add( new URL( String.format( IMG_URL_BASE_FMT, src ) ) );
                imagesURLCollection.add( getURL( src, i ) );
                }
            }

        gdauir.getAbstractLogger().info( "> URL founds = " + imagesURLCollection.size() );

        return imagesURLCollection;
    }

    @Override// GenericDownloaderAppInterface
    final//FIXME remove this
    public int getExtraStringSelectedIndex()
    {
        return extraStringSelectedIndex;
    }

    @Override// GenericDownloaderAppInterface
    final//FIXME remove this
    public void setExtraStringSelectedIndex( final int index )
    {
        this.extraStringSelectedIndex = index;
    }
}
