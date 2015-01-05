package com.googlecode.cchlib.util.duplicate.digest;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.Provider;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.DigestEventListener;

/**
 * Compute hash code for a file.
 *
 * <P>This class is not thread safe</P>
 *
 * @see FileDigestFactory
 * @since 4.2
 */
public class FileDigest implements Serializable, Closeable
{
    private static final long serialVersionUID = 1L;
    private static final char[] HEX = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };

    /** real buffer */
    private final byte[] buffer;
    /** wrapper of {@link buffer} */
    private final ByteBuffer byteBuffer;
    /** cancel status */
    private boolean cancel;
    private final StringBuilder computeDigestKeyStringBuilder = new StringBuilder();
    private FileChannel fchannel;
    private File file;
    private FileInputStream fis;
    private FileDigestListener listener;
    private final FileDigestDelegator mdd;

    /**
     * Create a {@link FileDigest} object that
     * use the specified messageDigest.
     *
     * @param messageDigest the messageDigest
     * @param bufferSize buffer size to use
     */
    FileDigest(
        final MessageDigest   messageDigest,
        final int             bufferSize
        )
    {
        this.mdd    = new FileDigestDelegator( messageDigest );
        this.buffer = new byte[bufferSize];
        this.byteBuffer = ByteBuffer.wrap( this.buffer );
    }

    @Override
    public void close() throws IOException
    {
        try {
            // Need !
            this.fis.close();
        }
        finally {
            this.file = null;
            this.fchannel = null;
            this.listener = null;
            this.cancel = false;
            this.fis = null;
        }
    }

    /**
     * Returns hex String value for digestKey.
     *
     * @param digestKey disgestKey to transform into String
     * @return Hex String
     * @see #computeDigestKey(String)
     * @see #digest()
     */
    private String computeDigestKeyString(
        final byte[] digestKey
        )
    {
        this.computeDigestKeyStringBuilder.setLength( 0 );

        for( final byte b :digestKey ) {
            this.computeDigestKeyStringBuilder.append( HEX[(b & 0x00f0)>>4 ] );
            this.computeDigestKeyStringBuilder.append( HEX[(b & 0x000f) ] );
            }

        return this.computeDigestKeyStringBuilder.toString();
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
    public byte[] computeFile(
            final File                file,
            final FileDigestListener  listener
            )
        throws FileNotFoundException,
               IOException, CancelRequestException
    {
        this.mdd.reset();
        this.file = file;
        this.fis = new FileInputStream(file);
        this.fchannel = this.fis.getChannel();
        this.listener = listener;
        this.cancel = false;

        try {
            while( hasNext() ) {
                next();
                }
            }
        finally {
            close();
            }

        if( this.cancel ) {
            // User ask to cancel current task
            throw new CancelRequestException();
            }

        return this.mdd.digestDelegator();
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
        final byte[] lastDigest = this.mdd.digest();

        if( lastDigest == null ) {
            throw new IllegalStateException();
            }

        return computeDigestKeyString( lastDigest );
    }

    /**
     * Returns {@link MessageDigest#getAlgorithm()} value
     * @return Returns a string that identifies the algorithm
     *         use by message digest
     */
    public String getAlgorithm()
    {
        return this.mdd.getAlgorithm();
    }

    public int getBufferSize()
    {
        return this.buffer.length;
    }

    /**
     * Returns {@link MessageDigest#getProvider()} value
     * @return the provider of the message digest object.
     */
    public Provider getProvider()
    {
        return this.mdd.getProvider();
    }

    private boolean hasNext() throws IOException
    {
        return (this.fchannel.read(this.byteBuffer) != -1) || (this.byteBuffer.position() > 0);
    }

    private void next() throws CancelRequestException
    {
        if( this.cancel ) {
            // User ask to cancel current task
            throw new CancelRequestException();
            }
        if( this.file == null ) {
            throw new IllegalStateException( "file is null" );
        }
        if( this.listener == null ) {
            throw new IllegalStateException( "listener is null" );
        }

        this.byteBuffer.flip();
        this.mdd.update(this.byteBuffer);

        this.listener.computeDigest( this.file, this.byteBuffer.position() );
        this.byteBuffer.compact();

        if( this.listener.isCancel() ) {
            // User ask to cancel current task
            this.cancel = true;
            throw new CancelRequestException();
            }
    }

//    protected ByteBuffer getBuffer()
//    {
//        return this.byteBuffer;
//    }
//
//    protected FileDigestDelegator getFileDigestDelegator()
//    {
//        return this.mdd;
//    }
}
