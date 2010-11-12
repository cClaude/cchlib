package cx.ath.choisnet.swing.list;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public abstract class JPopupMenuForJList
    extends AbstractJPopupMenuBuilder
{
    private JList jList;

    /**
     * Create JPopupMenuForJList
     *
     * @param jList to use.
     */
    public JPopupMenuForJList(JList jList)
    {
        this.jList = jList;
    }

    /**
     * Returns current JList
     * @return current JList
     */
    public JList getJList()
    {
        return jList;
    }

    /**
     * Returns ListModel for current JList
     * @return ListModel for current JList
     */
    public ListModel getListModel()
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
    public Object getValueAt( int rowIndex )
    {
        return getListModel().getElementAt( rowIndex );
    }

//    /**
//     * Sets the value.
//     *
//     * @param aValue      - the new value
//     * @param rowIndex    - rowIndex according to view
//     * @param columnIndex - columnIndex according to view
//     */
//    final
//    public void setValueAt( Object aValue, int rowIndex, int columnIndex )
//    {
//        return getListModel()..getElementAt( rowIndex );
//    }

    /**
     * TODO: Doc !
     *
     */
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
    }

//    final
//    protected void cancelCellEditing()
//    {
//        CellEditor ce = jList.getCellEditor();
//
//        if( ce != null ) {
//            ce.cancelCellEditing();
//        }
//    }

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
    protected JPopupMenu createContextMenu(
            final int rowIndex
            )
    {
        return new JPopupMenu();

        //addCopyMenuItem(contextMenu, rowIndex);
        //addPasteMenuItem(contextMenu, rowIndex);
    }

//    /**
//     * TO DO: Doc !
//     *
//     * @param contextMenu
//     * @param menuItem
//     */
//    final
//    protected void add(
//            JPopupMenu  contextMenu,
//            JMenuItem   menuItem
//            )
//    {
//        contextMenu.add( menuItem );
//    }

//    /**
//     * TO DO: Doc !
//     *
//     * @param contextSubMenu
//     * @param menuItem
//     */
//    final
//    public void add(
//            JMenu       contextSubMenu,
//            JMenuItem   menuItem
//            )
//    {
//        contextSubMenu.add( menuItem );
//    }
//
//    /**
//     * T ODO: Doc !
//     *
//     * @param contextMenu
//     * @param menuItem
//     * @param listener
//     */
//    final
//    public void add(
//            JPopupMenu      contextMenu,
//            JMenuItem       menuItem,
//            ActionListener  listener
//            )
//    {
//        menuItem.addActionListener( listener );
//        contextMenu.add( menuItem );
//    }
//
//    /**
//     * TO DO: Doc !
//     *
//     * @param contextSubMenu
//     * @param menuItem
//     * @param listener
//     */
//    final
//    public void add(
//            JMenu           contextSubMenu,
//            JMenuItem       menuItem,
//            ActionListener  listener
//            )
//    {
//        menuItem.addActionListener( listener );
//        contextSubMenu.add( menuItem );
//    }
//
//    /**
//     * TO DO: Doc !
//     *
//     * @param contextMenu
//     * @param menuItem
//     * @param listener
//     * @param actionCommand
//     */
//    final
//    public void add(
//            JPopupMenu      contextMenu,
//            JMenuItem       menuItem,
//            ActionListener  listener,
//            String          actionCommand
//            )
//    {
//        menuItem.setActionCommand(actionCommand);
//        menuItem.addActionListener( listener );
//        contextMenu.add( menuItem );
//    }
//
//    /**
//     * TO DO: Doc !
//     *
//     * @param contextSubMenu
//     * @param menuItem
//     * @param listener
//     * @param clientPropertyKey
//     * @param clientPropertyValue
//     */
//    final
//    public void add(
//            JMenu           contextSubMenu,
//            JMenuItem       menuItem,
//            ActionListener  listener,
//            Object          clientPropertyKey,
//            Object          clientPropertyValue
//            )
//    {
//        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
//        menuItem.addActionListener( listener );
//        contextSubMenu.add( menuItem );
//    }
//
//    /**
//     * TOD O: Doc !
//     *
//     * @param contextMenu
//     * @param menuItem
//     * @param listener
//     * @param clientPropertyKey
//     * @param clientPropertyValue
//     */
//    final
//    public void add(
//            JPopupMenu      contextMenu,
//            JMenuItem       menuItem,
//            ActionListener  listener,
//            Object          clientPropertyKey,
//            Object          clientPropertyValue
//            )
//    {
//        menuItem.putClientProperty( clientPropertyKey, clientPropertyValue );
//        menuItem.addActionListener( listener );
//        contextMenu.add( menuItem );
//    }
//
//    /**
//     * TO DO: Doc !
//     *
//     * @param contextSubMenu
//     * @param menuItem
//     * @param listener
//     * @param actionCommand
//     */
//    final
//    public void add(
//            JMenu           contextSubMenu,
//            JMenuItem       menuItem,
//            ActionListener  listener,
//            String          actionCommand
//            )
//    {
//        menuItem.setActionCommand(actionCommand);
//        menuItem.addActionListener( listener );
//        contextSubMenu.add( menuItem );
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
     * TODO: Doc !
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
     * @param rowIndex
     *
     * @return an ActionListener for 'copy' from
     *         current row to clip-board
     */
    final
    public ActionListener copyActionListener(final int rowIndex)
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                Object value = getListModel().getElementAt( rowIndex );

                setClipboardContents(
                        value == null ? "" : value.toString()
                        );
            }
        };
    }
//    final
//    protected void addPasteMenuItem(
//            JPopupMenu  contextMenu,
//            final int   rowIndex,
//            final int   columnIndex
//            )
//    {
//        JMenuItem pasteMenu = new JMenuItem();
//        pasteMenu.setText( "Paste" );
//
//        if( JPopupMenuForJTable.isClipboardContainingText( this )
//                && getListModel().isCellEditable( rowIndex, columnIndex ) ) {
//            pasteMenu.addActionListener(
//                    new ActionListener()
//                    {
//                        @Override
//                        public void actionPerformed( ActionEvent e )
//                        {
//                            String value = JPopupMenuForJTable.getClipboardContents( JPopupMenuForJList.this );
//
//                            getListModel().setValueAt( value, rowIndex, columnIndex );
//                        }
//                    });
//            }
//        else {
//            pasteMenu.setEnabled( false );
//        }
//
//        contextMenu.add( pasteMenu );
//    }

}
