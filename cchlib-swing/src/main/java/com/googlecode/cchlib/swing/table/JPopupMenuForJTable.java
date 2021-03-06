package com.googlecode.cchlib.swing.table;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.CellEditor;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.clipboard.ClipboardHelper;
import com.googlecode.cchlib.swing.label.JPopupMenuForJLabel;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;
import com.googlecode.cchlib.swing.menu.AbstractJPopupMenuBuilder;
import com.googlecode.cchlib.swing.textfield.JPopupMenuForJTextField;

/**
 * {@link JPopupMenuForJTable} is a context menu builder
 * for {@link JTable}.
 * <p>
 * Code inspired from
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 *
 * @see JPopupMenuForJTextField
 * @see JPopupMenuForJList
 * @see JPopupMenuForJLabel
 */
public abstract class JPopupMenuForJTable
    extends AbstractJPopupMenuBuilder
{
    private static final long serialVersionUID = 2L;
    private final JTable jTable;

    /**
     * Create JPopupMenuForJTable
     *
     * @param jTable     {@link JTable} to use.
     * @param attributes Configuration see {@link Attributs}
     * @throws NullPointerException if jTable is null
     */
    public JPopupMenuForJTable(
        @Nonnull final JTable          jTable,
        @Nullable final Set<Attributs> attributes
        )
    {
        super( attributes );

        if( jTable == null ) {
            throw new NullPointerException( "jTable is null" );
            }

        this.jTable = jTable;
    }

    /**
     * Create JPopupMenuForJTable using default configuration
     *
     * @param jTable {@link JTable} to use.
     * @throws NullPointerException if jTable is null
     */
    public JPopupMenuForJTable( @Nonnull final JTable jTable )
    {
        this( jTable, (Set<Attributs>)null );
    }

    /**
     * Returns {@link JTable} concern by this menu
     * @return {@link JTable} concern by this menu
     */
    protected final JTable getJTable()
    {
        return this.jTable;
    }

    /**
     * Returns {@link TableModel} use by {@link JTable}
     * @return {@link TableModel} use by {@link JTable}
     */
    protected final TableModel getTableModel()
    {
        return this.jTable.getModel();
    }

    /**
     * Get value from Model
     *
     * @param rowIndex    rowIndex according to view
     * @param columnIndex columnIndex according to view
     * @return value from Model
     */
    protected final Object getValueAt( final int rowIndex, final int columnIndex )
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
    protected final void setValueAt(
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
        this.jTable.addMouseListener( l );
    }

    @Override
    protected void removeMouseListener( final MouseListener l )
    {
        this.jTable.removeMouseListener( l );
    }

    @Override
    protected void maybeShowPopup( final MouseEvent event )
    {
        if( event.isPopupTrigger() && this.jTable.isEnabled() ) {
            final Point p = new Point( event.getX(), event.getY() );
            final int col = this.jTable.columnAtPoint( p );
            final int row = this.jTable.rowAtPoint( p );

            // translate table index to model index
            final int mcol = this.jTable
                .getColumn( this.jTable.getColumnName( col ) )
                .getModelIndex();

            if( (row >= 0) && (row < this.jTable.getRowCount()) ) {
                cancelCellEditing();

                // create popup menu...
                final JPopupMenu contextMenu = createContextMenu( row, mcol );

                // ... and show it
                if( (contextMenu != null)
                        && (contextMenu.getComponentCount() > 0) ) {
                    contextMenu.show( this.jTable, p.x, p.y );
                    }
                }
            }
    }

    private void cancelCellEditing()
    {
        final CellEditor ce = this.jTable.getCellEditor();

        if( ce != null ) {
            ce.cancelCellEditing();
            }
    }

    /**
     * You must overwrite this method !
     * <P>
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
     *
     * @param rowIndex    row index
     * @param columnIndex column index
     * @return JPopupMenu for this cell
     */
    protected abstract JPopupMenu createContextMenu(
        final int rowIndex,
        final int columnIndex
        );

    /**
     * Add copy to clip-board sub-menu
     *
     * @param contextMenu  Parent JPopupMenu
     * @param textForCopy  Localized string for copy (CTRL+C)
     * @param rowIndex     column index
     * @param columnIndex  column index
     */
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
     * NEEDDOC
     *
     * @param textForCopy  Localized string for copy (CTRL+C)
     * @param rowIndex     column index
     * @param columnIndex  column index
     * @return NEEDDOC
     */
    protected final JMenuItem createCopyJMenuItem(
        final String    textForCopy,
        final int       rowIndex,
        final int       columnIndex
        )
    {
        final JMenuItem m = new JMenuItem( textForCopy );

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
    protected final ActionListener createCopyActionListener(
        final int   rowIndex,
        final int   columnIndex
        )
    {
        return e -> {
            final Object value = getTableModel()
                .getValueAt( rowIndex, columnIndex );

            ClipboardHelper.setClipboardContents( value == null ? StringHelper.EMPTY : value.toString() );
        };
    }

    /**
     * Add paste to clip-board sub-menu
     *
     * @param contextMenu  Parent JPopupMenu
     * @param txtForPaste  Localized string for paste (CTRL+V)
     * @param rowIndex     column index
     * @param columnIndex  column index
     */
    protected final void addPasteMenuItem(
        final JPopupMenu    contextMenu,
        final String        txtForPaste,
        final int           rowIndex,
        final int           columnIndex
        )
    {
        final JMenuItem pasteMenu = new JMenuItem();

        pasteMenu.setText( txtForPaste );

        if( ClipboardHelper.isClipboardContainingText( this )
                && getTableModel().isCellEditable( rowIndex, columnIndex ) ) {
            pasteMenu.addActionListener(
                    e -> {
                        final String value = ClipboardHelper.getClipboardContents( JPopupMenuForJTable.this );

                        getTableModel().setValueAt( value, rowIndex, columnIndex );
                    });
            }
        else {
            pasteMenu.setEnabled( false );
            }

        contextMenu.add( pasteMenu );
    }
}
