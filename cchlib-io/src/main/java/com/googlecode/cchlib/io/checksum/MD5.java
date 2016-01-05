// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.io.checksum;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Santeri Paavolainen http://www.helsinki.fi/~sjpaavol/programs/md5/
 * @author Stephen Ostermiller http://ostermiller.org/contact.pl?regarding=Java+Utilities
 * @author Claude CHOISNET (remove some memory leaks, provide some optimization)
 * @since 4.1.7
 */
public class MD5
{
    /**
     * Class constructor
     */
    public MD5()
    {
        reset();
    }

    /**
     * Gets this hash sum as an array of 16 bytes.
     *
     * @return Array of 16 bytes, the hash of all updated bytes.
     */
    public byte[] getHash()
    {
        if( !this.finalState.valid ) {
            this.finalState.copy( this.workingState );

            final long bitCount = this.finalState.bitCount;

            // Compute the number of left over bits
            final int leftOver = (int) (((bitCount >>> 3)) & 0x3f);

            // Compute the amount of padding to add based on number of left over bits.
            final int padlen = (leftOver < 56) ? (56 - leftOver) : (120 - leftOver);

            // add the padding
            update(this.finalState, PADDING, 0, padlen);

            // add the length (computed before padding was added)
            update(this.finalState, encode(bitCount), 0, 8);
            this.finalState.valid = true;
            }

        // make a copy of the hash before returning it.
        return encode( this.finalState.state, 16 );
    }

    /**
     * Returns 32-character hex representation of this hash.
     *
     * @return String representation of this object's hash.
     */
    public String getHashString()
    {
        return toHex( this.getHash() );
    }

    /**
     * Gets the MD5 hash of the given byte array.
     *
     * @param b byte array for which an MD5 hash is desired.
     * @return Array of 16 bytes, the hash of all updated bytes.
     */
    public static byte[] getHash( final byte[] b )
    {
        final MD5 md5 = new MD5();
        md5.update(b);
        return md5.getHash();
    }

    /**
     * Gets the MD5 hash of the given byte array.
     *
     * @param b byte array for which an MD5 hash is desired.
     * @return 32-character hex representation the data's MD5 hash.
     */
    public static String getHashString( final byte[] b )
    {
        final MD5 md5 = new MD5();
        md5.update(b);
        return md5.getHashString();
    }

    /**
     * Gets the MD5 hash the data on the given InputStream.
     *
     * @param in byte array for which an MD5 hash is desired.
     * @return Array of 16 bytes, the hash of all updated bytes.
     * @throws IOException if an I/O error occurs.
     */
    public static byte[] getHash( final InputStream in ) throws IOException
    {
        final MD5       md5     = new MD5();
        final byte[]    buffer  = new byte[1024];
        int       read;

        while( (read = in.read(buffer)) != -1 ) {
            md5.update(buffer, read);
            }

        return md5.getHash();
    }

    /**
     * Gets the MD5 hash the data on the given InputStream.
     *
     * @param in byte array for which an MD5 hash is desired.
     * @return 32-character hex representation the data's MD5 hash.
     * @throws IOException if an I/O error occurs.
     */
    public static String getHashString( final InputStream in )
        throws IOException
    {
        final MD5       md5     = new MD5();
        final byte[]    buffer  = new byte[1024];
        int       read;

        while((read = in.read(buffer)) != -1 ) {
            md5.update(buffer, read);
            }

        return md5.getHashString();
    }

    /**
     * Gets the MD5 hash of the given file.
     *
     * @param f file for which an MD5 hash is desired.
     * @return Array of 16 bytes, the hash of all updated bytes.
     * @throws IOException if an I/O error occurs.
     */
    public static byte[] getHash( final File f ) throws IOException
    {
        byte[] hash;

        try( final InputStream is = new BufferedInputStream( new FileInputStream( f ) ) ) {
            hash  = getHash(is);
            }

        return hash;
    }

    /**
     * Gets the MD5 hash of the given file.
     *
     * @param f file array for which an MD5 hash is desired.
     * @return 32-character hex representation the data's MD5 hash.
     * @throws IOException if an I/O error occurs.
     */
    public static String getHashString( final File f )
        throws IOException
    {
        String hash;

        try( final InputStream is = new BufferedInputStream( new FileInputStream( f ) ) ) {
            hash = getHashString(is);
            }

        return hash;
    }

