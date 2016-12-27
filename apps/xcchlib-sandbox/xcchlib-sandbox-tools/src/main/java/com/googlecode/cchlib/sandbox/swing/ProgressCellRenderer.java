package com.googlecode.cchlib.sandbox.swing;

import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

class ProgressCellRenderer extends JProgressBar implements TableCellRenderer {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ProgressCellRenderer()
    {
        // Initialize the progress bar renderer to use a horizontal
        // progress bar.

        super( SwingConstants.HORIZONTAL );

        // Ensure that the progress bar border is not painted. (The
        // result is ugly when it appears in a table cell.)

        setBorderPainted( false );

        // Ensure that percentage text is painted on the progress bar.

        setStringPainted( true );
    }

    @Override
    public Component getTableCellRendererComponent( final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row,
            final int col )
    {
        if( value instanceof Integer ) {
            // Ensure that the nonselected background portion of a
            // progress bar is assigned the same color as the table's
            // background color. The resulting progress bar fits more
            // naturally (from a visual perspective) into the overall
            // table's appearance.

            setBackground( table.getBackground() );

            // Save the current progress bar value for subsequent
            // rendering. That value is converted from [0, 40] to
            // [0, 100].

            final int i = ((Integer)value).intValue();
            setValue( (int)((i * 5.0) / 2.0) );
        }

        return this;
    }
}