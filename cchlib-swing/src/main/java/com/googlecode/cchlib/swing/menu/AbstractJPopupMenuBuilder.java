package com.googlecode.cchlib.swing.menu;

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
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.EnumSet;
import javax.annotation.Nullable;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Handle context menu.
 */
public abstract class AbstractJPopupMenuBuilder implements Serializable
{
    private static final long serialVersionUID = 2L;

    // see readObject()
    private transient MouseListener menuMouseListener;

    private final EnumSet<Attributs> attributes;

    /**
     * @since 4.2
     */
    public enum Attributs {
        /**
         * Line or Cell should be selected to activate menu
         */
        MUST_BE_SELECTED,
    }

    @Deprecated
    protected AbstractJPopupMenuBuilder() {this(null);}

    /**
     * @param attributes configuration
     * @since 4.2
     */
    protected AbstractJPopupMenuBuilder(
        @Nullable final EnumSet<Attributs> attributes
        )
    {
        if( attributes == null) {
            this.attributes = EnumSet.noneOf( Attributs.class );
        }
        else {
            this.attributes = EnumSet.copyOf( attributes );
        }
    }

    /**
     * Return configuration, must be handle by super class
     * @return Returns configuration
     * @since 4.2
     */
    protected EnumSet<Attributs> getAttributs()
    {
        return this.attributes;
    }

    /**
     * @since 4.2
     */
   private void readObject( final ObjectInputStream in ) //
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();

