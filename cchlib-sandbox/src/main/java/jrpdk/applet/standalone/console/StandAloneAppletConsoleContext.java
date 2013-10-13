/*
** $VER: StandAloneAppletConsoleContext.java
*/
package jrpdk.applet.standalone.console;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
**
**
** @author Claude CHOISNET
** @version 1.00 24/01/2001
*/
public class StandAloneAppletConsoleContext implements AppletContext
{
/** */
private SAACCommandsPanel commandsPanel;

/** */
private Toolkit toolkit;

/**
**
*/
public StandAloneAppletConsoleContext( // ---------------------------------
    SAACCommandsPanel commandsPanel
    )
{
 this.commandsPanel = commandsPanel;
 this.toolkit       = Toolkit.getDefaultToolkit();
}

/**
** Interface AppletContext
**
** @return toujours null (faux, mais conforme � la documentation: not found)
*/
@Override
public Applet getApplet( String name ) // ---------------------------------
{
 System.err.println( "StandAloneAppletConsoleContext FAKE: getApplet( String ) : " + name );

 return null; // Not found !
}

/**
** Interface AppletContext
*/
@Override
public Enumeration getApplets() // ---------------------------------
{
 System.err.println( "StandAloneAppletConsoleContext FAKE : getApplets()" );

 return (new Vector()).elements(); // fake
}

/**
** Interface AppletContext
*/
@Override
public AudioClip getAudioClip( URL url ) // -------------------------------
{
 System.err.println( "StandAloneAppletConsoleContext NOT IMPLEMENTED : getAudioClip( URL ) : " + url );

 return null;
}

/**
** Cette m�thode est conforme � l'Interface AppletContext
**
** @see AppletContext#getImage( URL )
*/
@Override
public Image getImage( URL url ) // ---------------------------------------
{
 return toolkit.getImage( url ); // OK
}

/**
** Cette m�thode est conforme � l'Interface AppletContext.
**
** @see AppletContext#showDocument( URL )
*/
@Override
public void showDocument( URL url ) // ------------------------------------
{
 commandsPanel.showDocument( url );
}

/**
** Cette m�thode est conforme � l'Interface AppletContext.
**
** @see AppletContext#showDocument( URL, String )
*/
@Override
public void showDocument( URL url,  String target ) // --------------------
{
 commandsPanel.showDocument( url, target );
}

/**
** Cette m�thode est conforme � l'Interface AppletContext.
**
** @see AppletContext#showStatus( String )
*/
@Override
public void showStatus( String message ) // -------------------------------
{
 commandsPanel.showStatus( message );
}

/**
** @return un Iterator valide mais sans �l�ment.
*/
@Override
public Iterator getStreamKeys()
{
 return new Iterator() {
    @Override
    public boolean hasNext() { return false; }

    @Override
    public Object next() throws java.util.NoSuchElementException
    {
     throw new java.util.NoSuchElementException();
    }

    @Override
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
    };
}

/**
** Bidon: throw new UnsupportedOperationException()
*/
@Override
public void setStream( String str, InputStream input )
{
 throw new UnsupportedOperationException();
}

/**
** @return toujours null (faux, mais conforme � la documentation: not found)
*/
@Override
public InputStream getStream( String str )
{
 return null;
}

} // class
