/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLDocumentStringWriter.java
** Description   :
** Encodage      : ANSI
**
**  3.02.035 2006.08.03 Claude CHOISNET - Version initiale
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLDocumentStringWriter
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLDocumentStringWriter
*/
package cx.ath.choisnet.html.util;

/**
** <p>
**
** </p>
**
**
** @author Claude CHOISNET
** @since   3.02.035
** @version 3.02.037
*/
public interface HTMLDocumentStringWriter
    extends HTMLDocumentWriter
{

/**
** Return internal buffer as String.
*/
public String toString(); // ----------------------------------------------

/**
** Clean up internal buffer, and return previous value
*/
public String reset(); // -------------------------------------------------

} // class

