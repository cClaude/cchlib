/*
** $VER: StandAloneApplet.java
*/
package jrpdk.applet.standalone;

import java.applet.Applet;
import java.applet.AppletContext;
import java.net.URL;

/**
** Conform�ment � l'object java.awt.Component cette classe impl�mente
** correctenement l'interface Serializable
**
** @author Claude CHOISNET
** @version 1.00 30/01/2001
*/
public class StandAloneApplet extends Applet
{
    private static final long serialVersionUID = 1L;

/** */
private Boolean isStandAlone;

/** */
private StandAloneAppletInitializerInterface appletInitializer;

/**
** Initialisation par d�faut, il s'agit typiquement de l'initialisation
** faite par un navigateur.
*/
public StandAloneApplet() // ----------------------------------------------
{
 super();

 this.isStandAlone      = new Boolean( false );
 this.appletInitializer = null;
}

/**
** Constructeur �quivalent au constructeur par d�faut, puis � l'appel de
** la m�thode setContext().
**
** @see #setContext
*/
public StandAloneApplet( // -----------------------------------------------
    StandAloneAppletInitializerInterface appletInitializer
    )
{
 super();

 setContext( appletInitializer );
}


/**
** M�thode permettant de d�finir un environnement d'initialisation �quivalent
** � ce qu'un navigateur pour apporter comme context � un objet Applet.
**
** @see StandAloneAppletInitializerInterface
** @see java.applet.Applet
** @see java.applet.AppletContext
*/
public void setContext( // ------------------------------------------------
    StandAloneAppletInitializerInterface appletInitializer
    )
{
 this.isStandAlone          = new Boolean( true );
 this.appletInitializer     = appletInitializer;
}

/**
**
*/
public boolean isStandAlone() // ------------------------------------------
{
 return isStandAlone.booleanValue();
}

/**
** Applet
**
** @see java.applet.Applet#getDocumentBase()
*/
@Override
public URL getDocumentBase() // -------------------------------------------
{
 if( isStandAlone.booleanValue() ) {
    return appletInitializer.getDocumentBase();
    }
 else {
    return super.getDocumentBase();
    }
}

/**
** Applet
**
** @see java.applet.Applet#getCodeBase()
*/
@Override
public URL getCodeBase() // -----------------------------------------------
{
 if( isStandAlone.booleanValue() ) {
    return appletInitializer.getCodeBase();
    }
 else {
    return super.getCodeBase();
    }
}

/**
** Applet
**
** @see java.applet.Applet#getParameter( String )
*/
@Override
public String getParameter( String name ) // ------------------------------
{
 if( isStandAlone.booleanValue() ) {
    return appletInitializer.getParameter( name );
    }
 else {
    return super.getParameter( name );
    }
}

/**
** Applet
**
** @see java.applet.Applet#getAppletContext()
*/
@Override
public AppletContext getAppletContext() // --------------------------------
{
 if( isStandAlone.booleanValue() ) {
    return appletInitializer.getAppletContext();
    }
 else {
    return super.getAppletContext();
    }
}

} // class

