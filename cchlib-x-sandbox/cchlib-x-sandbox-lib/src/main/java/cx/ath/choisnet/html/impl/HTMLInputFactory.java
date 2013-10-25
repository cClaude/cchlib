/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/impl/HTMLInputFactory.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.038 2006.08.08 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.impl.HTMLInputFactory
**
*/
package cx.ath.choisnet.html.impl;

import cx.ath.choisnet.html.javascript.JavaScript;
import cx.ath.choisnet.html.HTMLInput;
import cx.ath.choisnet.html.gadgets.HTMLInputText;
import cx.ath.choisnet.html.gadgets.HTMLInputSubmit;
import cx.ath.choisnet.html.gadgets.HTMLInputCheckBox;

/**
** <p>
**
** </p>
**
**
**
** @author Claude CHOISNET
** @since   3.02.038
** @version 3.02.038
**
** @see HTMLInputText
** @see HTMLInputSubmit
** @see HTMLInputCheckBox
** @see HTMLInputImpl
*/
public class HTMLInputFactory
    implements java.io.Serializable
{
/** */
private static final long serialVersionUID = 1L;

/** HTMLObject */
private String cssClass;

/** HTMLObject */
private String id;

/** HTMLNamedFormObject */
private String name;

/** abstract */
private String type;

/** */
private String value;

/** */
private boolean checked; // writeObject/readObject

/** */
private boolean disabled; // writeObject/readObject

/** */
private boolean readOnly; // writeObject/readObject

/** */
private Integer size;

/** */
private Integer maxLength;

/** */
private String src;

/** */
private String alt;

/** */
private boolean isMap; // writeObject/readObject

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
private String accept;

/**
**
*/
public HTMLInputFactory() // ----------------------------------------------
{
 // empty !
}

/**
**
*/
public HTMLInputFactory setCSSClass( final String cssClass ) // -----------
{
 this.cssClass = cssClass;

 return this;
}
/**
**
*/
public HTMLInputFactory setId( final String name ) // ---------------------
{
 this.id = id;

 return this;
}

/**
**
*/
public HTMLInputFactory setName( final String name ) // -------------------
{
 this.name = name;

 return this;
}

/**
**
*/
public HTMLInputFactory setType( final String type ) // -------------------
{
 this.type = type.toLowerCase();

 return this;
}

/**
**
*/
public HTMLInputFactory setValue( final String value ) // -----------------
{
 this.value = value;

 return this;
}

/**
**
*/
public HTMLInputFactory setChecked( final boolean checked ) // ------------
{
 this.checked = checked ? Boolean.TRUE : null;

 return this;
}

/**
**
*/
public HTMLInputFactory setDisabled( final boolean disabled ) // ----------
{
 this.disabled = disabled ? Boolean.TRUE : null;

 return this;
}

/**
**
*/
public HTMLInputFactory setReadOnly( final boolean readOnly ) // ----------
{
 this.readOnly = readOnly ? Boolean.TRUE : null;

 return this;
}

/**
**
*/
public HTMLInputFactory setSize( final int size ) // ----------------------
{
 this.size = new Integer( size );

 return this;
}

/**
**
*/
public HTMLInputFactory setMaxLength( final int maxLength ) // ------------
{
 this.maxLength = new Integer( maxLength );

 return this;
}

/**
**
*/
public HTMLInputFactory setSrc( final String src ) // ---------------------
{
 this.src = src;

 return this;
}

/**
**
*/
public HTMLInputFactory setAlt( final String alt ) // ---------------------
{
 this.alt = alt;

 return this;
}

/**
**
*/
public HTMLInputFactory setIsMap( final boolean isMap ) // ----------------
{
 this.isMap = isMap ? Boolean.TRUE : null;

 return this;
}

/**
**
*/
public HTMLInputFactory setTabIndex( final int tabIndex ) // --------------
{
 this.tabIndex = tabIndex;

 return this;
}

/**
**
*/
public HTMLInputFactory setAccessKey( final char accessKey ) // -----------
{
 this.accessKey = new Character( accessKey );

 return this;
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
public HTMLInputFactory setAccept( final String accept ) // ---------------
{
 this.accept = accept;

 return this;
}

/**
**
*/
public HTMLInput newInstance() // -----------------------------------------
{
 HTMLInput object;

 if( this.type == null ) {
    object = new HTMLInputImpl();
    }
 else if( "text".equals( this.type ) ) {
    object = new HTMLInputText();
    }
 else if( "submit".equals( this.type ) ) {
    object = new HTMLInputSubmit();
    }
 else if( "checkbox".equals( this.type ) ) {
    object = new HTMLInputCheckBox();
    }
 else {
    object = new HTMLInputImpl().setType( this.type );
    }

 if( this.cssClass != null ) {
    object.setCSSClass( cssClass );
    }

 if( this.id != null ) {
    object.setId( id );
    }

 if( this.name != null ) {
    object.setName( name );
    }

 if( this.value != null ) {
    object.setValue( value );
    }

 if( this.checked ) {
    object.setChecked( true );
    }

 if( this.disabled ) {
    object.setDisabled( true );
    }

 if( this.readOnly ) {
    object.setReadOnly( true );
    }

 if( this.size != null ) {
    object.setSize( this.size.intValue() );
    }

 if( this.maxLength != null ) {
    object.setMaxLength( this.maxLength.intValue() );
    }

 if( this.isMap ) {
    object.setIsMap( true );
    }

 if( this.tabIndex != null ) {
    object.setTabIndex( this.tabIndex.intValue() );
    }

 if( this.accessKey != null ) {
    object.setAccessKey( this.accessKey.charValue() );
    }

 if( this.accept != null ) {
    object.setAccept( this.accept );
    }

 return object;
}

/**
**
** java.io.Serializable
*/
private void writeObject( final java.io.ObjectOutputStream stream ) // ----
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeBoolean( this.checked  );
 stream.writeBoolean( this.disabled  );
 stream.writeBoolean( this.readOnly  );
 stream.writeBoolean( this.isMap  );
}

/**
** java.io.Serializable
*/
private void readObject( final java.io.ObjectInputStream stream ) // ------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.checked   = stream.readBoolean();
 this.disabled  = stream.readBoolean();
 this.readOnly  = stream.readBoolean();
 this.isMap     = stream.readBoolean();
}

} // class
