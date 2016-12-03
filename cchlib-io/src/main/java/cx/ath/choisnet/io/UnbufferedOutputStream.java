package cx.ath.choisnet.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import javax.annotation.Nonnegative;

/**
 * This class is design to ensure stream use a fixed
 * buffer size (could be no buffer at all) even if stream
 * come from a {@link BufferedOutputStream}
 *
 * @since 3.01
 *
 * @see UnbufferedWriter
 */
public final class UnbufferedOutputStream
    extends OutputStream
{
    private final OutputStream outputStream;
    private final int maxBufferSize;
    private int bufferSize;

    /**
     * Create a {@link UnbufferedOutputStream} with a buffer of
     * {@code maxBufferSize} bytes.
     *
     * @param outputStream
     *            A valid {@link OutputStream}
     * @param maxBufferSize
     *            Buffer size (ensure this is the max size, but
     *            could be smaller)
     */
    public UnbufferedOutputStream(
        final OutputStream  outputStream,
        @Nonnegative
        final int           maxBufferSize
        )
    {
        if( outputStream == null ) {
            throw new InvalidParameterException();
        }

        if( maxBufferSize < 0 ) {
            throw new InvalidParameterException();
        }

        this.outputStream   = outputStream;
        this.maxBufferSize  = maxBufferSize;
        this.bufferSize     = 0;
    }

    /**
     * Create a {@link UnbufferedOutputStream} without buffer at all
     *
     * @param outputStream
     *            A valid {@link OutputStream}
     */
    public UnbufferedOutputStream( final OutputStream outputStream )
    {
        this( outputStream, 0 );
    }

    @Override
    public void close() throws IOException
    {
        this.outputStream.close();
    }

    @Override
    public void flush() throws IOException
    {
        this.outputStream.flush();
        this.bufferSize = 0;
    }

    @Override
    public void write( final int b ) throws IOException
    {
        this.outputStream.write( b );

        if( ++this.bufferSize >= this.maxBufferSize ) {
            flush();
        }
    }

    @Override
    public void write( final byte[] cbuf, final int off, final int len )
        throws IOException
    {
        for( int i = off; i < len; i++ ) {
            final byte b = cbuf[ i ];

            write( b );
        }
    }

    @Override
    public void write( final byte[] cbuf ) throws IOException
    {
        write( cbuf, 0, cbuf.length );
    }
}
