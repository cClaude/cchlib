/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLBody.java
** Description   :
**
**  1.00.___ 2000.10.28 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.document.Body
**  1.50.___ 2005.05.19 Claude CHOISNET
**                      Adaptation � l'interface HTMLWritable
**  3.02.031 2006.07.24 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.HTMLBody
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Implemenent: cx.ath.choisnet.html.util.HTMLTagWritable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.HTMLBody
**
*/
package cx.ath.choisnet.html;

import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import cx.ath.choisnet.html.util.HTMLWritable;
import cx.ath.choisnet.html.util.HTMLWritableCollection;

/**
** <p>
** Prise en charge du tag &lt;BODY&gt;
** </p>
**
** @author Claude CHOISNET
** @version 3.02.037
*/
public class HTMLBody
    extends cx.ath.choisnet.html.util.HTMLObject<HTMLBody>
{
/** */
private HTMLWritableCollection htmlCollection;

/**
**
*/
public HTMLBody() // ------------------------------------------------------
{
 this.htmlCollection = new HTMLWritableCollection();
}

/**
**
*/
@Override
public HTMLBody getThis() // ----------------------------------------------
{
 return this;
}

/**
**
*/
public HTMLBody add( final HTMLWritable entry ) // ------------------------
{
 this.htmlCollection.add( entry );

 return this;
}


/**
**
*/
public HTMLBody add( final String htmlStr ) // ----------------------------
{
 this.htmlCollection.add( htmlStr );

 return this;
}

/**
**
*/
@Override
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 try {
    out.append( "<body" );

    super.writeStartTag( out );

    out.append( ">\n" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
*/
@Override
public void writeBody( final HTMLDocumentWriter out ) // ------------------
    throws HTMLDocumentException
{
 this.htmlCollection.write( out );
}

/**
**
*/
@Override
public void writeEndTag( final HTMLDocumentWriter out ) // ----------------
    throws HTMLDocumentException
{
 try {
    out.append( "</body>\n" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
*/
@Override
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
 writeBody( out );
 writeEndTag( out );
}

} // class
