package cx.ath.choisnet.swing.table;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.CellEditor;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import cx.ath.choisnet.swing.menu.AbstractJPopupMenuBuilder;

/**
 * JPopupMenuForJTable is a context menu builder
 * for {@link JTable}.
 * <p>
 * Code inspired from
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 * </p>
 */
public abstract class JPopupMenuForJTable
    extends AbstractJPopupMenuBuilder
{
    private JTable jTable;

    /**
     * Create JPopupMenuForJTable
     * @param jTable {@link JTable} to use.
     */
    public JPopupMenuForJTable( final JTable jTable )
    {
        this.jTable = jTable;
    }

    /**
     * Returns {@link JTable} concern by this menu
     * @return {@link JTable} concern by this menu
     */
    final
    protected JTable getJTable()
    {
        return jTable;
    }

    /**
     * Returns {@link TableModel} use by {@link JTable}
     * @return {@link TableModel} use by {@link JTable}
     */
    final
    protected TableModel getTableModel()
    {
        return jTable.getModel();
    }

    /**
     * Get value from Model
     *
     * @param rowIndex    rowIndex according to view
     * @param columnIndex columnIndex according to view
     * @return value from Model
     */
    final
    protected Object getValueAt( final int rowIndex, final int columnIndex )
    {
        final int row = getJTable().convertRowIndexToModel( rowIndex );
        final int col = getJTable().convertColumnIndexToModel( columnIndex );

        return getTableModel().getValueAt( row, col );
    }

    /**
     * Sets the value in the cell at rowIndex and
     * columnIndex to aValue.
     *
     * @param aValue      the new value
     * @param rowIndex    rowIndex according to view
     * @param columnIndex columnIndex according to view
     */
    final
    protected void setValueAt(
            final Object    aValue,
            final int       rowIndex,
            final int       columnIndex
            )
    {
        final int row = getJTable().convertRowIndexToModel( rowIndex );
        final int col = getJTable().convertColumnIndexToModel( columnIndex );

        getTableModel().setValueAt( aValue, row, col );
    }

    @Override
    protected void addMouseListener( final MouseListener l )
    {
        jTable.addMouseListener( l );
    }

    @Override
    protected void maybeShowPopup( final MouseEvent e )
    {
        if( e.isPopupTrigger() && jTable.isEnabled() ) {
            Point p = new Point( e.getX(), e.getY() );
            int col = jTable.columnAtPoint( p );
            int row = jTable.rowAtPoint( p );

            // translate table index to model index
            int mcol = jTable
                .getColumn( jTable.getColumnName( col ) )
                .getModelIndex();

            if( row >= 0 && row < jTable.getRowCount() ) {
                cancelCellEditing();

                // create popup menu...
                JPopupMenu contextMenu = createContextMenu( row, mcol );

                // ... and show it
                if( contextMenu != null
                        && contextMenu.getComponentCount() > 0 ) {
                    contextMenu.show( jTable, p.x, p.y );
                    }
                }
            }
    }

    /**
     * Install context menu for specified table.
     *
    final
    public void setMenu()
    {
        jTable.addMouseListener(
            new MouseAdapter()
            {
                private void maybeShowPopup( MouseEvent e )
                {
                    if( e.isPopupTrigger() && jTable.isEnabled() ) {
                        Point p = new Point( e.getX(), e.getY() );
                        int col = jTable.columnAtPoint( p );
                        int row = jTable.rowAtPoint( p );

                        // translate table index to model index
                        int mcol = jTable
                            .getColumn( jTable.getColumnName( col ) )
                            .getModelIndex();

                        if( row >= 0 && row < jTable.getRowCount() ) {
                            cancelCellEditing();

                            // create popup menu...
                            JPopupMenu contextMenu = createContextMenu( row, mcol );

                            // ... and show it
                            if( contextMenu != null
                                    && contextMenu.getComponentCount() > 0 ) {
                                contextMenu.show( jTable, p.x, p.y );
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
            });
    }*/

    /**
     * TODOC
     */
    final
    private void cancelCellEditing()
    {
        CellEditor ce = jTable.getCellEditor();

        if( ce != null ) {
            ce.cancelCellEditing();
            }
    }

    /**
     * <P>
     * You must overwrite this method !
     * </P>
     * Create a JPopupMenu for this cell, this method
     * is call when user try to access to context menu
     * for the 'rowIndex', 'columnIndex' entry in JTable..
     *
     * <pre>
     * protected abstract JPopupMenu createContextMenu(
     *          final int rowIndex,
     *          final int columnIndex
     *          )
     *  {
     *      JPopupMenu contextMenu = new JPopupMenu();
     *
     *      addCopyMenuItem(contextMenu, rowIndex, columnIndex);
     *      addPasteMenuItem(contextMenu, rowIndex, columnIndex);
     *
     *      return contextMenu;
     *  }
     * </pre>
     * @param rowIndex
     * @param columnIndex
     * @return JPopupMenu for this cell
     */
    protected abstract JPopupMenu createContextMenu(
            final int rowIndex,
            final int columnIndex
            );

//        addShowHTMLMenuItem(contextMenu, rowIndex, columnIndex);


//        switch( columnIndex ) {
//            case ExampleTableModel.COLUMN_NAME:
//                break;
//            case ExampleTableModel.COLUMN_PRICE:
//                break;
//            case ExampleTableModel.COLUMN_QUANTITY:
//                contextMenu.addSeparator();
//                ActionListener changer = new ActionListener() {
//
//                    public void actionPerformed( ActionEvent e )
//                    {
//                        JMenuItem sourceItem = (JMenuItem)e.getSource();
//                        Object value = sourceItem
//                                .getClientProperty( PROP_CHANGE_QUANTITY );
//                        if( value instanceof Integer ) {
//                            Integer changeValue = (Integer)value;
//                            Integer currentValue = (Integer)getTableModel()
//                                    .getValueAt( rowIndex, columnIndex );
//                            getTableModel().setValueAt(
//                                    new Integer( currentValue.intValue()
//                                            + changeValue.intValue() ),
//                                    rowIndex, columnIndex );
//                        }
//                    }
//                };
//                JMenuItem changeItem = new JMenuItem();
//                changeItem.setText( "+1" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( 1 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "-1" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( -1 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "+10" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( 10 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "-10" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( -10 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = null;
//                break;
//            case ExampleTableModel.COLUMN_AMOUNT:
//                break;
//            default:
//                break;
//        }

    /**
     * TODOC
     *
     * @param contextMenu
     * @param rowIndex
     * @param columnIndex
     *
     */
    final
    public void addCopyMenuItem(
            final JPopupMenu    contextMenu,
            final int           rowIndex,
            final int           columnIndex
            )
    {
        add(
            contextMenu,
            buildCopyJMenuItem("Copy",rowIndex,columnIndex)
            );
    }

    /**
     * TODOC
     *
     * @param textForCopy
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    final
    protected JMenuItem buildCopyJMenuItem(
            final String    textForCopy,
            final int       rowIndex,
            final int       columnIndex
            )
    {
        JMenuItem m = new JMenuItem(textForCopy);
        m.addActionListener(
                copyActionListener(rowIndex,columnIndex)
                );
        return m;
    }

    /**
     * Returns an ActionListener that copy text content of
     * cell at specified row an column.
     *
     * @param rowIndex    row index of cell to copy text content
     * @param columnIndex column index of cell to copy text content
     * @return an ActionListener
     */
    final
    protected ActionListener copyActionListener(
            final int   rowIndex,
            final int   columnIndex
            )
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                Object value = getTableModel()
                    .getValueAt( rowIndex, columnIndex );

                setClipboardContents( value == null ? "" : value.toString() );
            }
        };
    }

    /**
     * TODOC
     *
     * @param contextMenu
     * @param rowIndex
     * @param columnIndex
     */
    final
    protected void addPasteMenuItem(
            final JPopupMenu    contextMenu,
            final int           rowIndex,
            final int           columnIndex
            )
    {
        JMenuItem pasteMenu = new JMenuItem();
        pasteMenu.setText( "Paste" );

        if( isClipboardContainingText( this )
                && getTableModel().isCellEditable( rowIndex, columnIndex ) ) {
            pasteMenu.addActionListener(
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed( ActionEvent e )
                        {
                            String value = getClipboardContents( JPopupMenuForJTable.this );

                            getTableModel().setValueAt( value, rowIndex, columnIndex );
                        }
                    });
            }
        else {
            pasteMenu.setEnabled( false );
        }

        contextMenu.add( pasteMenu );
    }
}
