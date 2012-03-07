package cx.ath.choisnet.swing.menu;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Reader;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Handle context menu.
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
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     {@link ActionCommand} to set on new menu
     * @param clientPropertyKey
     * @param clientPropertyValue
     * @return menuItem added
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     */
    final
    public JMenuItem add(
        final JPopupMenu      contextMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
        
        return menuItem;
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
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
        final JMenu           contextSubMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu
     * @param menuItem
     */
    final
    public void add(
        final JPopupMenu    contextMenu,
        final JMenuItem     menuItem
        )
    {
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextSubMenu
     * @param menuItem
     */
    final
    public void add(
        final JMenu     contextSubMenu,
        final JMenuItem menuItem
        )
    {
        contextSubMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu
     * @param menuItem
     * @param listener
     */
    final
    public void add(
        final JPopupMenu        contextMenu,
        final JMenuItem         menuItem,
        final ActionListener    listener
        )
    {
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu
     * @param menuItemTxt   Text for {@link JMenuItem}
     * @param listener      {@link ActionListener} for this menu
     */
    final
    public void add(
        final JPopupMenu      contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener
        )
    {
        add( contextMenu, new JMenuItem( menuItemTxt ), listener );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextSubMenu
     * @param menuItem
     * @param listener
     */
    final
    public void add(
        final JMenu             contextSubMenu,
        final JMenuItem         menuItem,
        final ActionListener    listener
        )
    {
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
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
        final JPopupMenu        contextMenu,
        final JMenuItem         menuItem,
        final ActionListener    listener,
        final String            actionCommand
        )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
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
        final JMenu             contextSubMenu,
        final JMenuItem         menuItem,
        final ActionListener    listener,
        final String            actionCommand
        )
    {
        menuItem.setActionCommand( actionCommand );
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
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
        final JMenu           contextSubMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextSubMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextSubMenu
     * @param menuItemTxt   Text for {@link JMenuItem}
     * @param listener
     * @param clientPropertyKey
     * @param clientPropertyValue
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     */
    final
    public void add(
        final JMenu           contextSubMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        add( contextSubMenu,
             new JMenuItem( menuItemTxt ),
             listener,
             clientPropertyKey,
             clientPropertyValue
             );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
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
        final JPopupMenu      contextMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextSubMenu
     * @param menuItemTxt Text for JMenuItem
     * @param listener
     * @param clientPropertyKey
     * @param clientPropertyValue
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     */
    final
    public void add(
        final JPopupMenu      contextSubMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        add( contextSubMenu,
             new JMenuItem( menuItemTxt ),
             listener,
             clientPropertyKey,
             clientPropertyValue
             );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextSubMenu
     * @param menuItemTxt Text for JMenuItem
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
        final JMenu           contextSubMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        add( contextSubMenu,
             new JMenuItem( menuItemTxt ),
             listener,
             actionCommand,
             clientPropertyKey,
             clientPropertyValue
             );
    }

    /**
     * TODOC
     */
    protected abstract void addMouseListener(MouseListener l);

    /**
     * TODOC
     */
    protected abstract void maybeShowPopup(MouseEvent e);

    /**
     * Install context menu for specified object.
     */
    final
    public void setMenu()
    {
        addMouseListener(
            new MouseAdapter()
            {
                public void mousePressed( final MouseEvent e )
                {
                    maybeShowPopup( e );
                }
                public void mouseReleased( final MouseEvent e )
                {
                    maybeShowPopup( e );
                }
            } );
    }

    /**
     * Test if clipboard contain text.
     *
     * @param requestor the object requesting the clip data (not used)
     *
     * @return true if clip-board contains text.
     */
    final
    public static boolean isClipboardContainingText(
        final Object requestor
        )
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
     * @throws IllegalStateException if the clipboard is currently
     * unavailable. For example, on some platforms, the system clipboard
     * is unavailable while it is accessed by another application.
     */
    final
    public static void setClipboardContents(
        final String s
        )
        throws IllegalStateException
    {
        StringSelection selection = new StringSelection( s );
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
    public static String getClipboardContents( final Object requestor )
        throws IllegalStateException
    {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );

        if( t != null ) {
            DataFlavor df = DataFlavor.stringFlavor;

            if( df != null ) {
                try {
                    final Reader        r       = df.getReaderForText( t );
                    final char[]        charBuf = new char[ 512 ];
                    final StringBuilder buf     = new StringBuilder();
                    int                 n;

                    while( (n = r.read( charBuf, 0, charBuf.length )) > 0 ) {
                        buf.append( charBuf, 0, n );
                        }

                    r.close();

                    return buf.toString();
                    }
                catch( IOException e ) {
                    // log, but ignore
                    e.printStackTrace();
                    }
                catch( UnsupportedFlavorException e ) {
                    // log, but ignore
                    e.printStackTrace();
                    }
                }
            }

        return null;
    }
}
