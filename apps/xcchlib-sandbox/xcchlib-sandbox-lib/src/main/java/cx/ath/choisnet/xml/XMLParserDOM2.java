/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/XMLParserDOM2.java
** Description   :
** Encodage      : ANSI
**
**  1.51.006 2005.06.20 Claude CHOISNET - Version initiale
**  1.54.005 2005.09.12 Claude CHOISNET
**  3.01.027 2006.04.19 Claude CHOISNET
**                      DEPRECATED!
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.XMLParserDOM2
**
*/
package cx.ath.choisnet.xml;

import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;

/**
**
** @author Claude CHOISNET
** @since   1.51.006
** @version 1.54.005
** @deprecated use {@link cx.ath.choisnet.xml.impl.XMLParserDOM2Impl}
*/
@Deprecated
public class XMLParserDOM2
    implements XMLParser
{
/** */
private final DocumentBuilder documentBuilder;

/** */
private Document document;

/**
**
*/
protected XMLParserDOM2( // -----------------------------------------------
    boolean         validation,         // true
    boolean         ignoreWhitespace,   // true
    boolean         ignoreComments,     // false
    boolean         putCDATAIntoText,   // false
    boolean         createEntityRefs,   // false
    ErrorHandler    errorHandler
    )
    throws
        java.io.IOException,
        javax.xml.parsers.ParserConfigurationException,
        org.xml.sax.SAXException
{
 this.documentBuilder
    = createDocumentBuilder(
            validation,
            ignoreWhitespace,
            ignoreComments,
            putCDATAIntoText,
            createEntityRefs,
            errorHandler
            );
 this.document = null;
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
    URL             anURL,
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
 this(  validation,
        ignoreWhitespace,
        ignoreComments,
        putCDATAIntoText,
        createEntityRefs,
        errorHandler
        );

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
    InputStream     aStream,
    boolean         validation,         // true
    boolean         ignoreWhitespace,   // true
    boolean         ignoreComments,     // false
    boolean         putCDATAIntoText,   // false
    boolean         createEntityRefs,   // false
    ErrorHandler    errorHandler
    )
    throws
        java.io.IOException,
        javax.xml.parsers.ParserConfigurationException,
        org.xml.sax.SAXException
{
 this(  validation,
        ignoreWhitespace,
        ignoreComments,
        putCDATAIntoText,
        createEntityRefs,
        errorHandler
        );

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
    boolean validation,
    boolean ignoreWhitespace,
    boolean ignoreComments,
    boolean putCDATAIntoText,
    boolean createEntityRefs
    )
{
 //
 // Step 1: create a DocumentBuilderFactory and configure it
 //
 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

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

/**
**
*/
private static DocumentBuilder createDocumentBuilder( // ------------------
    boolean         validation,
    boolean         ignoreWhitespace,
    boolean         ignoreComments,
    boolean         putCDATAIntoText,
    boolean         createEntityRefs,
    ErrorHandler    errorHandler
    )
    throws
        javax.xml.parsers.ParserConfigurationException
{
 final DocumentBuilderFactory dbf
    = createDocumentBuilderFactory(
            validation,
            ignoreWhitespace,
            ignoreComments,
            putCDATAIntoText,
            createEntityRefs
            );

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



