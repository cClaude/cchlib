package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.awt.Frame;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.json.JSONHelperException;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.FileDownloader;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppInterface;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.comboconfig.DefaultComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface;
import com.googlecode.cchlib.tools.downloader.proxy.PolyURLDownloadFileURL;

@SuppressWarnings({
    "squid:S00101", // Class naming convention
    })
public abstract class GDAI_tumblr_com
    extends AbstractDownloaderAppInterface
        implements GenericDownloaderAppInterface
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( GDAI_tumblr_com.class );

    protected GDAI_tumblr_com(
        final String displaySiteName,
        final int    maxPages
        )
    {
        this(
                displaySiteName,
                TumblrComData.NUMBER_OF_PICTURES_BY_PAGE,
                maxPages
                );
    }

    protected GDAI_tumblr_com(
            final String    format,
            final int       numberOfPicturesByPage,
            final int       defaultMaxPages
            )
    {
        super( format, numberOfPicturesByPage, defaultMaxPages );
    }


    protected abstract String getCurrentHostName();

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        final String[]      fullHostName = String.format( TumblrComData.SITE_NAME_FMT, getCurrentHostName() ).split( "\\." );
        final StringBuilder sb           = new StringBuilder();
        int                 i            = fullHostName.length - 1;

        while( i >= 0 ) {
            sb.append( fullHostName[ i ] );
            --i;

            if( i < 0 ) {
                break;
                }
            sb.append( '.' );
            }

        return sb.toString();
    }

    @Override
    public Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader(
            final GenericDownloaderAppUIResults   gdauir,
            final ContentDownloadURI<String>      content2Parse
            )
            throws MalformedURLException
    {
        final RegExgSplitter[] regexps = { new DefaultRegExgSplitter( "<img src=\"", '"' ) };

        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps );
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
            return new FileDownloader( src, null, getProxy() );
            }

        final String extension = src.substring( pos );
        final String prefix1   = src.substring( 0, pos );

        // Find picture size
        pos = prefix1.lastIndexOf( '_' );

        if( pos < 0 ) {
            // Unknown format: Try to download any way.
            return new FileDownloader( src, null, getProxy() );
            }

        final int size;
        try {
            final String sizeStr = prefix1.substring( pos + 1 );

            size = Integer.parseInt( sizeStr );
            }
        catch( final Exception e ) {
            LOGGER.warn( "size of " + src, e );
            // Can not find picture size: Try to download any way.
            return new FileDownloader( src, null, getProxy() );
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

        return new PolyURLDownloadFileURL( defaultURL, null, getProxy(), alternateURI, src );
    }

    public static final GDAI_tumblr_com createAllEntries(
        final Frame ownerFrame
        ) throws JSONHelperException
    {
        final Config                config  = ConfigHelper.load();
        final List<? extends Entry> entries = config.getEntries();

        final String[] blogNames        =
                ConfigHelper.toArrayString( entries, entry -> entry.getName() );
        final String[] blogDescriptions =
                ConfigHelper.toArrayString( entries, entry -> entry.getDescription() );;

        return new GDAI_tumblr_com_ForHost(
                ownerFrame,
                new DefaultComboBoxConfig(
                        "Name",
                        blogNames,
                        blogDescriptions
                        ),
                config
                );
    }

}
