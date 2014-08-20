/*
** $VER: StandAloneAppletInitializer.java
*/
package jrpdk.applet.standalone;

import jrpdk.applet.standalone.console.StandAloneAppletConsole;
import java.applet.AppletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
**
**
** @author Claude CHOISNET
** @version 1.00 26/01/2001
*/
public abstract class StandAloneAppletInitializer
    implements StandAloneAppletInitializerInterface
{
/** */
private URL documentBase;

/** */
private AppletContext appletContext;

/**
** Initialisations
*/
@SuppressWarnings("deprecation")
protected StandAloneAppletInitializer( // ---------------------------------
    AppletContext appletContext
    )
{
 //
 // Initialisation des informations permettant de completer la class Applet
 //
 File file = new File( "." );

 try {
    this.documentBase = file.toURL();
    }
 catch( MalformedURLException ignore ) {
    this.documentBase = null;
    }

 this.appletContext = appletContext;
}



/**
** Methode permettant de completer la classe java.applet.Applet
*/
@Override
public URL getDocumentBase() // -------------------------------------------
{
 return documentBase;
}

/**
** Methode permettant de completer la classe java.applet.Applet
*/
@Override
public URL getCodeBase() // -----------------------------------------------
{
 return documentBase;
}

/**
** Methode permettant de completer la classe java.applet.Applet
*/
@Override
public AppletContext getAppletContext() // --------------------------------
{
 return appletContext;
}

/**
** Contruction d'un initialiser par defaut e partir des arguments.
** <P>
** Methode permettant de completer la classe java.applet.Applet, en
** particulie la methode getParameter().
** <P>
** La methode tentera de construire un initialiser e avec le fichier
** HTML en cas d'echec, avec le fichier properties et enfin avec
** le tableau de chaene.
** <P>
** <B>Actuellement le parametre HTMLFileName est ignore</B>
**
** @param HTMLFileName          URL d'un fichier HTML depuis lequel les
**                              parametres seront lus.
** @param propertiesFileName    Nom d'un fichier properties depuis lequel
**                              les parametres seront lus.
** @param args                  Tableau de chaene en provenance de la
**                              methode statique main() depuis lequel les
**                              parametres seront lus.
**
**
*/
public static StandAloneAppletInitializerInterface getDefault( // ---------
    URL                     HTMLFileName,
    String                  propertiesFileName,
    String[]                args,
    StandAloneAppletConsole console
    )
{
 StandAloneAppletInitializer appletInit;

 try {
    appletInit =
        new StandAloneAppletProperties(
            new FileInputStream( propertiesFileName ),
            console.getAppletContext()
            );
    }
 catch( IOException e ) {
    appletInit = null;
    }

 if( appletInit == null ) {
    appletInit = new StandAloneAppletCLI( args, console.getAppletContext() );
    }

 return appletInit;
}

} // class
