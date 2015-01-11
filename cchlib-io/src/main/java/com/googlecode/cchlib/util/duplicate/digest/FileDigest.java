package com.googlecode.cchlib.util.duplicate.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.Provider;
import java.util.Arrays;
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
public class FileDigest implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final char[] HEX = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };

    /** real buffer */
    private final byte[] buffer;
    /** wrapper of {@link buffer} */
    private final ByteBuffer byteBuffer;

    private final StringBuilder computeDigestKeyStringBuilder = new StringBuilder();

    /** cancel status */
    private boolean cancel;
    private FileChannel fchannel;
    private File file;
    private FileInputStream fis;
    private FileDigestListener listener;
    private final FileDigestDelegator mdd;
    private byte[] digest;

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
        this.mdd        = new FileDigestDelegator( messageDigest );
        this.buffer     = new byte[bufferSize];
        this.byteBuffer = ByteBuffer.wrap( this.buffer );
    }

    /**
     * TODOC XXX
     * <p>Must be call for every succeed {@link #setFile(File, FileDigestListener)}</p>
     * @throws IllegalStateException is state is not valid
     * @throws IOException if any
     */
    public void reset() throws IOException
    {
        if( this.fis == null ) {
            throw new IllegalStateException( "No file, no open steam" );
        }

        try {
            // Need !
            this.fis.close();
        }
        finally {
            this.file     = null;
            this.fchannel = null;
            this.listener = null;
            this.cancel   = false;
            this.fis      = null;
            this.digest   = null;
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
        return computeDigestKeyString( this.computeDigestKeyStringBuilder, digestKey );
    }

    static String computeDigestKeyString( final StringBuilder stringBuilder, final byte[] digestKey )
    {
        stringBuilder.setLength( 0 );

        for( final byte b : digestKey ) {
            stringBuilder.append( HEX[(b & 0x00f0)>>4 ] );
            stringBuilder.append( HEX[(b & 0x000f) ] );
            }

        return stringBuilder.toString();
    }

    /**
     * Compute MD value for giving file
     * (use nio {@link FileChannel})
     * <p>
     *  Perform invocations to {@link #setFile(File, FileDigestListener)},
     *  then to {@link #hasNext()}, {@link #computeNext()} and finally
     *  to {@link #reset()}.
     * </p>
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
        setFile( file, listener );

        try {
            while( hasNext() ) {
                computeNext(false);
                }
            }
        finally {
            reset();
            }

        if( this.cancel ) {
            // User ask to cancel current task
            throw new CancelRequestException();
            }

        return this.digest;
    }

    /**
     * Prepare to compute new file
     *
     * @param file
     * @param listener
     * @throws FileNotFoundException
     * @throws IllegalStateException
     */
    public void setFile( final File file, final FileDigestListener listener ) throws FileNotFoundException
    {
        if( this.fis != null ) {
            // Previous resources not close. Expecting reset()
            throw new IllegalStateException( "Previous resources not close. Expecting reset()" );
        }

        this.mdd.reset();
        this.file = file;
        this.fis = new FileInputStream(file);
        this.fchannel = this.fis.getChannel();
        this.listener = listener;
        this.cancel = false;
        this.digest = null;
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

    /**
     * TODOC XXX
     * @return XXX
     * @throws IOException if any
     */
    public boolean hasNext() throws IOException
    {
        if( (this.fchannel.read(this.byteBuffer) != -1) || (this.byteBuffer.position() > 0) ) {
            return true;
        } else {
            if( this.digest == null ){
                this.digest = this.mdd.digestDelegator();
            }
            return false;
        }
    }

    /**
     * XXX
     * @param returnCurrentBuffer
     * @return
     * @throws CancelRequestException XXX
     */
    public byte[] computeNext( final boolean returnCurrentBuffer ) throws CancelRequestException
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

        byte[] currentBuffer;
        if( returnCurrentBuffer ) {
            // create a copy of current buffer (must be done before use if)
            currentBuffer = Arrays.copyOfRange( this.byteBuffer.array(), this.byteBuffer.position(), this.byteBuffer.remaining() );
        } else {
            currentBuffer = null;
        }
        this.mdd.update( this.byteBuffer );
        this.listener.computeDigest( this.file, this.byteBuffer.position() );

        this.byteBuffer.compact();

        if( this.listener.isCancel() ) {
            // User ask to cancel current task
            this.cancel = true;
            throw new CancelRequestException();
        }

        return currentBuffer;
    }
}
