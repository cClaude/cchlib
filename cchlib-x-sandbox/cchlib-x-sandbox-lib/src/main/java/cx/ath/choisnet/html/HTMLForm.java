/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLForm.java
** Description   :
**
**  1.00.___ 2000.09.29 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.document.Form
**  1.50.___ 2005.05.19 Claude CHOISNET
**                      Adaptation � l'interface HTMLWritable
**  3.02.031 2006.07.24 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.HTMLForm
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.HTMLForm
**
*/
package cx.ath.choisnet.html;

import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLNamedObject;
import cx.ath.choisnet.html.util.HTMLWritableCollection;
import cx.ath.choisnet.html.util.HTMLWritable;

/**
** <p>
** Prise en charge du tag &lt;FORM&gt;
** </p>
**
** @author Claude CHOISNET
** @version 3.02.031
*/
public class HTMLForm
    extends HTMLNamedObject<HTMLForm>
{
/** Liste des �l�ments HTML d�fini dans le formulaire */
private HTMLWritableCollection htmlCollection;

/** */
private String action;

/**
**
*/
public HTMLForm() // ------------------------------------------------------
{
 super();

 this.htmlCollection = new HTMLWritableCollection();
}

/**
**
*/
public HTMLForm getThis() // ----------------------------------------------
{
 return this;
}

/**
**
*/
public HTMLForm setAction( final String action ) // -----------------------
{
 this.action = action;

 return this;
}

/**
**
*/
public HTMLForm add( final HTMLWritable item ) // -------------------------
{
 this.htmlCollection.add( item );

 return this;
}

/**
**
*/
public HTMLForm add( final String htmlStr ) // ----------------------------
{
 this.htmlCollection.add( htmlStr );

 return this;
}

/**
**
*/
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 try {
    out.append( "<form" );

    super.writeStartTag( out );

    if( action != null ) {
        out.append( "action=\"" + action + "\"" );
        }

    out.append( ">\n" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}
/**
**
*/
public void writeBody( final HTMLDocumentWriter out ) // ------------------
    throws HTMLDocumentException
{
 this.htmlCollection.write( out );
}
/**
**
*/
public void writeEndTag( final HTMLDocumentWriter out ) // ----------------
    throws HTMLDocumentException
{
 try {
    out.append( "</form>\n" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
*/
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
 writeBody( out );
 writeEndTag( out );
}

} // class

