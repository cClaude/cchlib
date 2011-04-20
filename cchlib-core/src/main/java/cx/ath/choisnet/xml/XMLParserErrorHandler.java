package cx.ath.choisnet.xml;

import cx.ath.choisnet.xml.impl.SAXErrorHandlerImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * TODO: doc!
 *
 * @author Claude CHOISNET
 * @version $Id: $
 */
public class XMLParserErrorHandler
{
    private final ErrorHandler saxErrorHandler;

    /**
     * TODO: doc!
     *
     * @param saxErrorHandler a {@link org.xml.sax.ErrorHandler} object.
     * @see SAXErrorHandlerImpl
     */
    public XMLParserErrorHandler( final ErrorHandler saxErrorHandler )
    {
        this.saxErrorHandler = saxErrorHandler;
    }

    /**
     * TODO: doc!
     *
     * @param out a {@link java.io.PrintWriter} object.
     */
    public XMLParserErrorHandler( final PrintWriter out )
    {
        this( new SAXErrorHandlerImpl(out) );
    }

    /**
     * TODO: doc!
     *
     * @return a {@link org.xml.sax.ErrorHandler} object.
     */
    public ErrorHandler getSAXErrorHandler()
    {
        return this.saxErrorHandler;
    }

    /**
     * TODO: doc!
     *
     * @param ioe a {@link java.io.IOException} object.
     * @throws cx.ath.choisnet.xml.XMLParserException if any.
     */
    public void ioError( final IOException ioe )
        throws XMLParserException
    {
        String message = (new StringBuilder())
            .append("$IOError: ")
            .append(ioe.getMessage())
            .toString();
        throw new XMLParserException(message);
    }

    /**
     * TODO: doc!
     *
     * @param pce a {@link javax.xml.parsers.ParserConfigurationException} object.
     * @throws cx.ath.choisnet.xml.XMLParserException if any.
     */
    public void parserError( final ParserConfigurationException pce )
        throws XMLParserException
    {
        String message = (new StringBuilder())
            .append("$Fatal Error: ")
            .append(pce.getMessage())
            .toString();
        throw new XMLParserException(message);
    }

    /**
     * TODO: doc!
     *
     * @param saxe a {@link org.xml.sax.SAXException} object.
     * @throws cx.ath.choisnet.xml.XMLParserException if any.
     */
    public void saxError( final SAXException saxe )
        throws XMLParserException
    {
        throw new XMLParserException(saxe);
    }
}
