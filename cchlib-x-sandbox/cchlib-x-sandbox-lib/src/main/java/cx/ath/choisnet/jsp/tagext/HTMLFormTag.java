/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/jsp/tagext/HTMLFormTag.java
** Description   :
** Encodage      : ANSI
**
**  3.02.037 2006.08.07 Claude CHOISNET  - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.jsp.tagext.HTMLFormTag
**
*/
package cx.ath.choisnet.jsp.tagext;

import cx.ath.choisnet.html.HTMLForm;
import cx.ath.choisnet.html.util.HTMLWritable;
import cx.ath.choisnet.html.util.impl.HTMLDocumentWriterWrapper;
import java.util.Collection;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

/**
** <p>
**  Gestion du tag: &lt;CHCLibHTML:form&gt;   &lt;/CHCLibHTML:form&gt;
** </p>
**
** @author Claude CHOISNET
** @since   3.02.037
** @version 3.02.037
**
** @see HTMLForm
*/
public class HTMLFormTag
    extends javax.servlet.jsp.tagext.BodyTagSupport
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** Nom sur la session de la collection des formulaires */
public final static String HTMLFORM_COLLECTION_SESSION_NAME = HTMLFormCollection.class.getName();

/** */
private HTMLForm thisHTMLForm = new HTMLForm();

/** */
private HTMLDocumentWriterWrapper wrapper;

/**
**
** @see javax.servlet.jsp.tagext.TagSupport
*/
@Override
public int doStartTag() // ------------------------------------------------
    throws javax.servlet.jsp.JspException
{
System.out.println( "doStartTag()" );

 try {
    //
    // On sauvegarde dans le context de la page (sans nom, formulaire courant)
    //
    pageContext.setAttribute( HTMLForm.class.getName(), this.thisHTMLForm );

    //
    // On sauvegarde le context de la session courante
    //
    pageContext.getSession().setAttribute( getSessionName(), this.thisHTMLForm );

    this.wrapper = new HTMLDocumentWriterWrapper( pageContext );

    this.thisHTMLForm.writeStartTag( this.wrapper );
    }
 catch( Exception e ) {
    throw new javax.servlet.jsp.JspTagException(
                getClass().getName() + ":" + e.getMessage(),
                e
                );
    }

 // return super.doStartTag(); // Default processing of the start tag, returning SKIP_BODY.
 return EVAL_BODY_BUFFERED;
}

/**
**
** @see javax.servlet.jsp.tagext.TagSupport
*/
@Override
public void doInitBody() // -----------------------------------------------
    throws JspException
{
System.out.println( "doInitBody()" );
}

/**
**
** @see javax.servlet.jsp.tagext.BodyTagSupport
*/
@Override
public int doAfterBody() // -----------------------------------------------
    throws JspException
{
System.out.println( "doAfterBody()" );

 final BodyContent bc = getBodyContent();

 try {
    bc.writeOut( bc.getEnclosingWriter() );
    bc.clearBody();
    }
 catch( Exception e ) {
    throw new JspTagException(
                getClass().getName() + ":" + e.getMessage(),
                e
                );
    }

 return SKIP_BODY;
}

/**
**
** @see javax.servlet.jsp.tagext.TagSupport
*/
@Override
public int doEndTag() // ------------------------------------------------
    throws javax.servlet.jsp.JspException
{
 System.out.println( "doEndTag()" );

 try {
    this.thisHTMLForm.writeEndTag( this.wrapper );
    }
 catch( Exception e ) {
    throw new javax.servlet.jsp.JspTagException(
                getClass().getName() + ":" + e.getMessage(),
                e
                );
    }

 //
 // Supprime le formulaire du context de la page
 //
 pageContext.removeAttribute( HTMLForm.class.getName() );

 // return super.doEndTag(); // Default processing of the end tag returning EVAL_PAGE.
 return EVAL_PAGE;
}

/**
** <p>
** Tag parameter 'name'
** </p>
**
** @see HTMLForm#setName(String)
*/
public void setName( final String name ) // -------------------------------
{
 this.thisHTMLForm.setName( name );
}

/**
**
*/
public String getName() // ------------------------------------------------
{
 return this.thisHTMLForm.getName();
}

/**
** <p>
** Tag parameter 'action'
** </p>
**
** @see HTMLForm#setAction(String)
*/
public void setAction( final String action ) // ---------------------------
{
 this.thisHTMLForm.setAction( action );
}

/**
**
*/
private final String getSessionName() // ----------------------------------
{
 return HTMLForm.class.getName() + "@" + getName();
}


/**
**
*/
public final static HTMLForm getHTMLForm( // ------------------------------
    final PageContext pageContext
    )
{
 return HTMLForm.class.cast(
    pageContext.getAttribute( HTMLForm.class.getName() )
    );
}

/**
**
*/
public final static HTMLForm addToCurrent( // -----------------------------
    final PageContext   pageContext,
    final HTMLWritable  item
    )
{
 return getHTMLForm( pageContext ).add( item );
}

/**
**
*/
public final static Collection<HTMLForm> getHTMLFormCollection( // --------
    final HttpSession session
    )
{
 final HTMLFormCollection c = HTMLFormCollection.class.cast(
                                session.getAttribute( HTMLFORM_COLLECTION_SESSION_NAME )
                                );

 if( c != null ) {
    return c;
    }
 else {
    final HTMLFormCollection cnew = new HTMLFormCollection();

    session.setAttribute( HTMLFORM_COLLECTION_SESSION_NAME, cnew );

    return cnew;
    }
}

///**
//**
//*/
//private final void setHTMLForm( // ----------------------------------------
//    final HttpSession session,
//    final HTMLForm    aHTMLForm
//    )
//{
// final Collection<HTMLForm> c = getHTMLFormCollection( session );
//
// c.add( aHTMLForm );
//}

    /**
    **
    */
    private static class HTMLFormCollection
        extends java.util.LinkedList<HTMLForm>
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

    }

} // class
