package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;

class ProgressCells extends JFrame {
    private static final long serialVersionUID = 1L;

    ProgressCells( final String title )
    {
        // Pass the title to the JFrame superclass so that it appears in
        // the title bar.

        super( title );

        // Tell the program to exit when the user either selects Close
        // from the System menu or presses an appropriate X button on the
        // title bar.

        setDefaultCloseOperation( EXIT_ON_CLOSE );

        // Create a custom data model.

        final ProcessCellMyTableModel mtm = new ProcessCellMyTableModel();

        // Create a table using the previously created custom data model.

        final JTable jt = new JTable( mtm );

        // Create a renderer for displaying progress cells.

        final ProgressCellRenderer pcr = new ProgressCellRenderer();

        // Get the column model so we can extract individual columns.

        final TableColumnModel tcm = jt.getColumnModel();

        // Assign a progress cell renderer to column 2.

        tcm.getColumn( 2 ).setCellRenderer( pcr );
/*
        // Create an object from an anonymous subclass of
        // TableModelListener. That object's anonymous subclass overrides
        // tableChanged() to test any changes made to hoursWorked, in the
        // table model, by calls to the table model's
        // setValueAt(Object value, int rowIndex, int colIndex) method.
        // The idea is to validate user entry. (User should enter only
        // values ranging from 0 through 40 (inclusive).

        TableModelListener tml;
        tml = e -> {
            // Only updates to the table model are to be
            // considered. (Actually, it is not necessary to
            // test against UPDATE because there is no way a
            // table row will be inserted or deleted in this
            // program as it currently stands.
            // However, in the event that you wish to change the
            // program to allow for dynamic inserts and
            // removals, you might want to leave the following
            // if test.)

            if( e.getType() == TableModelEvent.UPDATE ) {
                // Obtain the current column index.

                final int column = e.getColumn();

                // Respond to updates that affect the middle
                // column only. (Actually, because only column 1
                // can be updated, the following if test is not
                // necessary. But you might decide to add
                // additional columns in
                // the future that are editable. Or, you might
                // decide
                // to make the leftmost names column editable.
                // In either situation, the if test is
                // necessary.)

                if( column == 1 ) {
                    // Identify the row containing the cell
                    // whose value changed.

                    final int row = e.getFirstRow();

                    // The TableModel is needed to identify the
                    // current cell's value and change that
                    // value, if necessary.

                    final TableModel tm = (TableModel)e.getSource();

                    // Extract the cell's integer value.

                    final int i = ((Integer)(tm.getValueAt( row, column ))).intValue();

                    // If that value is less than 0, change it to
                    // 0.

                    if( i < 0 ) {
                        tm.setValueAt( new Integer( 0 ), row, column );
                    }

                    // If that value is greater than 40, change
                    // it to 40.

                    if( i > 40 ) {
                        tm.setValueAt( new Integer( 40 ), row, column );
                    }
                }
            }
        };

        // Register the table model listener with the table's data model.

        jt.getModel().addTableModelListener( tml );
*/
        // Place the table in a JScrollPane object (to allow the table to
        // be vertically scrolled and display scrollbars, as necessary).

        final JScrollPane jsp = new JScrollPane( jt );

        // Add the JScrollPane object to the frame window's content pane.
        // That allows the table to be displayed within a displayed
        // scroll pane.

        getContentPane().add( jsp );

        // Establish the overall size of the frame window to 400
        // horizontal pixels by 150 vertical pixels.

        setSize( 400, 150 );

        // Display the frame window and all contained
        // components/containers.

        setVisible( true );

        new Thread( ()-> {
            for(;;) {
                try {
                    doForEver( jt );
                }
                catch( final InterruptedException e ) {
                    break;
                }
            }
        } ).start();
    }

    private void doForEver( final JTable table ) throws InterruptedException
    {
        final int row = 0;
        final int column = 1;
        final Object value = table.getValueAt( row, column );
        final int i = ((Integer)value).intValue();
        table.setValueAt( Integer.valueOf( i + 1 ), row, column );

        Thread.sleep( 1000 );
    }

    public static void main( final String[] args )
    {
        // Ensure that the percentage text that appears on a progress bar
        // is white.
        // That is important to the default Java look and feel, which
        // uses gray. Having the percentage text appear in white instead
        // of gray results in a higher contrast between the text and the
        // bar color.

        UIManager.put( "ProgressBar.selectionForeground", Color.white );

        // Create a ProgressCells object, which creates the GUI.

        final ProgressCells progressCells = new ProgressCells( "Progress Cells" );
    }
}
