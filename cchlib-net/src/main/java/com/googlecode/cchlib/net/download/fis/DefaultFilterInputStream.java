package com.googlecode.cchlib.net.download.fis;

import java.io.IOException;
import java.io.InputStream;
import com.googlecode.cchlib.io.checksum.MD5;
import com.googlecode.cchlib.io.filetype.ImageIOFileData;

/**
 *
 */
public class DefaultFilterInputStream
    extends CopyInputStream 
{
    public DefaultFilterInputStream( InputStream in )
    {
        super( in );
    }

//    public ImageFilterInputStream getImageFilterInputStream()
//    {
//        return this;
//    }
    
    /**
     * Returns an ImageIOFileData.
     * @return an ImageIOFileData.
     * @throws IOException 
     * @throws IllegalStateException 
     */
    public ImageIOFileData geImageIOFileData() throws IllegalStateException, IOException
    {
        return new ImageIOFileData( super.toInputStream() );
    }

    public String getHashString()
    {
        return MD5.getHashString( super.toByteArray() );
    }

}
