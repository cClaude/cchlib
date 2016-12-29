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
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;

/**
 **
 ** @author Claude CHOISNET
 ** @since 3.01
 */
public class XMLParserDOM2Impl implements XMLParser
{
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
    public static final Set<Attributs> DEFAULT_ATTRIBUTS = Collections.unmodifiableSet(
        EnumSet.of(
             // no_ENABLE_VALIDATING, false validation
             // no_IGNORE_COMMENTS, false ignoreComments
             Attributs.IGNORE_WHITESPACE // true
             // ignoreWhitespace
             // no_PUT_CDATA_INTO_TEXT false putCDATAIntoText
             // no_CREATE_ENTITY_REFERENCES false
             // createEntityRefs
             )
         );

    /**
     ** Seule la validation est activee.
     **
     ** @see Attributs#ENABLE_VALIDATING
     */
    public static final Set<Attributs> VALIDATE_ONLY = Collections.unmodifiableSet(
        EnumSet.of( Attributs.ENABLE_VALIDATING
             // no_IGNORE_COMMENTS,
             // no_IGNORE_WHITESPACE
             // no_PUT_CDATA_INTO_TEXT
             // no_CREATE_ENTITY_REFERENCES
             )
        );

    private DocumentBuilder documentBuilder;
    private Document        document;

    /**
     * NEEDDOC
     *
     * @param attributes NEEDDOC
     * @param errorHandler NEEDDOC
     * @throws XMLParserException NEEDDOC
     */
    protected XMLParserDOM2Impl(
        final Set<Attributs>        attributes,
        final XMLParserErrorHandler errorHandler
        ) throws  XMLParserException
    {
        try {
            this.documentBuilder = createDocumentBuilder( attributes, errorHandler.getSAXErrorHandler() );
            this.document = null;
        }
        catch( final ParserConfigurationException e ) {
            errorHandler.parserError( e );
        }
    }

    /**
     * Create a XMLParser object with theses defaults flags :
     *
     * <PRE>
     *  validation          = true
     *  ignoreWhitespace    = true
     *  ignoreComments      = false
     *  putCDATAIntoText    = true -&gt; false
     *  createEntityRefs    = false
     * </PRE>
     *
     * @param anURL NEEDDOC
     * @param attributes NEEDDOC
     * @param errorHandler NEEDDOC
     * @throws XMLParserException NEEDDOC
     */
    public XMLParserDOM2Impl(
        final URL anURL,
        final Set<Attributs> attributes,
        final XMLParserErrorHandler errorHandler
        ) throws XMLParserException
    {
        this( attributes, errorHandler );

        try {
            this.document = this.documentBuilder.parse( anURL.openStream(), anURL.toString() );
        }
        catch( final IOException e ) {
            errorHandler.ioError( e );
        }
        catch( final SAXException e ) {
            errorHandler.saxError( e );
        }
    }

    /**
     * NEEDDOC
     *
     * @param aStream NEEDDOC
     * @param attributes NEEDDOC
     * @param errorHandler NEEDDOC
     * @throws XMLParserException NEEDDOC
     */
    public XMLParserDOM2Impl(
        final InputStream aStream,
        final Set<Attributs> attributes,
        final XMLParserErrorHandler errorHandler
        ) throws XMLParserException
    {
        this( attributes, errorHandler );

        try {
            this.document = this.documentBuilder.parse( aStream );
        }
        catch( final SAXException e ) {
            errorHandler.saxError( e );
        }
        catch( final IOException e ) {
            errorHandler.ioError( e );
        }
    }

    @Override
    public Document getDocument()
    {
        return this.document;
    }

    protected void setDocument( final Document document )
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
        catch( final javax.xml.parsers.ParserConfigurationException pce ) {
            throw pce;
        }

        //
        // Set an ErrorHandler before parsing
        //
        db.setErrorHandler( errorHandler );

        return db;
    }
}

