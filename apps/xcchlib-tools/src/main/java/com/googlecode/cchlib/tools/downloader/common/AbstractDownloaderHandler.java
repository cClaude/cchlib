package com.googlecode.cchlib.tools.downloader.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface.RegExgSplitter;

public abstract class AbstractDownloaderHandler implements DownloaderHandler
{
    private final static Logger LOGGER = Logger.getLogger( AbstractDownloaderHandler.class );

    private final DownloaderData data;
    private GenericDownloaderAppButton buttonConfig;
    private Collection<GenericDownloaderAppComboBoxConfig> comboBoxConfigList;

    protected AbstractDownloaderHandler( final DownloaderData data )
    {
        this.data = data;
    }

    protected DownloaderData getDownloaderData()
    {
        return this.data;
    }

    @Override
    public GenericDownloaderAppButton getButtonConfig()
    {
        return this.buttonConfig;
    }

    /**
     * {@inheritDoc}
     * Default implementation based on
     * {@link #getPageCount()} and on
     * {@link #getDownloadStringURL(int)}
     */
    @Override
    public Collection<ContentDownloadURI<String>> getURLDownloadAndParseCollection()
            throws MalformedURLException, URISyntaxException
    {
        final List<ContentDownloadURI<String>> sdURLList = new ArrayList<>();

        for( int i=1; i<= this.data.getPageCount(); i++ ) {
            sdURLList.add( getDownloadStringURL( i ) );
            }

        return sdURLList;
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

    @Override
    public Collection<GenericDownloaderAppComboBoxConfig> getComboBoxConfigCollection()
    {
        if( this.comboBoxConfigList == null ) {
            return Collections.emptyList();
            }

        return this.comboBoxConfigList;
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
}
