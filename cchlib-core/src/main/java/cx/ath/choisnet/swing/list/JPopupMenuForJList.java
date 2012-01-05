package cx.ath.choisnet.swing.list;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import cx.ath.choisnet.swing.menu.AbstractJPopupMenuBuilder;

/**
 * JPopupMenuForJTable is a context menu builder
 * for {@link JList}.
 *
 * @author Claude CHOISNET
 * <p>
 * Code inspired from
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 * </p>
 */
public abstract class JPopupMenuForJList<E>
    extends AbstractJPopupMenuBuilder
{
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
    public JList<E> getJList()
    {
        return jList;
    }

    /**
     * Returns ListModel for current JList
     * @return ListModel for current JList
     */
    public ListModel<E> getListModel()
    {
        return jList.getModel();
    }

    /**
     * Get value from Model
     *
     * @param rowIndex rowIndex according to view
     * @return value from Model
     */
    final
    public E getValueAt( final int rowIndex )
    {
        return getListModel().getElementAt( rowIndex );
    }

//    /**
//     * Get value from Model
//     *
//     * @param rowIndex rowIndex according to view
//     * @return value from Model
//     */
//    final
//    public void setValueAt( final int rowIndex, Object value )
//    {
//        return getListModel().setElementAt( rowIndex );
//    }

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
     * TODO: Doc !
     *
     *
    final
    public void setMenu()
    {
        jList.addMouseListener(
            new MouseAdapter()
            {
                private void maybeShowPopup( MouseEvent e )
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

            public void mousePressed( MouseEvent e )
            {
                maybeShowPopup( e );
            }

            public void mouseReleased( MouseEvent e )
            {
                maybeShowPopup( e );
            }
        } );
    }*/

    /**
     * <P>
     * You must overwrite this method !
     * </P>
     * Create a JPopupMenu for this row, this method
     * is call when user try to access to context menu
     * for the 'rowIndex' entry in JList.
     *
     * @param rowIndex current rowIndex.
     * @return JPopupMenu for this row
     */
    protected abstract JPopupMenu createContextMenu(
            final int rowIndex
            );
//    {
//        return new JPopupMenu();
//
//        //addCopyMenuItem(contextMenu, rowIndex);
//        //addPasteMenuItem(contextMenu, rowIndex);
//    }

    /**
     * TODO: Doc !
     *
     * @param contextMenu
     * @param rowIndex
     */
    final
    protected void addCopyMenuItem(
            JPopupMenu  contextMenu,
            JMenuItem   copyMenu,
            int         rowIndex
            )
    {
        add(
            contextMenu,
            copyMenu,
            copyActionListener(rowIndex)
            );
    }
    /**
     * TODOC
     *
     * @param contextMenu
     * @param rowIndex
     */
    final
    protected void addDefaultCopyMenuItem(
            JPopupMenu  contextMenu,
            int         rowIndex
            )
    {
        addCopyMenuItem(
                contextMenu,
                new JMenuItem( "Copy" ),
                rowIndex
                );
    }

    /**
     * Returns an ActionListener for 'copy' from
     * current row to clip-board
     *
     * @param rowIndex
     * @return an ActionListener for 'copy' from
     *         current row to clip-board
     */
    final
    public ActionListener copyActionListener( final int rowIndex )
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
