/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/jsp/tagext/HTMLInputTag.java
** Description   :
** Encodage      : ANSI
**
**  3.02.038 2006.08.08 Claude CHOISNET  - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.jsp.tagext.HTMLInputTag
**
*/
package cx.ath.choisnet.jsp.tagext;

//import cx.ath.choisnet.html.HTMLForm;
import cx.ath.choisnet.html.HTMLInput;
import cx.ath.choisnet.html.impl.HTMLInputFactory;
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
public class HTMLInputTag
    extends javax.servlet.jsp.tagext.TagSupport
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
protected HTMLInputFactory gadgetFactory = new HTMLInputFactory();

/** */
protected HTMLDocumentWriterWrapper wrapper;

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
    final HTMLInput gadget = this.gadgetFactory.newInstance();

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
**
** @see javax.servlet.jsp.tagext.TagSupport
*/
@Override
public int doEndTag() // --------------------------------------------------
    throws javax.servlet.jsp.JspException
{
 return EVAL_PAGE;
}

/**
** <p>
** Tag parameter 'class'
** </p>
**
** @see HTMLInput#setCSSClass(String)
*/
public void setClass( final String cssClass ) // --------------------------
{
 this.gadgetFactory.setCSSClass( cssClass );
}

/**
** <p>
** Tag parameter 'id'
** </p>
**
** @see HTMLInput#setId(String)
*/
@Override
public void setId( final String id ) // -----------------------------------
{
 this.gadgetFactory.setId( id );
}

/**
** <p>
** Tag parameter 'name'
** </p>
**
** @see HTMLInput#setName(String)
*/
public void setName( final String name ) // -------------------------------
{
 this.gadgetFactory.setName( name );
}

/**
** <p>
** Tag parameter 'type'
** </p>
**
** @see HTMLInput#getType()
*/
public void setType( final String type ) // -------------------------------
{
 this.gadgetFactory.setType( type );
}

/**
** <p>
** Tag parameter 'size'
** </p>
**
** @see HTMLInput#setSize(int)
*/
public void setSize( final int size ) // ----------------------------------
{
 this.gadgetFactory.setSize( size );
}

/**
**
** <p>
** Tag parameter 'maxlength'
** </p>
**
** @see HTMLInput#setMaxLength(int)
*/
public void setMaxlength( final int maxlength ) // ------------------------
{
 this.gadgetFactory.setMaxLength( maxlength );
}

/**
**
** <p>
** Tag parameter 'value'
** </p>
**
** @see HTMLInput#setValue(String)
*/
public void setValue( final String value ) // -----------------------------
{
 this.gadgetFactory.setValue( value );
}

/**
**
** <p>
** Tag parameter 'checked'
** </p>
**
** @see HTMLInput#setChecked(boolean)
*/
public void setChecked( final boolean checked ) // ------------------------
{
 this.gadgetFactory.setChecked( checked );
}

/**
**
** <p>
** Tag parameter 'disabled'
** </p>
**
** @see HTMLInput#setDisabled(boolean)
*/
public void setDisabled( final boolean disabled ) // ----------------------
{
 this.gadgetFactory.setDisabled( disabled );
}

/**
**
** <p>
** Tag parameter 'readonly'
** </p>
**
** @see HTMLInput#setReadOnly(boolean)
*/
public void setReadonly( final boolean readOnly ) // ----------------------
{
 this.gadgetFactory.setReadOnly( readOnly );
}

/**
**
** <p>
** Tag parameter 'src'
** </p>
**
** @see HTMLInput#setSrc(String)
*/
public void setSrc( final String src ) // ---------------------------------
{
 this.gadgetFactory.setSrc( src );
}

/**
**
** <p>
** Tag parameter 'alt'
** </p>
**
** @see HTMLInput#setAlt(String)
*/
public void setAlt( final String alt ) // ---------------------------------
{
 this.gadgetFactory.setAlt( alt );
}

/**
**
** <p>
** Tag parameter 'ismap'
** </p>
**
** @see HTMLInput#setIsMap(boolean)
*/
public void setIsmap( final boolean isMap ) // ----------------------------
{
 this.gadgetFactory.setIsMap( isMap );
}


/**
**
** <p>
** Tag parameter 'tabindex'
** </p>
**
** @see HTMLInput#setTabIndex(int)
*/
public void setTabindex( final int tabIndex ) // --------------------------
{
 this.gadgetFactory.setTabIndex( tabIndex );
}

/**
**
** <p>
** Tag parameter 'accesskey'
** </p>
**
** @see HTMLInput#setAccessKey(char)
*/
public void setAccesskey( final char accessKey ) // -----------------------
{
 this.gadgetFactory.setAccessKey( accessKey );
}

/**
**
** <p>
** Tag parameter 'accept'
** </p>
**
** @see HTMLInput#setAccept(String)
*/
public void setAccept( final String accept ) // ---------------------------
{
 this.gadgetFactory.setAccept( accept );
}

} // class


