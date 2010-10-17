/**
 * 
 */
package cx.ath.choisnet.util.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/**
 * 
 * @author Claude CHOISNET
 */
public class MessageDigestFile
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final static char[] HEX = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };
    private transient MessageDigest md;
    private byte[] buffer;
    private byte[] lastDigest = null;
    
    /**
     * Default buffer size : {@value}
     */
    protected final static int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Create a MessageDigestFile object that based on
     * MD5 algorithm
     * 
     * @throws NoSuchAlgorithmException if no Provider supports a
     *         MessageDigestSpi implementation for MD5, (should
     *         not occur since MD5, should be implemented on all
     *         Java platforms.
     */
    public MessageDigestFile() throws NoSuchAlgorithmException
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
    public MessageDigestFile(
            String algorithm
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
    public MessageDigestFile(
            String algorithm,
            int    bufferSize
            ) 
        throws NoSuchAlgorithmException
    {
        this(
            MessageDigest.getInstance(algorithm),
            bufferSize
            );
    }

//    /**
//     * Create a MessageDigestFile object that
//     * use the specified digest algorithm. 
//     * 
//     * @param algorithm the name of the algorithm requested. 
//     * @param provider the provider. 
//     * @param bufferSize buffer size
//     * @throws NoSuchAlgorithmException if no Provider supports a
//     *         MessageDigestSpi implementation for the specified
//     *         algorithm.*
//     * @see MessageDigest#getInstance(String, Provider)
//     */
//    public MessageDigestFile(
//            String      algorithm, 
//            Provider    provider,
//            int         bufferSize
//            ) throws NoSuchAlgorithmException
//    {
//        this(
//            MessageDigest.getInstance( algorithm, provider ),
//            bufferSize
//            );
//    }

    /**
     * Create a MessageDigestFile object that
     * use the specified messageDigest. 
     * 
     * @param messageDigest the messageDigest
     * @param bufferSize buffer size
     */
    private MessageDigestFile(
            MessageDigest   messageDigest, 
            int             bufferSize
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
        this.lastDigest = md.digest();
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
        return md.getAlgorithm();
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
        return md.getDigestLength();
    }

    /**
     * Returns the provider of message digest object. 
     * @return the provider of message digest object.
     * @see java.security.MessageDigest#getProvider()
     */
    public final Provider getProvider()
    {
        return md.getProvider();
    }

    /**
     * 
     * @see java.security.MessageDigest#reset()
     */
    protected void reset()
    {
        lastDigest = null;
        md.reset();
    }

    /**
     * @param input
     * @see java.security.MessageDigest#update(byte)
     */
    protected void update( byte input )
    {
        md.update( input );
    }

    /**
     * @param input
     * @param offset
     * @param len
     * @see java.security.MessageDigest#update(byte[], int, int)
     */
    public void update( byte[] input, int offset, int len )
    {
        md.update( input, offset, len );
    }

    /**
     * @param input
     * @see java.security.MessageDigest#update(byte[])
     */
    protected void update( byte[] input )
    {
        md.update( input );
    }

    /**
     * @param input
     * @see java.security.MessageDigest#update(java.nio.ByteBuffer)
     */
    protected final void update( ByteBuffer input )
    {
        md.update( input );
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
    public final static String computeDigestKeyString(byte[] digestKey)
    {
        StringBuilder sb = new StringBuilder();

        for(byte b :digestKey) {
            sb.append( HEX[(b & 0x00f0)>>4 ] );
            sb.append( HEX[(b & 0x000f) ] );
        }

        return sb.toString();
    }

    /**
     * Returns array of bytes view (standard format) of
     * giving Hex String encoded value
     *
     * @param digestHexKey Hex String that represent a digest
     * @return digest has an array of bytes
     * @throws NumberFormatException
     * @see #computeDigestKeyString(byte[])
     */
    public static byte[] computeDigestKey(
            String digestHexKey
            )
        throws NumberFormatException
    {
        final int slen = digestHexKey.length();
        final int len   = slen / 2;

        if(len * 2 != slen ) {
            throw new NumberFormatException("key error * bad length()");
        }

        byte[] mdBytes = new byte[len];

        for(int i = 0; i < len; i++) {
            // TODO: some optimizations here !
            int     pos     = i << 1;
            String  digit = digestHexKey.substring(pos, pos + 2);

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
     * @throws FileNotFoundException
     * @throws IOException
     */
    public byte[] compute(File file)
        throws FileNotFoundException,
               IOException
    {
        reset();

        FileInputStream fis      = new FileInputStream(file);
        FileChannel     fchannel = fis.getChannel();

        for( ByteBuffer bb = ByteBuffer.wrap(this.buffer);
                fchannel.read(bb) != -1 || bb.position() > 0;
                bb.compact()
                ) {
            bb.flip();
            update(bb);
        }

        try {
            fchannel.close();
        }
        catch(Exception ignore) {
            }

        try {
            fis.close();
        }
        catch(Exception ignore) {
            }

        return digestDelegator();
    }
    
    /**
     * Compute MD value for giving file 
     * (use {@link FileInputStream})
     * 
     * @param file File to read
     * @return MD value has an array of bytes
     * @throws FileNotFoundException
     * @throws IOException
     */
    public byte[] computeInputStream(File file)
        throws FileNotFoundException,
               IOException
    {
        reset();

        FileInputStream fis  = new FileInputStream(file);
        int             l;
        
        while( (l = fis.read( buffer )) != -1 ) {
            update(buffer,0,l);
            }

        try {
            fis.close();
        }
        catch(Exception ignore) {
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
        return lastDigest;
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
        if( lastDigest == null ) {
            throw new IllegalStateException();
        }
        return computeDigestKeyString( lastDigest );
    }
    // END File specific
    
    //Serializable
    private void writeObject(java.io.ObjectOutputStream out)
        throws IOException
    {
        out.defaultWriteObject();
        out.writeUTF( getAlgorithm() );
    }
    
    //Serializable
    private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        String algorithm = in.readUTF();
        
        try {
            this.md = MessageDigest.getInstance(algorithm);
        }
        catch( NoSuchAlgorithmException e ) {
            throw new RuntimeException(e);
        }
    }
}
