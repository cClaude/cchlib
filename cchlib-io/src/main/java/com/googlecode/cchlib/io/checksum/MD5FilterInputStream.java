package com.googlecode.cchlib.io.checksum;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implements MD5 functionality on a stream.
 * <P>
 * More information about this class is available from 
 * <a target="_top" href="http://ostermiller.org/utils/MD5.html">ostermiller.org</a>.
 * </P><P>
 * This class produces a 128-bit "fingerprint" or "message digest"
 * for all data read from this stream.
 * <BR/>
 * It is conjectured that it is computationally infeasible to produce
 * two messages having the same message digest, or to produce any
 * message having a given pre-specified target message digest. The MD5
 * algorithm is intended for digital signature applications, where a
 * large file must be "compressed" in a secure manner before being
 * encrypted with a private (secret) key under a public-key 
 * cryptosystem such as RSA.
 * <p>
 * For more information see RFC1321.
 *
 * @see MD5
 * @see MD5FilterOutputStream
 *
 * @author Santeri Paavolainen http://www.helsinki.fi/~sjpaavol/programs/md5/
 * @author Stephen Ostermiller http://ostermiller.org/contact.pl?regarding=Java+Utilities
 * @author Claude CHOISNET (remove some memory leaks, provide some optimization)
 * @since 4.1.7
 */
public class MD5FilterInputStream extends FilterInputStream 
{
    /**
     * MD5 context
     */
    private MD5 md5;

    /**
     * Creates a MD5FilterInputStream
     * 
     * @param in the underlying input stream
     */
    public MD5FilterInputStream( final InputStream in ) 
    {
        super( in );

        md5 = new MD5();
    }

    /**
     * Reads the next byte of data from this input stream. The 
     * value byte is returned as an int in the range 0 to 255.
     * If no byte is available because the end of the stream has 
     * been reached, the value -1 is returned.
     * <br/>
     * This method blocks until input data is available, the end
     * of the stream is detected, or an exception is thrown.
     * <p>
     * This method simply performs in.read() and returns the result.
     *
     * @return the next byte of data, or -1 if the end of the 
     *         stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public int read() throws IOException 
    {
        int c = in.read();

        if( c == -1 ) {
            return -1;
            }

        md5.update( (byte)(c & 0xff) );

        return c;
    }

    /**
     * Reads up to length bytes of data from this input stream
     * into an array of bytes. This method blocks until some input 
     * is available.
     *
     * @param bytes the buffer into which the data is read.
     * @param offset the start offset of the data.
     * @param length the maximum number of bytes read.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public int read( final byte[] bytes, final int offset, final int length ) 
        throws IOException 
    {
        int r;

        if( (r = in.read(bytes, offset, length)) == -1 ) {
            return r;
            }

        md5.update(bytes, offset, r);

        return r;
    }

    /**
     * Returns array of bytes representing hash of the stream so far.
     * @return Array of 16 bytes, the hash of all read bytes.
     */
    public byte[] getHash()
    {
        return md5.getHash();
    }

    /**
     * Get a 32-character hex representation representing hash
     * of the stream so far.
     *
     * @return A string containing  the hash of all written bytes.
     */
    public String getHashString()
    {
        return md5.getHashString();
    }
}