        // restore menu (if needed)
        addMenu();
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
   public final void addJMenuItem(
        final JPopupMenu      contextMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menuItem.setActionCommand( actionCommand );
        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menuItem added
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    public final JMenuItem addJMenuItem(
        final JPopupMenu      contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     *
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    public final void addJMenuItem(
        final JPopupMenu      contextMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menuItem added
     *
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    final
    public JMenuItem addJMenuItem(
        final JPopupMenu      contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public void addJMenuItem(
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
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     * @return menuItem added
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public JMenuItem addJMenuItem(
        final JPopupMenu      contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     *
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public void addJMenuItem(
        final JPopupMenu      contextMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener
        )
    {
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @return menuItem added
     *
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public JMenuItem addJMenuItem(
        final JPopupMenu      contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menuItem          New menu to add
     */
    final
    public void addJMenuItem(
        final JPopupMenu      contextMenu,
        final JMenuItem       menuItem
        )
    {
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuItemTxt      Text for new menu to add
     * @return menuItem added
     */
    final
    public JMenuItem addJMenuItem(
        final JPopupMenu    contextMenu,
        final String        menuItemTxt
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem( contextMenu, newMenuItem );

        return newMenuItem;
    }

    ///
    ///////////////////////////////////////////////////////////////////////
    ///

    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menu              New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public void addJMenu(
        final JPopupMenu      contextMenu,
        final JMenu           menu,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menu.setActionCommand(actionCommand);
        menu.putClientProperty( clientPropertyKey, clientPropertyValue );
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuTxt          Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menu added
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public JMenu addJMenu(
        final JPopupMenu      contextMenu,
        final String          menuTxt,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menu              New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     *
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public void addJMenu(
        final JPopupMenu      contextMenu,
        final JMenu           menu,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        menu.setActionCommand(actionCommand);
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuTxt          Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menu added
     *
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public JMenu addJMenu(
        final JPopupMenu      contextMenu,
        final String          menuTxt,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu       Parent {@link JPopupMenu}
     * @param menu              New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public void addJMenu(
        final JPopupMenu      contextMenu,
        final JMenu           menu,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menu.putClientProperty( clientPropertyKey, clientPropertyValue );
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu      Parent {@link JPopupMenu}
     * @param menuTxt          Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     * @return menu added
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public JMenu addJMenu(
        final JPopupMenu      contextMenu,
        final String          menuTxt,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu   Parent {@link JPopupMenu}
     * @param menu          New menu to add
     * @param listener      {@link ActionListener} for new menu
     *
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public void addJMenu(
        final JPopupMenu      contextMenu,
        final JMenu           menu,
        final ActionListener  listener
        )
    {
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu  Parent {@link JPopupMenu}
     * @param menuTxt      Text for new menu to add
     * @param listener     {@link ActionListener} for new menu
     * @return menu added
     *
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public JMenu addJMenu(
        final JPopupMenu      contextMenu,
        final String          menuTxt,
        final ActionListener  listener
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu Parent {@link JPopupMenu}
     * @param menu        New menu to add
     */
    final
    public void addJMenu(
        final JPopupMenu  contextMenu,
        final JMenu       menu
        )
    {
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JPopupMenu}
     *
     * @param contextMenu Parent {@link JPopupMenu}
     * @param menuTxt     Text for new menu to add
     * @return menu added
     */
    final
    public JMenu addJMenu(
        final JPopupMenu    contextMenu,
        final String        menuTxt
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu( contextMenu, newMenuItem );

        return newMenuItem;
    }

    ///
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    final
    public void addJMenuItem(
        final JMenu           contextMenu,
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
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu      Parent {@link JMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menuItem added
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    final
    public JMenuItem addJMenuItem(
        final JMenu           contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     *
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    final
    public void addJMenuItem(
        final JMenu           contextMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu      Parent {@link JMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menuItem added
     *
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    final
    public JMenuItem addJMenuItem(
        final JMenu           contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public void addJMenuItem(
        final JMenu           contextMenu,
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
     * @param contextMenu      Parent {@link JMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     * @return menuItem added
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public JMenuItem addJMenuItem(
        final JMenu           contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menuItem          New menu to add
     * @param listener          {@link ActionListener} for new menu
     *
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public void addJMenuItem(
        final JMenu           contextMenu,
        final JMenuItem       menuItem,
        final ActionListener  listener
        )
    {
        menuItem.addActionListener( listener );
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu      Parent {@link JMenu}
     * @param menuItemTxt      Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @return menuItem added
     *
     * @see JMenuItem#addActionListener(ActionListener)
     */
    final
    public JMenuItem addJMenuItem(
        final JMenu           contextMenu,
        final String          menuItemTxt,
        final ActionListener  listener
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem(
                contextMenu,
                newMenuItem,
                listener
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menuItem          New menu to add
     */
    final
    public void addJMenuItem(
        final JMenu      contextMenu,
        final JMenuItem  menuItem
        )
    {
        contextMenu.add( menuItem );
    }

    /**
     * Add a {@link JMenuItem} on a {@link JMenu}
     *
     * @param contextMenu      Parent {@link JMenu}
     * @param menuItemTxt      Text for new menu to add
     * @return menuItem added
     */
    final
    public JMenuItem addJMenuItem(
        final JMenu    contextMenu,
        final String   menuItemTxt
        )
    {
        final JMenuItem newMenuItem = new JMenuItem( menuItemTxt );

        addJMenuItem( contextMenu, newMenuItem );

        return newMenuItem;
    }

    ///
    ///////////////////////////////////////////////////////////////////////
    ///

    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menu              New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public void addJMenu(
        final JMenu           contextMenu,
        final JMenu           menu,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menu.setActionCommand(actionCommand);
        menu.putClientProperty( clientPropertyKey, clientPropertyValue );
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu      Parent {@link JMenu}
     * @param menuTxt          Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menu added
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public JMenu addJMenu(
        final JMenu           contextMenu,
        final String          menuTxt,
        final ActionListener  listener,
        final String          actionCommand,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menu              New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param actionCommand     Action command to set on new menu
     *
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public void addJMenu(
        final JMenu           contextMenu,
        final JMenu           menu,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        menu.setActionCommand(actionCommand);
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu      Parent {@link JMenu}
     * @param menuTxt          Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param actionCommand    Action command to set on new menu
     * @return menu added
     *
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    final
    public JMenu addJMenu(
        final JMenu           contextMenu,
        final String          menuTxt,
        final ActionListener  listener,
        final String          actionCommand
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener,
                actionCommand
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu       Parent {@link JMenu}
     * @param menu              New menu to add
     * @param listener          {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public void addJMenu(
        final JMenu           contextMenu,
        final JMenu           menu,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        menu.putClientProperty( clientPropertyKey, clientPropertyValue );
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu      Parent {@link JMenu}
     * @param menuTxt          Text for new menu to add
     * @param listener         {@link ActionListener} for new menu
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     * @return menu added
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public JMenu addJMenu(
        final JMenu           contextMenu,
        final String          menuTxt,
        final ActionListener  listener,
        final Object          clientPropertyKey,
        final Object          clientPropertyValue
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener,
                clientPropertyKey,
                clientPropertyValue
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu   Parent {@link JMenu}
     * @param menu          New menu to add
     * @param listener      {@link ActionListener} for new menu
     *
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public void addJMenu(
        final JMenu      contextMenu,
        final JMenu           menu,
        final ActionListener  listener
        )
    {
        menu.addActionListener( listener );
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu  Parent {@link JMenu}
     * @param menuTxt      Text for new menu to add
     * @param listener     {@link ActionListener} for new menu
     * @return menu added
     *
     * @see JMenu#addActionListener(ActionListener)
     */
    final
    public JMenu addJMenu(
        final JMenu           contextMenu,
        final String          menuTxt,
        final ActionListener  listener
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu(
                contextMenu,
                newMenuItem,
                listener
                );

        return newMenuItem;
    }
    ///
    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu Parent {@link JMenu}
     * @param menu        New menu to add
     */
    final
    public void addJMenu(
        final JMenu  contextMenu,
        final JMenu  menu
        )
    {
        contextMenu.add( menu );
    }

    /**
     * Add a {@link JMenu} on a {@link JMenu}
     *
     * @param contextMenu Parent {@link JMenu}
     * @param menuTxt     Text for new menu to add
     * @return menu added
     */
    final
    public JMenu addJMenu(
        final JMenu   contextMenu,
        final String  menuTxt
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu( contextMenu, newMenuItem );

        return newMenuItem;
    }

    ///
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///

    /**
     * {@link MouseListener} that must be add on object that
     * need a JPopupMenu
     */
    protected abstract void addMouseListener(MouseListener l);

    /**
     * remove previous {@link MouseListener}
     */
    protected abstract void removeMouseListener( MouseListener l );

    /**
     * Must implement display or hide of menu
     */
    protected abstract void maybeShowPopup(MouseEvent e);

    /**
     * @deprecated Use {@link #addMenu()} instead
     */
    @Deprecated final public void setMenu()
    {
        addMenu();
    }

    /**
     * Install context menu for specified object.
     *
     * @see #addMouseListener(MouseListener)
     * @see #maybeShowPopup(MouseEvent)
     */
    public void addMenu()
    {
        if( this.menuMouseListener != null ) {
            removeMouseListener( this.menuMouseListener );
            }
        this.menuMouseListener = newMenu();
        addMouseListener( this.menuMouseListener );
    }

    private MouseAdapter newMenu()
    {
        return new MouseAdapter()
        {
            @Override
            public void mousePressed( final MouseEvent e )
            {
                maybeShowPopup( e );
            }
            @Override
            public void mouseReleased( final MouseEvent e )
            {
                maybeShowPopup( e );
            }
        };
    }

    ///
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///

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
        final Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );
        return (t != null)
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
        final StringSelection selection = new StringSelection( s );
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
        final Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );

        if( t != null ) {
            final DataFlavor df = DataFlavor.stringFlavor;

            if( df != null ) {
                try( final Reader r = df.getReaderForText( t ) ) {
                    final char[]        charBuf = new char[ 512 ];
                    final StringBuilder buf     = new StringBuilder();
                    int                 n;

                    while( (n = r.read( charBuf, 0, charBuf.length )) > 0 ) {
                        buf.append( charBuf, 0, n );
                        }
                    return buf.toString();
                    }
                catch( final IOException e ) {
                    // log, but ignore
                    e.printStackTrace();
                    }
                catch( final UnsupportedFlavorException e ) {
                    // log, but ignore
                    e.printStackTrace();
                    }
                }
            }

        return null;
    }

}
