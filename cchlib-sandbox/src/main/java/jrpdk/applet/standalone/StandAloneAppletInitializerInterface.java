/*
** $VER: StandAloneAppletInitializerInterface.java
*/
package jrpdk.applet.standalone;

import java.net.URL;
import java.applet.AppletContext;

/**
**
**
** @author Claude CHOISNET
** @version 1.00 24/01/2001
*/
public /*abstract*/ interface StandAloneAppletInitializerInterface
{
/**
** java.applet.Applet
*/
public abstract URL getDocumentBase(); // ---------------------------------

/**
** java.applet.Applet
*/
public abstract URL getCodeBase(); // -------------------------------------

/**
** java.applet.Applet
*/
public abstract String getParameter( String name ); // --------------------

/**
** java.applet.Applet
*/
public abstract AppletContext getAppletContext(); // ----------------------

} // class
