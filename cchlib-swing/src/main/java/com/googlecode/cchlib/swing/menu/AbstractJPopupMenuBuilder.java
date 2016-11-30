package com.googlecode.cchlib.swing.menu;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import com.googlecode.cchlib.swing.clipboard.ClipboardHelper;

/**
 * Handle context menu.
 */
@SuppressWarnings("squid:S00104") // Long file, but a lot of documentation
public abstract class AbstractJPopupMenuBuilder implements Serializable
{
    private static final long serialVersionUID = 2L;

    // see readObject()
    private transient MouseListener menuMouseListener;

    private final EnumSet<Attributs> attributes;

    private final class PopupMenuMouseAdapter extends MouseAdapter
    {
        @Override
        public void mousePressed( final MouseEvent event )
        {
            maybeShowPopup( event );
        }

        @Override
        public void mouseReleased( final MouseEvent event )
        {
            maybeShowPopup( event );
        }
    }

    /**
     * @since 4.2
     */
    public enum Attributs {
        /**
         * Line or Cell should be selected to activate menu
         */
        MUST_BE_SELECTED,
    }

    /**
     * @param attributes configuration
     * @since 4.2
     */
    protected AbstractJPopupMenuBuilder( @Nullable final Set<Attributs> attributes )
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
    private void readObject( final ObjectInputStream in )
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
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenuItem(
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenuItem(
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenuItem(
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenu(
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
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     * @return menu added
     *
     * @see JMenu#putClientProperty(Object, Object)
     * @see JMenu#getClientProperty(Object)
     * @see JMenu#addActionListener(ActionListener)
     * @see JMenu#setActionCommand(String)
     */
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenuItem(
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
     * @param clientPropertyKey     The client property key
     * @param clientPropertyValue   The client property value
     * @return menuItem added
     *
     * @see JMenuItem#putClientProperty(Object, Object)
     * @see JMenuItem#getClientProperty(Object)
     * @see JMenuItem#addActionListener(ActionListener)
     * @see JMenuItem#setActionCommand(String)
     */
    public final JMenuItem addJMenuItem(
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
    public final void addJMenuItem(
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenuItem(
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenuItem(
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenuItem(
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
    public final JMenuItem addJMenuItem(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    public final void addJMenu(
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
    public final JMenu addJMenu(
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
    ///

    /**
     * {@link MouseListener} that must be add on object that
     * need a JPopupMenu
     *
     * @param listener {@link MouseListener} to add
     */
    protected abstract void addMouseListener( MouseListener listener );

    /**
     * remove previous {@link MouseListener}
      *
     * @param listener {@link MouseListener} to remove
    */
    protected abstract void removeMouseListener( MouseListener listener );

    /**
     * Must implement display or hide of menu
     *
     * if {@link MouseEvent#isPopupTrigger()} is true, must
     * create the menu {@link JPopupMenu}.
     *
     * @param event mouse event
     */
    protected abstract void maybeShowPopup( MouseEvent event );

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

        this.menuMouseListener = new PopupMenuMouseAdapter();

        addMouseListener( this.menuMouseListener );
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * @param requestor deprecated
     * @return deprecated
     * @see ClipboardHelper#isClipboardContainingText(Object)
     * @deprecated use {@link ClipboardHelper#isClipboardContainingText(Object)}
     */
    @Deprecated
    public static final boolean isClipboardContainingText(
        final Object requestor
        )
    {
        return ClipboardHelper.isClipboardContainingText( requestor );
    }

    /**
     * @param str deprecated
     * @see ClipboardHelper#setClipboardContents(String)
     * @deprecated use {@link ClipboardHelper#setClipboardContents(String)}
     */
    @Deprecated
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static final void setClipboardContents( final String str ) //
            throws IllegalStateException
    {
        ClipboardHelper.setClipboardContents( str );
    }

    /**
     * @param requestor deprecated
     * @return deprecated
     * @see ClipboardHelper#setClipboardContents(String)
     * @deprecated use {@link ClipboardHelper#setClipboardContents(String)}
     */
    @Deprecated
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static final String getClipboardContents( final Object requestor )
        throws IllegalStateException
    {
        return ClipboardHelper.getClipboardContents( requestor );
    }
}
