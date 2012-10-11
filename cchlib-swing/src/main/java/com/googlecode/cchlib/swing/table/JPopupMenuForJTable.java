package com.googlecode.cchlib.swing.table;

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
import com.googlecode.cchlib.swing.menu.AbstractJPopupMenuBuilder;

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
     * 
     * @param jTable {@link JTable} to use.
     * @throws NullPointerException if jTable est null
     */
    public JPopupMenuForJTable( final JTable jTable )
    {
        if( jTable == null ) {
            throw new NullPointerException( "jTable is null" ); 
            }
        
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
        return getTableModel().getValueAt(
                convertRowIndexToModel( rowIndex ), 
                convertColumnIndexToModel( columnIndex )
                );
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
        final int row = convertRowIndexToModel( rowIndex );
        final int col = convertColumnIndexToModel( columnIndex );

        getTableModel().setValueAt( aValue, row, col );
    }
    
    /**
     * Maps the index of the row in terms of the view to the
     * underlying {@link TableModel}. If the contents of the
     * model are not sorted the model and view indices are
     * the same.
     * 
     * @param viewRowIndex the index of the row in the view
     * @return the index of the corresponding row in the model
     * @throws IndexOutOfBoundsException if sorting is enabled 
     *         and passed an index outside the range of the 
     *         {@link JTable} as determined by the method
     *         getRowCount
     * @see #convertColumnIndexToModel(int)
     * @see JTable#convertColumnIndexToView(int)
     * @since 4.1.7
     */
    protected int convertRowIndexToModel( final int viewRowIndex )
    {
        return getJTable().convertRowIndexToModel( viewRowIndex );
    }
    
    /**
     * Maps the index of the column in the view at viewColumnIndex 
     * to the index of the column in the table model. Returns the
     * index of the corresponding column in the model. If
     * viewColumnIndex is less than zero, returns viewColumnIndex.
     * 
     * @param viewColumnIndex the index of the column in the view
     * @return the index of the corresponding column in the model
     * @since 4.1.7
     */
    protected int convertColumnIndexToModel( final int viewColumnIndex )
    {
        return getJTable().convertColumnIndexToModel( viewColumnIndex );
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

//    /**
//     * @deprecated use {@link #addCopyMenuItem(JPopupMenu, String, int, int)} instead
//     */
//    @Deprecated
//    final
//    public void addCopyMenuItem(
//            final JPopupMenu    contextMenu,
//            final int           rowIndex,
//            final int           columnIndex
//            )
//    {
//        addCopyMenuItem( contextMenu, "Copy", rowIndex, columnIndex );
//    }

    /**
     * Add copy to clip-board sub-menu
     * @param contextMenu
     * @param textForCopy
     * @param rowIndex
     * @param columnIndex
     */
    final
    public void addCopyMenuItem(
            final JPopupMenu    contextMenu,
            final String        textForCopy,
            final int           rowIndex,
            final int           columnIndex
            )
    {
        addJMenuItem(
            contextMenu,
            createCopyJMenuItem(textForCopy,rowIndex,columnIndex)
            );
    }

    /**
     * TODOC
     *
     * @param textForCopy
     * @param rowIndex
     * @param columnIndex
     * @return TODOC
     */
    final
    protected JMenuItem createCopyJMenuItem(
            final String    textForCopy,
            final int       rowIndex,
            final int       columnIndex
            )
    {
        JMenuItem m = new JMenuItem( textForCopy );
        
        m.addActionListener(
            createCopyActionListener(rowIndex,columnIndex)
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
    protected ActionListener createCopyActionListener(
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
     * Add paste to clip-board sub-menu
     *
     * @param contextMenu
     * @param txtForPaste
     * @param rowIndex
     * @param columnIndex
     */
    final
    protected void addPasteMenuItem(
            final JPopupMenu    contextMenu,
            final String        txtForPaste,
            final int           rowIndex,
            final int           columnIndex
            )
    {
        JMenuItem pasteMenu = new JMenuItem();
        pasteMenu.setText( txtForPaste );

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
