package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * TODOC
 */
public final class XMLWriter extends Writer
    //implements Appendable, Flushable, Closeable
{
    private final Writer writer;
    private final StringBuilder sbuffer = new StringBuilder();

    /**
     * TODOC
     * @param writer
     */
    public XMLWriter(Writer writer)
    {
        this.writer = writer;
    }

    @Override
    public void close() throws IOException
    {
        writer.close();
    }

    @Override
    public void flush() throws IOException
    {
        writer.flush();
    }

    /**
     * TODOC
     */
    @Override
    public void write(char cbuf[], int off, int len)
        throws IOException
    {
        synchronized(super.lock) {
            sbuffer.setLength(0);

            for(int i = off; i < len; i++) {
                switch(cbuf[i]) {
                case 62:
                    sbuffer.append("&gt;");
                    break;
                case 60:
                    sbuffer.append("&lt;");
                    break;
                case 38:
                    sbuffer.append("&amp;");
                    break;
                default:
                    sbuffer.append(cbuf[i]);
                    break;
                }
            }

            writer.write(sbuffer.toString());
        }

    }

    /**
     * TODOC
     * @param throwable
     * @throws IOException
     */
    public void write(Throwable throwable)
        throws IOException
    {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        write(sw.toString());
    }

    /**
     * TODOC
     * @param str
     * @throws IOException
     */
    public void rawWrite(String str)
        throws IOException
    {
        writer.write(str);
    }

    /**
     * TODOC
     * @param cbuf
     * @throws IOException
     */
    public void rawWrite(char[] cbuf)
        throws IOException
    {
        writer.write(cbuf);
    }

    /**
     * TODOC
     * @param cbuf
     * @param off
     * @param len
     * @throws IOException
     */
    public void rawWrite(char[] cbuf, int off, int len)
        throws IOException
    {
        writer.write(cbuf, off, len);
    }
}
