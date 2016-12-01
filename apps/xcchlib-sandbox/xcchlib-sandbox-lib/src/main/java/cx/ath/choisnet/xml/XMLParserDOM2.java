package cx.ath.choisnet.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 **
 ** @author Claude CHOISNET
 ** @since 1.51
 ** @version 1.54.005
 ** @deprecated use {@link cx.ath.choisnet.xml.impl.XMLParserDOM2Impl}
 */
@Deprecated
public class XMLParserDOM2 implements XMLParser
{
    private final DocumentBuilder documentBuilder;
    private Document              document;

    /**
     * @param validation Deprecated
     * @param ignoreWhitespace Deprecated
     * @param ignoreComments Deprecated
     * @param putCDATAIntoText Deprecated
     * @param createEntityRefs Deprecated
     * @param errorHandler Deprecated
     * @throws IOException Deprecated
     * @throws ParserConfigurationException Deprecated
     * @throws SAXException Deprecated
     */
    protected XMLParserDOM2(
        final boolean validation, // true
        final boolean ignoreWhitespace, // true
        final boolean ignoreComments, // false
        final boolean putCDATAIntoText, // false
        final boolean createEntityRefs, // false
        final ErrorHandler errorHandler
        ) throws IOException, ParserConfigurationException, SAXException
    {
        this.documentBuilder = createDocumentBuilder( validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs, errorHandler );
        this.document = null;
    }

    /**
     * @param anURL Deprecated
     * @param validation Deprecated
     * @param ignoreWhitespace Deprecated
     * @param ignoreComments Deprecated
     * @param putCDATAIntoText Deprecated
     * @param createEntityRefs Deprecated
     * @param errorHandler Deprecated
     * @throws IOException Deprecated
     * @throws ParserConfigurationException Deprecated
     * @throws SAXException Deprecated
     */
    public XMLParserDOM2(
        final URL anURL,
        final boolean validation,
        final boolean ignoreWhitespace,
        final boolean ignoreComments,
        final boolean putCDATAIntoText,
        final boolean createEntityRefs,
        final ErrorHandler errorHandler
        ) throws IOException, ParserConfigurationException,SAXException
    {
        this( validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs, errorHandler );

        this.document = this.documentBuilder.parse( anURL.openStream(), anURL.toString() );
    }

    /**
     * Deprecated
     * @param aStream Deprecated
     * @param validation Deprecated
     * @param ignoreWhitespace Deprecated
     * @param ignoreComments Deprecated
     * @param putCDATAIntoText Deprecated
     * @param createEntityRefs Deprecated
     * @param errorHandler Deprecated
     * @throws IOException Deprecated
     * @throws ParserConfigurationException Deprecated
     * @throws SAXException Deprecated
     */
    public XMLParserDOM2(
        final InputStream aStream,
        final boolean validation, // true
        final boolean ignoreWhitespace, // true
        final boolean ignoreComments, // false
        final boolean putCDATAIntoText, // false
        final boolean createEntityRefs, // false
        final ErrorHandler errorHandler
        ) throws IOException, ParserConfigurationException, SAXException
    {
        this( validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs, errorHandler );

        this.document = this.documentBuilder.parse( aStream );
    }

    /**
     * @param document Deprecated
     */
    protected void setDocument( final Document document )
    {
        this.document = document;
    }

    @Override
    public Document getDocument()
    {
        return this.document;
    }

    private static DocumentBuilderFactory createDocumentBuilderFactory(
        final boolean validation,
        final boolean ignoreWhitespace,
        final boolean ignoreComments,
        final boolean putCDATAIntoText,
        final boolean createEntityRefs
        )
    {
        //
        // Step 1: create a DocumentBuilderFactory and configure it
        //
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        //
        // Optional: set various configuration options
        //
        dbf.setValidating( validation );
        dbf.setIgnoringComments( ignoreComments );
        dbf.setIgnoringElementContentWhitespace( ignoreWhitespace );
        dbf.setCoalescing( putCDATAIntoText );

        //
        // The opposite of creating entity ref nodes is expanding them inline
        //
        dbf.setExpandEntityReferences( !createEntityRefs );

        //
        // At this point the DocumentBuilderFactory instance can be saved
        // and reused to create any number of DocumentBuilder instances
        // with the same configuration options.
        //
        return dbf;
    }

    private static DocumentBuilder createDocumentBuilder(
        final boolean validation,
        final boolean ignoreWhitespace,
        final boolean ignoreComments,
        final boolean putCDATAIntoText,
        final boolean createEntityRefs,
        final ErrorHandler errorHandler
        ) throws ParserConfigurationException
    {
        final DocumentBuilderFactory dbf = createDocumentBuilderFactory( validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs );

        //
        // Step 2: create a DocumentBuilder that satisfies the constraints
        // specified by the DocumentBuilderFactory
        //
        DocumentBuilder db = null;

        try {
            db = dbf.newDocumentBuilder();
        }
        catch( final ParserConfigurationException pce ) {
            throw pce;
        }

        //
        // Set an ErrorHandler before parsing
        //
        db.setErrorHandler( errorHandler );

        return db;
    }
}

