/*
** $VER: HTMLDocument.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLDocument.java
** Description   :
**
**  1.00.___ 2000.10.28 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.document.HTMLDocument
**  1.50.___ 2005.05.19 Claude CHOISNET
**                      Adaptation à l'interface HTMLWritable
**  3.02.031 2006.07.24 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.HTMLDocument
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLDocument
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLDocument
**
*/
package cx.ath.choisnet.html.util;

import cx.ath.choisnet.html.HTMLBody;
import cx.ath.choisnet.html.HTMLHeader;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @version 3.02.031
**
*/
public class HTMLDocument
    implements HTMLWritable
{
/** Début du document */
private HTMLHeader header;

/** Corp du document */
private HTMLBody body;

/**
**
*/
public HTMLDocument() // --------------------------------------------------
{
 this.header    = null;
 this.body      = null;
}

/**
**
*/
public HTMLDocument setHeader( final HTMLHeader header ) // ---------------
{
 this.header = header;

 return this;
}

/**
**
*/
public HTMLHeader getHeader() // ------------------------------------------
{
 return this.header;
}

/**
**
*/
public HTMLDocument setBody( final HTMLBody body ) // ---------------------
{
 this.body = body;

 return this;
}

/**
**
*/
public HTMLBody getBody() // ----------------------------------------------
{
 return this.body;
}

/*
**
*/
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 if( this.header != null ) {
    this.header.write( out );
    }

 if( this.body != null ) {
    this.body.write( out );
    }
}

} // class

