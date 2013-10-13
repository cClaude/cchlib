/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLInput.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.036 2006.08.04 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.HTMLInput
**
*/
package cx.ath.choisnet.html;

import cx.ath.choisnet.html.javascript.JavaScript;
import cx.ath.choisnet.html.util.FormItem;
import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import java.util.Collection;
import javax.servlet.ServletRequest;

/**
** <p>
** Prise en charge du tag &lt;INPUT&gt;
** </p>
**
**
**
** @author Claude CHOISNET
** @since   3.02.036
** @version 3.02.036
*/
public abstract class HTMLInput<T extends HTMLInput,U>
    extends cx.ath.choisnet.html.util.HTMLNamedFormObject<T,U>
{
/** */
private String value;

/** */
private Boolean checked;

/** */
private Boolean disabled;

/** */
private Boolean readOnly;

/** */
private Integer size;

/** */
private Integer maxLength;

/** */
private String src;

/** */
private String alt;

/** */
private Boolean isMap;

/** */
private Integer tabIndex;

/** */
private Character accessKey;

/** */
private JavaScript onFocus;

/** */
private JavaScript onBlur;

/** */
private JavaScript onSelect;

/** */
private JavaScript onChange;

/** */
private String accept; // list of MIME types for file upload

/**
**
*/
public HTMLInput() // -----------------------------------------------------
{
 super();

 this.value     = null;
 this.checked   = null;
 this.disabled  = null;
 this.readOnly  = null;
 this.size      = null;
 this.maxLength = null;
 this.src       = null;
 this.alt       = null;
 this.isMap     = null; // http://www.w3.org/TR/html4/struct/objects.html#server-side
 this.tabIndex  = null;
 this.accessKey = null;
// this.onFocus     = null;
// this.onBlur      = null;
// this.onSelect    = null;
// this.onChange    = null;
 this.accept    = null;
}

/**
**
*/
public HTMLInput( final HTMLInput src ) // --------------------------------
{
 super();

 this.value     = src.getValue();
 this.checked   = new Boolean( src.isChecked() );
 this.disabled  = new Boolean( src.isDisabled() );
 this.readOnly  = new Boolean( src.isReadOnly() );
 this.size      = src.getSize();
 this.maxLength = src.getMaxLength();
 this.src       = src.getSrc();
 this.alt       = src.getAlt();
 this.isMap     = new Boolean( src.isIsMap() );
 this.tabIndex  = new Integer( src.getTabIndex() );
 this.accessKey = new Character( src.getAccessKey() );
// this.onFocus     = null;
// this.onBlur      = null;
// this.onSelect    = null;
// this.onChange    = null;
 this.accept    = src.getAccept();
}

/**
**
public T getThis() // -----------------------------------------------------
{
 throw new UnsupportedOperationException();
}
*/

/**
**
public T setCurrentValue( final U value ) // ------------------------------
{
 throw new UnsupportedOperationException();
}
*/

/**
**
public U getCurrentValue( final ServletRequest request ) // ---------------
    throws cx.ath.choisnet.html.HTMLGadgetNotFoundException
{
 throw new UnsupportedOperationException();
}
*/

/**
**
*/
public T append( final Collection<FormItem> collection ) // ---------------
{
 collection.add(
    new cx.ath.choisnet.html.util.impl.FormItemImpl( getName(), getValue() )
    );

 return getThis();
}

/**
**
*/
//public T setType( final String type ) // ----------------------------------
//{
// this.type = type;
//
// return getThis();
//}

/**
**
*/
public abstract String getType(); // --------------------------------------
//{
// return this.type;
//}

/**
**
*/
public T setValue( final String value ) // --------------------------------
{
 this.value = value;

 return getThis();
}

/**
**
*/
public String getValue() // -----------------------------------------------
{
 return this.value;
}

/**
**
*/
public T setChecked( final boolean checked ) // ---------------------------
{
 this.checked = checked ? Boolean.TRUE : null;

 return getThis();
}

/**
**
*/
public boolean isChecked() // ---------------------------------------------
{
 return this.checked == null ? false : this.checked.booleanValue();
}

/**
**
*/
public T setDisabled( final boolean disabled ) // -------------------------
{
 this.disabled = disabled ? Boolean.TRUE : null;

 return getThis();
}

/**
**
*/
public boolean isDisabled() // --------------------------------------------
{
 return this.disabled == null ? false : this.disabled.booleanValue();
}

/**
**
*/
public T setReadOnly( final boolean readOnly ) // -------------------------
{
 this.readOnly = readOnly ? Boolean.TRUE : null;

 return getThis();
}

/**
**
*/
public boolean isReadOnly() // --------------------------------------------
{
 return this.readOnly == null ? false : this.readOnly.booleanValue();
}

/**
**
*/
public T setSize( final int size ) // -------------------------------------
{
 this.size = new Integer( size );

 return getThis();
}

/**
**
**
** @throws java.util.NoSuchElementException() si la valeur n'a pas été définie
*/
public int getSize() // ---------------------------------------------------
{
 try {
    return this.size.intValue();
    }
 catch( NullPointerException e ) {
    throw new java.util.NoSuchElementException();
    }
}

/**
**
*/
public T setMaxLength( final int maxLength ) // ---------------------------
{
 this.maxLength = new Integer( maxLength );

 return getThis();
}

/**
**
**
**
** @throws java.util.NoSuchElementException() si la valeur n'a pas été définie
*/
public int getMaxLength() // ----------------------------------------------
{
 try {
    return this.maxLength.intValue();
    }
 catch( NullPointerException e ) {
    throw new java.util.NoSuchElementException();
    }
}

/**
**
*/
public T setSrc( final String src ) // ------------------------------------
{
 this.src = src;

 return getThis();
}

/**
**
*/
public String getSrc() // -------------------------------------------------
{
 return this.src;
}

/**
**
*/
public T setAlt( final String alt ) // ------------------------------------
{
 this.alt = alt;

 return getThis();
}

/**
**
*/
public String getAlt() // -------------------------------------------------
{
 return this.alt;
}

/**
**
*/
public T setIsMap( final boolean isMap ) // -------------------------------
{
 this.isMap = isMap ? Boolean.TRUE : null;

 return getThis();
}

/**
**
*/
public boolean isIsMap() // -----------------------------------------------
{
 return this.isMap == null ? false : this.isMap.booleanValue();
}


/**
**
*/
public T setTabIndex( final int tabIndex ) // -----------------------------
{
 this.tabIndex = tabIndex;

 return getThis();
}

/**
**
**
**
** @throws java.util.NoSuchElementException() si la valeur n'a pas été définie
*/
public int getTabIndex() // -----------------------------------------------
{
 try {
    return this.tabIndex.intValue();
    }
 catch( NullPointerException e ) {
    throw new java.util.NoSuchElementException();
    }
}

/**
**
*/
public T setAccessKey( final char accessKey ) // --------------------------
{
 this.accessKey = new Character( accessKey );

 return getThis();
}

/**
**
**
**
** @throws java.util.NoSuchElementException() si la valeur n'a pas été définie
*/
public char getAccessKey() // ---------------------------------------------
{
 try {
    return this.accessKey.charValue();
    }
 catch( NullPointerException e ) {
    throw new java.util.NoSuchElementException();
    }
}

//////////////////////
// private JavaScript onFocus;
// private JavaScript onBlur;
// private JavaScript onSelect;
// private JavaScript onChange;
//////////////////////

/**
**
*/
public T setAccept( final String accept ) // ------------------------------
{
 this.accept = accept;

 return getThis();
}

/**
**
*/
public String getAccept() // ----------------------------------------------
{
 return this.accept;
}

/**
**
*/
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 try {
    out.append( "<input" );

    super.writeStartTag( out );

    out.append( " type=\"" + getType() + "\"" );

    if( value != null ) {
        out.append( " value=\"" + value + "\"" );
        }

    if( isChecked() ) {
        out.append( " checked" );
        }

    if( isDisabled() ) {
        out.append( " disabled" );
        }

    if( isReadOnly() ) {
        out.append( " readonly" );
        }

    if( this.size != null ) {
        out.append( " size=\"" + this.size + "\"" );
        }

    if( this.maxLength != null ) {
        out.append( " maxlength=\"" + this.maxLength + "\"" );
        }

    if( isIsMap() ) {
        out.append( " ismap" );
        }

    if( this.tabIndex != null ) {
        out.append( " tabindex=\"" + this.tabIndex + "\"" );
        }

    if( this.accessKey != null ) {
        out.append( " accesskey=\"" + this.accessKey + "\"" );
        }

    if( this.accept != null ) {
        out.append( " accept=\"" + this.accept + "\"" );
        }

    out.append( " />\n" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
*/
public void writeBody( final HTMLDocumentWriter out ) // ------------------
    throws HTMLDocumentException
{
 throw new IllegalStateException();
}

/**
**
*/
public void writeEndTag( final HTMLDocumentWriter out ) // ----------------
    throws HTMLDocumentException
{
 throw new IllegalStateException();
}

/**
**
*/
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
}


} // class

/**
**
** java.io.Serializable
private void writeObject( final java.io.ObjectOutputStream stream ) // ----
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeBoolean( this.selected  );
 stream.writeBoolean( this.disabled  );
}
*/

/**
** java.io.Serializable
private void readObject( final java.io.ObjectInputStream stream ) // ------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.selected = stream.readBoolean();
 this.disabled = stream.readBoolean();
}
*/
