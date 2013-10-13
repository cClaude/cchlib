/*
** $VER: StandAloneAppletConsoleJFrame.java
*/
package jrpdk.applet.standalone.console;

import jrpdk.io.swing.OutputStreamInJTextArea;
import jrpdk.applet.standalone.StandAloneExitInterface;
import jrpdk.applet.standalone.StandAloneExitWindowAdapter;
import java.applet.AppletContext;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.PrintStream;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

/**
** Console g�n�rique.
** <PRE>
**  StandAloneExitInterface exit = new StandAloneExitInterface()
**     {
**     public void exit()
**         {
**             System.exit( 0 );
**         }
**     };
**
**  StandAloneAppletConsoleJFrame console =
**     new StandAloneAppletConsoleJFrame();
**
**  console.addWindowListener( new StandAloneExitWindowAdapter( exit ) );
**
**  console.redirectOut();
**  console.redirectErr();
**
**  console.setTitle( "My Title" );
** </PRE>
** <P>
** Conform�ment � l'object java.awt.Component cette classe impl�mente
** correctenement l'interface Serializable
**
**
** @author Claude CHOISNET
** @version 1.00 24/01/2001
*/
public class StandAloneAppletConsoleJFrame
    extends JFrame
    implements StandAloneAppletConsole
{
    private static final long serialVersionUID = 1L;
private PrintStream     saveSystemErr   = null;
private PrintStream     saveSystemOut   = null;
private PrintStream     consoleOut;
private AppletContext   context;
// private TextArea        consoleTextArea;
private JTextArea       consoleJTextArea;
private JMenuBar        consoleJMenuBar = null;
private JMenu           consoleJMenu    = null;
private SAACActions     consoleActions  = null;

private final static String CLS = new String();

/**
**
*/
public StandAloneAppletConsoleJFrame() // ---------------------------------
{
 super();

 Dimension  screenSize      = Toolkit.getDefaultToolkit().getScreenSize();
 int        frameWidth      = 500;
 int        frameHeight     = getRatio( screenSize.height, 2f / 3f );

 Insets insets = getInsets(); // JDK 1.1

 int        frameRealWidth  = insets.left + insets.right  + frameWidth;
 int        frameRealHeight = insets.top  + insets.bottom + frameHeight;
 Point      screenLocation  = new Point(
                screenSize.width - frameRealWidth,
                screenSize.height - frameRealHeight
                );
 this.setSize( frameRealWidth, frameRealHeight );

 this.setLocation( screenLocation );

 /* m�thode pour extends JFrame */
 Container thisContainer = getContentPane();

 //TextArea: consoleTextArea = new TextArea();
 //TextArea: consoleTextArea.setEditable( false );
 //TextArea: thisContainer.add( consoleTextArea, "Center" );

 consoleJTextArea = new JTextArea();
 consoleJTextArea.setEditable( false );

 JScrollPane areaScrollPane = new JScrollPane( consoleJTextArea );
 areaScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );

 thisContainer.add( areaScrollPane, "Center" );

 //
 // Gestion des actions de la console
 //
 consoleActions = new SAACActions( this );

 //
 //
 //
 buildConsoleMenu();

 SAACCommandsPanel commands = new SAACCommandsPanel( this );

 this.context = new StandAloneAppletConsoleContext( commands );
 thisContainer.add( commands, "South" );

 /* --- */

 /* m�thode pour "extends Frame"
 this.add( myTextArea, "Center" );
 this.add( status, "South" );
 /* --- */

 this.show();
}

/**
**
*/
public StandAloneAppletConsoleJFrame( String title ) // -------------------
{
 this();

 setTitle( title );
}


/**
**
*/
@Override
public AppletContext getAppletContext() // --------------------------------
{
 return context;
}

/**
** Ferme la console et restaure les I/O par d�faut, et lib�re les ressources
** de la fen�tre.
*/
@Override
public void close() // ----------------------------------------------------
{
 restaureOut();
 restaureErr();
 closeConsoleStream();

 dispose();
}

/**
**
*/
@Override
public void clear() // ----------------------------------------------------
{
 //TextArea: consoleTextArea.setText( "" );
 consoleJTextArea.setText( CLS );
}

