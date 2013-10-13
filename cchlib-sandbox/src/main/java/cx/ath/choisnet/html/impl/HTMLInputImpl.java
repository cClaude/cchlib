/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/impl/HTMLInputImpl.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.038 2006.08.08 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.impl.HTMLInputImpl
**
*/
package cx.ath.choisnet.html.impl;

import cx.ath.choisnet.html.HTMLInput;
import javax.servlet.ServletRequest;

/**
** <p>
** Implémentation minimum de {@link HTMLInput}
** </p>
**
** @author Claude CHOISNET
** @since   3.02.038
** @version 3.02.038
*/
public class HTMLInputImpl
    extends HTMLInput<HTMLInput,String>
{
/** */
private static final long serialVersionUID = 1L;

/** */
private String type;

/**
**
*/
public HTMLInput getThis() // ---------------------------------------------
{
 return this;
}

/**
**
*/
public HTMLInput setType( final String type ) // --------------------------
{
 this.type = type;

 return this;
}

/**
**
*/
public String getType() // ------------------------------------------------
{
 return this.type;
}

/**
**
*/
public HTMLInput setCurrentValue( final String value ) // -----------------
{
 setValue( value );

 return this;
}

/**
**
*/
public String getCurrentValue( // -----------------------------------------
    final ServletRequest request
    )
    throws cx.ath.choisnet.html.util.HTMLGadgetNotFoundException
{
 return getStringValue( request );
}

} // class

