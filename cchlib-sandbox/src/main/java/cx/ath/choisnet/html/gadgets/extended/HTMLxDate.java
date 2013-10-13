/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/gadgets/extended/HTMLxDate.java
** Description   :
**
**  3.02.033 2006.07.25 Claude CHOISNET - Version initiale
**                      Adaptation de cx.ath.choisnet.html.gadgets.advanced.AGDate
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.gadgets.extended.HTMLxDate
**
*/
package cx.ath.choisnet.html.gadgets.extended;

import cx.ath.choisnet.html.gadgets.HTMLSelectSingleString;
import cx.ath.choisnet.html.util.FormItem;
import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLDocumentStringWriter;
import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import cx.ath.choisnet.html.util.HTMLFormException;
import cx.ath.choisnet.util.datetime.BasicDate;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletRequest;

/**
** <p>
** Fabrication d'un selectionneur de jour, mois, année localisé.
** </p>
**
** </p>
** Cette classe permet la création et l'exploitation de formulaires
** HTML utilisant des notions de date.
** </p>
**
** @author Claude CHOISNET
** @since   3.02.033
** @version 3.02.033
*/
public class HTMLxDate
    extends cx.ath.choisnet.html.util.HTMLNamedObject<HTMLxDate>
        implements cx.ath.choisnet.html.util.HTMLFormEntry<HTMLxDate,BasicDate>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
public final static String YEAR_SUFFIX = "_Y";

/** */
public final static String MONTH_SUFFIX = "_M";

/** */
public final static String DAY_SUFFIX = "_D";

/** */
protected HTMLSelectSingleString gadgetYear_;

/** */
protected HTMLSelectSingleString gadgetMonth;

/** */
protected HTMLSelectSingleString gadgetDay__;

/** */
protected BasicDate dateToSelect;

/**
**
*/
public HTMLxDate() // -----------------------------------------------------
{
 this.gadgetYear_   = new HTMLSelectSingleString();
 this.gadgetMonth   = new HTMLSelectSingleString();
 this.gadgetDay__   = new HTMLSelectSingleString();
}

/**
**
*/
public HTMLxDate getThis() // ---------------------------------------------
{
 return this;
}

/**
**
*/
public HTMLxDate setCurrentValue( final BasicDate dateToSelect ) // -------
{
 this.dateToSelect = dateToSelect;

 return this;
}

/**
**
*/
public BasicDate getCurrentValue() // -------------------------------------
{
 return this.dateToSelect;
}

/**
** Lecture du formulaire avec une valeur par défaut
**
** @return un object BasicDate
*/
public BasicDate getCurrentValue( // --------------------------------------
    final ServletRequest request
    ) throws HTMLFormException
{
 try {
    return new BasicDate(
                Integer.parseInt( this.gadgetYear_.getCurrentValue( request ) ),
                Integer.parseInt( this.gadgetMonth.getCurrentValue( request ) ),
                Integer.parseInt( this.gadgetDay__.getCurrentValue( request ) )
                );
    }
 catch( cx.ath.choisnet.util.datetime.BasicDateException e ) {
    throw new HTMLFormException( "BasicDateException", e );
    }
}

/**
**
*/
public HTMLxDate append( final Collection<FormItem> a ) // ----------------
{
 this.gadgetYear_.append( a );
 this.gadgetMonth.append( a );
 this.gadgetDay__.append( a );

 return this;
}

/**
**
*/
public HTMLxDate setName( final String name ) // --------------------------
{
 super.setName( name );

 this.gadgetYear_.setName( name + YEAR_SUFFIX );
 this.gadgetMonth.setName( name + MONTH_SUFFIX );
 this.gadgetDay__.setName( name + DAY_SUFFIX );

 return this;
}

/**
**
*/
public HTMLxDate setYearRange( final int from, final int to ) // ----------
{
 this.gadgetYear_.clear();
 this.gadgetYear_.addRange( from, to );

 this.gadgetMonth.clear();
 this.gadgetMonth.addRange( 1, 12 );

 this.gadgetDay__.clear();
 this.gadgetDay__.addRange( 1, 31 );

 return this;
}

