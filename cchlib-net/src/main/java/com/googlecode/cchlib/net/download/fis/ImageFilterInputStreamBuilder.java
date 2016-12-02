package com.googlecode.cchlib.net.download.fis;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.filetype.ImageIOFileData;
import com.googlecode.cchlib.net.download.ContentDownloadURI;

public class ImageFilterInputStreamBuilder
    implements DownloadFilterInputStreamBuilder<File>
{
    private static final Logger LOGGER = Logger.getLogger( ImageFilterInputStreamBuilder.class );

    /**
     *
     */
    public ImageFilterInputStreamBuilder()
    {
        // Empty
    }

    @Override
    public ImageFilterInputStream createFilterInputStream( final InputStream is )
    {
        return new ImageFilterInputStream( is );
    }

    @Override
    public void storeFilterResult(
        final FilterInputStream        filter,
        final ContentDownloadURI<File> dURL
        )
    {
        final ImageFilterInputStream f = ImageFilterInputStream.class.cast( filter );

        try {
            final ImageIOFileData infos = f.geImageIOFileData();

            dURL.setProperty(
                    DefaultFilterInputStreamBuilder.DIMENSION,
                    infos.getDimension()
                    );
            dURL.setProperty(
                    DefaultFilterInputStreamBuilder.FORMAT_NAME,
                    infos.getFormatName()
                    );
            }
        catch( final IllegalStateException e ) {
            LOGGER.error( e );
            }
        catch( final IOException e ) {
            LOGGER.warn( e );
            }
    }
}