    /**
     * Gets the MD5 hash of the given String.
     * The string is converted to bytes using the current
     * platform's default character encoding.
     *
     * @param s String for which an MD5 hash is desired.
     * @return Array of 16 bytes, the hash of all updated bytes.
     */
    public static byte[] getHash( final String s )
    {
        final MD5 md5 = new MD5();
        md5.update( s );
        return md5.getHash();
    }

    /**
     * Gets the MD5 hash of the given String.
     * The string is converted to bytes using the current
     * platform's default character encoding.
     *
     * @param s String for which an MD5 hash is desired.
     * @return 32-character hex representation the data's MD5 hash.
     */
    public static String getHashString(final String s)
    {
        final MD5 md5 = new MD5();
        md5.update(s);
        return md5.getHashString();
    }

    /**
     * Gets the MD5 hash of the given String.
     *
     * @param s String for which an MD5 hash is desired.
     * @param enc The name of a supported character encoding.
     * @return Array of 16 bytes, the hash of all updated bytes.
     * @throws UnsupportedEncodingException If the named encoding is not supported.
     */
    public static byte[] getHash(final String s, final String enc) throws UnsupportedEncodingException
    {
        final MD5 md5 = new MD5();
        md5.update(s, enc);
        return md5.getHash();
    }

    /**
     * Gets the MD5 hash of the given String.
     *
     * @param s String for which an MD5 hash is desired.
     * @param enc The name of a supported character encoding.
     * @return 32-character hex representation the data's MD5 hash.
     * @throws UnsupportedEncodingException If the named encoding is not supported.
     */
    public static String getHashString(final String s, final String enc) throws UnsupportedEncodingException
    {
        final MD5 md5 = new MD5();
        md5.update(s, enc);
        return md5.getHashString();
    }

    /**
     * Reset the MD5 sum to its initial state.
     */
    public void reset()
    {
        this.workingState.reset();
        this.finalState.valid = false;
    }

    /**
     * Returns 32-character hex representation of this hash.
     *
     * @return String representation of this object's hash.
     */
    @Override
    public String toString()
    {
        return getHashString();
    }

    /**
     * Update this hash with the given data.
     * <p>
     * A state may be passed into this method so that we can add padding
     * and finalize a md5 hash without limiting our ability to update
     * more data later.
     * <p>
     * If length bytes are not available to be hashed, as many bytes as
     * possible will be hashed.
     *
     * @param state Which state is updated.
     * @param buffer Array of bytes to be hashed.
     * @param offset Offset to buffer array.
     * @param length number of bytes to hash.
     */
    private void update( final MD5State state, final byte[] buffer, final int offset, int length )
    {
        this.finalState.valid = false;

        // if length goes beyond the end of the buffer, cut it short.
        if( (length + offset) > buffer.length ) {
            length = buffer.length - offset;
            }

        // compute number of bytes mod 64
        // this is what we have sitting in a buffer
        // that have not been hashed yet
        int index = (int) (state.bitCount >>> 3) & 0x3f;

        // add the length to the count (translate bytes to bits)
        state.bitCount += length << 3;

        final int partlen = 64 - index;
        int i       = 0;

        if( length >= partlen ) {
            System.arraycopy( buffer, offset, state.buffer, index, partlen );

            transform( state, decode( state.buffer, 64, 0 ) );

            for (i = partlen; (i + 63) < length; i+= 64){
                transform(state, decode(buffer, 64, i));
                }

            index = 0;
            }

        // buffer remaining input
        if( i < length ) {
            for (final int start = i; i < length; i++) {
                state.buffer[(index + i) - start] = buffer[i + offset];
                }
            }
    }

    /**
     * Update this hash with the given data.
     * <p>
     * If length bytes are not available to be hashed, as many bytes as
     * possible will be hashed.
     *
     * @param buffer Array of bytes to be hashed.
     * @param offset Offset to buffer array.
     * @param length number of bytes to hash.
     */
    public void update( final byte[] buffer, final int offset, final int length )
    {
        update( this.workingState, buffer, offset, length );
    }

    /**
     * Update this hash with the given data.
     * <p>
     * If length bytes are not available to be hashed, as many bytes as
     * possible will be hashed.
     *
     * @param buffer Array of bytes to be hashed.
     * @param length number of bytes to hash.
     */
    public void update( final byte[] buffer, final int length )
    {
        update( buffer, 0, length );
    }

