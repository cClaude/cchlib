/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLInputText.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.030 2006.07.21 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLInputText
**
*/
package cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;

/**
** <p>
** Prise en charge du tag &lt;INPUT type="TEXT"&gt;
** </p>
**
**
**
** @author Claude CHOISNET
** @since   3.02.030
** @version 3.02.030
*/
public class HTMLInputText
    extends cx.ath.choisnet.html.HTMLInput<HTMLInputText,String>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public HTMLInputText() // -------------------------------------------------
{
 super();
}

/**
**
*/
@Override
public HTMLInputText getThis() // -----------------------------------------
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
public HTMLInputText setCurrentValue( final String value ) // -------------
{
 return setValue( value );
}

/**
**
*/
@Override
public String getType() // -----------------------------------------------
{
 return "text";
}

} // class