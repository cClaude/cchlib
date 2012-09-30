package com.googlecode.cchlib.net.download.fis;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.filetype.ImageIOFileData;
import com.googlecode.cchlib.net.download.DownloadFileURL;

/**
 * TODOC
 * 
 * @since 4.1.7
 */
public class DefaultFilterInputStreamBuilder 
    implements DownloadFilterInputStreamBuilder
{
    private final static Logger logger = Logger.getLogger( DefaultFilterInputStreamBuilder.class );

    /** 
     * Property name for {@link Dimension}
     * if {@link DownloadFileURL} is a valid picture
     * <br>
     * Property result is return in a {@link Dimension} object
     */
    public static final String DIMENSION = "Dimension";
    
    /** 
     * Property name for picture format name
     * if {@link DownloadFileURL} is a valid picture
     * <br>
     * Property result is return in a {@link String} object
     */
    public static final String FORMAT_NAME = "FormatName";

    /** 
     * Property name for hash code of {@link DownloadFileURL} 
     * content.
     * <br>
     * Property result is return in a {@link String} object
     */
    public static final String HASH_CODE = "HashCode";

    /**
     * 
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
     * Set filter result on {@link DownloadFileURL}.
     * 
     * @param filter    Closed filter to use for result
     * @param dURL      DownloadFileURL that will received result.
     */
    @Override
    public void storeFilterResult(
        final FilterInputStream filter,
        final DownloadFileURL   dURL
        )
    {
        DefaultFilterInputStream f = DefaultFilterInputStream.class.cast( filter );
        
        try {
            ImageIOFileData infos = f.geImageIOFileData();

//            dURL.setProperty( "Dimension", infos.getDimension() );
            dURL.setProperty( DIMENSION, infos.getDimension() );
//            dURL.setProperty( "FormatName", infos.getFormatName() );
            dURL.setProperty( FORMAT_NAME, infos.getFormatName() );
            }
        catch( IllegalStateException e ) {
            logger .error( e );
            }
        catch( IOException e ) {
            logger.warn( e );
            }

//      dURL.setProperty( "HashCode",  f.getHashString() );
        dURL.setProperty( HASH_CODE,  f.getHashString() );
    }
}
