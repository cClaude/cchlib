/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLWritable.java
** Description   :
** Encodage      : ANSI
**
**  1.50.___ 2005.05.19 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.HTMLWritable
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLWritable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLWritable
*/
package cx.ath.choisnet.html.util;

/**
** <p>
**
** </p>
**
**
** @author Claude CHOISNET
** @version 1.50
*/
public interface HTMLWritable
{

/**
** Fabrication de l'objet en langage HTML.
*/
public void write( HTMLDocumentWriter out ) // ----------------------------
    throws HTMLDocumentException;

//public void writeHTML( HTMLDocumentWriter out ) // ------------------------

} // class
