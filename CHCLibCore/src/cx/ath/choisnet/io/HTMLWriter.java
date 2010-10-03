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
public final class HTMLWriter extends Writer
    implements Appendable, Flushable, Closeable
{
    private final Writer        writer;
    private final StringBuilder sbuffer = new StringBuilder();

    public HTMLWriter(Writer writer)
    {
        this.writer = writer;
    }

    public void close()
        throws java.io.IOException
    {
        flush();
        writer.close();
    }

    public void flush()
        throws java.io.IOException
    {
        writer.flush();
    }

    public void rawWrite(String htmlWellFormatString)
        throws java.io.IOException
    {
        writer.write(htmlWellFormatString);
    }

    public void write(char[] cbuf, int off, int len)
        throws java.io.IOException
    {
        synchronized(super.lock) {
            sbuffer.setLength(0);

            for(int i = off; i < len; i++) {
                switch(cbuf[i]) {

                case 32: // SPACE
                    sbuffer.append("&nbsp;");
                    break;
                case 13:
                    sbuffer.append("<!-- \\r -->\r");
                    break;
                case 10:
                    sbuffer.append("<br /><!-- \\n -->\n");
                    break;
                case 9: // TAB
                    sbuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    break;
                case 62:
                    sbuffer.append("&gt;");
                    break;
                case 60:
                    sbuffer.append("&lt;");
                    break;
                case 34:
                    sbuffer.append("&quot;");
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

        write( sw.toString() );
    }
}
