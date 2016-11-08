package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * TODOC
 *
 */
public final class UnbufferedOutputStream
    extends OutputStream
{
    private final OutputStream outputStream;
    private final int maxBufferSize;
    private int bufferSize;

    /**
     * TODOC
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
     * TODOC
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
        outputStream.close();
    }

    @Override
    public void flush()
        throws java.io.IOException
    {
        outputStream.flush();

        bufferSize = 0;
    }

    @Override
    public void write(int b) throws IOException
    {
        outputStream.write(b);

        if(++bufferSize >= maxBufferSize) {
            flush();
        }
    }

    @Override
    public void write(byte[] cbuf, int off, int len)
        throws IOException
    {
        for(int i = off; i < len; i++) {
            byte b = cbuf[i];

            write(b);
        }
    }

    @Override
    public void write(byte[] cbuf)
        throws java.io.IOException
    {
        write(cbuf, 0, cbuf.length);
    }
}
