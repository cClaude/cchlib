/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLSelectIndex.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.034 2006.08.02 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLSelectIndex
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
public class HTMLSelectIndex
    extends HTMLSelectSingleValue<HTMLSelectIndex,Integer>
//    extends HTMLSelect<HTMLSelectIndex,Integer>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public HTMLSelectIndex() // -----------------------------------------------
{
 super();
}

/**
**
*/
@Override
public HTMLSelectIndex getThis() // ---------------------------------------
{
 return this;
}

/**
**
*/
@Override
public Integer getCurrentValue( final ServletRequest request ) // ---------
    throws cx.ath.choisnet.html.util.HTMLGadgetNotFoundException
{
 return Integer.valueOf( getStringValue( request ) );
}

/**
**
*/
@Override
public HTMLSelectIndex setCurrentValue( final Integer value ) // ----------
{
 return setValue( value.toString() );
}

/**
**
public HTMLSelectIndex setValue( final String value ) // ------------------
{
 boolean setDone = false;

System.out.println( "setValue : " + value );

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

System.out.println( "setValue ? " + setDone );

 return this;
}
*/


/**
**
public HTMLSelectSingleValue append( // -----------------------------------
    final Collection<FormItem> collection
    )
{
 final String value = getFirstSelectedValue();

 if( value != null ) {
    collection.add(
        new cx.ath.choisnet.html.util.impl.FormItemImpl( getName(), value )
        );
    }

 return this;
}
*/

} // class

