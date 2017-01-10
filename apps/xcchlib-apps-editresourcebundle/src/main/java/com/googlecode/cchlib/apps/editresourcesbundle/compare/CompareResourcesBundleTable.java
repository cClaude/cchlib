package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleTableModel.Colunms;
import com.googlecode.cchlib.swing.table.LeftDotTableCellRenderer;

/** public for ResourceBuilder only */
public final class CompareResourcesBundleTable extends JTable
{
    private class MyJLabel extends JLabel implements TableCellRenderer
    {
        private static final long serialVersionUID = 1L;

        public MyJLabel()
        {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                final JTable  table,
                final Object  value,
                final boolean isSelected,
                final boolean hasFocus,
                final int     row,
                final int     column
                )
        {
            final String str = String.class.cast( value );

            if( value == null ) {
                setBackground( Color.RED );
                }
            else if( CompareResourcesBundleTableModel.isStringEditable( str ) ) {
                if( str.trim().length() == 0 ) {
                    // WhiteCharacters are ignored by properties
                    setBackground( Color.YELLOW );
                }
                else {
                    setBackground( Color.WHITE );
                    }
                }
            else {
                setBackground( Color.LIGHT_GRAY );
                }

            setText( str );

            return this;
        }
        // The following methods override the defaults for performance reasons
        @Override public void validate() { /* Empty*/ }
        @Override public void revalidate() { /* Empty*/ }
        @Override protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) { /* Empty*/ }
        @Override public void firePropertyChange(final String propertyName, final boolean oldValue, final boolean newValue) { /* Empty*/ }
    }

    @SuppressWarnings("squid:MaximumInheritanceDepth")
    private final class MyTableCellRenderer extends LeftDotTableCellRenderer
    {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(
                final JTable  table,
                final Object  value,
                final boolean isSelected,
                final boolean hasFocus,
                final int     row,
                final int     column
                )
            {
                //Component c =
                super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column
                        );
                setBackground( Color.LIGHT_GRAY );

                return this;
            }
    }

    private static final long serialVersionUID = 1L;

    private final MyJLabel myJLabel = new MyJLabel();
    private final DefaultTableCellRenderer leftDotRenderer = new MyTableCellRenderer();
    private final Colunms colunms;

    private final CompareResourcesBundlePopupMenu popupMenu;

    CompareResourcesBundleTable(
        final AbstractTableModel model,
        final Colunms           colunms
        )
    {
        super( model );

        this.colunms   = colunms;
        this.popupMenu = new CompareResourcesBundlePopupMenu(
                this,
                model,
                colunms
                );

        this.popupMenu.addMenu();
    }

    public CompareResourcesBundlePopupMenu getCompareResourcesBundlePopupMenu()
    {
        return this.popupMenu;
    }

    @Override
    public TableCellRenderer getCellRenderer( final int row, final int column )
    {
        if( column == this.colunms.getColunmKey() ) {
            return this.leftDotRenderer;
            }
        else if( this.colunms.getColunmValueIndex( column ) != -1 ) {
            return this.myJLabel;
            }
        else {
            return super.getCellRenderer(row, column);
            }
    }
}
