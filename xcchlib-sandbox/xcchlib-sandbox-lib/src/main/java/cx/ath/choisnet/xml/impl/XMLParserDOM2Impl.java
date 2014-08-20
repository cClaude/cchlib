/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/impl/XMLParserDOM2Impl.java
** Description   :
** Encodage      : ANSI
**
**  3.01.027 2006.04.19 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.xml.impl.XMLParserDOM2
**                      Adaptation de cx.ath.choisnet.xml.XMLParserDOM2
**  3.02.002 2006.05.30 Claude CHOISNET - Version initiale
**                      Nouveau nom: cx.ath.choisnet.xml.impl.XMLParserDOM2Impl
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.impl.XMLParserDOM2Impl
**
*/
package cx.ath.choisnet.xml.impl;

import cx.ath.choisnet.xml.XMLParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
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
** @version 3.02.002
*/
public class XMLParserDOM2Impl
    implements XMLParser
{
    /**
    **
    */
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
private /*final*/ DocumentBuilder documentBuilder;

/** */
private Document document;

/**
**
*/
protected XMLParserDOM2Impl( // -------------------------------------------
    final EnumSet<Attributs>    attributes,
    final XMLParserErrorHandler errorHandler
    )
    throws cx.ath.choisnet.xml.XMLParserException
{
 try {
    this.documentBuilder = createDocumentBuilder(
                                    attributes,
                                    errorHandler.getSAXErrorHandler()
                                    );
    this.document = null;
    }
 catch( javax.xml.parsers.ParserConfigurationException e ) {
    errorHandler.parserError( e );
    }
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
public XMLParserDOM2Impl( // ----------------------------------------------
    final URL                   anURL,
    final EnumSet<Attributs>    attributes,
    final XMLParserErrorHandler errorHandler
    )
    throws cx.ath.choisnet.xml.XMLParserException
{
 this( attributes, errorHandler );

 try {
    this.document = this.documentBuilder.parse( anURL.openStream(), anURL.toString()  );
    }
 catch( java.io.IOException e ) {
    errorHandler.ioError( e );
    }
 catch( org.xml.sax.SAXException e ) {
    errorHandler.saxError( e );
    }
}

/**
**
*/
public XMLParserDOM2Impl( // ----------------------------------------------
    final InputStream           aStream,
    final EnumSet<Attributs>    attributes,
    final XMLParserErrorHandler errorHandler
    )
    throws cx.ath.choisnet.xml.XMLParserException
{
 this( attributes, errorHandler );

 try {
    this.document = this.documentBuilder.parse( aStream );
    }
 catch( org.xml.sax.SAXException e ) {
    errorHandler.saxError( e );
    }
 catch( java.io.IOException e ) {
    errorHandler.ioError( e );
    }
}

/**
**
*/
private static DocumentBuilderFactory createDocumentBuilderFactory( // ----
    EnumSet<Attributs>  attributes
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



