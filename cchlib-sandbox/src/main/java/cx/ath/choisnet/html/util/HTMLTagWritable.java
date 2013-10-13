/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLTagWritable.java
** Description   :
** Encodage      : ANSI
**
**  3.02.037 2006.08.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLTagWritable
*/
package cx.ath.choisnet.html.util;

/**
** <p>
**
** </p>
**
**
** @author Claude CHOISNET
** @since   3.02.037
** @version 3.02.037
*/
public interface HTMLTagWritable
{

/**
**
*/
public void writeStartTag( HTMLDocumentWriter out ) // --------------------
    throws HTMLDocumentException;

/**
**
*/
public void writeBody( HTMLDocumentWriter out ) // ------------------------
    throws HTMLDocumentException,IllegalStateException;

/**
**
*/
public void writeEndTag( HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException,IllegalStateException;

} // class
