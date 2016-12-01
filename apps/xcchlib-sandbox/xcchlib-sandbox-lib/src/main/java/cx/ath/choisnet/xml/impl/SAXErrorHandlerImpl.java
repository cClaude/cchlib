/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/xml/impl/SAXErrorHandlerImpl.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  3.02.002 2006.05.30 Claude CHOISNET
 **                      Adaptation de cx.ath.choisnet.xml.XMLParserErrorHandler
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.xml.impl.SAXErrorHandlerImpl
 **
 */
package cx.ath.choisnet.xml.impl;

import java.io.PrintWriter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 ** Error handler to report errors and warnings
 **
 **
 ** @author Claude CHOISNET
 ** @since 3.02.002
 ** @version 3.02.002
 */
public class SAXErrorHandlerImpl implements ErrorHandler
{
    /** Error handler output goes here */
    private final PrintWriter output;

    public SAXErrorHandlerImpl( final PrintWriter out )
    {
        this.output = out;
    }

    /**
     * @return a string describing parse exception details
     */
    private static final String getParseExceptionInfo(
        final SAXParseException spe
        )
    {
        final StringBuilder sb = new StringBuilder();

        if( (spe.getSystemId() != null) && (spe.getPublicId() != null) ) {
            sb.append( "URI('systemId','publicId')=(" );
            sb.append( spe.getSystemId() );
            sb.append( "," );
            sb.append( spe.getPublicId() );
            sb.append( ") " );
        }

        sb.append( "(L:" );
        sb.append( spe.getLineNumber() );
        sb.append( " ,C:" );
        sb.append( spe.getColumnNumber() );
        sb.append( "):" );

        sb.append( spe.getMessage() );

        // sb.append( " / E: {" );
        // sb.append( spe.getException() );
        // sb.append( "}" );

        if( spe.getCause() != null ) {
            sb.append( " cause by : " );
            sb.append( spe.getCause() );
        }

        return sb.toString();
    }

    /**
     * The following method is standard SAX ErrorHandler methods.
     *
     * See SAX documentation for more info.
     */
    @Override
    public void warning( final SAXParseException spe )
            throws SAXException
    {
        final String message = "$Warning: " + getParseExceptionInfo( spe );

        this.output.println( message );
    }

    /**
     * The following method is standard SAX ErrorHandler methods.
     *
     * See SAX documentation for more info.
     */
    @Override
    public void error( final SAXParseException spe )
            throws SAXException
    {
        final String message = "$Error: " + getParseExceptionInfo( spe );

        throw new SAXException( message );
    }

    /**
     * The following method is standard SAX ErrorHandler methods.
     *
     * See SAX documentation for more info.
     */
    @Override
    public void fatalError( final SAXParseException spe )
            throws SAXException
    {
        final String message = "$Fatal Error: " + getParseExceptionInfo( spe );

        throw new SAXException( message );
    }
}