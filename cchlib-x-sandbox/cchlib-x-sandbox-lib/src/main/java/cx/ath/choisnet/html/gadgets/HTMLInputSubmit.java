/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLInputSubmit.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.038 2006.08.08 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLInputSubmit
**
*/
package cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;

/**
** <p>
** Prise en charge du tag &lt;INPUT type="SUBMIT"&gt;
** </p>
**
**
**
** @author Claude CHOISNET
** @since   3.02.038
** @version 3.02.038
*/
public class HTMLInputSubmit
    extends cx.ath.choisnet.html.HTMLInput<HTMLInputSubmit,String>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public HTMLInputSubmit() // -----------------------------------------------
{
 super();
}

/**
**
*/
@Override
public HTMLInputSubmit getThis() // ---------------------------------------
{
 return this;
}

/**
**
*/
@Override
public String getCurrentValue( final ServletRequest request ) // ----------
    throws cx.ath.choisnet.html.util.HTMLGadgetNotFoundException
{
 return getStringValue( request );
}

/**
**
*/
@Override
public HTMLInputSubmit setCurrentValue( final String value ) // -----------
{
 return setValue( value );
}

/**
**
*/
@Override
public String getType() // ------------------------------------------------
{
 return "submit";
}

} // class