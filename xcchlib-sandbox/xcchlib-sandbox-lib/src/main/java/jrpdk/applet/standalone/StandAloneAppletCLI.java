/*
** $VER: StandAloneAppletCLI.java
*/
package jrpdk.applet.standalone;

import java.applet.AppletContext;

/**
**
**
** @author Claude CHOISNET
** @version 1.00
*/
public class StandAloneAppletCLI
    extends StandAloneAppletInitializer
{
/** Arguments mis en minuscules */
private String[] standAloneArgsFromStringsLowerCase;

/** Arguments */
private String[] standAloneArgsFromStrings;

/**
**
*/
public StandAloneAppletCLI( // --------------------------------------------
    String[]        parameters,
    AppletContext   appletContext
    )
{
 super( appletContext );

 this.standAloneArgsFromStrings             = parameters;
 this.standAloneArgsFromStringsLowerCase    = new String[ parameters.length ];

 for( int i = 0; i < parameters.length; i++ ) {
    this.standAloneArgsFromStringsLowerCase[ i ] = parameters[ i ].toLowerCase();
    }
}

/**
** Utilise le tableau de chaene parameters du constructeur (en general, le
** tableau donne par la methode statique main() ) pour simuler la methode
** equivalente de la classe java.applet.Applet
**
** @see java.applet.Applet#getParameter( String )
*/
@Override
public String getParameter( String name ) // ------------------------------
// public String DEBUG_getParameter( String name )
{
 int    paramNameLen    = name.length();
 String nameLowerCase   = name.toLowerCase();
 String currentParam;

 for( int i = 0; i < standAloneArgsFromStringsLowerCase.length; i++ ) {
    currentParam = standAloneArgsFromStringsLowerCase[ i ];

    if( (currentParam != null) && currentParam.startsWith( nameLowerCase ) ) {

        if( currentParam.length() == paramNameLen ) {
            return "";
            }
        else { // if( currentParam.lenght() > paramNameLen )
            if( currentParam.charAt( paramNameLen ) == '=' ) {
                return standAloneArgsFromStrings[ i ].substring( paramNameLen + 1 );
                }
            }
        }
    }
// System.out.println( "***Warning " + name + " not found !" );

 return null;
}

/*
public String getParameter( String name ) // ------------------------------
{
 String value = DEBUG_getParameter( name );

 System.out.println( "getParameter( '" + name + "' ) => '" + value + "'" );

 return value;
}
*/
} // class
