/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/impl/HTMLDocumentStringWriterImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.02.035 2006.08.03 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.impl.HTMLDocumentStringWriterImpl
**  3.02.037 2006.08.07 Claude CHOISNET - Version initiale
**                      Nouveau nom: cx.ath.choisnet.html.util.impl.HTMLDocumentStringWriterImpl
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.impl.HTMLDocumentStringWriterImpl
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
** @since   3.02.035
** @version 3.02.037
*/
public class HTMLDocumentStringWriterImpl
        implements HTMLDocumentStringWriter
{
/** */
private final StringBuilder strBuilder;

/** */
private final HTMLDocumentWriter hWriter;

/**
**
*/
public HTMLDocumentStringWriterImpl() // ----------------------------------
{
 this.strBuilder    = new StringBuilder();
 this.hWriter       = new HTMLDocumentWriterImpl( this.strBuilder, null );
}

/**
**
*/
public HTMLDocumentStringWriterImpl( // -----------------------------------
    final Locale locale
    )
{
 this.strBuilder    = new StringBuilder();
 this.hWriter       = new HTMLDocumentWriterImpl( this.strBuilder, locale );
}

/**
**
*/
public HTMLDocumentStringWriterImpl( // -----------------------------------
    final HTMLDocumentWriter htmlDocumentWriter
    )
{
 this( htmlDocumentWriter.getLocale() );
}


/**
**
*/
public String toString() // -----------------------------------------------
{
 return this.strBuilder.toString();
}

/**
** Clean up internal buffer, and return previous value
*/
public String reset() // --------------------------------------------------
{
 final String previous = toString();

 this.strBuilder.setLength( 0 );

 return previous;
}

/**
**
*/
public Appendable append( final char c ) // -------------------------------
    throws java.io.IOException
{
 return this.hWriter.append( c );
}

/**
**
*/
public Appendable append( final CharSequence csq ) // ---------------------
    throws java.io.IOException
{
 return this.hWriter.append( csq );
}

/**
**
*/
public Appendable append( // ----------------------------------------------
    final CharSequence  csq,
    final int           start,
    final int           end
    )
    throws java.io.IOException
{
 return this.hWriter.append( csq, start, end );
}

/**
** @return un object Locale correspondant � la localisation en cours,
**         utilis� pour l'encodage de certain gadgets, en particulier pour
**         les dates et les horaires.
**
** @throws UnsupportedOperationException
*/
public Locale getLocale() // ----------------------------------------------
    throws UnsupportedOperationException
{
 return this.hWriter.getLocale();
}

/**
**
*/
public String formatTag( final String tag ) // ----------------------------
{
 return this.hWriter.formatTag( tag );
}

/**
**
*/
public String formatAttribute( final String attribute ) // ----------------
{
 return this.hWriter.formatAttribute( attribute );
}

/**
**
*/
public HTMLDocumentStringWriter getHTMLDocumentStringWriter() // ----------
{
 return new HTMLDocumentStringWriterImpl( this );
}

} // class
