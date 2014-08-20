/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/impl/XMLParserDOM2.java
** Description   :
** Encodage      : ANSI
**
**  3.01.027 2006.04.19 Claude CHOISNET - Version initiale
**                      Adaptation de cx.ath.choisnet.xml.XMLParserDOM2
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.impl.XMLParserDOM2
**
*/
package cx.ath.choisnet.xml.impl;

import cx.ath.choisnet.xml.XMLParser;
import java.io.InputStream;
import java.net.URL;
import java.util.EnumSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;

/**
**
** @author Claude CHOISNET
** @since   3.01.027
** @version 3.01.027
** @deprecated use {@link cx.ath.choisnet.xml.impl.XMLParserDOM2Impl}
*/
@Deprecated
public class XMLParserDOM2
    implements XMLParser
{
    /**
    **
    ** @deprecated use {@link cx.ath.choisnet.xml.impl.XMLParserDOM2Impl.Attributs}
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

    };

    /**
    ** Pas de validation, les commentaires sont gardes,
    ** les WHITESPACE sont ignores.
    **
    ** @see Attributs#IGNORE_WHITESPACE
    */
    public final static EnumSet<Attributs> DEFAULT_ATTRIBUTS
                      = EnumSet.of(
                            // no_ENABLE_VALIDATING,        false   validation
                            // no_IGNORE_COMMENTS,          false   ignoreComments
                            Attributs.IGNORE_WHITESPACE     //true  ignoreWhitespace
                            // no_PUT_CDATA_INTO_TEXT       false   putCDATAIntoText
                            // no_CREATE_ENTITY_REFERENCES  false   createEntityRefs
                            );

    /**
    ** Seule la validation est activee.
    **
    ** @see Attributs#ENABLE_VALIDATING
    */
    public final static EnumSet<Attributs> VALIDATE_ONLY
                      = EnumSet.of(
                            Attributs.ENABLE_VALIDATING
                            // no_IGNORE_COMMENTS,
                            // no_IGNORE_WHITESPACE
                            // no_PUT_CDATA_INTO_TEXT
                            // no_CREATE_ENTITY_REFERENCES
                            );

/** */
private final DocumentBuilder documentBuilder;

/** */
private Document document;

/**
**
*/
protected XMLParserDOM2( // -----------------------------------------------
    final EnumSet<Attributs>    attributes,
    final ErrorHandler          errorHandler
//    boolean         validation,         // true
//    boolean         ignoreWhitespace,   // true
//    boolean         ignoreComments,     // false
//    boolean         putCDATAIntoText,   // false
//    boolean         createEntityRefs,   // false
    )
    throws
        java.io.IOException,
        javax.xml.parsers.ParserConfigurationException,
        org.xml.sax.SAXException
{
 this.documentBuilder   = createDocumentBuilder( attributes, errorHandler );
 this.document          = null;
}


/**
**
*/
@Override
public Document getDocument() // ------------------------------------------
{
 return this.document;
}

/**
**
*/
protected void setDocument( Document document ) // ------------------------
{
 this.document = document;
}

/**
** <P>Create a XMLParser object with theses defaults flags :</P>
**
** <PRE>
**  validation          = true
**  ignoreWhitespace    = true
**  ignoreComments      = false
**  putCDATAIntoText    = true --> false
**  createEntityRefs    = false
** </PRE>
*/
public XMLParserDOM2( // --------------------------------------------------
    final URL                   anURL,
    final EnumSet<Attributs>    attributes,
    final ErrorHandler          errorHandler
//    boolean         validation,
//    boolean         ignoreWhitespace,
//    boolean         ignoreComments,
//    boolean         putCDATAIntoText,
//    boolean         createEntityRefs,
    )
    throws
        java.io.IOException,
        javax.xml.parsers.ParserConfigurationException,
        org.xml.sax.SAXException
{
 this( attributes, errorHandler );

 this.document = this.documentBuilder.parse( anURL.openStream(), anURL.toString()  );
}

/**
**
** <B>Attention ce constructeur peut avoir des soucis avec certains type d'encodage.</B>
**
** @see String#getBytes()
public XMLParserDOM2( // --------------------------------------------------
    String          xmlString,
    boolean         validation,
    boolean         ignoreWhitespace,
    boolean         ignoreComments,
    boolean         putCDATAIntoText,
    boolean         createEntityRefs,
    ErrorHandler    errorHandler
    )
    throws
        java.io.IOException,
        javax.xml.parsers.ParserConfigurationException,
        org.xml.sax.SAXException
{
 DocumentBuilder db
    = createDocumentBuilder(
            validation,
            ignoreWhitespace,
            ignoreComments,
            putCDATAIntoText,
            createEntityRefs,
            errorHandler
            );

 //
 // Attention ne gere pas correctement l'encodage !!!!
 //
 java.io.InputStream is = new java.io.ByteArrayInputStream( xmlString.getBytes() );

 this.document = db.parse( is );
}
*/

/**
**
*/
public XMLParserDOM2( // --------------------------------------------------
    final InputStream           aStream,
    final EnumSet<Attributs>    attributes,
    final ErrorHandler          errorHandler
//    boolean         validation,         // true
//    boolean         ignoreWhitespace,   // true
//    boolean         ignoreComments,     // false
//    boolean         putCDATAIntoText,   // false
//    boolean         createEntityRefs,   // false
//    ErrorHandler    errorHandler
    )
    throws
        java.io.IOException,
        javax.xml.parsers.ParserConfigurationException,
        org.xml.sax.SAXException
{
 this( attributes, errorHandler );

 this.document = this.documentBuilder.parse( aStream );
}


/**
**
public XMLParserDOM2( // --------------------------------------------------
    DocumentBuilder aDocumentBuilder
    )
//    throws
//        java.io.IOException,
//        javax.xml.parsers.ParserConfigurationException,
//        org.xml.sax.SAXException
{
 this.document = aDocumentBuilder.parse( aStream );
}
*/
/**
**
*/
private static DocumentBuilderFactory createDocumentBuilderFactory( // ----
    EnumSet<Attributs>  attributes
//    boolean validation,
//    boolean ignoreWhitespace,
//    boolean ignoreComments,
//    boolean putCDATAIntoText,
//    boolean createEntityRefs
    )
{
 //
 // Step 1: create a DocumentBuilderFactory and configure it
 //
 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

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

/**
**
*/
private static DocumentBuilder createDocumentBuilder( // ------------------
    final EnumSet<Attributs>    attributes,
    final ErrorHandler          errorHandler
//    boolean         validation,
//    boolean         ignoreWhitespace,
//    boolean         ignoreComments,
//    boolean         putCDATAIntoText,
//    boolean         createEntityRefs,
    )
    throws
        javax.xml.parsers.ParserConfigurationException
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
 catch( javax.xml.parsers.ParserConfigurationException pce ) {
    throw pce;
    }

 //
 // Set an ErrorHandler before parsing
 //
 db.setErrorHandler( errorHandler );

 return db;
}

} // class



