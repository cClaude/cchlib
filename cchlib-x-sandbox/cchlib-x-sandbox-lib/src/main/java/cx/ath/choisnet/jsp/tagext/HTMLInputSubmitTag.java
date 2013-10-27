/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/jsp/tagext/HTMLInputSubmitTag.java
** Description   :
** Encodage      : ANSI
**
**  3.02.038 2006.08.08 Claude CHOISNET  - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.jsp.tagext.HTMLInputSubmitTag
**
*/
package cx.ath.choisnet.jsp.tagext;

import cx.ath.choisnet.html.HTMLInput;
import cx.ath.choisnet.html.util.impl.HTMLDocumentWriterWrapper;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @since   3.02.038
** @version 3.02.038
*/
public class HTMLInputSubmitTag
    extends HTMLInputTag
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
**
** @see javax.servlet.jsp.tagext.TagSupport
*/
@Override
public int doStartTag() // ------------------------------------------------
    throws javax.servlet.jsp.JspException
{
 try {
    final HTMLInput gadget = this.gadgetFactory.setType( "submit" ).newInstance();

    //
    // On sauvegarde dans le formulaire courant
    //
    HTMLFormTag.addToCurrent( pageContext, gadget );

    this.wrapper = new HTMLDocumentWriterWrapper( pageContext );

    gadget.writeStartTag( this.wrapper );
    }
 catch( Exception e ) {
    throw new javax.servlet.jsp.JspTagException(
                getClass().getName() + ":" + e.getMessage(),
                e
                );
    }

 return SKIP_BODY;
}

/**
** @throws UnsupportedOperationException
*/
@Override
public void setType( final String type ) // -------------------------------
    throws UnsupportedOperationException
{
 throw new UnsupportedOperationException();
}

} // class


