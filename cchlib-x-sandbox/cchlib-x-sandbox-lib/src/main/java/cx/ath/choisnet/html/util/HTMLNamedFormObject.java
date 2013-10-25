/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLNamedFormObject.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.034 2006.08.02 Claude CHOISNET - Version initial
**                      Nom: cx.ath.choisnet.html.HTMLNamedFormObject
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLNamedFormObject
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLNamedFormObject
**
*/
package cx.ath.choisnet.html.util;

import javax.servlet.ServletRequest;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @since   3.02.034
** @version 3.02.037
*/
public  abstract class HTMLNamedFormObject<T extends HTMLNamedFormObject,U>
    extends HTMLNamedObject<T>
        implements HTMLFormEntry<T,U>
{

/**
**
*/
public HTMLNamedFormObject() // -------------------------------------------
{
 super();
}

/**
**
*/
public String[] getStringValues( final ServletRequest request ) // --------
    throws HTMLGadgetNotFoundException
{
 final String[] values = request.getParameterValues( getName() );

 if( values == null ) {
    throw new HTMLGadgetNotFoundException( getName() );
    }
 else {
    return values;
    }
}

/**
**
*/
public String getStringValue( final ServletRequest request ) // -----------
    throws HTMLGadgetNotFoundException
{
 return getStringValues( request )[ 0 ];
}

} // class
