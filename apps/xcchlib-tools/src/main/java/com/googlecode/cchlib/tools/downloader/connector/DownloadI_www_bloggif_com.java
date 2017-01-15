package com.googlecode.cchlib.tools.downloader.connector;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.FileDownloader;
import com.googlecode.cchlib.net.download.StringDownloader;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface;

@SuppressWarnings({
    "squid:S00101", // Class naming convention
    })
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
    public Collection<ContentDownloadURI<String>> getURLDownloadAndParseCollection()
            throws MalformedURLException, URISyntaxException
    {
        final ArrayList<ContentDownloadURI<String>> list = new ArrayList<>();

        for( int i=1; i<getPageCount(); i++ ) {
            list.add( new StringDownloader( htmlURLBase + i, null, getProxy() ) );
            }

        return list;
    }

    @Override
    public Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader(
            final GenericDownloaderAppUIResults   gdauir,
            final ContentDownloadURI<String>      content2Parse
            )
            throws MalformedURLException, URISyntaxException
    {
        final String[] regexps = {
                "<img class=\"img_progress ...\" src=\"",
                "<img class=\"img_progress ....\" src=\""
                };

        final Collection<ContentDownloadURI<File>> imagesURLCollection = new HashSet<>();

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
                imagesURLCollection.add(
                        new FileDownloader( src, null, getProxy() )
                        );
                }
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "> URL founds = " + imagesURLCollection.size() );
            }

        return imagesURLCollection;
    }

    @Override
    public ContentDownloadURI<String> getDownloadStringURL( final int pageNumber )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ContentDownloadURI<File> getDownloadURLFrom( final String src, final int regexpIndex )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public GenericDownloaderAppButton getButtonConfig()
    {
        return null;
    }

    @Override
    public void doSelectedItems( final List<Item> selectedItems )
    {
        // TODO Not implemented
        Logger.getLogger( getClass() ).warn( "NOT IMPLEMENTED" );
    }
}
