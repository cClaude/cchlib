package cx.ath.choisnet.swing.menu;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Reader;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * TODO: Doc !
 *
 *
 * @author Claude CHOISNET
 * <p>
 * Code inspired from
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 * </p>
 */
public abstract class AbstractJPopupMenuBuilder
{
    /**
     * TODOC
     *
     * @param contextMenu
     * @param menuItem
     */
    public final void add(
            JPopupMenu  contextMenu,
            JMenuItem   menuItem
            )
    {
        contextMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextSubMenu
     * @param menuItem
     */
    final
    public void add(
            JMenu       contextSubMenu,
            JMenuItem   menuItem
            )
    {
        contextSubMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextMenu
     * @param menuItem
     * @param listener
     */
    final
    public void add(
            JPopupMenu      contextMenu,
            JMenuItem       menuItem,
            ActionListener  listener
            )
    {
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextSubMenu
     * @param menuItem
     * @param listener
     */
    final
    public void add(
            JMenu           contextSubMenu,
            JMenuItem       menuItem,
            ActionListener  listener
            )
    {
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextMenu
     * @param menuItem
     * @param listener
     * @param actionCommand
     *
     * @see JMenuItem#setActionCommand(String)
     */
    final
    public void add(
            JPopupMenu      contextMenu,
            JMenuItem       menuItem,
            ActionListener  listener,
            String          actionCommand
            )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextSubMenu
     * @param menuItem
     * @param listener
     * @param actionCommand
     *
     * @see JMenuItem#setActionCommand(String)
     */
    final
    public void add(
            JMenu           contextSubMenu,
            JMenuItem       menuItem,
            ActionListener  listener,
            String          actionCommand
            )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextSubMenu
     * @param menuItem
     * @param listener
     * @param clientPropertyKey
     * @param clientPropertyValue
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     */
    final
    public void add(
            JMenu           contextSubMenu,
            JMenuItem       menuItem,
            ActionListener  listener,
            Object          clientPropertyKey,
            Object          clientPropertyValue
            )
    {
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextMenu
     * @param menuItem
     * @param listener
     * @param clientPropertyKey
     * @param clientPropertyValue
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     */
    final
    public void add(
            JPopupMenu      contextMenu,
            JMenuItem       menuItem,
            ActionListener  listener,
            Object          clientPropertyKey,
            Object          clientPropertyValue
            )
    {
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextMenu
     * @param menuItem
     * @param listener
     * @param actionCommand ActionCommand to set
     * @param clientPropertyKey
     * @param clientPropertyValue
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     */
    final
    public void add(
            JPopupMenu      contextMenu,
            JMenuItem       menuItem,
            ActionListener  listener,
            String          actionCommand,
            Object          clientPropertyKey,
            Object          clientPropertyValue
            )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * TODOC
     *
     * @param contextSubMenu
     * @param menuItem
     * @param listener
     * @param actionCommand
     * @param clientPropertyKey
     * @param clientPropertyValue
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     */
    final
    public void add(
            JMenu           contextSubMenu,
            JMenuItem       menuItem,
            ActionListener  listener,
            String          actionCommand,
            Object          clientPropertyKey,
            Object          clientPropertyValue
            )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * Test if clipboard contain text.
     *
     * @param requestor the object requesting the clip data (not used)
     *
     * @return true if clip-board contains text.
     */
    final
    public static boolean isClipboardContainingText( Object requestor )
    {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );
        return t != null
                && (t.isDataFlavorSupported( DataFlavor.stringFlavor )
                        /*|| t.isDataFlavorSupported( DataFlavor.plainTextFlavor )*/);
    }

    /**
     * Set String content to clipboard
     *
     * @param s String to put in clipboard
     *
     * @throws IllegalStateException if the clipboard
     * is currently unavailable. For example, on some
     * platforms, the system clipboard is unavailable
     * while it is accessed by another application.
     */
    final
    public static void setClipboardContents(String s)
        throws IllegalStateException
    {
        StringSelection selection = new StringSelection(s);
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                    .setContents(
                            selection,
                            selection
                            );
    }

    /**
     * Returns String representing the current contents of
     * the clipboard. If the clipboard currently has no
     * contents, it returns null.
     *
     * @param requestor the object requesting the clip data (not used)
     *
     * @return String representing the current contents of the clipboard.
     *         If the clipboard currently has no contents, it returns null.
     *
     * @throws IllegalStateException if the clipboard is currently
     *         unavailable. For example, on some platforms, the system
     *         clipboard is unavailable while it is accessed by another
     *         application.
     */
    final
    public static String getClipboardContents( Object requestor )
        throws IllegalStateException
    {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );
        if( t != null ) {
            DataFlavor df = DataFlavor.stringFlavor;
            if( df != null ) {
                try {
                    Reader r = df.getReaderForText( t );
                    char[] charBuf = new char[512];
                    StringBuffer buf = new StringBuffer();
                    int n;
                    while( (n = r.read( charBuf, 0, charBuf.length )) > 0 ) {
                        buf.append( charBuf, 0, n );
                    }
                    r.close();
                    return (buf.toString());
                }
                catch( IOException ex ) {
                    ex.printStackTrace();
                }
                catch( UnsupportedFlavorException ex ) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
}
