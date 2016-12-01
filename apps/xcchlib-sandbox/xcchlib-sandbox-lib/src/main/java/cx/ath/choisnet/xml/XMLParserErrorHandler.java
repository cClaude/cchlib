package cx.ath.choisnet.xml;

import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import cx.ath.choisnet.xml.impl.SAXErrorHandlerImpl;

/**
 * Error handler to report errors and warnings
 *
 * @author Claude CHOISNET
 * @since 1.51
 */
public class XMLParserErrorHandler
{
    private final ErrorHandler saxErrorHandler;

    public XMLParserErrorHandler( final ErrorHandler saxErrorHandler )
    {
        this.saxErrorHandler = saxErrorHandler;
    }

    public XMLParserErrorHandler( final PrintWriter out )
    {
        this( new SAXErrorHandlerImpl( out ) );
    }

    public ErrorHandler getSAXErrorHandler()
    {
        return this.saxErrorHandler;
    }

    /**
     * The following method is convenient methods to deal with
     * {@link IOException}.
     *
     * @since 3.02
     */
    public void ioError( final IOException ioe ) throws XMLParserException
    {
        final String message = "$IOError: " + ioe.getMessage();

        throw new XMLParserException( message );
    }

    /**
     * The following method is convenient methods to deal with
     * {@link ParserConfigurationException}.
     *
     * @since 3.02.002
     */
    public void parserError( final ParserConfigurationException pce )
        throws XMLParserException
    {
        final String message = "$Fatal Error: " + pce.getMessage();

        throw new XMLParserException( message );
    }

    /**
     * The following method is convenient methods to deal with
     * {@link SAXException}.
     *
     * @since 3.02.002
     */
    public void saxError( final SAXException saxe )
            throws XMLParserException
    {
        throw new XMLParserException( saxe );
    }
}
