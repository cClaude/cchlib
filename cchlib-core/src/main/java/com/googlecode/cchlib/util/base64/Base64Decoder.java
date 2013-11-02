// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.util.base64;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Decode Base64 text
 *
 * @see Base64Encoder
 */
public class Base64Decoder extends Base64
{
    private char[] buffer;

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

//    /**
//     * @deprecated use {@link #decode(Reader, OutputStream)} instead
//     */
//    @Deprecated
//    public void decode( InputStream in, OutputStream out )
//        throws Base64FormatException, IOException
//    {
//        for(;;) {
//            int len;
//
//            for( len = 0; len<buffer.length; len++ ) {
//                int c = in.read();
//
//                if( c < 0 ) {
//                    break; // EOF
//                }
//                buffer[ len ] = (char)c;
//            }
//
//            if( len == 0 ) {
//                return;
//            }
//
//            byte[] dec = decode( buffer, 0, len);
//
//            out.write( dec );
//        }
//    }

//    /**
//     * @deprecated use {@link #decode(char[], OutputStream)} instead
//     */
//    @Deprecated
//    public void decode( byte[] datas, OutputStream out )
//        throws Base64FormatException, IOException
//    {
//        decode( new ByteArrayInputStream( datas ), out );
//    }

    /**
     * TODOC
     *
     * @param in
     * @param out
     * @throws Base64FormatException
     * @throws IOException
     */
    public void decode( final Reader in, final OutputStream out )
        throws Base64FormatException, IOException
    {
        int len;

        while( (len = in.read( buffer )) > 0 ) {
            //System.out.println( "BUF:" + new String( buffer ) );
            //System.out.println( "LEN:" + len);
            byte[] dec = decode( buffer, 0, len);
            //System.out.println( "dLEN:" + dec.length);

            out.write( dec );
            }
    }

    /**
     * TODOC
     *
     * @param datas
     * @param out
     * @throws Base64FormatException
     * @throws IOException
     */
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
     * @throws UnsupportedEncodingException
     */
    public static String decode(
            final String str,
            final String charsetName
            )
        throws UnsupportedEncodingException
    {
        byte[] dec = decode( str.toCharArray(), 0, str.length() );

        return new String( dec, charsetName );
    }

    /**
     * Efficient decode method for String
     *
     * @param str String to decode
     * @return decoded String using default Charset
     * @throws UnsupportedEncodingException
     */
    public static String decode( final String str )
        throws UnsupportedEncodingException
    {
        byte[] dec = decode( str.toCharArray(), 0, str.length() );

        return new String( dec );
    }

    /**
     * Efficient decode method pour char arrays
     * @param str     String to decode
     * @param charset {@link java.nio.charset.Charset} to use to encode String
     * @return decoded String according to CharSet
     */
    public static String decode(
        final String  str,
        final Charset charset
        )
    {
        byte[] dec = decode( str.toCharArray(), 0, str.length() );

        return new String( dec, charset );
    }

    /**
     * Efficient decode method for char arrays
     *
     * @param in Char array to decode
     * @return an array of bytes
     */
    public static byte[] decode( final char[] in )
    {
        return decode( in, 0, in.length );
    }

    /**
     * Efficient decode method for char arrays
     *
     * @param in        Char array to decode
     * @param offset    Index of first char to use to decode
     * @param length    Number of char to read in array (must be divisible by 4)
     * @return an array of bytes
     */
    public static byte[] decode(
        final char[]    in,
        final int       offset,
        int             length
        )
    {
        if( (length % 4) != 0 ) {
            throw new IllegalArgumentException(
                "Length of Base64 encoded input string is not a multiple of 4."
                );
            }

        while( (length > 0) && (in[offset+length-1] == '=') ) {
            length--;
            }

        int     oLen    = (length*3) / 4;
        byte[]  out     = new byte[oLen];
        int     ip      = offset;
        int     iEnd    = offset + length;
        int     op      = 0;

        while( ip < iEnd ) {
            int i0 = in[ip++];
            int i1 = in[ip++];
            int i2 = (ip < iEnd) ? in[ip++] : 'A';
            int i3 = (ip < iEnd) ? in[ip++] : 'A';

            if( (i0 > 127) || (i1 > 127) || (i2 > 127) || i3 > 127 ) {
                throw new IllegalArgumentException(
                    "Illegal character in Base64 encoded data. (<127) : ip=" + ip
                    );
                }

            int b0 = map2[i0];
            int b1 = map2[i1];
            int b2 = map2[i2];
            int b3 = map2[i3];

            if( b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0 ) {
                throw new IllegalArgumentException(
                    "Illegal character in Base64 encoded data. (>0) : ip=" + ip
                    );
                }

            int o0 = ( b0       <<2) | (b1>>>4);
            int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
            int o2 = ((b2 &   3)<<6) |  b3;

            out[op++] = (byte)o0;

            if (op<oLen) { out[op++] = (byte)o1; }
            if (op<oLen) { out[op++] = (byte)o2; }
            }

        return out;
        }
}
