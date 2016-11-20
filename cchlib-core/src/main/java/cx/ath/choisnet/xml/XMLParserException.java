package cx.ath.choisnet.xml;

/**
 * XMLParser exception
 */
public class XMLParserException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for XMLParserException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public XMLParserException(final String message)
    {
        super(message);
    }

    /**
     * <p>Constructor for XMLParserException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause a {@link java.lang.Throwable} object.
     */
    public XMLParserException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * <p>Constructor for XMLParserException.</p>
     *
     * @param cause a {@link java.lang.Throwable} object.
     */
    public XMLParserException(final Throwable cause)
    {
        super(cause);
    }
}
