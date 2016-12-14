package com.googlecode.cchlib.util.base64;

import static com.googlecode.cchlib.util.base64.Base64.DEFAULT_BUFFER_SIZE;
import static com.googlecode.cchlib.util.base64.Base64.MAP2;
import static com.googlecode.cchlib.util.base64.Base64.computeDecoderBufferSize;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import com.googlecode.cchlib.NeedDoc;

/**
 * Decode Base64 text
 *
 * @see Base64Encoder
 */
public class Base64Decoder
{
    private final char[] buffer;

    /**
     * Create a Base64Decoder
     */
    public Base64Decoder()
    {
        this( DEFAULT_BUFFER_SIZE );
    }

    /**
     * Create a Base64Decoder with the specified buffer size.
     *
     * @param bufferSize Buffer size to use for IO operations
     */
    public Base64Decoder( final int bufferSize )
    {
        this.buffer = new char[ computeDecoderBufferSize( bufferSize ) ];
    }

    @NeedDoc
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public void decode( final Reader in, final OutputStream out )
        throws Base64FormatException, IOException
    {
        int len;

        while( (len = in.read( this.buffer )) > 0 ) {
            final byte[] dec = decode( this.buffer, 0, len);

            out.write( dec );
            }
    }

    @NeedDoc
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public void decode( final char[] datas, final OutputStream out )
        throws Base64FormatException, IOException
    {
        decode( new CharArrayReader( datas ), out );
    }

     /**
     * Efficient decode method for String
     *
     * @param str         String to decode
     * @param charsetName {@link java.nio.charset.Charset} to use to encode String
     * @return decoded String according to Charset
     * @throws IllegalArgumentException If {@code str} can not be decoded, mainly according to size.
     * @throws UnsupportedEncodingException  If the named charset is not supported
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public static String decode(
            final String str,
            final String charsetName
            )
        throws UnsupportedEncodingException, IllegalArgumentException
    {
        final byte[] dec = decode( str.toCharArray(), 0, str.length() );

        return new String( dec, charsetName );
    }

    /**
     * Efficient decode method for String
     *
     * @param str String to decode
     * @return decoded String using default Charset
     * @throws IllegalArgumentException If {@code str} can not be decoded, mainly according to size.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static String decode( final String str )
        throws IllegalArgumentException
    {
        final byte[] dec = decode( str.toCharArray(), 0, str.length() );

        return new String( dec );
    }

    /**
     * Efficient decode method pour char arrays
     * @param str     String to decode
     * @param charset {@link java.nio.charset.Charset} to use to encode String
      * @return decoded String according to CharSet
    * @throws IllegalArgumentException if {@code str} can not be decoded, mainly according to size.
     * @throws UnsupportedEncodingException  If the named charset is not supported
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static String decode(
        final String  str,
        final Charset charset
        ) throws IllegalArgumentException, UnsupportedEncodingException
    {
        final byte[] dec = decode( str.toCharArray(), 0, str.length() );

        return new String( dec, charset );
    }

    /**
     * Efficient decode method for char arrays
     *
     * @param in Char array to decode
     * @throws IllegalArgumentException if {@code in} can not be decoded, mainly according to size.
     * @return an array of bytes
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static byte[] decode( final char[] in ) throws IllegalArgumentException
    {
        return decode( in, 0, in.length );
    }

    /**
     * Efficient decode method for char arrays
     *
     * @param in        Char array to decode
     * @param offset    Index of first char to use to decode
     * @param length    Number of char to read in array (must be divisible by 4)
     * @throws IllegalArgumentException if {@code in} can not be decoded, mainly according to size.
     * @return an array of bytes
     */
    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        "squid:MethodCyclomaticComplexity",
        "squid:S00122"})
    public static byte[] decode(
        final char[]    in,
        final int       offset,
        final int       length
        ) throws IllegalArgumentException
    {
        int clength = length;

        if( (clength % 4) != 0 ) {
            throw new IllegalArgumentException(
                "Length of Base64 encoded input string is not a multiple of 4."
                );
            }

        while( (clength > 0) && (in[(offset+clength)-1] == '=') ) {
            clength--;
            }

        final int     oLen    = (clength*3) / 4;
        final byte[]  out     = new byte[oLen];
        int     ip      = offset;
        final int     iEnd    = offset + clength;
        int     op      = 0;

        while( ip < iEnd ) {
            final int i0 = in[ip++];
            final int i1 = in[ip++];
            final int i2 = (ip < iEnd) ? in[ip++] : 'A';
            final int i3 = (ip < iEnd) ? in[ip++] : 'A';

            if( (i0 > 127) || (i1 > 127) || (i2 > 127) || (i3 > 127) ) {
                throw new IllegalArgumentException(
                    "Illegal character in Base64 encoded data. (<127) : ip=" + ip
                    );
                }

            final int b0 = MAP2[i0];
            final int b1 = MAP2[i1];
            final int b2 = MAP2[i2];
            final int b3 = MAP2[i3];

            if( (b0 < 0) || (b1 < 0) || (b2 < 0) || (b3 < 0) ) {
                throw new IllegalArgumentException(
                    "Illegal character in Base64 encoded data. (>0) : ip=" + ip
                    );
                }

            final int o0 = ( b0       <<2) | (b1>>>4);
            final int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
            final int o2 = ((b2 &   3)<<6) |  b3;

            out[op++] = (byte)o0;

            if (op<oLen) { out[op++] = (byte)o1; }
            if (op<oLen) { out[op++] = (byte)o2; }
            }

        return out;
        }
}
