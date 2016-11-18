package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * NEEDDOC
 */
public final class XMLWriter extends Writer
    //implements Appendable, Flushable, Closeable
{
    private final Writer writer;
    private final StringBuilder sbuffer = new StringBuilder();

    /**
     * NEEDDOC
     * @param writer
     */
    public XMLWriter(final Writer writer)
    {
        this.writer = writer;
    }

    @Override
    public void close() throws IOException
    {
        this.writer.close();
    }

    @Override
    public void flush() throws IOException
    {
        this.writer.flush();
    }

    /**
     * NEEDDOC
     */
    @Override
    public void write(final char[] cbuf, final int off, final int len)
        throws IOException
    {
        synchronized(super.lock) {
            this.sbuffer.setLength(0);

            for(int i = off; i < len; i++) {
                switch(cbuf[i]) {
                case 62:
                    this.sbuffer.append("&gt;");
                    break;
                case 60:
                    this.sbuffer.append("&lt;");
                    break;
                case 38:
                    this.sbuffer.append("&amp;");
                    break;
                default:
                    this.sbuffer.append(cbuf[i]);
                    break;
                }
            }

            this.writer.write(this.sbuffer.toString());
        }

    }

    /**
     * NEEDDOC
     * @param throwable
     * @throws IOException
     */
    public void write(final Throwable throwable)
        throws IOException
    {
        final StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        write(sw.toString());
    }

    /**
     * NEEDDOC
     * @param str
     * @throws IOException
     */
    public void rawWrite(final String str)
        throws IOException
    {
        this.writer.write(str);
    }

    /**
     * NEEDDOC
     * @param cbuf
     * @throws IOException
     */
    public void rawWrite(final char[] cbuf)
        throws IOException
    {
        this.writer.write(cbuf);
    }

    /**
     * NEEDDOC
     * @param cbuf
     * @param off
     * @param len
     * @throws IOException
     */
    public void rawWrite(final char[] cbuf, final int off, final int len)
        throws IOException
    {
        this.writer.write(cbuf, off, len);
    }
}
