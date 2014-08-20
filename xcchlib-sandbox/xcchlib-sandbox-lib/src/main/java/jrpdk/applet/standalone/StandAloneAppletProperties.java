/*
** $VER: StandAloneAppletProperties.java
*/
package jrpdk.applet.standalone;

import java.applet.AppletContext;
import java.io.InputStream;
import java.util.Properties;

/**
** Emulation des parametres d'une applet dans un fichier properties.
**
** @author Claude CHOISNET
** @version 1.00 24/01/2001
*/
public class StandAloneAppletProperties
    extends StandAloneAppletInitializer
{
/**
**
*/
private Properties standAloneArgsFromProperties;

/**
**
*/
public StandAloneAppletProperties( // -------------------------------------
    InputStream     parametersProperties,
    AppletContext   appletContext
    )
    throws java.io.IOException
{
 super( appletContext );

 this.standAloneArgsFromProperties  = new Properties();
 this.standAloneArgsFromProperties.load( parametersProperties );
}

/**
** Attention cette methode n'est pas completement compatible avec la
** methode de la classe Apllet, car elle est partiellement sensible
** e la case.
**
** @see java.applet.Applet#getParameter( String )
*/
@Override
public String getParameter( String name ) // ------------------------------
// public String DEBUG_getParameter( String name )
{
 String value = standAloneArgsFromProperties.getProperty( name );

 if( value == null ) {
    value = standAloneArgsFromProperties.getProperty( name.toUpperCase() );

    if( value == null ) {
        value = standAloneArgsFromProperties.getProperty( name.toLowerCase() );
        }
    }

// System.out.println( "getParameter( '" + name + "' ) => '" + value + "'" );

 return value;
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
