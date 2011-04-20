package cx.ath.choisnet.io;

import java.io.OutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public final class UnbufferedOutputStream
    extends OutputStream
{
    private final OutputStream outputStream;
    private final int maxBufferSize;
    private int bufferSize;

    public UnbufferedOutputStream(OutputStream outputStream, int maxBufferSize)
    {
        this.outputStream   = outputStream;
        this.maxBufferSize  = maxBufferSize;
        this.bufferSize     = 0;
    }

    public UnbufferedOutputStream(java.io.OutputStream outputStream)
    {
        this(outputStream, 0);
    }

    public void close()
        throws java.io.IOException
    {
        outputStream.close();
    }

    public void flush()
        throws java.io.IOException
    {
        outputStream.flush();

        bufferSize = 0;
    }

    public void write(int b)
        throws java.io.IOException
    {
        outputStream.write(b);

        if(++bufferSize >= maxBufferSize) {
            flush();
        }
    }

    public void write(byte[] cbuf, int off, int len)
        throws java.io.IOException
    {
        for(int i = off; i < len; i++) {
            byte b = cbuf[i];

            write(b);
        }
    }

    public void write(byte[] cbuf)
        throws java.io.IOException
    {
        write(cbuf, 0, cbuf.length);
    }
}
