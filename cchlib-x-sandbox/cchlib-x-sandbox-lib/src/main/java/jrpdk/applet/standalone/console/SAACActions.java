/*
** $VER: SAACActions.java
*/
package jrpdk.applet.standalone.console;

import java.net.URL;

/**
**
**
** @author Claude CHOISNET
** @version 1.00
*/
class SAACActions implements java.awt.event.ActionListener
{

/**
** Interface ActionListener
** <P>
*/
@Override
public void actionPerformed( java.awt.event.ActionEvent anEvent ) // ------
{
 System.out.println( "actionPerformed: " + anEvent );
 System.out.println( "getActionCommand() : " + anEvent.getActionCommand() );
 System.out.println( "paramString() : " + anEvent.paramString() );

 Object o = anEvent.getSource();

 System.out.println( "getSource() : " + o );
 System.out.println( "getSource() class : " + (o != null ? o.getClass().getName() : "") );
}

private StandAloneAppletConsole saacBase;
//private JLabel                  showStatus;
private SAACBrowser             saacBrowser;
//private JCheckBox               viewBrowser;

private final static int        SPACE_PIXELS = 2;

/**
**
*/
public SAACActions( StandAloneAppletConsole saacBase ) // ----------------
{
 //super( new BorderLayout() );
 this.saacBase = saacBase;

 //JPanel firstPart = new JPanel(
 //   new GridLayout( 2, 3, SPACE_PIXELS, SPACE_PIXELS )
 //   );
}

/*
    {
    //
    // View Browser
    //
    viewBrowser = new JCheckBox( "View browser" );
    viewBrowser.addActionListener(
        new ActionListener()
            {
            public void actionPerformed( ActionEvent e ) // ----------------------
            {
             JCheckBox checkbox = (JCheckBox)e.getSource();

             actionShowBrowser( checkbox.isSelected() );
            }
            } // ChangeListener
        );

    viewBrowser.setEnabled( false );
    firstPart.add( viewBrowser );
    }

    {
    //
    // Clear Console
    //
    JButton clearConsole = new JButton( "Clear Console" );

    clearConsole.addActionListener(
        new ActionListener()
            {
            public void actionPerformed( ActionEvent e ) // ----------------------
            {
//             JButton button = (JButton)e.getSource();
             actionClearConsole();
            }
            } // ChangeListener
        );

    firstPart.add( clearConsole );
    }

    {
    //
    // Display memory
    //
    JButton displayMemory = new JButton( "Display memory" );

    displayMemory.addActionListener(
        new ActionListener()
            {
            public void actionPerformed( ActionEvent e ) // ----------------------
            {
             actionDisplayMemory();
            }
            } // ChangeListener
        );

    firstPart.add( displayMemory );
    }

    {
    //
    // Force nettoyage de la m�moire
    //
    JButton garbageCollector = new JButton( "Run garbage collector" );

    garbageCollector.addActionListener(
        new ActionListener()
            {
            public void actionPerformed( ActionEvent e ) // ----------------------
            {
             actionGarbageCollector();
            }
            } // ChangeListener
        );

    firstPart.add( garbageCollector );
    }

 //
 // DISABLED
 //
 for( int i = 0; i < 2; i++ ) {
    JButton bouton = new JButton( "DISABLE" );

    bouton.setEnabled( false );
    firstPart.add( bouton );
    }

 add( "Center"  , firstPart );
 add( "South"   , showStatus = new JLabel( "Done." ) );
}

/**
**
*/
public void actionShowBrowser( boolean view ) // --------------------------
{
/*
 if( view && ( saacBrowser == null ) ) {
    saacBrowser = new SAACBrowser( "Default Browser" );
    }

*/
 if( saacBrowser != null ) {
    saacBrowser.setVisible( view );
    }
}

/**
**
*/
public void actionClearConsole() // ---------------------------------------
{
 saacBase.clear();
}

/**
**
*/
public void actionDisplayMemory() // --------------------------------------
{
 final Runtime  runtime = Runtime.getRuntime();
 final long     total   = runtime.totalMemory();
 final long     free    = runtime.freeMemory();
 final long     used    = total - free;

 saacBase.println( "Total Memory: " + total + "o (" + (total/1024) + "ko)" );
 saacBase.println( "Free Memory: "  + free  + "o (" + (free /1024) + "ko)" );
 saacBase.println( "Memory: "       + used  + "o (" + (used /1024) + "ko)" );
}

/**
**
*/
public void actionGarbageCollector() // -----------------------------------
{
 saacBase.println( "Running garbage collector" );
 Runtime.getRuntime().gc();
 saacBase.println( "Done." );
}


/*
*
** Cette m�thode est conforme � l'Interface AppletContext.
**
** @see AppletContext#showStatus( String )
*
/
public void showStatus( String message ) // -------------------------------
{
 showStatus.setText( message );
}
*/

/**
** Cette m�thode est conforme � l'Interface AppletContext.
**
** @see AppletContext#showDocument( URL )
*/
public void showDocument( URL url ) // ------------------------------------
{
// System.err.println( "SAACCommandsPanel: NOT IMPLEMENTED : showDocument( URL ) : " + url );
 if( saacBrowser == null ) {
    saacBrowser = new SAACBrowser( "Default Browser", url );
    }
 else {
    saacBrowser.jumpTo( url );
    }

 saacBrowser.setVisible( true );
// viewBrowser.setEnabled( true );
// viewBrowser.setSelected( true );
}

/**
** Cette m�thode est conforme � l'Interface AppletContext.
**
** @see AppletContext#showDocument( URL, String )
*/
public void showDocument( URL url,  String target ) // --------------------
{
// System.err.println( "SAACCommandsPanel: NOT IMPLEMENTED : showDocument( URL, String ) : " + url + ", " + target );
 showDocument( url );
}


} // class
