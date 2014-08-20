/*
** $VER: StandAloneAppletConsole.java
*/
package jrpdk.applet.standalone.console;

import java.applet.AppletContext;
import java.io.PrintStream;

/**
** Interface
**
** @author Claude CHOISNET
** @version 1.00 24/01/2001
*/
public interface StandAloneAppletConsole
{
/**
** Retrouve l'object AppletContext lier e la console.
**
** @return un object de type AppletContext
*/
abstract public AppletContext getAppletContext(); // ----------------------

/**
** Ferme le console
*/
abstract public void close(); // ------------------------------------------

/**
** Efface le texte de la console
*/
abstract public void clear(); // ------------------------------------------

/**
** Affiche une ligne dans la console
**
** @param s chaene devrant etre ecrite sur la console.
*/
abstract public void println( String s ); // ------------------------------

/**
** Retrouve l'objet permettant d'ecrire sur la console.
**
** @return un objet PrintStream correspondant e la sortie vers la console.
*/
abstract public PrintStream getOut(); // ----------------------------------

} // class
