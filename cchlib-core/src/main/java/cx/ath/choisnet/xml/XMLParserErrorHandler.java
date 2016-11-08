package cx.ath.choisnet.xml;

import cx.ath.choisnet.xml.impl.SAXErrorHandlerImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * TODOC
 *
 */
public class XMLParserErrorHandler
{
    private final ErrorHandler saxErrorHandler;

    /**
     * TODOC
     *
     * @param saxErrorHandler a {@link org.xml.sax.ErrorHandler} object.
     * @see SAXErrorHandlerImpl
     */
    public XMLParserErrorHandler( final ErrorHandler saxErrorHandler )
    {
        this.saxErrorHandler = saxErrorHandler;
    }

    /**
     * TODOC
     *
     * @param out a {@link java.io.PrintWriter} object.
     */
    public XMLParserErrorHandler( final PrintWriter out )
    {
        this( new SAXErrorHandlerImpl(out) );
    }

    /**
     * TODOC
     *
     * @return a {@link ErrorHandler} object.
     */
    public ErrorHandler getSAXErrorHandler()
    {
        return this.saxErrorHandler;
    }

    /**
     * TODOC
     *
     * @param ioe a {@link IOException} object.
     * @throws XMLParserException if any.
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
     * TODOC
     *
     * @param pce a {@link ParserConfigurationException} object.
     * @throws XMLParserException if any.
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
     * TODOC
     *
     * @param saxe a {@link SAXException} object.
     * @throws XMLParserException if any.
     */
    public void saxError( final SAXException saxe )
        throws XMLParserException
    {
        throw new XMLParserException(saxe);
    }
}
