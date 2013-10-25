/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/impl/HTMLDocumentWriterWrapper.java
** Description   :
** Encodage      : ANSI
**
**  3.02.037 2006.08.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.impl.HTMLDocumentWriterWrapper
*/
package cx.ath.choisnet.html.util.impl;

import javax.servlet.jsp.PageContext;

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
public class HTMLDocumentWriterWrapper
    extends AbstractHTMLDocumentWriter<HTMLDocumentWriterWrapper>
{
/** */
private final PageContext pageContext;

/**
**
*/
public HTMLDocumentWriterWrapper( // --------------------------------------
    final PageContext pageContext
    )
{
 this.pageContext = pageContext;
}

/**
**
*/
public HTMLDocumentWriterWrapper getThis() // -----------------------------
{
 return this;
}

/**
**
*/
public Appendable append( final char c ) // -------------------------------
    throws java.io.IOException
{
 return this.pageContext.getOut().append( c );
}

/**
**
*/
public Appendable append( final CharSequence csq ) // ---------------------
    throws java.io.IOException
{
 return this.pageContext.getOut().append( csq );
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
 return this.pageContext.getOut().append( csq, start, end );
}

/**
** @return un object Locale correspondant � la localisation en cours,
**         utilis� pour l'encodage de certain gadgets, en particulier pour
**         les dates et les horaires.
**
** @throws UnsupportedOperationException
public Locale getLocale() // ----------------------------------------------
    throws UnsupportedOperationException
{
 if( this.locale == null ) {
    //
    // R�cup�re la locale du browser !
    //

    /// $$$$$$$$$$ A ECRIRE $$$$$$$$$$$$$$$$

    //
    // Si rien trouv�, on prend le traitement par d�faut
    //
    if( this.locale == null ) {
        super.getLocale();
        }
    }

 return this.locale;
}
*/


} // class
