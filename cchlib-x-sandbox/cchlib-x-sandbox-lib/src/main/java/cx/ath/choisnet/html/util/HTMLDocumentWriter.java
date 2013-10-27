/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLDocumentWriter.java
** Description   :
** Encodage      : ANSI
**
**  1.50.___ 2005.05.19 Claude CHOISNET - Version initiale
**  3.02.031 2006.07.24 Claude CHOISNET
**                      Suppression de: write(String)
**                      Etends l'interface: Appendable
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLDocumentWriter
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLDocumentWriter
*/
package cx.ath.choisnet.html.util;

import java.util.Locale;

/**
** <p>
**
** </p>
**
**
** @author Claude CHOISNET
** @since   1.50
** @version 3.02.037
*/
public interface HTMLDocumentWriter
    extends Appendable
{
/**
**
**
** @return un object Locale correspondant é la localisation en cours,
**         utilisé pour l'encodage de certain gadgets, en particulier pour
**         les dates et les horaires.
**
** @throws UnsupportedOperationException if the getLocale operation is not
**         supported by this HTMLDocumentWriter.
*/
public Locale getLocale() // ----------------------------------------------
    throws UnsupportedOperationException;

/**
**
*/
public String formatTag( String tag ); // ---------------------------------

/**
**
*/
public String formatAttribute( String attribute ); // ---------------------

/**
**
*/
public HTMLDocumentStringWriter getHTMLDocumentStringWriter(); // ---------

} // class


/**
**
public void append( String htmlContent ) // -------------------------------
    throws HTMLDocumentException;
*/

/**
**
public void write( String htmlContent ) // --------------------------------
    throws HTMLDocumentException;
*/

