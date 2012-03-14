package cx.ath.choisnet.xml.impl;

import cx.ath.choisnet.xml.XMLParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.EnumSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 *
 */
@Deprecated
public class XMLParserDOM2Impl
    implements XMLParser
{
    /**
     *
     */
    @Deprecated
    public enum Attributs {
        /**
         *
         */
        ENABLE_VALIDATING,
        /**
         *
         */
        IGNORE_COMMENTS,
        /**
         *
         */
        IGNORE_WHITESPACE,
        /**
         *
         */
        PUT_CDATA_INTO_TEXT,
        /**
         *
         */
        CREATE_ENTITY_REFERENCES
        };

    public static final EnumSet<Attributs> DEFAULT_ATTRIBUTS = EnumSet.of( Attributs.IGNORE_WHITESPACE );
    public static final EnumSet<Attributs> VALIDATE_ONLY = EnumSet.of( Attributs.ENABLE_VALIDATING );

    private DocumentBuilder documentBuilder;
    private Document        document;

    protected XMLParserDOM2Impl(
            EnumSet<Attributs>      attributes,
            XMLParserErrorHandler   errorHandler
            )
        throws XMLParserException
    {
        try {
            documentBuilder = XMLParserDOM2Impl.createDocumentBuilder(attributes, errorHandler.getSAXErrorHandler());
            document        = null;
            }
        catch(ParserConfigurationException e) {
            errorHandler.parserError(e);
            }
    }

    public XMLParserDOM2Impl(
            URL                     anURL,
            EnumSet<Attributs>      attributes,
            XMLParserErrorHandler   errorHandler
            )
        throws XMLParserException
    {
        this(attributes, errorHandler);

        try {
            document = documentBuilder.parse(anURL.openStream(), anURL.toString());
            }
        catch(IOException e) {
            errorHandler.ioError(e);
            }
        catch(SAXException e) {
            errorHandler.saxError(e);
            }
    }

    public XMLParserDOM2Impl(
            InputStream             aStream,
            EnumSet<Attributs>      attributes,
            XMLParserErrorHandler   errorHandler
            )
        throws XMLParserException
    {
        this(attributes, errorHandler);

        try {
            document = documentBuilder.parse(aStream);
            }
        catch( SAXException e ) {
            errorHandler.saxError(e);
            }
        catch( IOException e ) {
            errorHandler.ioError(e);
            }
    }

    public Document getDocument()
    {
        return document;
    }

    protected void setDocument(Document document)
    {
        this.document = document;
    }


    private static DocumentBuilderFactory createDocumentBuilderFactory(
            EnumSet<Attributs> attributes
            )
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(attributes.contains(Attributs.ENABLE_VALIDATING));
        dbf.setIgnoringComments(attributes.contains(Attributs.IGNORE_COMMENTS));
        dbf.setIgnoringElementContentWhitespace(attributes.contains(Attributs.IGNORE_WHITESPACE));
        dbf.setCoalescing(attributes.contains(Attributs.PUT_CDATA_INTO_TEXT));
        dbf.setExpandEntityReferences(!attributes.contains(Attributs.CREATE_ENTITY_REFERENCES));

        return dbf;
    }

    private static DocumentBuilder createDocumentBuilder(
            EnumSet<Attributs>  attributes,
            ErrorHandler        errorHandler
            )
        throws ParserConfigurationException
    {
        DocumentBuilderFactory  dbf = XMLParserDOM2Impl.createDocumentBuilderFactory(attributes);
        DocumentBuilder         db;

        try {
            db = dbf.newDocumentBuilder();
            }
        catch( ParserConfigurationException pce ) {
            throw pce;
            }

        db.setErrorHandler(errorHandler);

        return db;
    }

}
