/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/XMLParserException.java
** Description   :
** Encodage      : ANSI
**
**  1.51.006 2005.06.20 Claude CHOISNET - Version initiale
**
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.XMLParserException
**
*/
package cx.ath.choisnet.xml;

import org.w3c.dom.Document;

/**
**
** @author Claude CHOISNET
** @since   1.51.006
** @version 1.51.006
*/
public class XMLParserException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public XMLParserException( // ---------------------------------------------
    String  message
    )
{
 super( message );
}

/**
**
*/
public XMLParserException( // ---------------------------------------------
    String      message,
    Throwable   cause
    )
{
 super( message, cause );
}

/**
**
*/
public XMLParserException( // ---------------------------------------------
    Throwable   cause
    )
{
 super( cause );
}


} // interface