    /**
     * Update this hash with the given data.
     *
     * @param buffer Array of bytes to be hashed.
     */
    public void update( final byte[] buffer )
    {
        update(buffer, 0, buffer.length);
    }

    /**
     * Updates this hash with a single byte.
     *
     * @param b byte to be hashed.
     */
    public void update( final byte b )
    {
        final byte[] buffer = new byte[1];
        buffer[0] = b;
        update( buffer, 1 );
    }

    /**
     * Update this hash with a String.
     * The string is converted to bytes using the current
     * platform's default character encoding.
     *
     * @param s String to be hashed.
     */
    public void update( final String s )
    {
        update( s.getBytes() );
    }

    /**
     * Update this hash with a String.
     *
     * @param s String to be hashed.
     * @param enc The name of a supported character encoding.
     * @throws UnsupportedEncodingException If the named encoding is not supported.
     */
    public void update( final String s, final String enc ) throws UnsupportedEncodingException
    {
        update( s.getBytes( enc ) );
    }

    /**
     * The current state from which the hash sum
     * can be computed or updated.
     */
    private final MD5State workingState = new MD5State();

    /**
     * Cached copy of the final MD5 hash sum.  This is created when
     * the hash is requested and it is invalidated when the hash
     * is updated.
     */
    private final MD5State finalState = new MD5State();

    /**
     * Temporary buffer cached here for performance reasons.
     */
    private final int[] decodeBuffer = new int[16];

    /**
     * 64 bytes of padding that can be added if the length
     * is not divisible by 64.
     */
    private static final byte[] PADDING = {
      (byte) 0x80, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             };

    /**
     * Contains internal state of the MD5 class.
     * Passes MD5 test suite as defined in RFC1321.
     */
    private static class MD5State
    {
        /**
         * True if this state is valid.
         */
        private boolean valid = true;

        /**
         * Reset to initial state.
         */
        private void reset()
        {
            this.state[0] = 0x67452301;
            this.state[1] = 0xefcdab89;
            this.state[2] = 0x98badcfe;
            this.state[3] = 0x10325476;

            this.bitCount = 0;
        }

        /**
         * 128-byte state
         */
        private final int[] state = new int[4];

        /**
         * 64-bit count of the number of bits that have
         * been hashed.
         */
        private long bitCount;

        /**
         * 64-byte buffer (512 bits) for storing
         * to-be-hashed characters
         */
        private final byte[] buffer = new byte[ 64 ];

        private MD5State()
        {
            reset();
        }

        /**
         * Set this state to be exactly the same as some other.
         *
         * @param from state to copy from.
         */
        private void copy(final MD5State from)
        {
            System.arraycopy(from.buffer, 0, this.buffer, 0, this.buffer.length);
            System.arraycopy(from.state, 0, this.state, 0, this.state.length);

            this.valid    = from.valid;
            this.bitCount = from.bitCount;
        }
    }//class MD5State

    /**
     * Turns array of bytes into string representing each byte as
     * a two digit unsigned hex number.
     *
     * @param hash Array of bytes to convert to hex-string
     * @return  Generated hex string
     */
    private static String toHex( final byte[] hash )
    {
        final StringBuilder buf = new StringBuilder( hash.length * 2 );

        for( final byte element: hash ) {
            final int intVal = element & 0xff;

            if( intVal < 0x10 ) {
                // append a zero before a one digit hex
                // number to make it two digits.
                buf.append('0');
                }

            buf.append( Integer.toHexString( intVal ) );
            }

        return buf.toString().toUpperCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
    }

    private static int FF( int a, final int b, final int c, final int d, final int x, final int s, final int ac )
    {
        a += ((b & c) | (~b & d));
        a += x;
        a += ac;
        //return rotateLeft(a, s) + b;
        a = (a << s) | (a >>> (32 - s));
        return a + b;
    }

    private static int GG( int a, final int b, final int c, final int d, final int x, final int s, final int ac )
    {
        a += ((b & d) | (c & ~d));
        a += x;
        a += ac;
        //return rotateLeft(a, s) + b;
        a = (a << s) | (a >>> (32 - s));
        return a + b;
    }

