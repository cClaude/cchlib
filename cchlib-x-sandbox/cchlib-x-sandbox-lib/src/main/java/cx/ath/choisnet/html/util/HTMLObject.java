/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLObject.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.030 2006.07.21 Claude CHOISNET - Version initial
**                      Nom: cx.ath.choisnet.html.HTMLObject
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLObject
**                      Implemenent: cx.ath.choisnet.html.util.HTMLTagWritable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLObject
**
*/
package cx.ath.choisnet.html.util;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @since   3.02.030
** @version 3.02.037
*/
public abstract class HTMLObject<T extends HTMLObjectInterface>
    implements
        HTMLWritable,
        HTMLTagWritable,
        HTMLObjectInterface<T>
{
/** */
public final static String EMPTY = "";

/** */
private String cssClass;

/** */
private String id;

/**
**
*/
public HTMLObject() // ----------------------------------------------------
{
 this.cssClass  = null;
 this.id        = null;
}

/**
**
*/
public abstract T getThis(); // -------------------------------------------

/**
**
*/
final
public T setCSSClass( final String cssClass ) // --------------------------
{
 this.cssClass = cssClass;

 return getThis();
}

/**
**
*/
final
public String getCSSClass() // --------------------------------------------
{
 return this.cssClass;
}

/**
**
*/
final
public T setId( final String id ) // --------------------------------------
{
 this.id = id;

 return getThis();
}

/**
**
*/
final
public String getId() // --------------------------------------------------
{
 return this.id;
}

/**
**
*/
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 try {
    if( this.cssClass != null ) {
        out.append( " class=\"" + this.cssClass + "\"" );
        }

    if( this.id != null ) {
        out.append( " id=\"" + this.id + "\"" );
        }

// if( javascript != null ) {
//    sb.append( " " + javascript );
//    }
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
}
*/

/**
**
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
 writeBody( out );
 writeEndTag( out );
}
*/

/**
**
*/
final
public String toString() // -----------------------------------------------
{
 final cx.ath.choisnet.html.util.impl.HTMLDocumentStringWriterImpl out
 = new cx.ath.choisnet.html.util.impl.HTMLDocumentStringWriterImpl();

 try {
    write( out );
    }
 catch( HTMLDocumentException e ) {
    throw new RuntimeException( e );
    }

 return out.toString();
}

} // class
