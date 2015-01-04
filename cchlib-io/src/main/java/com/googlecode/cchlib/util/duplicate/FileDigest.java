package com.googlecode.cchlib.util.duplicate;

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

/**
 * <P>This class is not thread safe</P>
 */
public class FileDigest
    implements Serializable
{

 private static final long serialVersionUID = 1L;
    private static final char[] HEX = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };
    private final FileDigestDelegator mdd;
    private final byte[] buffer;
    private final StringBuilder computeDigestKeyStringBuilder = new StringBuilder();

    /**
     * Create a MessageDigestFile object that
     * use the specified messageDigest.
     *
     * @param messageDigest the messageDigest
     * @param bufferSize buffer size
     */
    FileDigest(
        final MessageDigest   messageDigest,
        final int             bufferSize
        )
    {
        this.mdd    = new FileDigestDelegator( messageDigest );
        this.buffer = new byte[bufferSize];
    }

    public String getAlgorithm()
    {
        return this.mdd.getAlgorithm();
    }

    public Provider getProvider()
    {
        return this.mdd.getProvider();
    }

    public int getBufferSize()
    {
        return this.buffer.length;
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
    public byte[] compute(
            final File                file,
            final FileDigestListener  listener
            )
        throws FileNotFoundException,
               IOException, CancelRequestException
    {
        this.mdd.reset();

        final FileInputStream fis      = new FileInputStream(file);
        final FileChannel     fchannel = fis.getChannel();
        boolean               cancel   = false;

        try {
            for( final ByteBuffer bb = ByteBuffer.wrap(this.buffer);
                    (fchannel.read(bb) != -1) || (bb.position() > 0);
                    bb.compact()
                    ) {
                bb.flip();
                this.mdd.update(bb);

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
        finally {
            // Need !
            fis.close();
            }

        if( cancel ) {
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
}
