package cx.ath.choisnet.io;

import java.io.StringWriter;

/**
 *
 * @author Claude CHOISNET
 *
 */
public final class StringBufferWriter extends StringWriter
{
    public StringBufferWriter()
    {
    }

    public StringBufferWriter(int initialSize)
    {
        super(initialSize);
    }

    public String cleanBuffer()
    {
        StringBuffer sb     = getBuffer();
        String       result = sb.toString();

        sb.setLength(0);

        return result;
    }

    public int length()
    {
        return getBuffer().length();
    }

    public void setLength(int len)
    {
        getBuffer().setLength(len);
    }
}
