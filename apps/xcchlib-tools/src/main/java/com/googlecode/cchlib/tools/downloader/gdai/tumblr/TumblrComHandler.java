package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.FileDownloader;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface.DefaultRegExgSplitter;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface.RegExgSplitter;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderHandler;
import com.googlecode.cchlib.tools.downloader.common.DownloaderHandler;
import com.googlecode.cchlib.tools.downloader.proxy.PolyURLDownloadFileURL;

public class TumblrComHandler
    extends AbstractDownloaderHandler
        implements DownloaderHandler
{
    private static final Logger LOGGER = Logger.getLogger( TumblrComHandler.class );

    private String comboBoxConfig_hostname_;
    private final TumblrComData config;

    protected TumblrComHandler( final TumblrComData config  )
    {
        super( config );

        this.config = config;
    }

    @Override
    public void doSelectedItems( final List<Item> selectedItems )
    {
        if( ! selectedItems.isEmpty() ) {
            this.comboBoxConfig_hostname_ = selectedItems.get( 0 ).getJComboBoxText();
            }
    }

    @Override
    public Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader( final GenericDownloaderAppUIResults gdauir, final ContentDownloadURI<String> content2Parse )
            throws MalformedURLException, URISyntaxException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "<img src=\"", '"' ) };

        return getURLToDownloadCollection( gdauir, content2Parse, regexps );
    }

    @Override
    public ContentDownloadURI<String> getDownloadStringURL( final int pageNumber ) throws MalformedURLException, URISyntaxException
    {
        return TumblrComHelper.getDownloadStringURL(
                this.config.getCurrentHostName(),
                pageNumber,
                getDownloaderData().getProxy()
                );
    }

    @Override
    public ContentDownloadURI<File> getDownloadURLFrom(
            final String src,
            final int    regexpIndex
            ) throws MalformedURLException, URISyntaxException
    {
        {
            // Ignore avatars
            final URL    urlBase = new URL( src );
            final String path    = urlBase.getPath();

            if( path.startsWith( "avatar_" ) ) {
                // Don't want to download avatars !
                return null;
                }
        }

        // Find extension.
        int pos = src.lastIndexOf( '.' );

        if( pos < 0 ) {
            // <0  - No extension: Try to download any way.
            return new FileDownloader(
                    src,
                    null,
                    getDownloaderData().getProxy()
                    );
            }

        final String extension = src.substring( pos );
        final String prefix1   = src.substring( 0, pos );

        // Find picture size
        pos = prefix1.lastIndexOf( '_' );

        if( pos < 0 ) {
            // Unknown format: Try to download any way.
            return new FileDownloader( src, null, getDownloaderData().getProxy() );
            }

        final int size;
        try {
            final String sizeStr = prefix1.substring( pos + 1 );

            size = Integer.parseInt( sizeStr );
            }
        catch( final Exception e ) {
            LOGGER.warn( "size of " + src, e );
            // Can not find picture size: Try to download any way.
            return new FileDownloader( src, null, getDownloaderData().getProxy() );
            }
        final String prefix2 = prefix1.substring( 0, pos + 1 );

        final URL       defaultURL;
        final List<URI> alternateURI = new ArrayList<>();
        {
            // Build list of URL
            final List<String> urls = new ArrayList<>();

            for( int i = 0; i<TumblrComData.TUMBLR_COM_KNOWN_SIZES.length; i++ ) {
                if( size >= TumblrComData.TUMBLR_COM_KNOWN_SIZES[ i ] ) {
                    break;
                    }
                urls.add(
                    prefix2 + TumblrComData.TUMBLR_COM_KNOWN_SIZES[ i ] + extension
                    );
                }

            urls.add(
                prefix2 + size + extension
                );

            boolean first       = true;
            String  firstURLStr = null;

            for( final String urlStr : urls ) {
                if( first ) {
                    firstURLStr = urlStr;
                    first       = false;
                    }
                else {
                    alternateURI.add( new URL( urlStr ).toURI() );
                    }
                }

            defaultURL = new URL( firstURLStr );
        }

        return new PolyURLDownloadFileURL(
                defaultURL,
                null,
                getDownloaderData().getProxy(),
                alternateURI,
                src
                );
    }
}
