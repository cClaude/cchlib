/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/XMLDOMTools.java
** Description   :
** Encodage      : ANSI
**
**  1.51.007 2005.08.18 Claude CHOISNET - Version initiale
**
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.XMLDOMTools
**
*/
package cx.ath.choisnet.xml;

import java.io.OutputStream;
import java.io.Writer;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/**
**
**
** @author Claude CHOISNET
** @since   1.51.007
** @version 1.51.007
*/
public class XMLDOMTools
{

/**
** This method writes a DOM document to a stream
*/
public static void writeXML( Node node, OutputStream output ) // ----------
    throws
        javax.xml.transform.TransformerConfigurationException,
        javax.xml.transform.TransformerException
{
 // Prepare the DOM document for writing
 Source source = new DOMSource( node );

 // Prepare the output file
 Result result = new StreamResult( output );

 // Write the DOM document to the file
 Transformer xformer = TransformerFactory.newInstance().newTransformer();

 xformer.transform( source, result );
}

/**
** This method writes a DOM document to a stream
*/
public static void writeXML( Node node, Writer output ) // ----------------
    throws
        javax.xml.transform.TransformerConfigurationException,
        javax.xml.transform.TransformerException
{
 // Prepare the DOM document for writing
 Source source = new DOMSource( node );

 // Prepare the output file
 Result result = new StreamResult( output );

 // Write the DOM document to the file
 Transformer xformer = TransformerFactory.newInstance().newTransformer();

 xformer.transform( source, result );
}

/**
** This method writes a DOM document to a String
*/
public static String toString( Node node ) // -----------------------------
{
 try {
    //java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
    java.io.StringWriter buffer = new java.io.StringWriter();

    writeXML( node, buffer );

    return buffer.toString();
    }
 catch( javax.xml.transform.TransformerConfigurationException e ) {
    return "TransformerConfigurationException: " + e.getMessage();
    }
 catch( javax.xml.transform.TransformerException e ) {
    return "TransformerException: " + e.getMessage();
    }
}

} // interface