    private static int HH( int a, final int b, final int c, final int d, final int x, final int s, final int ac )
    {
        a += (b ^ c ^ d);
        a += x;
        a += ac;
        //return rotateLeft(a, s) + b;
        a = (a << s) | (a >>> (32 - s));
        return a + b;
    }

    private static int II( int a, final int b, final int c, final int d, final int x, final int s, final int ac )
    {
        a += (c ^ (b | ~d));
        a += x;
        a += ac;
        //return rotateLeft(a, s) + b;
        a = (a << s) | (a >>> (32 - s));
        return a + b;
    }

    private static byte[] encode( final long l )
    {
        final byte[] out = new byte[8];
        out[0] = (byte) (l & 0xff);
        out[1] = (byte) ((l >>> 8) & 0xff);
        out[2] = (byte) ((l >>> 16) & 0xff);
        out[3] = (byte) ((l >>> 24) & 0xff);
        out[4] = (byte) ((l >>> 32) & 0xff);
        out[5] = (byte) ((l >>> 40) & 0xff);
        out[6] = (byte) ((l >>> 48) & 0xff);
        out[7] = (byte) ((l >>> 56) & 0xff);
        return out;
    }

    private static byte[] encode( final int[] input, final int len )
    {
        final byte[] out = new byte[len];
        int i, j;

        for (i = j = 0; j  < len; i++, j += 4) {
            out[j] = (byte) (input[i] & 0xff);
            out[j + 1] = (byte) ((input[i] >>> 8) & 0xff);
            out[j + 2] = (byte) ((input[i] >>> 16) & 0xff);
            out[j + 3] = (byte) ((input[i] >>> 24) & 0xff);
            }

        return out;
    }

    private int[] decode( final byte[] buffer, final int len, final int offset )
    {
      int i, j;

      for (i = j = 0; j < len; i++, j += 4) {
          this.decodeBuffer[i] = (
                  (buffer[j + offset] & 0xff)) |
                (((buffer[j + 1 + offset] & 0xff)) << 8) |
                (((buffer[j + 2 + offset] & 0xff)) << 16) |
                (((buffer[j + 3 + offset] & 0xff)) << 24
                );
          }
      return this.decodeBuffer;
    }

