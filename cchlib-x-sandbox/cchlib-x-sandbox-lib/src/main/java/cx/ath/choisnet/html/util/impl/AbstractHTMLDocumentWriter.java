/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/impl/AbstractHTMLDocumentWriter.java
** Description   :
** Encodage      : ANSI
**
**  3.02.037 2006.08.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.impl.AbstractHTMLDocumentWriter
*/
package cx.ath.choisnet.html.util.impl;

import cx.ath.choisnet.html.util.HTMLDocumentStringWriter;
import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import java.util.Locale;

/**
** <p>
**
** </p>
**
**
** @author Claude CHOISNET
** @since   3.02.037
** @version 3.02.037
*/
public abstract class AbstractHTMLDocumentWriter<T extends AbstractHTMLDocumentWriter<?>>
    implements HTMLDocumentWriter
{
/** */
private Locale locale;

/**
**
*/
protected AbstractHTMLDocumentWriter() // ---------------------------------
{
 // empty
}

/**
**
*/
public abstract T getThis(); // -------------------------------------------

/**
**
** @see #getLocale()
*/
public T setLocale( final Locale locale ) // -------------------------------
{
 this.locale = locale;

 return getThis();
}

/**
** @return un object Locale correspondant � la localisation en cours,
**         utilis� pour l'encodage de certain gadgets, en particulier pour
**         les dates et les horaires.
**
** @see #setLocale
**
** @throws UnsupportedOperationException
*/
@Override
public Locale getLocale() // ----------------------------------------------
    throws UnsupportedOperationException
{
 if( this.locale == null ) {
    this.locale = Locale.getDefault();
    }

 return this.locale;
}

/**
**
*/
@Override
public String formatTag( final String tag ) // ----------------------------
{
 return tag.toLowerCase();
}

/**
**
*/
@Override
public String formatAttribute( final String attribute ) // ----------------
{
 return attribute.toLowerCase();
}

/**
**
*/
@Override
public HTMLDocumentStringWriter getHTMLDocumentStringWriter() // ----------
{
 return new HTMLDocumentStringWriterImpl( this );
}

} // class


/**
**
public Appendable append( final char c ) // -------------------------------
    throws java.io.IOException
{
 return this.appendable.append( c );
}
*/

/**
**
public Appendable append( final CharSequence csq ) // ---------------------
    throws java.io.IOException
{
 return this.appendable.append( csq );
}
*/

/**
**
public Appendable append( // ----------------------------------------------
    final CharSequence  csq,
    final int           start,
    final int           end
    )
    throws java.io.IOException
{
 return this.appendable.append( csq, start, end );
}
*/
