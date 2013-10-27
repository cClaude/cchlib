/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLInputCheckBox.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.036 2006.08.04 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLInputCheckBox
**
*/
package cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;

/**
** <p>
** Prise en charge du tag &lt;INPUT type="CHECKBOX"&gt;
** </p>
**
**
**
** @author Claude CHOISNET
** @since   3.02.036
** @version 3.02.036
*/
public class HTMLInputCheckBox
    extends cx.ath.choisnet.html.HTMLInput<HTMLInputCheckBox,Boolean>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public HTMLInputCheckBox() // ---------------------------------------------
{
 super();
}

/**
**
*/
@Override
public HTMLInputCheckBox getThis() // -------------------------------------
{
 return this;
}

/**
**
*/
@Override
public String getType() // ------------------------------------------------
{
 return "checkbox";
}

/**
**
*/
@Override
public HTMLInputCheckBox setCurrentValue( final Boolean value ) // --------
{
 return setChecked( booleanValue( value ) );
}

/**
**
*/
public HTMLInputCheckBox setChecked() // ----------------------------------
{
 return setChecked( true );
}

/**
**
*/
@Override
public Boolean getCurrentValue( final ServletRequest request ) // ---------
    throws cx.ath.choisnet.html.util.HTMLGadgetNotFoundException
{
 if( booleanValue( getStringValue( request ) ) ) {
    return Boolean.TRUE;
    }
 else {
    return Boolean.FALSE;
    }
}

/**
**
*/
public final static boolean booleanValue( final Boolean value ) // --------
{
 if( value == null ) {
    return false;
    }
 else {
    return value.booleanValue();
    }
}

/**
**
*/
public final static boolean booleanValue( final String value ) // ---------
{
 if( value == null ) {
    return false;
    }
 else {
    return value.toLowerCase().matches( "on|true|yes|1" );
    }
}

} // class



/**
**
public HTMLInputCheckbox setValue( final String value ) // ----------------
{
 this.value = value;

 return getThis();
}
*/

/**
**
public String getValue() // -----------------------------------------------
{
 return this.value;
}
*/

/**
**
public HTMLInputText setSize( final int size ) // -------------------------
{
 this.size = new Integer( size );

 return getThis();
}
*/

/**
**
public int getSize() // ---------------------------------------------------
{
 return this.size.intValue();
}
*/

/**
**
public HTMLInputCheckbox setChecked( final boolean checked ) // -----------
{
 this.checked = checked;

 return getThis();
}
*/

/**
**
public boolean getChecked() // --------------------------------------------
{
 return this.checked;
}
*/

/**
**
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws cx.ath.choisnet.html.HTMLDocumentException
{
 try {
    out.append( "<input type=\"checkbox\"" );

    super.write( out );

    if( checked ) {
        out.append( " checked" );
        }

    if( value != null ) {
        out.append( " value=\"" + value + "\"" );
        }

    out.append( "/>\n" );
    }
 catch( java.io.IOException e ) {
    throw new cx.ath.choisnet.html.HTMLDocumentException( e );
    }
}
*/

/**
**
public HTMLInputCheckbox append( // ---------------------------------------
    final Collection<FormItem> collection
    )
{
 collection.add(
    new cx.ath.choisnet.html.util.impl.FormItemImpl( getName(), getValue() )
    );

 return this;
}
*/
