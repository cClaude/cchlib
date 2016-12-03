package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Collection;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * <P>This class is not thread safe</P>
 *
 * @deprecated Use {@link FileDigest} instead.
 */
@Deprecated
public class XMessageDigestFile
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final char[] HEX = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };
    private transient MessageDigest md;
    /** @serial */
    private final byte[] buffer;
    /** @serial */
    private byte[] lastDigest = null;

    /**
     * Default buffer size : {@value}
     */
    protected static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Create a MessageDigestFile object that based on
     * MD5 algorithm
     *
     * @throws NoSuchAlgorithmException if no Provider supports a
     *         MessageDigestSpi implementation for MD5, (should
     *         not occur since MD5, should be implemented on all
     *         Java platforms.
     */
    public XMessageDigestFile() throws NoSuchAlgorithmException
    {
        this("MD5");
    }

    /**
     * Create a MessageDigestFile object that
     * use the specified digest algorithm.
     *
     * @param algorithm the name of the algorithm requested.
     * @throws NoSuchAlgorithmException if no Provider supports a
     *         MessageDigestSpi implementation for the specified
     *         algorithm.*
     * @see MessageDigest#getInstance(String)
     */
    public XMessageDigestFile(
            final String algorithm
            )
        throws NoSuchAlgorithmException
    {
        this(
            MessageDigest.getInstance(algorithm),
            DEFAULT_BUFFER_SIZE
            );
    }

    /**
     * Create a MessageDigestFile object that
     * use the specified digest algorithm.
     *
     * @param algorithm the name of the algorithm requested.
     * @param bufferSize buffer size
     * @throws NoSuchAlgorithmException if no Provider supports a
     *         MessageDigestSpi implementation for the specified
     *         algorithm.*
     * @see MessageDigest#getInstance(String)
     */
    public XMessageDigestFile(
            final String algorithm,
            final int    bufferSize
            )
        throws NoSuchAlgorithmException
    {
        this(
            MessageDigest.getInstance(algorithm),
            bufferSize
            );
    }

    /**
     * Create a MessageDigestFile object that
     * use the specified messageDigest.
     *
     * @param messageDigest the messageDigest
     * @param bufferSize buffer size
     */
    private XMessageDigestFile(
            final MessageDigest   messageDigest,
            final int             bufferSize
            )
    {
        this.md     = messageDigest;
        this.buffer = new byte[bufferSize];
    }

    // BEGIN MessageDigestDelegator -----
    /**
     * Completes the hash computation by performing final
     * operations such as padding. The digest is reset after
     * this call is made.
     * <p>
     * Cache result that could be retrieve using {@link #digest()}
     * or {@link #digestString()}.
     * <p>
     * @return the array of bytes for the resulting hash value.
     * @see java.security.MessageDigest#digest()
     * @throws IllegalStateException if digest not yet
     *         initialized
     */
    protected byte[] digestDelegator()
    {
        this.lastDigest = this.md.digest();
        return this.lastDigest;
    }

    /**
     * Returns a string that identifies the algorithm, independent
     * of implementation details.
     *
     * @return the name of the algorithm.
     * @see java.security.MessageDigest#getAlgorithm()
     */
    public final String getAlgorithm()
    {
        // The name should be a standard Java
        // Security name (such as "SHA", "MD5", and so on).
        // See Appendix A in the Java Cryptography Architecture
        // API Specification & Reference for information
        // about standard algorithm names.
        return this.md.getAlgorithm();
    }

    /**
     * Returns the length of the digest in bytes, or 0 if
     * this operation is not supported by the provider
     * and the implementation is not cloneable
     *
     * @return the length of the digest in bytes.
     * @see java.security.MessageDigest#getDigestLength()
     */
    public final int getDigestLength()
    {
        return this.md.getDigestLength();
    }

    /**
     * Returns the provider of message digest object.
     * @return the provider of message digest object.
     * @see java.security.MessageDigest#getProvider()
     */
    public final Provider getProvider()
    {
        return this.md.getProvider();
    }

    /**
     *
     * @see java.security.MessageDigest#reset()
     */
    protected void reset()
    {
        this.lastDigest = null;
        this.md.reset();
    }

    /**
     * @param input deprecated
     * @see java.security.MessageDigest#update(byte)
     */
    protected void update( final byte input )
    {
        this.md.update( input );
    }

    /**
     * @param input deprecated
     * @param offset deprecated
     * @param len deprecated
     * @see java.security.MessageDigest#update(byte[], int, int)
     */
    public void update( final byte[] input, final int offset, final int len )
    {
        this.md.update( input, offset, len );
    }

    /**
     * @param input deprecated
     * @see java.security.MessageDigest#update(byte[])
     */
    protected void update( final byte[] input )
    {
        this.md.update( input );
    }

    /**
     * @param input deprecated
     * @see java.security.MessageDigest#update(java.nio.ByteBuffer)
     */
    protected final void update( final ByteBuffer input )
    {
        this.md.update( input );
    }
    // END MessageDigestDelegar -----

    /**
     * Returns hex String value for digestKey.
     *
     * @param digestKey disgestKey to transform into String
     * @return Hex String
     * @see #computeDigestKey(String)
     * @see #digest()
     */
    public static String computeDigestKeyString(
        final byte[] digestKey
        )
    {
        final StringBuilder sb = new StringBuilder();

        for( final byte b :digestKey ) {
            sb.append( HEX[(b & 0x00f0)>>4 ] );
            sb.append( HEX[ b & 0x000f ] );
            }

        return sb.toString();
    }

    /**
     * Returns array of bytes view (standard format) of
     * giving Hex String encoded value
     *
     * @param digestHexKey Hex String that represent a digest
     * @return digest has an array of bytes
     * @throws NumberFormatException if any
     * @see #computeDigestKeyString(byte[])
     */
    public static byte[] computeDigestKey(
            final String digestHexKey
            )
        throws NumberFormatException
    {
        final int slen = digestHexKey.length();
        final int len   = slen / 2;

        if( ( len * 2 ) != slen ) {
            throw new NumberFormatException("key error * bad length()");
            }

        final byte[] mdBytes = new byte[ len ];

        for( int i = 0; i < len; i++ ) {
            // TODO: some optimizations here !
            final int     pos   = i << 1;
            final String  digit = digestHexKey.substring(pos, pos + 2);

            mdBytes[i] = Integer.valueOf(digit, 16).byteValue();
            }

        return mdBytes;
    }

    // BEGIN File specific
    /**
     * Compute MD value for giving file
     * (use nio {@link FileChannel})
     *
     * @param file File to read
     * @return MD value has an array of bytes
     * @throws FileNotFoundException deprecated
     * @throws IOException deprecated
     */
    public byte[] compute( final File file )
        throws FileNotFoundException,
               IOException
    {
        reset();

        try (final FileInputStream fis = new FileInputStream(file)) {
            try( final FileChannel fchannel = fis.getChannel() ) {
                for( final ByteBuffer bb = ByteBuffer.wrap(this.buffer);
                        (fchannel.read(bb) != -1) || (bb.position() > 0);
                        bb.compact()
                        ) {
                    bb.flip();
                    update( bb );
                    }
                }
            }

        return digestDelegator();
    }

    /**
     * Compute MD value for giving file
     * (use nio {@link FileChannel})
     *
     * @param file File to read
     * @param listeners deprecated
     * @return MD value has an array of bytes
     * @throws FileNotFoundException could append if file is locked
     * @throws IOException any unexpected IO error
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    public byte[] compute(
            final File                            file,
            final Collection<DigestEventListener> listeners
            )
        throws FileNotFoundException,
               IOException, CancelRequestException
    {
        reset();

        boolean  cancel = false;

        try( final FileInputStream fis = new FileInputStream(file) ) {
            try( final FileChannel fchannel = fis.getChannel() ) {
                for( final ByteBuffer bb = ByteBuffer.wrap(this.buffer);
                        (fchannel.read(bb) != -1) || (bb.position() > 0);
                        bb.compact()
                        ) {
                    bb.flip();
                    update(bb);

                    for( final DigestEventListener l:listeners ) {
                        l.computeDigest( file, bb.position() );

                        if( l.isCancel() ) {
                            // User ask to cancel current task
                            cancel = true;
                            break;
                            }
                        }

                    if( cancel ) {
                        // User ask to cancel current task
                        break;
                        }
                    }
                }
        }

        if( cancel ) {
            // User ask to cancel current task
            throw new CancelRequestException();
            }

        return digestDelegator();
    }

    /**
     * Compute MD value for giving file
     * (use nio {@link FileChannel})
     *
     * @param file File to read
     * @param listener {@link DigestEventListener} to get results
     * @return MD value has an array of bytes
     * @throws FileNotFoundException could append if file is locked
     * @throws IOException any unexpected IO error
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    public byte[] compute(
            final File                file,
            final DigestEventListener listener
            )
        throws FileNotFoundException,
               IOException, CancelRequestException
    {
        reset();

        boolean cancel = false;

        try( final FileInputStream fis = new FileInputStream(file) ) {
            try( final FileChannel fchannel = fis.getChannel() ) {
                for( final ByteBuffer bb = ByteBuffer.wrap(this.buffer);
                        (fchannel.read(bb) != -1) || (bb.position() > 0);
                        bb.compact()
                        ) {
                    bb.flip();
                    update(bb);

                    listener.computeDigest( file, bb.position() );

                    if( listener.isCancel() ) {
                        // User ask to cancel current task
                        cancel = true;
                        break;
                        }

                    if( cancel ) {
                        // User ask to cancel current task
                        break;
                        }
                    }
                }
        }

        if( cancel ) {
            // User ask to cancel current task
            throw new CancelRequestException();
            }

        return digestDelegator();
    }

    /**
     * Compute MD value for giving file
     * (use {@link FileInputStream})
     *
     * @param file File to read
     * @return MD value has an array of bytes
     * @throws FileNotFoundException deprecated
     * @throws IOException deprecated
     */
    public byte[] computeInputStream( final File file )
        throws FileNotFoundException,
               IOException
    {
        reset();

        try (FileInputStream fis = new FileInputStream(file)) {
            int l;

            while( (l = fis.read( this.buffer )) != -1 ) {
                update(this.buffer,0,l);
                }
            }

        return digestDelegator();
    }

    /**
     * Return last hash computation
     *
     * @return the array of bytes for the resulting hash value.
     * @throws IllegalStateException if digest not yet
     *         initialized
     */
    public byte[] digest()
    {
        if( this.lastDigest == null ) {
            throw new IllegalStateException();
            }
        return this.lastDigest;
    }

    /**
     * Returns Hex representation of digest
     * @return Hex representation of digest
     * @see #digest()
     * @see #computeDigestKeyString(byte[])
     * @throws IllegalStateException if digest not yet
     *         initialized
     */
    public String digestString()
    {
        if( this.lastDigest == null ) {
            throw new IllegalStateException();
            }
        return computeDigestKeyString( this.lastDigest );
    }
    // END File specific

    //Serializable
    private void writeObject( final ObjectOutputStream out )
        throws IOException
    {
        out.defaultWriteObject();
        out.writeUTF( getAlgorithm() );
    }

    //Serializable
    private void readObject( final ObjectInputStream in )
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        final String algorithm = in.readUTF();

        try {
            this.md = MessageDigest.getInstance( algorithm );
            }
        catch( final NoSuchAlgorithmException e ) {
            throw new ClassNotFoundException( algorithm, e );
            }
    }
}
