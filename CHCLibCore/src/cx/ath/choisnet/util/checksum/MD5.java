package cx.ath.choisnet.util.checksum;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class MD5
{
    private MessageDigest md5;

    public MD5() throws NoSuchAlgorithmException
    {
//        try {
        this.md5 = java.security.MessageDigest.getInstance("MD5");
//        }
//        catch(java.security.NoSuchAlgorithmException e) {
//            throw new RuntimeException("MD5:NoSuchAlgorithmException", e);
//        }

    }

    public void reset()
    {
        md5.reset();
    }

    public String getStringValue()
    {
        return MD5.computeHEX( getValue() );
    }

    public byte[] getValue()
    {
        return md5.digest();
    }

    public byte[] getValue(byte[] key)
    {
        return md5.digest(key);

    }

    public void update(byte[] b, int off, int len)
    {
        md5.update(b, off, len);

    }

    public void update(ByteBuffer buffer)
    {
        md5.update(buffer);

    }

    public static String computeHEX(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();

        for(byte b :bytes){
            sb.append(Integer.toHexString(0xff00 | b & 0xff).substring(2, 4).toUpperCase());
        }

        return sb.toString();

    }

    public static byte[] computeMessageDigest(String hexKey)
        throws java.lang.NumberFormatException
    {
        int len = hexKey.length() / 2;

        if(len * 2 != hexKey.length()) {
            throw new NumberFormatException("key error * bad length()");

        }

        byte[] mdBytes = new byte[len];

        for(int i = 0; i < len; i++) {
            int pos = i << 1;
            String digit = hexKey.substring(pos, pos + 2);

            mdBytes[i] = Integer.valueOf(digit, 16).byteValue();

        }

        return mdBytes;
    }
}
