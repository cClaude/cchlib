package cx.ath.choisnet.xml;

/**
 **
 ** @author Claude CHOISNET
 ** @since 1.51
 */
public class XMLParserException extends Exception
{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public XMLParserException( final String message )
    {
        super( message );
    }

    public XMLParserException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public XMLParserException( final Throwable cause )
    {
        super( cause );
    }
}
