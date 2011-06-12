package cx.ath.choisnet.util.base64;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
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
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private int bufferSize;

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
    public Base64Decoder( int bufferSize )
    {
        if( bufferSize%4 != 0 ) {
            this.bufferSize = ((bufferSize / 4) + 1)*4;
            }
        else {
            this.bufferSize = bufferSize;
            }
    }

    /**
     * @deprecated use {@link #decode(Reader, OutputStream)} instead
     */
    @Deprecated
    public void decode( InputStream in, OutputStream out )
        throws Base64FormatException, IOException
    {
        char[] buffer = new char[this.bufferSize];

        for(;;) {
            int len;

            for( len = 0; len<buffer.length; len++ ) {
                int c = in.read();

                if( c < 0 ) {
                    break; // EOF
                }
                buffer[ len ] = (char)c;
            }

            if( len == 0 ) {
                return;
            }

            byte[] dec = decode( buffer, 0, len);

            out.write( dec );
        }
    }

    /**
     * @deprecated use {@link #decode(char[], OutputStream)} instead
     */
    @Deprecated
    public void decode( byte[] datas, OutputStream out )
        throws Base64FormatException, IOException
    {
        decode( new ByteArrayInputStream( datas ), out );
    }

    /**
     * TODO: Doc!
     *
     * @param in
     * @param out
     * @throws Base64FormatException
     * @throws IOException
     */
    public void decode( final Reader in, final OutputStream out )
        throws Base64FormatException, IOException
    {
        char[] buffer = new char[this.bufferSize];

        for(;;) {
            int len;

            for( len = 0; len<buffer.length; len++ ) {
                int c = in.read();

                if( c < 0 ) {
                    break; // EOF
                }
                buffer[ len ] = (char)c;
            }

            if( len == 0 ) {
                return;
            }

            byte[] dec = decode( buffer, 0, len);

            out.write( dec );
        }
    }

    /**
     * TODO: Doc!
     *
     * @param datas
     * @param out
     * @throws Base64FormatException
     * @throws IOException
     */
    public void decode( char[] datas, OutputStream out )
        throws Base64FormatException, IOException
    {
        decode( new CharArrayReader( datas ), out );
    }

    /*
    public void decode(InputStream in, OutputStream out)
        throws Base64FormatException, java.io.IOException
    {
        byte buffer[];
        byte chunk[];
        int got;
        int ready;
        buffer = new byte[BUFFER_SIZE];
        chunk = new byte[4];

        got = -1;
        ready = 0;

        for(;;) { //_L2:
            int skiped;

            if((got = in.read(buffer)) <= 0) {
                //break MISSING_BLOCK_LABEL_181;
                break;
            }
            skiped = 0;
//_L4:
//  if(skiped >= got) goto _L2; else goto _L1
            if( skiped >= got ) {
                continue;
            }
//_L1:
            if(ready >= 4) {
                //break MISSING_BLOCK_LABEL_86;
                break;
            }

//        if(skiped < got) goto _L3; else goto _L2
            if(!(skiped < got)) {
                continue;
            }
//_L3:
            int ch = check(buffer[skiped++]);

            if(ch >= 0) {
                chunk[ready++] = (byte)ch;
            }
        //  goto _L1
        //  goto _L2

        if(chunk[2] == 65) {
            out.write(get1(chunk, 0));
            return;
        }

        if(chunk[3] == 65) {
            out.write(get1(chunk, 0));
            out.write(get2(chunk, 0));
            return;
        }
        out.write(get1(chunk, 0));
        out.write(get2(chunk, 0));
        out.write(get3(chunk, 0));
        ready = 0;
//          goto _L4
        } // for(;;)

        if(ready != 0) {
            throw new Base64FormatException("Invalid length.");
        }
        else {
            out.flush();
            return;
        }
    }
    */

     /**
     * Efficient decode method for String
     *
     * @param s String to decode
     * @param charsetName
     * @return decoded String according to Charset
     * @throws UnsupportedEncodingException
     */
    public static String decode(String s, String charsetName )
        throws UnsupportedEncodingException
    {
        byte[] dec = decode(s.toCharArray(),0,s.length());

        return new String( dec, charsetName );
    }

    /**
     * Efficient decode method for String
     * @param s String to decode
     * @return decoded String using default Charset
     * @throws UnsupportedEncodingException
     */
    public static String decode(String s)
        throws UnsupportedEncodingException
    {
        byte[] dec = decode(s.toCharArray(),0,s.length());

        return new String( dec );
    }

    /**
     * Efficient decode method pour char arrays
     * @param s String to decode
     * @param charset
     * @return decoded String according to CharSet
     */
    public static String decode(String s, Charset charset)
    {
        byte[] dec = decode(s.toCharArray(),0,s.length());

        return new String( dec, charset );
    }

    /**
     * Efficient decode method pour char arrays
     *
     * @param in
     * @param iOff
     * @param iLen
     * @return an array of bytes
     */
    public static byte[] decode (char[] in, int iOff, int iLen)
    {
        if (iLen%4 != 0) {
            throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
        }

        while (iLen > 0 && in[iOff+iLen-1] == '=') iLen--;
        int oLen = (iLen*3) / 4;
        byte[] out = new byte[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;

        while (ip < iEnd) {
           int i0 = in[ip++];
           int i1 = in[ip++];
           int i2 = ip < iEnd ? in[ip++] : 'A';
           int i3 = ip < iEnd ? in[ip++] : 'A';
           if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) {
              throw new IllegalArgumentException ("Illegal character in Base64 encoded data. (<127)");
           }
           int b0 = map2[i0];
           int b1 = map2[i1];
           int b2 = map2[i2];
           int b3 = map2[i3];
           if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) {
              throw new IllegalArgumentException ("Illegal character in Base64 encoded data. (>0)");
            }
           int o0 = ( b0       <<2) | (b1>>>4);
           int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
           int o2 = ((b2 &   3)<<6) |  b3;
           out[op++] = (byte)o0;
           if (op<oLen) out[op++] = (byte)o1;
           if (op<oLen) out[op++] = (byte)o2; }
        return out;
        }
}

//private final int get1(byte[] buf, int off)
//{
//  return (buf[off] & 0x3f) << 2 | (buf[off + 1] & 0x30) >>> 4;
//}
//
//private final int get2(byte[] buf, int off)
//{
//  return (buf[off + 1] & 0xf) << 4 | (buf[off + 2] & 0x3c) >>> 2;
//}
//
//private final int get3(byte[] buf, int off)
//{
//  return (buf[off + 2] & 3) << 6 | buf[off + 3] & 0x3f;
//}
//
//private final int check(int ch)
//{
//  if(ch >= 65 && ch <= 90) {
//      return ch - 65;
//  }
//  if(ch >= 97 && ch <= 122) {
//      return (ch - 97) + 26;
//  }
//  if(ch >= 48 && ch <= 57) {
//      return (ch - 48) + 52;
//  }
//
//  switch(ch) {
//  case 61:
//      return 65;
//  case 43:
//      return 62;
//  case 47:
//      return 63;
//  }
//  return -1;
//}

