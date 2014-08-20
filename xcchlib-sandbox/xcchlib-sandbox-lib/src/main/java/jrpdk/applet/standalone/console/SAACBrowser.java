/*
** $VER: SAACBrowser.java
*/
package jrpdk.applet.standalone.console;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.*;
import javax.swing.text.*;
import javax.swing.event.*;

/**
** Conformement e l'object java.awt.Component cette classe implemente
** correctenement l'interface Serializable
**
** @author Claude CHOISNET
** @version 1.00 30/01/2001
*/
public class SAACBrowser extends JFrame // extends JInternalFrame
{
    private static final long serialVersionUID = 1L;
/** */
private HtmlPane html;

/**
**
*/
public SAACBrowser( String title, URL url ) // ----------------------------
{
 super( title );
// super("Help", true, true, true, true);
// setFrameIcon( (Icon)UIManager.get("Tree.openIcon")); // PENDING(steve) need more general palce to get this icon
 setBounds( 200, 25, 400, 400);

 html = new HtmlPane( url );

 setContentPane( html );
}

public void jumpTo( URL url ) // ------------------------------------------
{
 html.linkActivated( url );
}

} // SAACBrowser

class HtmlPane extends JScrollPane implements HyperlinkListener
{
    private static final long serialVersionUID = 1L;
/** */
JEditorPane html;

/*
*
**
*
/
public HtmlPane() // ------------------------------------------------------
{
 this( getDefaultURL() );
}

/*
*
**
*
/
private static URL getDefaultURL() // -------------------------------------
{
 try {
    File f = new File ( "HelpFiles/toc.html" );

    return new URL( "file:" + f.getAbsolutePath() );
    }
 catch( MalformedURLException e ) {
    System.err.println( "HtmlPane - Malformed URL: " + e );
    System.out.println( "Malformed URL: " + e );

    return null;
    }
} */

/**
**
*/
public HtmlPane( URL url ) // ---------------------------------------------
{
 try {
    html = new JEditorPane( url );
    html.setEditable( false );
    html.addHyperlinkListener( this );

    JViewport vp = getViewport();
    vp.add( html );
    }
 catch( IOException e ) {
    System.err.println( "HtmlPane - IOException: " + e );
    System.out.println( "IOException: " + e );
    }
// catch( Exception e ) {
//    System.err.println( "HtmlPane - Exception: " + e );
///    System.out.println( "Exception: " + e );
//    }
}

/**
* Notification of a change relative to a
* hyperlink.
*/
@Override
public void hyperlinkUpdate( HyperlinkEvent e ) // ------------------------
{
 if( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) {

    System.out.println( "New url: " + e.getURL() );

    linkActivated( e.getURL() );
    }
}

/**
* Follows the reference in an
* link.  The given url is the requested reference.
* By default this calls <a href="#setPage">setPage</a>,
* and if an exception is thrown the original previous
* document is restored and a beep sounded.  If an
* attempt was made to follow a link, but it represented
* a malformed url, this method will be called with a
* null argument.
*
* @param u the URL to follow
*/
protected void linkActivated( URL u ) // ----------------------------------
{
 Cursor c           = html.getCursor();
 Cursor waitCursor  = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );

 html.setCursor( waitCursor );
 SwingUtilities.invokeLater( new PageLoader( u, c ) );
}

/**
* temporary class that loads synchronously (although
* later than the request so that a cursor change
* can be done).
*/
class PageLoader implements Runnable
{
URL     url;
Cursor  cursor;

PageLoader( URL u, Cursor c ) // ------------------------------------------
{
 url    = u;
 cursor = c;
}

@Override
public void run() // ------------------------------------------------------
{
 if( url == null ) {
    // restore the original cursor
    html.setCursor( cursor );

    // PENDING(prinz) remove this hack when
    // automatic validation is activated.
    Container parent = html.getParent();

    parent.repaint();
    }
 else {
    Document doc = html.getDocument();

    try {
        html.setPage( url );
        }
    catch( IOException ioe ) {
        System.err.println( "*** " + ioe );

        html.setDocument( doc );

        getToolkit().beep();
        }
    finally {
        // schedule the cursor to revert after
        // the paint has happended.
        url = null;

        SwingUtilities.invokeLater( this );
        }
    }
}

} // class PageLoader

} // class HtmlPane
