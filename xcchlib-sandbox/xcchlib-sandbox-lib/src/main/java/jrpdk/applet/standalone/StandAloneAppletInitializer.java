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
 // Initialisation des informations permettant de compl�ter la class Applet
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
** M�thode permettant de compl�ter la classe java.applet.Applet
*/
@Override
public URL getDocumentBase() // -------------------------------------------
{
 return documentBase;
}

/**
** M�thode permettant de compl�ter la classe java.applet.Applet
*/
@Override
public URL getCodeBase() // -----------------------------------------------
{
 return documentBase;
}

/**
** M�thode permettant de compl�ter la classe java.applet.Applet
*/
@Override
public AppletContext getAppletContext() // --------------------------------
{
 return appletContext;
}

/**
** Contruction d'un initialiser par d�faut � partir des arguments.
** <P>
** M�thode permettant de compl�ter la classe java.applet.Applet, en
** particuli� la m�thode getParameter().
** <P>
** La m�thode tentera de construire un initialiser � avec le fichier
** HTML en cas d'�chec, avec le fichier properties et enfin avec
** le tableau de cha�ne.
** <P>
** <B>Actuellement le param�tre HTMLFileName est ignor�</B>
**
** @param HTMLFileName          URL d'un fichier HTML depuis lequel les
**                              param�tres seront lus.
** @param propertiesFileName    Nom d'un fichier properties depuis lequel
**                              les param�tres seront lus.
** @param args                  Tableau de cha�ne en provenance de la
**                              m�thode statique main() depuis lequel les
**                              param�tres seront lus.
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
