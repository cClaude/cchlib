// $codepro.audit.disable
package com.googlecode.cchlib.util.base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;

/**
 * Handle Base 64 encoding
 *
 * @see Base64Decoder
 */
public class Base64Encoder extends Base64
{
    private byte[] buffer;

    /**
     * Create a Base64Encoder
     */
    public Base64Encoder()
    {
        this( DEFAULT_BUFFER_SIZE );
    }

    /**
     * Create a Base64Encoder
     *
     * @param bufferSize Size of buffer to use for I/O operations
     */
    public Base64Encoder( final int bufferSize )
    {
        this.buffer = new byte[ computeEncoderBufferSize( bufferSize ) ];
    }

    /**
     * Encode {@link InputStream} to {@link Writer}
     *
     * @param in    {@link InputStream} to encode
     * @param out   {@link Writer} where to store base 64 result
     * @throws IOException if any
     * @throws UnsupportedEncodingException if any
     */
    public synchronized void encode(
        final InputStream in,
        final Writer      out
        )
        throws IOException, UnsupportedEncodingException
    {
        int len;

        //FIXME: bad result if buffer is not big enough !

        while( (len = in.read( buffer )) > 0 ) {
            char[] enc = encodeToChar( buffer, 0, len );
            out.write( enc );
            }

        out.flush();
    }

    /**
     * Encode a {@link String} using default {@link java.nio.charset.Charset}
     *
     * @param str String to encode
     * @return Base 64 encoded string.
     * @throws UnsupportedEncodingException if any
     */
    public static String encode( final String str )
        throws UnsupportedEncodingException
    {
        return encode( str.getBytes() );
    }

    /**
     * Encode a {@link String}
     *
     * @param str           String to encode
     * @param charsetName   {@link java.nio.charset.Charset} to use transform given string into bytes.
     * @return Base 64 encoded string.
     * @throws UnsupportedEncodingException if any
     */
    public static String encode(
        final String str,
        final String charsetName
        )
        throws UnsupportedEncodingException
    {
        return encode( str.getBytes( charsetName ) );
    }

    /**
     * Encode an array of bytes
     *
     * @param bytes Array of bytes to encode
     * @return Base 64 encoded string.
     * @throws UnsupportedEncodingException if any
     */
    public static String encode( final byte[] bytes )
        throws UnsupportedEncodingException
    {
        return new String( encodeToChar( bytes ) );
    }

    /**
     * Encode an array of bytes
     *
     * @param bytes     Bytes to encodes
     * @param offset    Index of first byte
     * @param length    Number of bytes
     * @return Base 64 encoded array of char
     * @throws UnsupportedEncodingException if any
     */
    public static char[] encodeToChar(
            final byte[]    bytes,
            final int       offset,
            final int       length
            )
        throws UnsupportedEncodingException
    {
        if( offset != 0 || length != bytes.length ) {
            // Need a new array...
            return encodeToChar( Arrays.copyOfRange( bytes, offset, length ) );
            }
        else {
            return encodeToChar( bytes );
            }
    }

    /**
     * Encode an array of bytes
     *
     * @param bytes Array of bytes to encodes
     * @return Base 64 encoded array of char
     * @throws UnsupportedEncodingException if any
     */
    public static char[] encodeToChar( final byte[] bytes )
        throws UnsupportedEncodingException
    {
        char[] chars = private_encodeRaw( bytes );

        // Fix chars length!
        int i = chars.length - 1;

        while( i>=0 ) {
            if( chars[ i ] != 0 ) {
                break;
                }
            i--;
            }
        i++;

        if( i == chars.length ) {
            return chars;
            }

        return Arrays.copyOf( chars, i );
    }

    // result char[] must be fixed !
    private static char[] private_encodeRaw( final byte[] bytes_ )
        throws UnsupportedEncodingException
    {
        if( bytes_.length == 0 ) {
            return new char[0];
            }

        try {
            final  int   length = bytes_.length;
            final char[] ac     = new char[(length / 3) * 4 + 4];
            final int    b      = (length / 3) * 3;
            int    k = 0;
            int    i;

            for(i = 0; i < b; i += 3) {
                int j = (bytes_[i] & 0x00FF) << 16 | (bytes_[i + 1] & 0x00FF) << 8 | (bytes_[i + 2] & 0x00FF);

                ac[k + 3] = BASE64[j & 0x3f];
                ac[k + 2] = BASE64[j >>> 6 & 0x3f];
                ac[k + 1] = BASE64[j >>> 12 & 0x3f];
                ac[k] = BASE64[j >>> 18 & 0x3f];
                k += 4;
                }

            if(i < length) {
                //ac[k]     = BASE64[bytes[i] >>> 2];
                ac[k]     = BASE64[(bytes_[i] & 0x00FF)>>> 2];
                ac[k + 3] = '=';

                final int l = ((bytes_[i] & 0x00FF) & 3) << 4;
                ac[k + 1] = BASE64[l];
                ac[k + 2] = '=';

                if(i != length - 1) {
                    ac[k + 1] = BASE64[l | ((bytes_[i + 1] & 0x00FF) & 0xf0) >>> 4];
                    ac[k + 2] = BASE64[((bytes_[i + 1] & 0x00FF) & 0xf) << 2];
                    }
                }
            return ac;
            }
        catch( Exception e ) {
            UnsupportedEncodingException uee = new UnsupportedEncodingException("Base64Encode");

            uee.initCause( e );
            throw uee;
            }
    }
}
