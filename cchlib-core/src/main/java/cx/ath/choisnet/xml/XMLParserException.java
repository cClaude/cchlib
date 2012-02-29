package cx.ath.choisnet.xml;

/**
 * TODOC
 *
 * @author Claude CHOISNET
 * @version $Id: $
 */
public class XMLParserException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for XMLParserException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public XMLParserException(String message)
    {
        super(message);
    }

    /**
     * <p>Constructor for XMLParserException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause a {@link java.lang.Throwable} object.
     */
    public XMLParserException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * <p>Constructor for XMLParserException.</p>
     *
     * @param cause a {@link java.lang.Throwable} object.
     */
    public XMLParserException(Throwable cause)
    {
        super(cause);
    }
}
