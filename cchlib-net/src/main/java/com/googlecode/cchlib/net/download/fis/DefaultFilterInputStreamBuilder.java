package com.googlecode.cchlib.net.download.fis;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.filetype.ImageIOFileData;
import com.googlecode.cchlib.net.download.ContentDownloadURI;

/**
 * Default implementation for {@link DownloadFilterInputStreamBuilder}
 *
 * @param <R>
 *            Result expected type for download
 *
 * @since 4.1.7
 */
public class DefaultFilterInputStreamBuilder<R>
    implements DownloadFilterInputStreamBuilder<R>
{
    private static final Logger LOGGER = Logger.getLogger( DefaultFilterInputStreamBuilder.class );

    /**
     * Property name for {@link java.awt.Dimension Dimension}
     * if {@link ContentDownloadURI} is a valid picture
     * <br>
     * Property result is return in a {@link java.awt.Dimension Dimension} object
     */
    public static final String DIMENSION = "Dimension";

    /**
     * Property name for picture format name
     * if {@link ContentDownloadURI} is a valid picture
     * <br>
     * Property result is return in a {@link String} object
     */
    public static final String FORMAT_NAME = "FormatName";

    /**
     * Property name for hash code of {@link ContentDownloadURI}
     * content.
     * <br>
     * Property result is return in a {@link String} object
     */
    public static final String HASH_CODE = "HashCode";

    /**
     * Create {@link DefaultFilterInputStreamBuilder}
     */
    public DefaultFilterInputStreamBuilder()
    {
        // Empty
    }

    /**
     * Create a new {@link DefaultFilterInputStream} based on
     * given {@link InputStream}
     *
     * @param is underlying {@link InputStream}
     * @return a new {@link DefaultFilterInputStream}
     */
    @Override
    public DefaultFilterInputStream createFilterInputStream( final InputStream is )
    {
        return new DefaultFilterInputStream( is );
    }

    /**
     * Set filter result on {@link ContentDownloadURI}.
     *
     * @param filter
     *            A {@link DefaultFilterInputStream} filter to use for result
     * @param downloader
     *            {@link ContentDownloadURI} that will received result.
     * @throws IllegalArgumentException
     *             if filter is not a {@link DefaultFilterInputStream}
     */
    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void storeFilterResult(
        final FilterInputStream     filter,
        final ContentDownloadURI<R> downloader
        ) throws IllegalArgumentException
    {
        if( !( filter instanceof DefaultFilterInputStream) ) {
            throw new IllegalArgumentException( "filter is not a DefaultFilterInputStream" );
        }

        final DefaultFilterInputStream dFilter = (DefaultFilterInputStream)filter;

        try {
            final ImageIOFileData infos = dFilter.geImageIOFileData();

            downloader.setProperty( DIMENSION, infos.getDimension() );
            downloader.setProperty( FORMAT_NAME, infos.getFormatName() );
            }
        catch( final IllegalStateException e ) {
            LOGGER.error( e );
            }
        catch( final IOException e ) {
            LOGGER.warn( e );
            }

        downloader.setProperty( HASH_CODE,  dFilter.getHashString() );
    }
}
