package cx.ath.choisnet.io;

import java.io.StringWriter;

/**
 * StringBufferWriter offer some standard
 * method not provided by {@link StringWriter}
 */
public final class StringBufferWriter
    extends StringWriter
{
    /**
     * Create a {@link StringBufferWriter}
     */
    public StringBufferWriter()
    {
    }

    /**
     * Create a {@link StringBufferWriter}
     *
     * @param initialSize Initial buffer size
     */
    public StringBufferWriter( int initialSize )
    {
        super( initialSize );
    }

    /**
     * Clean buffer and returns previous content buffer
     * @return previous content buffer
     */
    public String cleanBuffer()
    {
        StringBuffer sb     = getBuffer();
        String       result = sb.toString();

        sb.setLength(0);

        return result;
    }

    /**
     * Returns buffer length
     * @return buffer length
     */
    public int length()
    {
        return getBuffer().length();
    }

    /**
     * Set buffer length
     * @param len Length to set
     */
    public void setLength(int len)
    {
        getBuffer().setLength(len);
    }
}
