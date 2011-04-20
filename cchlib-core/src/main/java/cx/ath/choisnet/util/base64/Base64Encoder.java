package cx.ath.choisnet.util.base64;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class Base64Encoder extends Base64
{
    public Base64Encoder()
    {
    }

    public static String encode(String str)
        throws java.io.UnsupportedEncodingException
    {
        return Base64Encoder.encode(str.getBytes());
    }

    public static String encode(String str, String charsetName)
        throws java.io.UnsupportedEncodingException
    {
        return Base64Encoder.encode(str.getBytes(charsetName));
    }

    public static String encode(byte[] bytes)
        throws java.io.UnsupportedEncodingException
    {
        return  new String( encodeToChar(bytes) );
    }

    public static char[] encodeToChar(byte[] bytes,int offset,int length)
        throws java.io.UnsupportedEncodingException
    {
        byte[] resize = Arrays.copyOfRange( bytes, offset, length );

        return encodeToChar( resize );
    }

    public static char[] encodeToChar(byte[] bytes)
        throws java.io.UnsupportedEncodingException
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

    protected static char[] encodeRaw(byte[] bytes)
        throws java.io.UnsupportedEncodingException
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
            java.io.UnsupportedEncodingException uee = new UnsupportedEncodingException("Base64Encode");

            uee.initCause(e);
            throw uee;
        }
    }

    // TODO: method using InputStream/OutputStream
    // and one using Reader/Writer
    // decode par part ?
}
