/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLOption.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.034 2006.08.02 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLInputText
**
*/
package cx.ath.choisnet.html.gadgets;

import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLDocumentWriter;

/**
** <p>
**
** </p>
**
**
**
**
** @author Claude CHOISNET
** @since   3.02.034
** @version 3.02.034
*/
public class HTMLOption
    extends cx.ath.choisnet.html.util.HTMLObject<HTMLOption>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private String value;

/** */
private String componentText;

/** */
private String label;

/** */
private boolean selected; // writeObject/readObject

/** */
private boolean disabled; // writeObject/readObject


/**
**
*/
public HTMLOption() // ----------------------------------------------------
{
 super();

 this.value         = null; // defaults to element content
 this.componentText = null;
 this.label         = null; // for use in hierarchical menus
 this.selected      = false;
 this.disabled      = false; // unavailable in this context
}

/**
**
*/
public HTMLOption( // -----------------------------------------------------
    final String value,
    final String componentText
    )
{
 super();

 setValue( value );
 setText( componentText );
}

/**
**
*/
@Override
public HTMLOption getThis() // --------------------------------------------
{
 return this;
}

/**
**
*/
public HTMLOption setValue( final String value ) // -----------------------
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
public HTMLOption setText( final String componentText ) // ----------------
{
 this.componentText = componentText;

 return getThis();
}

/**
**
*/
public String getText() // -----------------------------------------------
{
 return this.componentText;
}

/**
**
*/
public HTMLOption setLabel( final String label ) // -----------------------
{
 this.label = label;

 return getThis();
}

/**
**
*/
public String getLabel() // -----------------------------------------------
{
 return this.label;
}

/**
**
*/
public HTMLOption setSelected( final boolean selected ) // ----------------
{
 this.selected = selected;

 return getThis();
}

/**
**
*/
public boolean isSelected() // --------------------------------------------
{
 return this.selected;
}

/**
**
*/
public HTMLOption setDisabled( final boolean disabled ) // ----------------
{
 this.disabled = disabled;

 return getThis();
}

/**
**
*/
public boolean isDisabled() // --------------------------------------------
{
 return this.disabled;
}

/**
**
*/
@Override
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 try {
    out.append( "<option" );

    if( this.value != null ) {
        out.append( " value=\"" + this.value + "\"" );
        }

    if( this.label != null ) {
        out.append( " label=\"" + this.label + "\"" );
        }

    if( isSelected() ) {
        out.append( " selected" );
        }

    if( isDisabled() ) {
        out.append( " disabled" );
        }

    out.append( ">" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}
/**
**
*/
@Override
public void writeBody( final HTMLDocumentWriter out ) // ------------------
    throws HTMLDocumentException
{
 try {
    if( this.componentText != null ) {
        out.append( this.componentText );
        }
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
*/
@Override
public void writeEndTag( final HTMLDocumentWriter out ) // ----------------
    throws HTMLDocumentException
{
 try {
    out.append( "</option>" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
*/
@Override
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
 writeBody( out );
 writeEndTag( out );
}

/**
**
** java.io.Serializable
*/
private void writeObject( final java.io.ObjectOutputStream stream ) // ----
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeBoolean( this.selected  );
 stream.writeBoolean( this.disabled  );
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

 this.selected = stream.readBoolean();
 this.disabled = stream.readBoolean();
}

} // class
