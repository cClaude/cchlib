package cx.ath.choisnet.util.base64;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;

/**
 *
 *
 */
public class Base64Encoder extends Base64
{
    /**
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode( String str )
        throws UnsupportedEncodingException
    {
        return encode( str.getBytes() );
    }

    /**
     *
     * @param str
     * @param charsetName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode( String str, String charsetName )
        throws UnsupportedEncodingException
    {
        return Base64Encoder.encode( str.getBytes( charsetName ) );
    }

    /**
     *
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode( byte[] bytes )
        throws UnsupportedEncodingException
    {
        return  new String( encodeToChar( bytes ) );
    }

    /**
     *
     * @param bytes
     * @param offset
     * @param length
     * @return
     * @throws UnsupportedEncodingException
     */
    public static char[] encodeToChar( byte[] bytes, int offset, int length )
        throws UnsupportedEncodingException
    {
        byte[] resize = Arrays.copyOfRange( bytes, offset, length );

        return encodeToChar( resize );
    }

    /**
     *
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    public static char[] encodeToChar( byte[] bytes )
        throws UnsupportedEncodingException
    {
        char[] chars = encodeRaw( bytes );

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

    private static char[] encodeRaw( byte[] bytes )
        throws UnsupportedEncodingException
    {
        if( bytes.length == 0 ) {
            return new char[0];
            }

        try {
            int    k = 0;
            int    length = bytes.length;
            char[] ac     = new char[(length / 3) * 4 + 4];
            int    b      = (length / 3) * 3;
            int    i;

            for(i = 0; i < b; i += 3) {
                int j = bytes[i] << 16 | bytes[i + 1] << 8 | bytes[i + 2];
                ac[k + 3] = BASE64[j & 0x3f];
                ac[k + 2] = BASE64[j >>> 6 & 0x3f];
                ac[k + 1] = BASE64[j >>> 12 & 0x3f];
                ac[k] = BASE64[j >>> 18 & 0x3f];
                k += 4;
            }

            if(i < length) {
                ac[k]     = BASE64[bytes[i] >>> 2];
                ac[k + 3] = '=';
                int l = (bytes[i] & 3) << 4;
                ac[k + 1] = BASE64[l];
                ac[k + 2] = '=';

                if(i != length - 1) {
                    ac[k + 1] = BASE64[l | (bytes[i + 1] & 0xf0) >>> 4];
                    ac[k + 2] = BASE64[(bytes[i + 1] & 0xf) << 2];
                }
            }
            return ac;//new String(ac);
        }
        catch(Exception e) {
            UnsupportedEncodingException uee = new UnsupportedEncodingException("Base64Encode");

            uee.initCause( e );
            throw uee;
        }
    }

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private int bufferSize;

    public Base64Encoder()
    {
        this( DEFAULT_BUFFER_SIZE );
    }

    public Base64Encoder( int bufferSize )
    {
        this.bufferSize = bufferSize;
    }

    // TODO: method using InputStream/OutputStream
    // and one using Reader/Writer
    // decode par part ?
    public void encode( InputStream in, OutputStream out )
        throws IOException, UnsupportedEncodingException
    {
        byte[] buffer   = new byte[this.bufferSize ];
        Writer writer   = new BufferedWriter( new OutputStreamWriter( out ) );

        for(;;) {
            int len;

            for( len = 0; len<buffer.length; len++ ) {
                int c = in.read();

                if( c < 0 ) {
                    break; // EOF
                    }
                buffer[ len ] = (byte)c;
                }

            if( len == 0 ) {
                return;
                }

            char[] enc = encodeToChar( buffer, 0, len );
            writer.write( enc );
            }
    }


}
