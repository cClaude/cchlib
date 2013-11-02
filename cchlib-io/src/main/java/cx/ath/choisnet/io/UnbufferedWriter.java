package cx.ath.choisnet.io;

import java.io.Writer;

/**
 * TODOC
 */
public final class UnbufferedWriter extends Writer
{
    private final Writer writer;
    private final int maxBufferSize;
    private int bufferSize;

    public UnbufferedWriter(Writer writer, int maxBufferSize)
    {
        this.writer         = writer;
        this.maxBufferSize  = maxBufferSize;
        this.bufferSize     = 0;
    }

    public UnbufferedWriter(Writer writer)
    {
        this(writer, 0);
    }

    @Override
    public void close()
        throws java.io.IOException
    {
        flush();
        writer.close();
    }

    @Override
    public void flush()
        throws java.io.IOException
    {
        writer.flush();
        bufferSize = 0;
    }

    @Override
    public void write(char[] cbuf, int off, int len)
        throws java.io.IOException
    {
        for(int i = off; i < len; i++) {
            char b = cbuf[i];

            writer.write(b);

            if((b == '\n') || (b == '\r')) {
                flush();
            }

            if(++bufferSize >= maxBufferSize) {
                flush();
            }
        }
    }
}
