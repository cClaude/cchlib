package cx.ath.choisnet.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 * @author Claude CHOISNET
 *
 */
public final class XMLWriter extends Writer
    implements Appendable, Flushable, Closeable
{
    private final Writer writer;
    private final StringBuilder sbuffer = new StringBuilder();

    public XMLWriter(Writer writer)
    {
        this.writer = writer;
    }

    public void close()
        throws java.io.IOException
    {
        writer.close();
    }

    public void flush()
        throws java.io.IOException
    {
        writer.flush();
    }

    public void write(char cbuf[], int off, int len)
        throws java.io.IOException
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

    public void write(Throwable throwable)
        throws java.io.IOException
    {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        write(sw.toString());
    }

    public void rawWrite(String str)
        throws java.io.IOException
    {
        writer.write(str);
    }

    public void rawWrite(char[] cbuf)
        throws java.io.IOException
    {
        writer.write(cbuf);
    }

    public void rawWrite(char[] cbuf, int off, int len)
        throws java.io.IOException
    {
        writer.write(cbuf, off, len);
    }
}
