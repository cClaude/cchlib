package com.googlecode.cchlib.tools.downloader.connector;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;
import com.googlecode.cchlib.tools.downloader.AbstractDownloaderAppInterface;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;


/**
 *
 *
 *
 */
public class DownloadI_www_bloggif_com
    extends AbstractDownloaderAppInterface
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DownloadI_www_bloggif_com.class );

    /** number of pages to explore */
    private static final int DEFAULT_MAX_PAGES = 25;
    /** Average number of pictures on a page */
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 10;
    private static final String SITE_NAME = "www.bloggif.com";

    private static final String serverRootURLString = "http://www.bloggif.com";
    private static final String htmlURLBase         = serverRootURLString + "/creations?page=";

    //private List<StringDownloadURL> _htmlURLList = null;

    public DownloadI_www_bloggif_com()
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
        return "com.bloggif.www";
    }

    @Override
    public Collection<DownloadStringURL> getURLDownloadAndParseCollection()
            throws MalformedURLException, URISyntaxException
    {
        final ArrayList<DownloadStringURL> list = new ArrayList<DownloadStringURL>();

        for( int i=1; i<getPageCount(); i++ ) {
            list.add( new DefaultDownloadStringURL( htmlURLBase + i, null, getProxy() ) );
            }

        return list;
    }

    @Override
    public Collection<DownloadFileURL> getURLToDownloadCollection(
            final GenericDownloaderAppUIResults   gdauir,
            final DownloadStringURL               content2Parse
            )
            throws MalformedURLException, URISyntaxException
    {
        final String[] regexps = {
                "<img class=\"img_progress ...\" src=\"",
                "<img class=\"img_progress ....\" src=\""
                };

        final Collection<DownloadFileURL> imagesURLCollection = new HashSet<DownloadFileURL>();

        for( final String regexp : regexps ) {
            final String[] strs = content2Parse.toString().split( regexp );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "> img founds = " + (strs.length - 1));
                }

            for( int i=1; i<strs.length; i++ ) {
                final String  s   = strs[ i ];
                final int     end = s.indexOf( '"' );
                final String  src = s.substring( 0, end );

                //imagesURLCollection.add( new URL( serverRootURLString + src ) );
                imagesURLCollection.add( new DefaultDownloadFileURL( src, null, getProxy() ) );
                }
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "> URL founds = " + imagesURLCollection.size() );
            }

        return imagesURLCollection;
    }

    @Override
    public DownloadStringURL getDownloadStringURL( final int pageNumber )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( final String src, final int regexpIndex )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public GenericDownloaderAppButton getButtonConfig()
    {
        return null;
    }

    @Override
    public void setSelectedItems( final List<Item> selectedItems )
    {
        // TODO Auto-generated method stub
    }
}
