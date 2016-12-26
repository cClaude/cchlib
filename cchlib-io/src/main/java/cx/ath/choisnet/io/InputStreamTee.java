package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * This allow to spy an {@link InputStream}
 *
 * @since 3.02
 */
public class InputStreamTee extends InputStream
{
    private final InputStream       inputStream;
    private final PipedOutputStream pipeOut;
    private final PipedInputStream  pipeIn;

    public InputStreamTee( final InputStream inputStream ) throws IOException
    {
        this.inputStream = inputStream;
        this.pipeOut     = new PipedOutputStream();
        this.pipeIn      = new PipedInputStream( this.pipeOut );
    }

    @Override
    public int available() throws IOException
    {
        return this.inputStream.available();
    }

    @Override
    public void close() throws IOException
    {
        this.inputStream.close();
        this.pipeOut.close();
    }

    @Override
    public void mark( final int readlimit )
    {
        this.inputStream.mark( readlimit );
    }

    @Override
    public boolean markSupported()
    {
        return this.inputStream.markSupported();
    }

    @Override
    public int read() throws IOException
    {
        final int c = this.inputStream.read();

        this.pipeOut.write( c );

        return c;
    }

    @Override
    public int read( final byte[] b ) throws IOException
    {
        return read( b, 0, b.length );
    }

    @Override
    public int read( final byte[] b, final int off, final int len )
        throws IOException
    {
        final int bytesLen = this.inputStream.read( b, off, len );

        if( bytesLen == -1 ) {
            return -1; // EOF
        } else {
            this.pipeOut.write( b, off, bytesLen );

            return bytesLen;
        }
    }

    @Override
    public void reset() throws IOException
    {
        this.inputStream.reset();
    }

    @Override
    public long skip( final long n ) throws IOException
    {
        return this.inputStream.skip( n );
    }

    public InputStream getTeeInputStream()
    {
        return this.pipeIn;
    }

} // class
