package cx.ath.choisnet.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Format String and char to HTML
 */
public final class HTMLWriter extends Writer
    implements Appendable, Flushable, Closeable
{
    private final Writer        writer;
    private final StringBuilder sbuffer = new StringBuilder();
    private int tabLength = 4;

    /**
     * TODOC
     * @param writer destination Writer
     * @throws NullPointerException if writer is null
     */
    public HTMLWriter(final Writer writer)
        throws NullPointerException
    {
        if( writer == null ) {
            throw new NullPointerException();
        }
        this.writer = writer;
    }

    /**
     * TODOC
     * @param writer destination Writer
     * @param tabLength number of HTML space character to write for
     *        each tab [0x09] character
     * @throws NullPointerException if writer is null
     * @throws IllegalArgumentException if tabLength is negative
     */
    public HTMLWriter(final Writer writer, final int tabLength)
        throws NullPointerException, IllegalArgumentException
    {
        this( writer );

        if( tabLength < 0 ) {
            throw new IllegalArgumentException();
            }
        this.tabLength = tabLength;
    }

    @Override
    public void close()throws IOException
    {
        flush();
        this.writer.close();
    }

    @Override
    public void flush() throws IOException
    {
        writer.flush();
    }

    /**
     * Writes a string without modifications.
     *
     * @param htmlWellFormatString String to be written
     * @throws java.io.IOException
     */
    public void rawWrite(final String htmlWellFormatString)
        throws java.io.IOException
    {
        this.writer.write(htmlWellFormatString);
    }

    /**
     * Writes the {@link Throwable} stack trace without modifications.
     *
     * @param throwable exception to get the stack trace.
     * @see Throwable#printStackTrace(PrintWriter)
     * @throws java.io.IOException
     */
    public void rawWrite(final Throwable throwable)
        throws java.io.IOException
    {
        final StringWriter sw = new StringWriter();

        throwable.printStackTrace(new PrintWriter(sw));

        rawWrite( sw.toString() );
    }

    /**
     * Format characters into HTML
     *
     * @param cbuf
     * @param off
     * @param len
     * @throws IOException
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        synchronized(super.lock) {
            writer.write( toString(cbuf,off,len) );
            }
    }

    /**
     * Writes the {@link Throwable} stack trace formatted for HTML.
     * This is typically to be use to write into a &lt;textarea&gt;
     * @param throwable exception to get the stack trace.
     * @see Throwable#printStackTrace(PrintWriter)
     * @throws IOException
     */
    public void write(final Throwable throwable)
        throws IOException
    {
        final StringWriter sw = new StringWriter();

        throwable.printStackTrace(new PrintWriter(sw));

        write( sw.toString() );
    }

    /**
     * Convert String to HTML String
     *
     * @param str String to convert into HTML
     * @return a String where HTML characters are converted.
     */
    public String toString( final String str )
    {
        return toString( str.toCharArray(), 0, str.length() );
    }

    /**
     * Convert array of characters to HTML String
     *
     * @param cbuf array of char
     * @param off first entry to convert
     * @param len length to convert
     * @return a String where HTML characters are converted.
     */
    public String toString( char[] cbuf, int off, int len )
    {// TODO: improve encoding ! Add Attribute set on this class
     // to offer some choices
        String str;

        synchronized(sbuffer) {
            sbuffer.setLength(0);

            for(int i0 = off; i0 < len; i0++) {
                switch(cbuf[i0]) {
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
                        for(int i1=0;i1<tabLength;i1++) {
                            sbuffer.append("&nbsp;");
                        }
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
                        sbuffer.append(cbuf[i0]);
                        break;
                    }
                }
            str = sbuffer.toString();
        }
        return str;
    }
}