    private static void transform(final MD5State state, final int[] x)
    {
        int a = state.state[0];
        int b = state.state[1];
        int c = state.state[2];
        int d = state.state[3];

        /* Round 1 */
        a = FF (a, b, c, d, x[ 0],   7, 0xd76aa478); /* 1 */
        d = FF (d, a, b, c, x[ 1],  12, 0xe8c7b756); /* 2 */
        c = FF (c, d, a, b, x[ 2],  17, 0x242070db); /* 3 */
        b = FF (b, c, d, a, x[ 3],  22, 0xc1bdceee); /* 4 */
        a = FF (a, b, c, d, x[ 4],   7, 0xf57c0faf); /* 5 */
        d = FF (d, a, b, c, x[ 5],  12, 0x4787c62a); /* 6 */
        c = FF (c, d, a, b, x[ 6],  17, 0xa8304613); /* 7 */
        b = FF (b, c, d, a, x[ 7],  22, 0xfd469501); /* 8 */
        a = FF (a, b, c, d, x[ 8],   7, 0x698098d8); /* 9 */
        d = FF (d, a, b, c, x[ 9],  12, 0x8b44f7af); /* 10 */
        c = FF (c, d, a, b, x[10],  17, 0xffff5bb1); /* 11 */
        b = FF (b, c, d, a, x[11],  22, 0x895cd7be); /* 12 */
        a = FF (a, b, c, d, x[12],   7, 0x6b901122); /* 13 */
        d = FF (d, a, b, c, x[13],  12, 0xfd987193); /* 14 */
        c = FF (c, d, a, b, x[14],  17, 0xa679438e); /* 15 */
        b = FF (b, c, d, a, x[15],  22, 0x49b40821); /* 16 */

        /* Round 2 */
        a = GG (a, b, c, d, x[ 1],   5, 0xf61e2562); /* 17 */
        d = GG (d, a, b, c, x[ 6],   9, 0xc040b340); /* 18 */
        c = GG (c, d, a, b, x[11],  14, 0x265e5a51); /* 19 */
        b = GG (b, c, d, a, x[ 0],  20, 0xe9b6c7aa); /* 20 */
        a = GG (a, b, c, d, x[ 5],   5, 0xd62f105d); /* 21 */
        d = GG (d, a, b, c, x[10],   9, 0x02441453); /* 22 */
        c = GG (c, d, a, b, x[15],  14, 0xd8a1e681); /* 23 */
        b = GG (b, c, d, a, x[ 4],  20, 0xe7d3fbc8); /* 24 */
        a = GG (a, b, c, d, x[ 9],   5, 0x21e1cde6); /* 25 */
        d = GG (d, a, b, c, x[14],   9, 0xc33707d6); /* 26 */
        c = GG (c, d, a, b, x[ 3],  14, 0xf4d50d87); /* 27 */
        b = GG (b, c, d, a, x[ 8],  20, 0x455a14ed); /* 28 */
        a = GG (a, b, c, d, x[13],   5, 0xa9e3e905); /* 29 */
        d = GG (d, a, b, c, x[ 2],   9, 0xfcefa3f8); /* 30 */
        c = GG (c, d, a, b, x[ 7],  14, 0x676f02d9); /* 31 */
        b = GG (b, c, d, a, x[12],  20, 0x8d2a4c8a); /* 32 */

        /* Round 3 */
        a = HH (a, b, c, d, x[ 5],   4, 0xfffa3942); /* 33 */
        d = HH (d, a, b, c, x[ 8],  11, 0x8771f681); /* 34 */
        c = HH (c, d, a, b, x[11],  16, 0x6d9d6122); /* 35 */
        b = HH (b, c, d, a, x[14],  23, 0xfde5380c); /* 36 */
        a = HH (a, b, c, d, x[ 1],   4, 0xa4beea44); /* 37 */
        d = HH (d, a, b, c, x[ 4],  11, 0x4bdecfa9); /* 38 */
        c = HH (c, d, a, b, x[ 7],  16, 0xf6bb4b60); /* 39 */
        b = HH (b, c, d, a, x[10],  23, 0xbebfbc70); /* 40 */
        a = HH (a, b, c, d, x[13],   4, 0x289b7ec6); /* 41 */
        d = HH (d, a, b, c, x[ 0],  11, 0xeaa127fa); /* 42 */
        c = HH (c, d, a, b, x[ 3],  16, 0xd4ef3085); /* 43 */
        b = HH (b, c, d, a, x[ 6],  23, 0x04881d05); /* 44 */
        a = HH (a, b, c, d, x[ 9],   4, 0xd9d4d039); /* 45 */
        d = HH (d, a, b, c, x[12],  11, 0xe6db99e5); /* 46 */
        c = HH (c, d, a, b, x[15],  16, 0x1fa27cf8); /* 47 */
        b = HH (b, c, d, a, x[ 2],  23, 0xc4ac5665); /* 48 */

        /* Round 4 */
        a = II (a, b, c, d, x[ 0],   6, 0xf4292244); /* 49 */
        d = II (d, a, b, c, x[ 7],  10, 0x432aff97); /* 50 */
        c = II (c, d, a, b, x[14],  15, 0xab9423a7); /* 51 */
        b = II (b, c, d, a, x[ 5],  21, 0xfc93a039); /* 52 */
        a = II (a, b, c, d, x[12],   6, 0x655b59c3); /* 53 */
        d = II (d, a, b, c, x[ 3],  10, 0x8f0ccc92); /* 54 */
        c = II (c, d, a, b, x[10],  15, 0xffeff47d); /* 55 */
        b = II (b, c, d, a, x[ 1],  21, 0x85845dd1); /* 56 */
        a = II (a, b, c, d, x[ 8],   6, 0x6fa87e4f); /* 57 */
        d = II (d, a, b, c, x[15],  10, 0xfe2ce6e0); /* 58 */
        c = II (c, d, a, b, x[ 6],  15, 0xa3014314); /* 59 */
        b = II (b, c, d, a, x[13],  21, 0x4e0811a1); /* 60 */
        a = II (a, b, c, d, x[ 4],   6, 0xf7537e82); /* 61 */
        d = II (d, a, b, c, x[11],  10, 0xbd3af235); /* 62 */
        c = II (c, d, a, b, x[ 2],  15, 0x2ad7d2bb); /* 63 */
        b = II (b, c, d, a, x[ 9],  21, 0xeb86d391); /* 64 */

        state.state[0] += a;
        state.state[1] += b;
        state.state[2] += c;
        state.state[3] += d;
    }
}
