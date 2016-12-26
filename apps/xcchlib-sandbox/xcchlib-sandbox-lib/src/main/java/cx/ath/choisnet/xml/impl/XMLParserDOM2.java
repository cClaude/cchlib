package cx.ath.choisnet.xml.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import cx.ath.choisnet.xml.XMLParser;

/**
 **
 ** @author Claude CHOISNET
 ** @since 3.01
 ** @deprecated use {@link XMLParserDOM2Impl}
 */
@Deprecated
public class XMLParserDOM2
    implements XMLParser
{
    /**
     ** @deprecated use {@link XMLParserDOM2Impl.Attributs}
     */
    @Deprecated
    public enum Attributs
    {
        /**
         ** @see DocumentBuilderFactory#setValidating
         */
        ENABLE_VALIDATING,

        /**
         ** @see DocumentBuilderFactory#setIgnoringComments
         */
        IGNORE_COMMENTS,

        /**
         ** @see DocumentBuilderFactory#setIgnoringElementContentWhitespace
         */
        IGNORE_WHITESPACE,

        /**
         ** @see DocumentBuilderFactory#setCoalescing
         */
        PUT_CDATA_INTO_TEXT,

        /**
         ** @see DocumentBuilderFactory#setExpandEntityReferences
         */
        CREATE_ENTITY_REFERENCES,
    }

    /**
     * Pas de validation, les commentaires sont gardes, les WHITESPACE
     * sont ignores.
     *
     * @see Attributs#IGNORE_WHITESPACE
     */
    public static final Set<Attributs> DEFAULT_ATTRIBUTS
        = Collections.unmodifiableSet(
              EnumSet.of(
                    // no_ENABLE_VALIDATING,        false   validation
                    // no_IGNORE_COMMENTS,          false   ignoreComments
                    Attributs.IGNORE_WHITESPACE     //true  ignoreWhitespace
                    // no_PUT_CDATA_INTO_TEXT       false   putCDATAIntoText
                    // no_CREATE_ENTITY_REFERENCES  false   createEntityRefs
                    )
            );

    /**
    ** Seule la validation est activee.
    **
    ** @see Attributs#ENABLE_VALIDATING
    */
    public static final Set<Attributs> VALIDATE_ONLY
        = Collections.unmodifiableSet(
             EnumSet.of(
                    Attributs.ENABLE_VALIDATING
                    // no_IGNORE_COMMENTS,
                    // no_IGNORE_WHITESPACE
                    // no_PUT_CDATA_INTO_TEXT
                    // no_CREATE_ENTITY_REFERENCES
                    )
            );

    private final DocumentBuilder documentBuilder;
    private Document document;

    protected XMLParserDOM2(
        final Set<Attributs> attributes,
        final ErrorHandler errorHandler
        ) throws IOException, ParserConfigurationException, SAXException
    {
        this.documentBuilder = createDocumentBuilder( attributes, errorHandler );
        this.document = null;
    }

    /**
     * Create a XMLParser object with theses defaults flags :
     *
     * <PRE>
     *  validation          = true
     *  ignoreWhitespace    = true
     *  ignoreComments      = false
     *  putCDATAIntoText    = true --> false
     *  createEntityRefs    = false
     ** </PRE>
     *
     * @param anURL NEEDDOC
     * @param attributes NEEDDOC
     * @param errorHandler NEEDDOC
     * @throws IOException NEEDDOC
     * @throws ParserConfigurationException NEEDDOC
     * @throws SAXException NEEDDOC
     */
    public XMLParserDOM2(
        final URL anURL,
        final Set<Attributs> attributes,
        final ErrorHandler errorHandler
        ) throws IOException, ParserConfigurationException, SAXException
    {
        this( attributes, errorHandler );

        this.document = this.documentBuilder.parse( anURL.openStream(), anURL.toString() );
    }

    public XMLParserDOM2(
        final InputStream    aStream,
        final Set<Attributs> attributes,
        final ErrorHandler   errorHandler
        ) throws IOException, ParserConfigurationException, SAXException
    {
        this( attributes, errorHandler );

        this.document = this.documentBuilder.parse( aStream );
    }

    @Override
    public Document getDocument()
    {
        return this.document;
    }

    protected void setDocument( final Document document ) // ------------------------
    {
        this.document = document;
    }

    private static DocumentBuilderFactory createDocumentBuilderFactory(
        final Set<Attributs> attributes
        )
    {
        //
        // Step 1: create a DocumentBuilderFactory and configure it
        //
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        //
        // Optional: set various configuration options
        //
        dbf.setValidating( attributes.contains( Attributs.ENABLE_VALIDATING ) );
        dbf.setIgnoringComments( attributes.contains( Attributs.IGNORE_COMMENTS ) );
        dbf.setIgnoringElementContentWhitespace( attributes.contains( Attributs.IGNORE_WHITESPACE ) );
        dbf.setCoalescing( attributes.contains( Attributs.PUT_CDATA_INTO_TEXT ) );

        //
        // The opposite of creating entity ref nodes is expanding them inline
        //
        dbf.setExpandEntityReferences( !attributes.contains( Attributs.CREATE_ENTITY_REFERENCES ) );

        //
        // At this point the DocumentBuilderFactory instance can be saved
        // and reused to create any number of DocumentBuilder instances
        // with the same configuration options.
        //
        return dbf;
    }

    private static DocumentBuilder createDocumentBuilder(
        final Set<Attributs> attributes,
        final ErrorHandler   errorHandler
        ) throws ParserConfigurationException
    {
        final DocumentBuilderFactory dbf = createDocumentBuilderFactory( attributes );

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

