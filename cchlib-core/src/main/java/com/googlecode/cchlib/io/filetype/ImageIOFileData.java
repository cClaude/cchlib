package com.googlecode.cchlib.io.filetype;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @since 4.1.7
 */
//Not public
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
        ImageInputStream iis = ImageIO.createImageInputStream( is );
        
        try {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders( iis );
            
            if( readers.hasNext() ) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput( iis );
                    formatName = reader.getFormatName();
                    dimension  = new Dimension( reader.getWidth(0), reader.getHeight(0) );
                    }
                finally {
                    reader.dispose();
                    }
                }
            // else { dimension = null; }
            }
        finally {
            if( iis != null ) {
                iis.close();
                }
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
        return dimension;
    }

    /**
     * Returns a String identifying the format of the input source. 
     * @return the format name, as a String.
     * @see ImageReader#getFormatName()
     */
    public String getFormatName()
    {
        return formatName;
    }
}
