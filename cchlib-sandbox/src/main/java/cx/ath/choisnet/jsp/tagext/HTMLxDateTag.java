/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/jsp/tagext/HTMLxDateTag.java
** Description   :
** Encodage      : ANSI
**
**  3.02.038 2006.08.08 Claude CHOISNET  - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.jsp.tagext.HTMLxDateTag
**
*/
package cx.ath.choisnet.jsp.tagext;

//import cx.ath.choisnet.html.HTMLForm;
import cx.ath.choisnet.html.gadgets.extended.HTMLxDate;
import cx.ath.choisnet.html.util.impl.HTMLDocumentWriterWrapper;
import cx.ath.choisnet.util.datetime.BasicDate;
import java.text.SimpleDateFormat;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @since   3.02.038
** @version 3.02.038
**
** @see HTMLxDate
** @see BasicDate
*/
public class HTMLxDateTag
    extends javax.servlet.jsp.tagext.TagSupport
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
public static final String DEFAULT_DATE_FORMAT = "yyyymmdd";

/** */
private HTMLxDate gadget = new HTMLxDate();

/** */
private HTMLDocumentWriterWrapper wrapper;

/** */
private String dateFormat = DEFAULT_DATE_FORMAT;

/** */
private String date;

/** */
private Integer yearFrom;

/** */
private Integer yearTo;

/**
**
**
** @see javax.servlet.jsp.tagext.TagSupport
*/
public int doStartTag() // ------------------------------------------------
    throws javax.servlet.jsp.JspException
{
 try {
    //
    // Fini l'initialisation
    //
    if( date == null ) {
        this.gadget.setCurrentValue( new BasicDate() );
        }
    else {
        this.gadget.setCurrentValue( new BasicDate( date, getSimpleDateFormat() ) );
        }

    this.gadget.setYearRange( yearFrom.intValue(), yearTo.intValue() );

    //
    // On sauvegarde dans le formulaire courant
    //
    HTMLFormTag.addToCurrent( pageContext, this.gadget );

    this.wrapper = new HTMLDocumentWriterWrapper( pageContext );

    this.gadget.writeStartTag( this.wrapper );
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
public int doEndTag() // --------------------------------------------------
    throws javax.servlet.jsp.JspException
{
 return EVAL_PAGE;
}

/**
** <p>
** Tag parameter 'name'
** </p>
**
** @see HTMLxDate#setName(String)
*/
public void setName( final String name ) // -------------------------------
{
 this.gadget.setName( name );
}

/**
** <p>
** Tag parameter 'from'
** </p>
**
** @see HTMLxDate#setYearRange(int,int)
*/
public void setFrom( final int yearFrom ) // ------------------------------
{
 this.yearFrom = new Integer( yearFrom );
}

/**
** <p>
** Tag parameter 'to'
** </p>
**
** @see HTMLxDate#setYearRange(int,int)
*/
public void setTo( final int yearTo ) // ----------------------------------
{
 this.yearTo = new Integer( yearTo );
}

/**
** <p>
** Tag parameter 'date'
** </p>
**
** @see HTMLxDate#setCurrentValue
*/
public void setDate( final String date ) // -------------------------------
    throws java.text.ParseException
{
 this.date = date;
}

/**
** <p>
** Tag parameter 'format'
** </p>
**
** @see BasicDate#toString(java.text.Format)
*/
public void setFormat( final String format ) // ---------------------------
{
 this.dateFormat = format;
}

/**
** <p>
** Tag parameter 'class'
** </p>
**
** @see HTMLxDate#setCSSClass(String)
*/
public void setClass( final String cssClass ) // --------------------------
{
 this.gadget.setCSSClass( cssClass );
}

/**
** <p>
** Tag parameter 'id'
** </p>
**
** @see HTMLxDate#setId(String)
*/
public void setId( final String id ) // -----------------------------------
{
 this.gadget.setId( id );
}

/**
**
*/
protected SimpleDateFormat getSimpleDateFormat() // -----------------------
{
 return new SimpleDateFormat( this.dateFormat );
}

} // class
