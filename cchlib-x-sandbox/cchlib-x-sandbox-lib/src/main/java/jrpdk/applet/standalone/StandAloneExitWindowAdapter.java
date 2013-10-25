/*
** $VER: StandAloneExitWindowAdapter.java
*/
package jrpdk.applet.standalone;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
**
** @since JDK 1.1
**
** @author Claude CHOISNET
** @version 1.00 26/01/2001
**
** @see WindowAdapter
** @see StandAloneExitInterface
*/
public class StandAloneExitWindowAdapter extends WindowAdapter
{
private StandAloneExitInterface exit;

public StandAloneExitWindowAdapter( StandAloneExitInterface exit ) // -----
{
 this.exit = exit;
}

@Override
public void windowClosing( WindowEvent e ) // -----------------------------
{
 exit.exit();
}

} // class WindowExit
