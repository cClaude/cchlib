/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLLink.java
** Description   :
**
**  3.02.032 2006.07.21 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.HTMLLink
**
*/
package cx.ath.choisnet.html;

import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLWritable;

/**
** <p>
** Prise en charge du tag &lt;LINK&gt;
** </p>
**
** @author Claude CHOISNET
** @version 3.02.032
*/
public class HTMLLink
    implements HTMLWritable
{
/** */
public final static String REL_CSS = "stylesheet";

/** */
public final static String TYPE_CSS = "text/css";

/** */
private String rel;

/** */
private String type;

/** */
private String href;

/** */
private String media;


/**
**
*/
public HTMLLink() // ------------------------------------------------------
{
 this.rel   = null;
 this.type  = null;
 this.href  = null;
 this.media = null;
}

/**
**
*/
public HTMLLink setRel( final String rel ) // -----------------------------
{
 this.rel = rel;

 return this;
}

/**
**
*/
public HTMLLink setType( final String type ) // ---------------------------
{
 this.type = type;

 return this;
}

/**
**
*/
public HTMLLink setHRef( final String href ) // ---------------------------
{
 this.href = href;

 return this;
}

/**
**
*/
public HTMLLink setMedia( final String media ) // -------------------------
{
 this.media = media;

 return this;
}

/**
**
*/
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 try {
    out.append( "<link" );

    if( this.rel != null ) {
        out.append( " rel=\"" + this.rel + "\"" );
        }

    if( this.type != null ) {
        out.append( " type=\"" + this.type + "\"" );
        }

    if( this.media != null ) {
        out.append( " media=\"" + this.media + "\"" );
        }

    if( this.href != null ) {
        out.append( " href=\"" + this.href + "\"" );
        }

    out.append( "/>\n" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

} // class
