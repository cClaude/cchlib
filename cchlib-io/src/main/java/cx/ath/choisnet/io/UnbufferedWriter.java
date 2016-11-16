package cx.ath.choisnet.io;

import java.io.Writer;

/**
 * NEEDDOC
 */
public final class UnbufferedWriter extends Writer
{
    private final Writer writer;
    private final int maxBufferSize;
    private int bufferSize;

    public UnbufferedWriter(final Writer writer, final int maxBufferSize)
    {
        this.writer         = writer;
        this.maxBufferSize  = maxBufferSize;
        this.bufferSize     = 0;
    }

    public UnbufferedWriter(final Writer writer)
    {
        this(writer, 0);
    }

    @Override
    public void close()
        throws java.io.IOException
    {
        flush();
        this.writer.close();
    }

    @Override
    public void flush()
        throws java.io.IOException
    {
        this.writer.flush();
        this.bufferSize = 0;
    }

    @Override
    public void write(final char[] cbuf, final int off, final int len)
        throws java.io.IOException
    {
        for(int i = off; i < len; i++) {
            final char b = cbuf[i];

            this.writer.write(b);

            if((b == '\n') || (b == '\r')) {
                flush();
            }

            if(++this.bufferSize >= this.maxBufferSize) {
                flush();
            }
        }
    }
}
