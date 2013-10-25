/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLSelect.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.034 2006.08.02 Claude CHOISNET - Version initial
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.HTMLSelect
**
*/
package cx.ath.choisnet.html.gadgets;

import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
** <p>
** Gestion des gadgets HTML de type SELECT
** </p>
**
** @author Claude CHOISNET
** @since   3.02.034
** @version 3.02.034
**
** @see HTMLSelectSingleValue
*/
public abstract class HTMLSelect<T extends HTMLSelect,U>
    extends cx.ath.choisnet.html.util.HTMLNamedFormObject<T,U>
{
/** */
private Integer size;

/** */
private List<HTMLOption> options;

/**
**
*/
public HTMLSelect() // ----------------------------------------------------
{
 super();

 this.options = new LinkedList<HTMLOption>();
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
*/
public int getSize() // ---------------------------------------------------
{
 return this.size.intValue();
}

/**
**
*/
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 try {
    out.append( "<select" );

    super.writeStartTag( out );

    if( this.size != null ) {
        out.append( " size=\"" + this.size + "\"" );
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
public void writeBody( final HTMLDocumentWriter out ) // ------------------
    throws HTMLDocumentException
{
 for( HTMLOption option : this.options ) {
    option.write( out );
    }
}

/**
**
*/
public void writeEndTag( final HTMLDocumentWriter out ) // ----------------
    throws HTMLDocumentException
{
 try {
    out.append( "</select>" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

/**
**
*/
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
 writeBody( out );
 writeEndTag( out );
}

/**
**
*/
public T add( final HTMLOption option ) // --------------------------------
{
 this.options.add( option );

 return getThis();
}

/**
**
*/
public T addAll( final Collection<HTMLOption> options ) // ----------------
{
 this.options.addAll( options );

 return getThis();
}

/**
**
**
** @see #clear()
*/
public T addRange( final int from, final int to ) // ----------------------
{
 final int len = to - from + 1;

 for( int i = 0; i < len; i++ ) {
    final String value = Integer.toString( i + from );

    add( new HTMLOption( value, value ) );
    }

 return getThis();
}

/**
** Clear option collection
*/
public T clear() // -------------------------------------------------------
{
 this.options.clear();

 return getThis();
}

/**
** Unselect all options
*/
public T unselectAll() // -------------------------------------------------
{
 for( HTMLOption option : this.options ) {
    if( option.isSelected() ) {
        option.setSelected( false );
        }
    }

 return getThis();
}

/**
**
*/
public Collection<HTMLOption> getHTMLOptions() // -------------------------
{
 return Collections.unmodifiableCollection( this.options );
}

/**
**
**
** @return a String within the value of the first selected OPTION,
**         if there is no selected OPTION, first OPTION value is return,
**         if there is no OPTION null is return
*/
public String getFirstSelectedValue() // ----------------------------------
{
 String     defaultValue    = null;
 boolean    first           = true;

 for( HTMLOption option : this.options ) {
    if( first ) {
        defaultValue    = option.getValue();
        first           = false;
        }

    if( option.isSelected() ) {
        return option.getValue();
        }
    }

 return defaultValue;
}

/**
**
*/
public Collection<String> getSelectedValues() // --------------------------
{
 final List<String> values = new LinkedList<String>();

 for( HTMLOption option : this.options ) {
    if( option.isSelected() ) {
        values.add( option.getValue() );
        }
    }

 return values;
}

} // class
