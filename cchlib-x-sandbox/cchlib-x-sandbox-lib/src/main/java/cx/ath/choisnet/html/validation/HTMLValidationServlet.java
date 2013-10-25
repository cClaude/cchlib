/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/validation/HTMLValidationServlet.java
** Description   :
**
**  1.01.___ 2001.01.30 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.validation.ValidationServlet
**  1.30.___ 2005.05.19 Claude CHOISNET
**                      Utilisation de HTMLWriter pour l'affichage des erreurs
**  1.50.___ 2005.05.19 Claude CHOISNET
**                      Adaptation à l'interface HTMLWritable
**  3.02.031 2006.07.24 Claude CHOISNET
**                      Nouveau nom:
**                          cx.ath.choisnet.html.validation.HTMLValidationServlet
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.validation.HTMLValidationServlet
**
*/
package cx.ath.choisnet.html.validation;

import cx.ath.choisnet.html.gadgets.HTMLInputCheckBox;
import cx.ath.choisnet.html.gadgets.HTMLInputText;
import cx.ath.choisnet.html.HTMLBody;
import cx.ath.choisnet.html.HTMLForm;
import cx.ath.choisnet.html.HTMLHeader;
import cx.ath.choisnet.html.util.HTMLDocument;
import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.util.datetime.BasicDate;
import cx.ath.choisnet.util.datetime.BasicTime;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
** <p>
** Servlet de validation du package cx.ath.choisnet.html
** </p>
**
** @author Claude CHOISNET
** @version 3.02.031
*/
public class HTMLValidationServlet
    extends HttpServlet
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public void service( // ---------------------------------------------------
    final HttpServletRequest    request,
    final HttpServletResponse   response
    )
    throws
        java.io.IOException,
        javax.servlet.ServletException
{
 response.setContentType( "text/html" );

 PrintWriter out = response.getWriter();

 try {
    doService( request, response, out );
    }
 catch( HTMLDocumentException e ) {
    throw new javax.servlet.ServletException( e );
    }

}
/**
**
*/
public void doService( // -------------------------------------------------
    final HttpServletRequest    request,
    final HttpServletResponse   response,
    final PrintWriter           out
    )
    throws HTMLDocumentException
{
 final BasicTime    beginTime   = new BasicTime();
 final HTMLDocument document    = new HTMLDocument();

 document.setHeader(
    new HTMLHeader().setTitle( "hello the world !" )
    .add( "<style type=\"text/css\">\n" )
    .add( "\n" )
    .add( "</style>\n" )
    );

 document.setBody(
    new HTMLBody()
        .add( "start build() " + beginTime + "<br />\n" )
    );

 final HTMLForm myForm = new HTMLForm()
    .setName( "myForm" )
    .setAction( "/debug/InfosServlet" )
    .add( "<table border=\"2\" cellspacing=\"1\" cellpadding=\"5\" width=\"100%\">")

    .add( "<tr><td>" )
    .add(
        new HTMLInputText()
            .setName( "HTMLInputText" )
            .setValue( "une valeur" )
            .setSize( 16 )
            .setMaxLength( 16 )
        )
    .add( "</td><td>" )
    .add( "new HTMLInputText().setName( \"TEST1\" ).setValue( \"une valeur\" ).setSize( 16 ).setMaxLength( 16 )" )
    .add( "</td></tr>"  )

    .add( "<tr><td>" )
    .add(
        new HTMLInputText()
            .setCSSClass( "setCSSClass" )
            .setId( "setId" )

            .setName( "HTMLInputText" )

            .setAccept( "setAccept" )
            .setAccessKey( 'a' )
            .setAlt( "setAlt" )
            .setChecked( true )
            .setCurrentValue( "xx" )
             //.setDisabled( true )
            .setIsMap( true )
            .setMaxLength( 8 )
            .setReadOnly( true )
            .setSize( 16 )
            .setSrc( "setSrc" )
            .setTabIndex( 1 )
            .setValue( "setValue" )
        )
    .add( "</td><td>" )
    .add( "TEST" )
    .add( "</td></tr>"  )

    .add( "<tr><td>" )
    .add(
        new HTMLInputCheckBox()
            .setName( "HTMLInputCheckBox" )
            .setChecked()
        )
    .add( "</td><td>" )
    .add( "HTMLInputCheckBox yes" + HTMLInputCheckBox.booleanValue( "yes" )  )
    .add( "HTMLInputCheckBox " + HTMLInputCheckBox.booleanValue( "HTMLInputCheckBox" )  )
    .add( "no " + HTMLInputCheckBox.booleanValue( "no" )  )
    .add( "on " + HTMLInputCheckBox.booleanValue( "on" )  )
    .add( "</td></tr>"  )

    .add( "<tr><td>" )
    .add(
        new cx.ath.choisnet.html.gadgets.extended.HTMLxDate()
            .setName( "HTMLxDate" )
            .setYearRange( 1950, 2050 )
            .setCurrentValue( new BasicDate() )
        )
    .add( "</td><td>" )
    .add( "new HTMLxDate().setName( \"TEST2\" ).setYearRange( 1950, 2050 ).setValue( new BasicDate() )" )
    .add( "</td></tr>"  )

    .add( "</table>" );

 final BasicTime endTime = new BasicTime();

 document.getBody()
            .add( myForm )
            .add( "end build() " + endTime + "<br />\n" );

 try {
    document.getBody().add( "=> " + BasicTime.subtract( endTime, beginTime ) + "<br />\n" );
    }
 catch( cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException e ) {
    throw new RuntimeException( e );
    }

 document.write(
    new cx.ath.choisnet.html.util.impl.HTMLDocumentWriterImpl(
        out,
        Locale.FRENCH
        )
    );

}

} // class

