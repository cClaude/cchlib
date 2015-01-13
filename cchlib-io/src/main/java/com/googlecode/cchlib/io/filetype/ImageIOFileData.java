package com.googlecode.cchlib.io.filetype;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Not public * DO NOT USE
 *
 * @since 4.1.7
 */
public
class ImageIOFileData
{
    private Dimension dimension;
    private String formatName;

    /**
     *
     * @param is
     * @throws IOException
     */
    public ImageIOFileData( final InputStream is ) throws IOException
    {
        try( ImageInputStream iis = ImageIO.createImageInputStream( is ) ) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders( iis );

            if( readers.hasNext() ) {
                final ImageReader reader = readers.next();
                try {
                    reader.setInput( iis );
                    this.formatName = reader.getFormatName();
                    this.dimension  = new Dimension( reader.getWidth(0), reader.getHeight(0) );
                    }
                finally {
                    reader.dispose();
                    }
                }
            // else { dimension = null; }
            }
    }

    /**
     * Returns {@link Dimension} of this picture
     * @return {@link Dimension} of this picture
     * @see ImageReader#getWidth(int)
     * @see ImageReader#getHeight(int)
     */
    public Dimension getDimension()
    {
        return this.dimension;
    }

    /**
     * Returns a String identifying the format of the input source.
     * @return the format name, as a String.
     * @see ImageReader#getFormatName()
     */
    public String getFormatName()
    {
        return this.formatName;
    }
}