/** Construction des données HTML */
public void writeStartTag( final HTMLDocumentWriter out ) // --------------
    throws HTMLDocumentException
{
 this.gadgetYear_.setValue( Integer.toString( this.dateToSelect.getYear()  ) );
 this.gadgetMonth.setValue( Integer.toString( this.dateToSelect.getMonth() ) );
 this.gadgetDay__.setValue( Integer.toString( this.dateToSelect.getDay()   ) );

 final Object[] params = new Object[ 3 ]; // 0:year - 1:month - 2:day

 final HTMLDocumentStringWriter htmlDSW = out.getHTMLDocumentStringWriter();

 this.gadgetYear_.write( htmlDSW );
 params[ 0 ] = htmlDSW.reset();

 this.gadgetMonth.write( htmlDSW );
 params[ 1 ] = htmlDSW.reset();

 this.gadgetDay__.write( htmlDSW );
 params[ 2 ] = htmlDSW.reset();

 try {
    out.append( getLayout( out ).format( params ) );
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
 throw new IllegalStateException();
}

/**
**
*/
public void writeEndTag( final HTMLDocumentWriter out ) // ----------------
    throws HTMLDocumentException
{
 throw new IllegalStateException();
}

/**
**
*/
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 writeStartTag( out );
}

/**
** {0} YEAR
** {1} MONTH
** {2} DAY
*/
protected MessageFormat getLayout( final HTMLDocumentWriter out ) // ------
{
//NOTE ne pas faire
// if( out.getLocale().equals( Locale.FRENCH ) ) { ... }

 if( out.getLocale().getDisplayLanguage().equals( Locale.FRENCH.getDisplayLanguage() ) ) {
    return new MessageFormat(
        "<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\"><tr><td>{2}</td><td>{1}</td><td>{0}</td></tr></table>\n"
        );
    }
 else {
    return new MessageFormat(
        "<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\"><tr><td>{1}</td><td>{2}</td><td>{0}</td></tr></table>\n"
        );
    }
}

} // class

/**
** Lecture du formulaire
**
** @return un object BasicDate
public BasicDate getCurrentBasicDate( final ServletRequest request ) // ---
    throws
       HTMLFormException,
        cx.ath.choisnet.util.datetime.BasicDateException
{
 int year  = Integer.parseInt( this.gadgetYear_.getCurrentValue( request ) );
 int month = Integer.parseInt( this.gadgetMonth.getCurrentValue( request ) );
 int day   = Integer.parseInt( this.gadgetDay__.getCurrentValue( request ) );

 return new BasicDate( year, month, day );
}
*/

/**
** Lecture du formulaire avec une valeur par défaut
**
** @return un object BasicDate
public BasicDate getCurrentBasicDate( // ----------------------------------
    final ServletRequest  request,
    final BasicDate       defaultValue
    )
    throws cx.ath.choisnet.util.datetime.BasicDateException
{
 try {
    return getCurrentBasicDate( request );
    }
 catch( HTMLFormException e ) {
    return new BasicDate( defaultValue );
    }
}
*/

/*
** Lecture des donnée depuis le code HTML, retourne les données sous forme
** de BasicDate.
public BasicDate getValue( final ServletRequest request ) // --------------
    throws cx.ath.choisnet.html.HTMLFormException
{
 return protected_getBasicDateValue( request );
}
*/

/**
** Retourne la valeur sous forme d'entier long
**
** @return un long correspondant à la valeur du gadget.
**
** @see BasicDate#longValue()
public long getLongValue( ServletRequest request ) // ---------------------
    throws cx.ath.choisnet.html.HTMLFormException
{
 return protected_getBasicDateValue( request ).longValue();
}
*/

/**
** Retourne la valeur sous forme de chaîne
**
** @return une String correspondant à la valeur du gadget.
**
** @see BasicDate#toString()
public String getStringValue( final ServletRequest request ) // -----------
    throws cx.ath.choisnet.html.HTMLFormException
{
 return protected_getBasicDateValue( request ).toString();
}
*/
