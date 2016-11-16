package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * NEEDDOC
 *
 */
public final class UnbufferedOutputStream
    extends OutputStream
{
    private final OutputStream outputStream;
    private final int maxBufferSize;
    private int bufferSize;

    /**
     * NEEDDOC
     * @param outputStream
     * @param maxBufferSize
     */
    public UnbufferedOutputStream(
        final OutputStream  outputStream,
        final int           maxBufferSize
        )
    {
        this.outputStream   = outputStream;
        this.maxBufferSize  = maxBufferSize;
        this.bufferSize     = 0;
    }

    /**
     * NEEDDOC
     * @param outputStream
     */
    public UnbufferedOutputStream(
        final OutputStream outputStream
        )
    {
        this( outputStream, 0 );
    }

    @Override
    public void close() throws IOException
    {
        this.outputStream.close();
    }

    @Override
    public void flush()
        throws java.io.IOException
    {
        this.outputStream.flush();

        this.bufferSize = 0;
    }

    @Override
    public void write(final int b) throws IOException
    {
        this.outputStream.write(b);

        if(++this.bufferSize >= this.maxBufferSize) {
            flush();
        }
    }

    @Override
    public void write(final byte[] cbuf, final int off, final int len)
        throws IOException
    {
        for(int i = off; i < len; i++) {
            final byte b = cbuf[i];

            write(b);
        }
    }

    @Override
    public void write(final byte[] cbuf)
        throws java.io.IOException
    {
        write(cbuf, 0, cbuf.length);
    }
}
