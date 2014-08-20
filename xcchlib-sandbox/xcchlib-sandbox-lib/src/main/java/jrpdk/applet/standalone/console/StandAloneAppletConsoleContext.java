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
import java.util.NoSuchElementException;

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
** @return toujours null (faux, mais conforme e la documentation: not found)
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
public Enumeration<Applet> getApplets() // ---------------------------------
{
 System.err.println( "StandAloneAppletConsoleContext FAKE : getApplets()" );

 return new Enumeration<Applet>(){

    @Override
    public boolean hasMoreElements()
    {
        return false;
    }

    @Override
    public Applet nextElement()
    {
        return null;
    }};
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
** Cette methode est conforme e l'Interface AppletContext
**
** @see AppletContext#getImage( URL )
*/
@Override
public Image getImage( URL url ) // ---------------------------------------
{
 return toolkit.getImage( url ); // OK
}

/**
** Cette methode est conforme e l'Interface AppletContext.
**
** @see AppletContext#showDocument( URL )
*/
@Override
public void showDocument( URL url ) // ------------------------------------
{
 commandsPanel.showDocument( url );
}

/**
** Cette methode est conforme e l'Interface AppletContext.
**
** @see AppletContext#showDocument( URL, String )
*/
@Override
public void showDocument( URL url,  String target ) // --------------------
{
 commandsPanel.showDocument( url, target );
}

/**
** Cette methode est conforme e l'Interface AppletContext.
**
** @see AppletContext#showStatus( String )
*/
@Override
public void showStatus( String message ) // -------------------------------
{
 commandsPanel.showStatus( message );
}

/**
** @return
 * @return un Iterator valide mais sans élément.
*/
@Override
public Iterator<String> getStreamKeys()
{
 return new Iterator<String>() {
    @Override
    public boolean hasNext() { return false; }

    @Override
    public String next() throws NoSuchElementException
    {
     throw new NoSuchElementException();
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
** @return toujours null (faux, mais conforme e la documentation: not found)
*/
@Override
public InputStream getStream( String str )
{
 return null;
}

} // class
