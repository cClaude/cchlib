package com.googlecode.cchlib.swing.list;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.clipboard.ClipboardHelper;
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
 *
 * @param <E> Type on {@link JList} elements
 */
public abstract class JPopupMenuForJList<E>
    extends AbstractJPopupMenuBuilder
{
    private static final long serialVersionUID = 2L;
    private final JList<E> jList;

    /**
     * Create JPopupMenuForJList
     *
     * @param jList to use.
     * @param attributes Configuration
     * @since 4.2
     * @see #isListMustBeSelected()
     * @see AbstractJPopupMenuBuilder.Attributs#MUST_BE_SELECTED
     */
    public JPopupMenuForJList( //
        final JList<E>       jList, //
        final Set<Attributs> attributes //
        )
    {
        super( attributes );

        this.jList = jList;
    }

  /**
   * Create JPopupMenuForJList
   *
   * @param jList to use.
   * @param first First attribute for configuration
   * @param rest  others attributes for configuration
   * @since 4.2
   */
    public JPopupMenuForJList( //
        final JList<E>      jList, //
        final Attributs     first, //
        final Attributs...  rest )
    {
        this( jList, EnumSet.of( first, rest ) );
    }

    /**
     * Returns current JList
     * @return current JList
     */
    protected JList<E> getJList()
    {
        return this.jList;
    }

    /**
     * Returns ListModel for current JList
     * @return ListModel for current JList
     */
    protected ListModel<E> getListModel()
    {
        return this.jList.getModel();
    }

    /**
     * Returns a shadow copy of selected indices.
     * @return a shadow copy of selected indices.
     * @since 4.1.7
     */
    protected int[] getSelectedIndices()
    {
        final int[] selectedIndices = this.jList.getSelectedIndices();
        final int[] copy            = new int[ selectedIndices.length ];
        System.arraycopy( selectedIndices, 0, copy, 0, selectedIndices.length );

        return copy;
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
        this.jList.addMouseListener( l );
    }

    @Override
    protected void removeMouseListener( final MouseListener l )
    {
        this.jList.removeMouseListener( l );
    }

    @Override
    protected void maybeShowPopup( final MouseEvent event )
    {
        if( event.isPopupTrigger() && this.jList.isEnabled() ) {
            final Point point   = new Point( event.getX(), event.getY() );
            final int   row     = getRow( point );

            if( row >= 0 ) {
                // create popup menu...
                final JPopupMenu contextMenu = createContextMenu( row );

                // ... and show it
                if( (contextMenu != null)
                        && (contextMenu.getComponentCount() > 0) ) {
                    contextMenu.show( this.jList, point.x, point.y );
                    }
                }
            }
    }

    /**
     * Return row for the current mouse location (or selection)
     * @param location the coordinates of the point
     * @return Returns -1 if there is no selection.
     */
    private int getRow( final Point location )
    {
        // get the list item on which the user right-clicked
        final int row = this.jList.locationToIndex( location );

        if( row >=0 ) {
            // use the selected item
            if( isListMustBeSelected() ) {
                if( this.jList.getSelectedIndex() >=0 ) {
                    return row;
                }
            }
            else {
                // use the list item on which the user right-clicked
                return row;
            }
        }
        return -1;
    }

    /**
     * Could be override to change
     * @return true to use selected entry to create menu (is no selected
     *         entry, menu is not created)
     * @since 4.2
     */
    public boolean isListMustBeSelected()
    {
        return getAttributs().contains( Attributs.MUST_BE_SELECTED );
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
        return e -> {
            final E value = getListModel().getElementAt( rowIndex );

            ClipboardHelper.setClipboardContents(
                    value == null ? StringHelper.EMPTY : value.toString()
                    );
        };
    }
}
