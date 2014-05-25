package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

//NOT public
class CurrentFileProgressCellRenderer extends JProgressBar implements TableCellRenderer
{
    private static final long serialVersionUID = 1L;

    CurrentFileProgressCellRenderer()
    {
        // Initialize the progress bar renderer to use a horizontal
        // progress bar.

        super( JProgressBar.HORIZONTAL );

        // Ensure that the progress bar border is not painted. (The
        // result is ugly when it appears in a table cell.)
        setBorderPainted( false );

        // Ensure that percentage text is painted on the progress bar.
        setStringPainted( true );
        setMinimum( 0 );
        setIndeterminate( false );
        setEnabled( true );
   }

    @Override
    public Component getTableCellRendererComponent( //
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int col
            )
    {
        if( value instanceof CurrentFile ) {
            // Ensure that the nonselected background portion of a
            // progress bar is assigned the same color as the table's
            // background color. The resulting progress bar fits more
            // naturally (from a visual perspective) into the overall
            // table's appearance.

            setBackground( table.getBackground() );

            // Save the current progress bar value for subsequent
            // rendering. That value is converted from [0, 40] to
            // [0, 100].

            final CurrentFile currentFile = ((CurrentFile)value);

            setValue( currentFile.getCurrentPos() );
            setMaximum( currentFile.getLength() );
            setString( currentFile.getString() );
        }

        return this;
    }
}
