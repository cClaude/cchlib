/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLSelectSingleValue.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.034 2006.08.02 Claude CHOISNET - Version initial
**  3.02.038 2006.08.08 Claude CHOISNET
**                      Ajout de getValue()
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLSelectSingleValue
**
*/
package cx.ath.choisnet.html.gadgets;

import cx.ath.choisnet.html.util.FormItem;
import java.util.Collection;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @since   3.02.034
** @version 3.02.034
*/
public abstract class HTMLSelectSingleValue<T extends HTMLSelectSingleValue,U>
    extends HTMLSelect<T,U>
{
/**
**
*/
public HTMLSelectSingleValue() // -----------------------------------------
{
 super();
}

/**
**
*/
public T setValue( final String value ) // --------------------------------
{
 boolean setDone = false;

 for( HTMLOption option : getHTMLOptions() ) {

    if( option.isSelected() ) {
         option.setSelected( false );
         }

    if( ! setDone ) {
        if( value.equals( option.getValue() ) ) {
            option.setSelected( true );
            setDone = true;
            }
        }

     }

 return getThis();
}

/**
**
*/
public String getValue() // -----------------------------------------------
{
 return getFirstSelectedValue();
}

/**
**
*/
public T append( // -------------------------------------------------------
    final Collection<FormItem> collection
    )
{
 final String value = getFirstSelectedValue();

 if( value != null ) {
    collection.add(
        new cx.ath.choisnet.html.util.impl.FormItemImpl( getName(), value )
        );
    }

 return getThis();
}

} // class

