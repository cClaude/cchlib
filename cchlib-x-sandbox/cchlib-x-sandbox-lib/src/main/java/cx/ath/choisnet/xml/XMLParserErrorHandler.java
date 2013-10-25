/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/XMLParserErrorHandler.java
** Description   :
** Encodage      : ANSI
**
**  1.51.005 2004.12.09 Claude CHOISNET - Version initiale
**  2.01.020 2005.10.19 Claude CHOISNET
**                      Reprise du formatage du message d'erreur.
**                      Le constructeur XMLParserErrorHandler( PrintStream )
**                      est deprecated.
**  3.02.002 2006.05.30 Claude CHOISNET
**                      Ajout des m�thodes:
**                          ioError(IOException)
**                          parserError(ParserConfigurationException)
**  3.02.043 2007.01.09 Claude CHOISNET
**                      Modification de getSAXErrorHandler() qui retournait
**                      toujours null, retourne maintenant l'objet
**                      ErrorHandler interne
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.XMLParserErrorHandler
**
*/
package cx.ath.choisnet.xml;

import cx.ath.choisnet.xml.impl.SAXErrorHandlerImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
** Error handler to report errors and warnings
**
**
** @author Claude CHOISNET
** @since   1.51.005
** @version 3.02.043
*/
public class XMLParserErrorHandler
{

/** */
private final ErrorHandler saxErrorHandler;

/**
**
*/
public XMLParserErrorHandler( final ErrorHandler saxErrorHandler ) // -----
{
 this.saxErrorHandler = saxErrorHandler;
}

/**
**
*/
public XMLParserErrorHandler( final PrintWriter out ) // ------------------
{
 this( new SAXErrorHandlerImpl( out ) );
}

/**
**
*/
public ErrorHandler getSAXErrorHandler() // -------------------------------
{
 // return null;
 return this.saxErrorHandler;
}

/**
** <p>The following method is convenient methods to deal with {@link java.io.IOException}.</p>
**
** @since 3.02.002
*/
public void ioError( final IOException ioe ) // ---------------------------
    throws XMLParserException
{
 final String message = "$IOError: " + ioe.getMessage();

 throw new XMLParserException( message );
}

/**
** <p>The following method is convenient methods to deal with {@link ParserConfigurationException}.</p>
**
** @since 3.02.002
*/
public void parserError( final ParserConfigurationException pce ) // ------
    throws XMLParserException
{
 final String message = "$Fatal Error: " + pce.getMessage();

 throw new XMLParserException( message );
}

/**
** <p>The following method is convenient methods to deal with {@link SAXException}.</p>
**
** @since 3.02.002
*/
public void saxError( final SAXException saxe ) // ------------------------
    throws XMLParserException
{
 throw new XMLParserException( saxe );
}

} // class

/**
** @deprecated use XMLParserErrorHandler( PrintWriter ) instead
@Deprecated
public XMLParserErrorHandler( PrintStream outStream ) // ------------------
{
 OutputStreamWriter errorWriter = new OutputStreamWriter( outStream );

 this.output = new PrintWriter( errorWriter, true );
}
*/