/**
**
*/
@Override
public void println( String s ) // ----------------------------------------
{
// console.append( s + "\n" );
 consoleOut.flush();
 consoleOut.println( s );
}

/**
**
*/
@Override
public PrintStream getOut() // --------------------------------------------
{
 return consoleOut;
}

/**
** Appel la m�thode {@link #close()} puis la m�thode finalize() parente.
*/
@Override
protected void finalize() throws Throwable // -----------------------------
{
 this.close();

 super.finalize();
}

/**
**
*/
public void closeConsoleStream() // ---------------------------------------
{
 if( consoleOut != null ) {
    consoleOut.close();
    consoleOut = null;
    }
}

/**
**
*/
private void createConsoleStream() // -------------------------------------
{
 if( consoleOut == null ) {
    //TextArea: newOutErr = new PrintStream( new OutputStreamInTextArea( consoleTextArea ) );
    consoleOut = new PrintStream( new OutputStreamInJTextArea( consoleJTextArea ) );
    }
}

/**
** Redirect System.out to console
*/
public void redirectOut()
{
 if( saveSystemOut == null ) {
    //
    // not yet set
    //
    createConsoleStream();

    saveSystemOut = System.out;

    System.setOut( consoleOut );
    }
}

/**
** Restaure original System.out
*/
public void restaureOut()
{
 if( saveSystemOut == null ) {
    System.setOut( saveSystemOut );
    saveSystemOut = null;
    }
}

/**
** Redirect System.err to console
*/
public void redirectErr()
{
 if( saveSystemErr == null ) {
    //
    // not yet set
    //
    createConsoleStream();

    saveSystemErr = System.err;

    System.setErr( consoleOut );
    }
}

/**
** Restaure original System.err
*/
public void restaureErr()
{
 if( saveSystemErr == null ) {
    System.setErr( saveSystemErr );
    saveSystemErr = null;
    }
}

/**
** calcul la valeur enti�re de a * b
*/
private static int getRatio( int a, float b ) // --------------------------
{
 float r = a * b;

 return (int)r;
}

/**
** @deprecated use {@link #restaureOut()} and {@link #restaureErr()} or {@link #close()}
*/
@Deprecated
public void restaureOutput() // -------------------------------------------
{
 restaureOut();
 restaureErr();

 if( consoleOut != null ) {
    consoleOut.close();
    consoleOut = null;
    }
}

/**
** Construction d'un object StandAloneAppletConsole g�n�rique.
**
** @param exit  Objet StandAloneExitInterface permettant de d�finir
**              la m�thode appell�e lors de la fermeture de la console
**              par l'utilisateur.
** @param title Objet String contenant le nom de la fen�tre de la
**              la console.
**
** @deprecated no remplacement.
*/
@Deprecated
public static StandAloneAppletConsole getDefault( // ----------------------
    StandAloneExitInterface exit,
    String                  title
    )
{
 StandAloneAppletConsoleJFrame console =
    new StandAloneAppletConsoleJFrame( title );

 console.addWindowListener( new StandAloneExitWindowAdapter( exit ) );

 console.redirectOut();
 console.redirectErr();

 return console;
}

/**
**
*/
private void buildConsoleMenu()
{
 consoleJMenuBar = new JMenuBar();

 setJMenuBar( consoleJMenuBar );

 //Build the first menu.
 consoleJMenu = new JMenu( "Console" );
 consoleJMenuBar.add( consoleJMenu );

 JMenuItem item;

 item = new JMenuItem( "show memory use" );
 item.addActionListener( consoleActions );
 consoleJMenu.add( item );

 item = new JMenuItem( "clear" );
 item.addActionListener( consoleActions );
 consoleJMenu.add( item );

 consoleJMenu.addSeparator(); // --- SEPARATOR ---

 item = new JMenuItem( "Run garbage collector" );
 item.addActionListener( consoleActions );
 consoleJMenu.add( item );

 consoleJMenu.addSeparator(); // --- SEPARATOR ---

 item = new JMenuItem( "exit" );
 item.addActionListener( consoleActions );
 consoleJMenu.add( item );

}


} // class
