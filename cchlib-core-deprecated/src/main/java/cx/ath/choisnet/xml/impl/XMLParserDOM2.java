package cx.ath.choisnet.xml.impl;

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
import cx.ath.choisnet.xml.XMLParser;

/**
 * @deprecated Class XMLParserDOM2 is deprecated
 */
@Deprecated
public class XMLParserDOM2 implements XMLParser
{
    public enum Attributs
    {
        ENABLE_VALIDATING,
        IGNORE_COMMENTS,
        IGNORE_WHITESPACE,
        PUT_CDATA_INTO_TEXT,
        CREATE_ENTITY_REFERENCES
        }

    public static final EnumSet<Attributs> DEFAULT_ATTRIBUTS = EnumSet.of(Attributs.IGNORE_WHITESPACE);
    public static final EnumSet<Attributs> VALIDATE_ONLY = EnumSet.of(Attributs.ENABLE_VALIDATING);
    private final DocumentBuilder documentBuilder;
    private Document document;

    protected XMLParserDOM2(
            EnumSet<Attributs>  attributes, 
            ErrorHandler        errorHandler
            )
        throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException
    {
        documentBuilder = XMLParserDOM2.createDocumentBuilder(attributes, errorHandler);
        document = null;
    }

    @Override
    public Document getDocument()
    {
        return document;
    }

    protected void setDocument(org.w3c.dom.Document document)
    {
        this.document = document;
    }

    public XMLParserDOM2(
            URL                 anURL, 
            EnumSet<Attributs>  attributes, 
            ErrorHandler        errorHandler
            )
        throws IOException, 
               ParserConfigurationException, 
               SAXException
    {
        this(attributes, errorHandler);

        document = documentBuilder.parse(anURL.openStream(), anURL.toString());
    }

    public XMLParserDOM2(
            InputStream         aStream, 
            EnumSet<Attributs>  attributes, 
            ErrorHandler        errorHandler
            )
        throws  IOException, 
                ParserConfigurationException,
                SAXException
    {
        this(attributes, errorHandler);

        document = documentBuilder.parse(aStream);
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
        DocumentBuilderFactory dbf = XMLParserDOM2.createDocumentBuilderFactory(attributes);
        DocumentBuilder db = null;

        try {
            db = dbf.newDocumentBuilder();
        }
        catch(ParserConfigurationException pce) {
            throw pce;
        }
        
        db.setErrorHandler(errorHandler);

        return db;
    }
}
