package com.googlecode.cchlib.swing.list;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import com.googlecode.cchlib.swing.menu.AbstractJPopupMenuBuilder;

/**
 * JPopupMenuForJTable is a context menu builder
 * for {@link JList}.
 * <p>Usage</p>
 * <pre>
 * // create JList
 * ...
 * // create PopupMenu
 * final JPopupMenuForJList&lt;File&gt; popupMenu = new JPopupMenuForJList&lt;File&gt;( jList )
 * {
 *     &#64;Override
 *     protected JPopupMenu createContextMenu( int rowIndex )
 *     {
 *        JPopupMenu cm = new JPopupMenu();
 *
 *        addCopyMenuItem( cm, "Copy", rowIndex );
 *        addJMenuItem(
 *           cm,
 *           "Do Something",
 *           getActionListener(),
 *           ACTION_DO_REMOVE,    // String constant
 *           ACTION_OBJECT,       // Object constant
 *           new Integer( rowIndex )
 *           );
 *
 *        return cm;
 *     }
 * };
 *
 * popupMenu.setMenu();
 *
 * // ...
 * </pre>
 * You also need to create your own {@link ActionListener}
 * <pre>
 * private ActionListener getActionListener()
 * {
 *    if( actionListener == null ) {
 *        actionListener = new ActionListener()
 *        {
 *           public void actionPerformed( ActionEvent event )
 *           {
 *              final String cmd = event.getActionCommand();
 *
 *              // ...
 *              if( ACTION_XXX.equals( cmd ) ) {
 *                 doXXX();
 *                 }
 *              else if( ACTION_DO_REMOVE.equals( cmd ) ) {
 *
 *                 if( event.getSource() instanceof JButton ) {
 *                    // Remove selected entries in list
 *                    doRemove( jList.getSelectedIndices() );
 *                    }
 *                 else {
 *                    // Remove current entry (using contextual menu)
 *                    final int index = Integer.class.cast( JMenuItem.class.cast( event.getSource() ).getClientProperty( ACTION_OBJECT ) );
 *                    doRemove( new int[]{ index } );
 *                    }
 *                 }
 *               // ...
 *           }
 *        };
 *     }
 *   return actionListener;
 * }
 * </pre>
 * <p>
 * Code inspired from
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 * </p>
 */
public abstract class JPopupMenuForJList<E>
    extends AbstractJPopupMenuBuilder
{
    private static final long serialVersionUID = 1L;
    private JList<E> jList;

    /**
     * Create JPopupMenuForJList
     *
     * @param jList to use.
     */
    public JPopupMenuForJList( final JList<E> jList )
    {
        this.jList = jList;
    }

    /**
     * Returns current JList
     * @return current JList
     */
    protected JList<E> getJList()
    {
        return jList;
    }

    /**
     * Returns ListModel for current JList
     * @return ListModel for current JList
     */
    protected ListModel<E> getListModel()
    {
        return jList.getModel();
    }

    /**
     * Get value from Model
     *
     * @param rowIndex Row index according to view
     * @return value from Model
     */
    protected E getValueAt( final int rowIndex )
    {
        return getListModel().getElementAt( rowIndex );
    }

    @Override
    protected void addMouseListener( final MouseListener l )
    {
        jList.addMouseListener( l );
    }

    @Override
    protected void maybeShowPopup( final MouseEvent e )
    {
        if( e.isPopupTrigger() && jList.isEnabled() ) {
            // get the list item on which the user right-clicked
            Point   p   = new Point( e.getX(), e.getY() );
            int     row = jList.locationToIndex( p );

            if( row >= 0 ) {
                // create popup menu...
                JPopupMenu contextMenu = createContextMenu( row );

                // ... and show it
                if( contextMenu != null
                        && contextMenu.getComponentCount() > 0 ) {
                    contextMenu.show( jList, p.x, p.y );
                    }
                }
            }
    }

    /**
     * <P>
     * You must overwrite this method !
     * </P>
     * <p>
     * Create a JPopupMenu for this row, this method
     * is call when user try to access to context menu
     * for the 'rowIndex' entry in JList.
     * </P>
     * Typically you would add something like :
     * <pre>
     * protected abstract JPopupMenu createContextMenu(
     *     final int rowIndex
     *     )
     * {
     *     JPopupMenu contextMenu =  new JPopupMenu();
     *
     *     addCopyMenuItem(contextMenu, rowIndex);
     *     addPasteMenuItem(contextMenu, rowIndex);
     * }
     * </pre>
     * @param rowIndex Row index according to view
     * @return JPopupMenu for this row
     * @see #addCopyMenuItem(JPopupMenu, JMenuItem, int)
     */
    protected abstract JPopupMenu createContextMenu(
            final int rowIndex
            );

    /**
     * Add copy menu using default copy {@link ActionListener}.
     *
     * @param contextMenu Parent {@link JPopupMenu}
     * @param copyMenu    {@link JMenuItem} for copy action
     * @param rowIndex Row index according to view
     * @see #copyActionListener(int)
     */
    protected void addCopyMenuItem(
        final JPopupMenu  contextMenu,
        final JMenuItem   copyMenu,
        final int         rowIndex
        )
    {
        addJMenuItem(
            contextMenu,
            copyMenu,
            copyActionListener(rowIndex)
            );
    }

    /**
     * Add copy menu using default copy {@link ActionListener}.
     *
     * @param contextMenu  Parent {@link JPopupMenu}
     * @param copyTextMenu Text for {@link JMenuItem} of copy action
     * @param rowIndex Row index according to view
     * @see #copyActionListener(int)
     */
    protected void addCopyMenuItem(
        final JPopupMenu  contextMenu,
        final String      copyTextMenu,
        final int         rowIndex
        )
    {
        addCopyMenuItem(
            contextMenu,
            new JMenuItem( copyTextMenu ),
            rowIndex
            );
    }

    @Deprecated
    protected void addDefaultCopyMenuItem(
        final JPopupMenu contextMenu,
        final int        rowIndex
        )
    {
        addCopyMenuItem( contextMenu,"Copy", rowIndex );
    }

    /**
     * Returns an ActionListener for 'copy' from
     * current row to clip-board
     *
     * @param rowIndex Row index according to view
     * @return an ActionListener for 'copy' from
     *         current row to clip-board
     */
    protected ActionListener copyActionListener(
        final int rowIndex
        )
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                E value = getListModel().getElementAt( rowIndex );

                setClipboardContents(
                        value == null ? "" : value.toString()
                        );
            }
        };
    }
}
