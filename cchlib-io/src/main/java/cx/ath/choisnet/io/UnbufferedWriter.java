package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Writer;

/**
 * This class is for very special required, having a {@link Writer} without
 * any buffer (or a defined buffer size), even if used {@link Writer} use
 * {@link java.io.BufferedWriter}.
 *
 * @since 3.02
 * @see UnbufferedOutputStream
 */
public final class UnbufferedWriter extends Writer
{
    private final Writer writer;
    private final int    maxBufferSize;

    private int bufferSize;

    /**
     * Create a {@link UnbufferedWriter} using a {@code maxBufferSize} buffer
     *
     * @param writer Parent {@link Writer}
     * @param maxBufferSize Maximum buffer size.
     */
    public UnbufferedWriter( final Writer writer, final int maxBufferSize )
    {
        this.writer         = writer;
        this.maxBufferSize  = maxBufferSize;
        this.bufferSize     = 0;
    }

    /**
     * Create a {@link UnbufferedWriter} without any buffer
     *
     * @param writer Parent {@link Writer}
     */
    public UnbufferedWriter( final Writer writer )
    {
        this( writer, 0 );
    }

    @Override
    public void close() throws IOException
    {
        flush();
        this.writer.close();
    }

    @Override
    public void flush() throws IOException
    {
        this.writer.flush();
        this.bufferSize = 0;
    }

    @Override
    public void write( final char[] cbuf, final int off, final int len )
        throws IOException
    {
        for( int i = off; i < len; i++ ) {
            final char b = cbuf[ i ];

            this.writer.write( b );

            if((b == '\n') || (b == '\r')) {
                flush();
            }

            if( ++this.bufferSize >= this.maxBufferSize ) {
                flush();
            }
        }
    }
}
