/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/impl/HTMLDocumentWriterImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.02.037 2006.08.07 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.util.impl.HTMLDocumentWriterImpl
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.impl.HTMLDocumentWriterImpl
*/
package cx.ath.choisnet.html.util.impl;

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
public class HTMLDocumentWriterImpl
    extends AbstractHTMLDocumentWriter<HTMLDocumentWriterImpl>
{
/** */
private final Appendable appendable;

/**
**
*/
public HTMLDocumentWriterImpl( // -----------------------------------------
    final Appendable appendable
    )
{
 this.appendable = appendable;
}

/**
**
*/
public HTMLDocumentWriterImpl( // -----------------------------------------
    final Appendable    appendable,
    final Locale        locale
    )
{
 this( appendable );

 setLocale( locale );
}

/**
**
*/
public HTMLDocumentWriterImpl getThis() // --------------------------------
{
 return this;
}

/**
**
*/
public Appendable append( final char c ) // -------------------------------
    throws java.io.IOException
{
 return this.appendable.append( c );
}

/**
**
*/
public Appendable append( final CharSequence csq ) // ---------------------
    throws java.io.IOException
{
 return this.appendable.append( csq );
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
 return this.appendable.append( csq, start, end );
}

/**
**
public HTMLDocumentWriterImpl setLocale( final Locale local ) // ----------
{
 this.locale = locale;

 return this;
}
*/



/**
**
public String formatTag( final String tag ) // ----------------------------
{
 return tag.toLowerCase();
}
*/

/**
**
public String formatAttribute( final String attribute ) // ----------------
{
 return attribute.toLowerCase();
}
*/

/**
**
public HTMLDocumentStringWriter getHTMLDocumentStringWriter() // ----------
{
 return new HTMLDocumentStringWriterImpl( this );
}
*/

} // class

/**
** @see HTMLDocumentWriter#append( String )
public void append( final String htmlContent ) // -------------------------
    throws HTMLDocumentException
{
 try {
    out.write( htmlContent );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}
*/

/**
** @see HTMLDocumentWriter#append( String )
public void write( final String htmlContent ) // --------------------------
    throws HTMLDocumentException
{
 append( htmlContent );
}
*/


/**
**
public void flush() // ----------------------------------------------------
    throws HTMLDocumentException
{
 try {
    out.flush();
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}
*/

/**
**
public void close() // ----------------------------------------------------
    throws HTMLDocumentException
{
 try {
    out.close();
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}
*/

