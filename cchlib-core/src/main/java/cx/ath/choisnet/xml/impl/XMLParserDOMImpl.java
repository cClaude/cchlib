package cx.ath.choisnet.xml.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import com.googlecode.cchlib.NeedDoc;
import cx.ath.choisnet.xml.XMLParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;

@NeedDoc
public class XMLParserDOMImpl
    implements XMLParser
{
    /**
     * Help to setup configuration for {@link DocumentBuilderFactory}
     */
    public enum Attributs {
        /**
         * set {@link DocumentBuilderFactory#setValidating(boolean)}
         * to true if present in EnumSet
         */
        ENABLE_VALIDATING,

        /**
         * set {@link DocumentBuilderFactory#setIgnoringComments(boolean)}
         * to true if present in EnumSet
         */
        IGNORE_COMMENTS,

        /**
         * set {@link DocumentBuilderFactory#setIgnoringElementContentWhitespace(boolean)}
         * to true if present in EnumSet
         */
        IGNORE_WHITESPACE,

        /**
         * set {@link DocumentBuilderFactory#setCoalescing(boolean)}
         * to true if present in EnumSet
         */
        PUT_CDATA_INTO_TEXT,

        /**
         * set {@link DocumentBuilderFactory#setCoalescing(boolean)}
         * to false if present in EnumSet (default true)
         */
        CREATE_ENTITY_REFERENCES
        }

    private DocumentBuilder documentBuilder;
    private Document        document;

    @NeedDoc
    protected XMLParserDOMImpl(
            final XMLParserErrorHandler errorHandler,
            final Set<Attributs>        attributes
            )
        throws XMLParserException
    {
        try {
            this.documentBuilder = XMLParserDOMImpl.createDocumentBuilder( attributes, errorHandler.getSAXErrorHandler() );
            this.document        = null;
            }
        catch(final ParserConfigurationException e) {
            errorHandler.parserError(e);
            }
    }

    @NeedDoc
    public XMLParserDOMImpl(
            final File                  file,
            final XMLParserErrorHandler errorHandler,
            final Set<Attributs>        attributes
            )
        throws XMLParserException
    {
        this(errorHandler, attributes );

        try {
            this.document = this.documentBuilder.parse( file );
            }
        catch(final IOException e) {
            errorHandler.ioError(e);
            }
        catch(final SAXException e) {
            errorHandler.saxError(e);
            }
    }

    @NeedDoc
    public XMLParserDOMImpl(
            final URL                   anURL,
            final XMLParserErrorHandler errorHandler,
            final Set<Attributs>        attributes
            )
        throws XMLParserException
    {
        this(errorHandler, attributes );

        try {
            this.document = this.documentBuilder.parse( anURL.openStream(), anURL.toString() );
            }
        catch(final IOException e) {
            errorHandler.ioError(e);
            }
        catch(final SAXException e) {
            errorHandler.saxError(e);
            }
    }

    @NeedDoc
    public XMLParserDOMImpl(
            final InputStream           aStream,
            final XMLParserErrorHandler errorHandler,
            final Set<Attributs>        attributes
            )
        throws XMLParserException
    {
        this(errorHandler, attributes );

        try {
            this.document = this.documentBuilder.parse(aStream);
            }
        catch( final SAXException e ) {
            errorHandler.saxError(e);
            }
        catch( final IOException e ) {
            errorHandler.ioError(e);
            }
    }

    @Override
    public Document getDocument()
    {
        return this.document;
    }

    @NeedDoc
    protected void setDocument(final Document document)
    {
        this.document = document;
    }

    private static DocumentBuilderFactory createDocumentBuilderFactory(
            final Set<Attributs> attributes
            )
    {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating( attributes.contains( Attributs.ENABLE_VALIDATING ) );
        dbf.setIgnoringComments( attributes.contains( Attributs.IGNORE_COMMENTS ) );
        dbf.setIgnoringElementContentWhitespace( attributes.contains(Attributs.IGNORE_WHITESPACE ) );
        dbf.setCoalescing( attributes.contains(Attributs.PUT_CDATA_INTO_TEXT ) );
        dbf.setExpandEntityReferences( !attributes.contains(Attributs.CREATE_ENTITY_REFERENCES ) );

        return dbf;
    }

    private static DocumentBuilder createDocumentBuilder(
            final Set<Attributs> attributes,
            final ErrorHandler   errorHandler
            )
        throws ParserConfigurationException
    {
        return newDocumentBuilder(
                XMLParserDOMImpl.createDocumentBuilderFactory( attributes ),
                errorHandler
                );
    }

    private static DocumentBuilder newDocumentBuilder(
            final DocumentBuilderFactory dbf,
            final ErrorHandler           errorHandler
            ) throws ParserConfigurationException
    {
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            }
        catch( final ParserConfigurationException pce ) {
            throw pce;
            }

        db.setErrorHandler( errorHandler );

        return db;
    }

}
