package cx.ath.choisnet.io;

import java.io.StringWriter;

/**
 * StringBufferWriter offer some standard
 * method not provided by {@link StringWriter}
 *
 * @since 1.30
 */
public final class StringBufferWriter extends StringWriter
{
    /**
     * Create a {@link StringBufferWriter}
     */
    public StringBufferWriter()
    {
        super();
    }

    /**
     * Create a {@link StringBufferWriter}
     *
     * @param initialSize Initial buffer size
     */
    public StringBufferWriter( final int initialSize )
    {
        super( initialSize );
    }

    /**
     * Clean buffer and returns previous content buffer
     * @return previous content buffer
     */
    public String cleanBuffer()
    {
        final String result = getBuffer().toString();

        getBuffer().setLength(0);

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
    public void setLength( final int len )
    {
        getBuffer().setLength(len);
    }
}
