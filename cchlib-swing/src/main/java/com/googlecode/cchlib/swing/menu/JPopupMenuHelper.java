package com.googlecode.cchlib.swing.menu;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Contains static methods to help to handle context menu.
 *
 * @since 4.1.8
 */
public class JPopupMenuHelper {

    protected JPopupMenuHelper() {}

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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    static final public void addJMenuItem(
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
    public final static JMenuItem addJMenuItem(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
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
    public final static void addJMenu(
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
    public final static JMenu addJMenu(
        final JMenu   contextMenu,
        final String  menuTxt
        )
    {
        final JMenu newMenuItem = new JMenu( menuTxt );

        addJMenu( contextMenu, newMenuItem );

        return newMenuItem;
    }
}
