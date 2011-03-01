package cx.ath.choisnet.xml;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class XMLParserException extends Exception
{

    private static final long serialVersionUID = 1L;

    public XMLParserException(String message)
    {
        super(message);
    }

    public XMLParserException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public XMLParserException(Throwable cause)
    {
        super(cause);
    }
}
