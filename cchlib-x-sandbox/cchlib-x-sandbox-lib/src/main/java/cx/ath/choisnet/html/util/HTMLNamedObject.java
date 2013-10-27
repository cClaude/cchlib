/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLNamedObject.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.030 2006.07.21 Claude CHOISNET - Version initial
**                      Nom: cx.ath.choisnet.html.HTMLNamedObject
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLNamedObject
**                      Implemenent: cx.ath.choisnet.html.util.HTMLTagWritable
**  3.02.038 2006.08.08 Claude CHOISNET
**                      Utilisation de getName() pour le traitement de
**                      writeStartTag() (et nom pas directement de la variable
**                      membre).
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLNamedObject
**
*/
package cx.ath.choisnet.html.util;

import cx.ath.choisnet.html.util.HTMLDocumentException;

/**
** <p>
** Prise en charge d'un object HTML acceptant l'attribut 'name'
** </p>
** <p>
** La m�thode {@link #getName()} peut �ventuellement �tre surcharg�e
** </p>
**
** @author Claude CHOISNET
** @since   3.02.030
** @version 3.02.038
*/
public abstract class HTMLNamedObject<T extends HTMLNamedObject>
    extends cx.ath.choisnet.html.util.HTMLObject<T>
{
/** */
private String name;

/**
**
*/
public HTMLNamedObject() // -----------------------------------------------
{
 this.name = null;
}

/**
**
*/
public T setName( final String name ) // ----------------------------------
{
 this.name = name;

 return getThis();
}

/**
**
*/
public String getName() // ------------------------------------------------
{
 return this.name;
}

/**
**
*/
@Override
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 final String name = getName();

 if( name != null ) {
    try {
        out.append( " name=\"" );
        out.append( name );
        out.append( "\"" );
        }
    catch( java.io.IOException e ) {
        throw new HTMLDocumentException( e );
        }
    }

 super.writeStartTag( out );
}

} // class
