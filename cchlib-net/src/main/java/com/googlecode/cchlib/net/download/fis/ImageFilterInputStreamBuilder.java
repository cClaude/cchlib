package com.googlecode.cchlib.net.download.fis;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.filetype.ImageIOFileData;
import com.googlecode.cchlib.net.download.DownloadFileURL;

public class ImageFilterInputStreamBuilder 
    implements DownloadFilterInputStreamBuilder
{
    private final static Logger logger = Logger.getLogger( ImageFilterInputStreamBuilder.class );

    /**
     * 
     */
    public ImageFilterInputStreamBuilder()
    {
        // Empty
    }
    
    @Override
    public ImageFilterInputStream createFilterInputStream( InputStream is )
    {
        return new ImageFilterInputStream( is );
    }

    @Override
    public void storeFilterResult( 
        final FilterInputStream filter,
        final DownloadFileURL   dURL
        )
    {
        ImageFilterInputStream f = ImageFilterInputStream.class.cast( filter );
        
        try {
            ImageIOFileData infos = f.geImageIOFileData();

//            dURL.setDimension( infos.getDimension() );
//            dURL.setFormatName( infos.getFormatName() );
            dURL.setProperty( "Dimension", infos.getDimension() );
            dURL.setProperty( "FormatName", infos.getFormatName() );
            }
        catch( IllegalStateException e ) {
            logger .error( e );
            }
        catch( IOException e ) {
            logger.warn( e );
            }
    }

}
