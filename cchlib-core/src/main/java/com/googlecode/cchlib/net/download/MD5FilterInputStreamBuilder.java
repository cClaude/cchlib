package com.googlecode.cchlib.net.download;

import java.io.InputStream;
import com.googlecode.cchlib.io.checksum.MD5FilterInputStream;

/**
 * 
 * @since 4.1.7
 */
public class MD5FilterInputStreamBuilder
{
    /**
     * 
     */
    public MD5FilterInputStreamBuilder()
    {
        // Empty
    }

    /**
     * Create a new {@link MD5FilterInputStream} based on
     * given {@link InputStream}
     * @param is underlying {@link InputStream}
     * @return a new {@link MD5FilterInputStream}
     */
    public MD5FilterInputStream createMD5FilterInputStream( final InputStream is )
    {
        return new MD5FilterInputStream( is );
    }
}
