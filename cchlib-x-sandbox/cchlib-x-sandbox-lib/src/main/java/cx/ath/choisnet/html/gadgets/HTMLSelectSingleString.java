/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLSelectSingleValue.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.034 2006.08.02 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLSelectSingleValue
**
*/
package cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @since   3.02.034
** @version 3.02.034
*/
public class HTMLSelectSingleString
    extends HTMLSelectSingleValue<HTMLSelectSingleString,String>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public HTMLSelectSingleString() // ----------------------------------------
{
 super();
}

/**
**
*/
public HTMLSelectSingleString getThis() // --------------------------------
{
 return this;
}

/**
**
*/
public HTMLSelectSingleString setCurrentValue( final String value ) // ----
{
 return setValue( value );
}

/**
**
*/
public String getCurrentValue( final ServletRequest request ) // ----------
    throws cx.ath.choisnet.html.util.HTMLGadgetNotFoundException
{
 return getStringValue( request );
}

} // class
