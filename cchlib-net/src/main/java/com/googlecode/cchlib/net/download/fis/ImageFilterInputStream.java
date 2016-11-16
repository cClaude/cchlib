package com.googlecode.cchlib.net.download.fis;

import java.io.IOException;
import java.io.InputStream;
import com.googlecode.cchlib.io.filetype.ImageIOFileData;

/**
 * NEEDDOC
 *
 * @since 4.1.7
 */
public class ImageFilterInputStream extends CopyInputStream
{
    /**
     * Creates a ImageFilterInputStream
     *
     * @param in the underlying input stream
     */
    public ImageFilterInputStream( final InputStream in )
    {
        super( in );
    }


    /**
     * Returns an ImageIOFileData.
     * @return an ImageIOFileData.
     * @throws IOException
     * @throws IllegalStateException
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public ImageIOFileData geImageIOFileData() throws IllegalStateException, IOException
    {
        return new ImageIOFileData( super.toInputStream() );
    }
}

