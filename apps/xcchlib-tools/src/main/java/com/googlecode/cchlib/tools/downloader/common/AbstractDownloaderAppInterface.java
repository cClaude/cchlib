package com.googlecode.cchlib.tools.downloader.common;

import java.io.File;
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
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.comboconfig.DefaultComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.comboconfig.GenericDownloaderAppComboBoxConfig;

public abstract class AbstractDownloaderAppInterface
    implements com.googlecode.cchlib.tools.downloader.GenericDownloaderAppInterface, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger LOGGER = Logger.getLogger( AbstractDownloaderAppInterface.class );

    private final String  siteName;
    private final int     numberOfPicturesByPage;

    private int                                      pageCount;
    private Proxy                                    proxy;
    private List<GenericDownloaderAppComboBoxConfig> comboBoxConfigList;

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
    public final String getSiteName()
    {
        return this.siteName;
    }

    @Override
    public final int getNumberOfPicturesByPage()
    {
        return this.numberOfPicturesByPage;
    }

    @Override// GenericDownloaderAppInterface
    public final int getPageCount()
    {
        return this.pageCount;
    }

    @Override// GenericDownloaderAppInterface
    public final void setPageCount( final int pageCount )
    {
        // TODO  >= 1; // Min value ???
        this.pageCount = pageCount;
    }

    @Override// GenericDownloaderAppInterface
    public final int getMaxPageCount()
    {
        return Integer.MAX_VALUE; //Default value !
    }

    @Override// GenericDownloaderAppInterface
    public final Proxy getProxy()
    {
        return this.proxy;
    }

    @Override// GenericDownloaderAppInterface
    public final void setProxy( final Proxy proxy )
    {
        this.proxy = proxy;
    }

    /**
     * NEEDDOC !
     * @param pageNumber NEEDDOC
     * @return NEEDDOC
     * @throws MalformedURLException NEEDDOC
     * @throws URISyntaxException NEEDDOC
     */
    public abstract ContentDownloadURI<String> getDownloadStringURL( final int pageNumber )
            throws MalformedURLException, URISyntaxException;

    /**
     * {@inheritDoc}
     * Default implementation based on
     * {@link #getPageCount()} and on
     * {@link #getDownloadStringURL(int)}
     */
    @Override// GenericDownloaderAppInterface
    public Collection<ContentDownloadURI<String>> getURLDownloadAndParseCollection()
            throws MalformedURLException, URISyntaxException
    {
        final List<ContentDownloadURI<String>> sdURLList = new ArrayList<>();

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
    public abstract ContentDownloadURI<File> getDownloadURLFrom( String src, int regexpIndex )
        throws MalformedURLException, URISyntaxException;

    public interface RegExgSplitter
    {
        public String getBeginRegExp();
        public char getLastChar();
    }

    public static class DefaultRegExgSplitter implements RegExgSplitter
    {
        private final String beginRegExg;
        private final char lastChar;
        public DefaultRegExgSplitter( final String beginRegExg, final char lastChar )
        {
            this.beginRegExg = beginRegExg;
            this.lastChar = lastChar;
        }
        @Override
        public String getBeginRegExp() { return this.beginRegExg; }
        @Override
        public char getLastChar() { return this.lastChar; }
    }

    public final Collection<ContentDownloadURI<File>> getURLToDownloadCollection(
        final GenericDownloaderAppUIResults gdauir,
        final ContentDownloadURI<String>    content2Parse,
        final RegExgSplitter[]              regexps
        )
    {
        final Set<ContentDownloadURI<File>> imagesURLCollection = new HashSet<>();

        for( final RegExgSplitter regexp : regexps ) {
            final String[] strs = content2Parse.getResult().split( regexp.getBeginRegExp() );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "> img founds = " + (strs.length - 1));
                }

            for( int i=1; i<strs.length; i++ ) {
                final String    strPart = strs[ i ];
                final int       end = strPart.indexOf( regexp.getLastChar() /*'"'*/ );
                final String    src = strPart.substring( 0, end );

                try {
                    //imagesURLCollection.add( getDownloadURLFrom( src, i ) );
                    final ContentDownloadURI<File> dfURL = getDownloadURLFrom( src, i );

                    dfURL.setProperty( PropertiesNames.DownloadFileURL_PARENT_URL_PROPERTY, content2Parse.getURL() );

                    imagesURLCollection.add( dfURL );
                    }
                catch( MalformedURLException | URISyntaxException e ) {
                    LOGGER.warn( "URL Exception src = [" + src + "]" );
                    LOGGER.warn( "URL Exception", e );
                    LOGGER.warn( "URL Exception strPart:\n------->>\n"
                            + strPart
                            + "\n<<-------"
                            );
                    LOGGER.warn( "URL Exception content2Parse:\n------->>\n"
                            + content2Parse.getResult()
                            + "\n<<-------"
                            );
                    }
                }
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "> URL founds = " + imagesURLCollection.size() );
            }

        return imagesURLCollection;
    }

    protected void addComboBoxConfig( final GenericDownloaderAppComboBoxConfig entry )
    {
        if( this.comboBoxConfigList == null ) {
            this.comboBoxConfigList = new ArrayList<>();
            }

        this.comboBoxConfigList.add( entry );
    }

    /**
     * {@inheritDoc}
     * @see DefaultComboBoxConfig
     */
    @Override// GenericDownloaderAppInterface
    public Collection<GenericDownloaderAppComboBoxConfig> getComboBoxConfigCollection()
    {
        if( this.comboBoxConfigList == null ) {
            return Collections.emptyList();
            }

        return this.comboBoxConfigList;
    }
}