/*
protected void addTools_HTMLBuilder_AdvancedGadgets() // -----------------
    throws
        BasicDateTimeException,
        HTMLDocumentException
{
 addGadgets(
    new AGDate(
        getGN(),
//        Locale.FRANCE,
        1950,               // fromYear
        2050,               // toYear
        new BasicDate(),    // dateToSelect
        null,               // AbstractJavascript  javascriptYear,
        null,               // AbstractJavascript  javascriptMonth,
        null                // AbstractJavascript  javascriptDay
        ),
    "AGDate( NAME, Locale.FRANCE, 1950, 2050, today, null, null, null )"
    );

 addGadgets(
    new AGDate(
        getGN(),
//        Locale.ENGLISH,
        1950,               // fromYear
        2050,               // toYear
        new BasicDate(),    // dateToSelect
        null,               // AbstractJavascript javascriptYear,
        null,               // AbstractJavascript  javascriptMonth,
        null                // AbstractJavascript  javascriptDay
        ),
    "AGDate( NAME, Locale.ENGLISH, 1950, 2050, today, null, null, null )"
    );

 addGadgets(
    new AGFormatTime(
        getGN(),
        new SimpleDateFormat( "hh:mm a", Locale.FRANCE ),
        new BasicTime(  2*60*60 ),              // timeFrom
        new BasicTime( 22*60*60 ),              // timeTo
        new BasicTime(),                        // timeToSelect
        null,                                   // Integer  minutesStep
        null                                    // AbstractJavascript  javascript
        ),
    "AGFormatTime( NAME, Locale.FRANCE, 2h, 22h, now, null, null )"
    );

 addGadgets(
    new AGFormatTime(
        getGN(),
        new SimpleDateFormat( "hh:mm a", Locale.ENGLISH ),
        new BasicTime(  2*60*60 ),              // timeFrom
        new BasicTime( 22*60*60 ),              // timeTo
        new BasicTime(),                        // timeToSelect
        null,                                   // Integer  minutesStep
        null                                    // AbstractJavascript  javascript
        ),
    "AGFormatTime( NAME, Locale.ENGLISH, 2h, 22h, now, null, null )"
    );

 addGadgets(
    new AGTime(
        getGN(),
//        Locale.FRANCE,
        2,
        22,
        new BasicTime(),
        null,                       // Integer             minutesStep
        null,                       // AbstractJavascript  javascriptHours
        null                        // AbstractJavascript  javascriptMinutes
        ),
    "AGTime( NAME, Locale.FRANCE, 2h, 22h, now, null, null )"
    );
}


protected void addTools_HTMLBuilder_BasicGadgets() // ---------------------
    throws HTMLDocumentException
{
 String[] radioValue = { "BGInputRadio 1", "BGInputRadio 2", "BGInputRadio 3" };

 addGadgets(
    new BGInputRadio( getGN(), radioValue, radioValue, 2, null ),
    "BGInputRadio( NAME, radioValue, radioValue, 2, null )"
    );

 addGadgets(
    new  BGInputText(
        getGN(),
        new Integer( 6 ),   // lengthChars ou null
        new Integer( 8 ),   // maxChars ou null
        "BGInputText",
        null                // AbstractJavascript
        ),
    "BGInputText( NAME, 6, 8, 'defText', null )"
    );

 addGadgets(
    new BGCheckbox( getGN(), true, null ),
    "BGCheckbox( NAME, true, null )"
    );
}

*/