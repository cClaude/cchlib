/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLString.java
** Description   :
**
**  1.50.___ 2005.05.19 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.HTMLString
**  3.02.030 2006.07.21 Claude CHOISNET
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLString
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLString
**
*/
package cx.ath.choisnet.html.util;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @version 3.02.037
*/
public class HTMLString
    implements HTMLWritable
{
/** */
private StringBuilder sb;

/**
**
*/
public HTMLString() // ----------------------------------------------------
{
 this.sb = new StringBuilder();
}

/**
**
*/
public HTMLString( final String str ) // ----------------------------------
{
 this.sb = new StringBuilder( str );
}

/**
**
*/
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 try {
    out.append( this.sb.toString() );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

} // class
