package cx.ath.choisnet.xml;

import cx.ath.choisnet.xml.impl.SAXErrorHandlerImpl;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class XMLParserErrorHandler
{
    private final ErrorHandler saxErrorHandler;

    public XMLParserErrorHandler(ErrorHandler saxErrorHandler)
    {
        this.saxErrorHandler = saxErrorHandler;
    }

    public XMLParserErrorHandler(PrintWriter out)
    {
        this( new SAXErrorHandlerImpl(out) );
    }

    public ErrorHandler getSAXErrorHandler()
    {
        return this.saxErrorHandler;
    }

    public void ioError(java.io.IOException ioe)
        throws XMLParserException
    {
        String message = (new StringBuilder())
            .append("$IOError: ")
            .append(ioe.getMessage())
            .toString();
        throw new XMLParserException(message);
    }

    public void parserError(ParserConfigurationException pce)
        throws cx.ath.choisnet.xml.XMLParserException
    {
        String message = (new StringBuilder())
            .append("$Fatal Error: ")
            .append(pce.getMessage())
            .toString();
        throw new XMLParserException(message);
    }

    public void saxError(SAXException saxe)
        throws cx.ath.choisnet.xml.XMLParserException
    {
        throw new XMLParserException(saxe);
    }
}
