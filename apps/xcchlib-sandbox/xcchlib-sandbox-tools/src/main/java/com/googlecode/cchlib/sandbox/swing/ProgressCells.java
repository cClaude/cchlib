package com.googlecode.cchlib.sandbox.swing;

import java.awt.Color;
import java.awt.Component;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import com.googlecode.cchlib.lang.Threads;

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

        final MyTableModel mtm = new MyTableModel();

        // Create a table using the previously created custom data model.

        final JTable jt = new JTable( mtm );

        // Create a renderer for displaying progress cells.

        final ProgressCellRenderer pcr = new ProgressCellRenderer();

        // Get the column model so we can extract individual columns.

        final TableColumnModel tcm = jt.getColumnModel();

        // Assign a progress cell renderer to column 2.

        tcm.getColumn( 2 ).setCellRenderer( pcr );

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

        Threads.sleep( 1, TimeUnit.SECONDS );
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

        @SuppressWarnings("unused")
        final ProgressCells progressCells = new ProgressCells( "Progress Cells" );
    }
}

class MyTableModel extends AbstractTableModel {
    // The following private field holds all names for the leftmost
    // column's rows.

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String[]    names            = { "John Doe", "Jane Smith", "Jack Jones", "Paul Finch", };

    // The following private field holds all hours worked values for
    // next-to-leftmost column's rows.

    private final Integer[]   hoursWorked      = new Integer[this.names.length];

    {
        // This instance block initializer is called when a MyTableModel
        // object is created. It is called just after the default
        // no-argument MyTableModel constructor calls its
        // AbstractTableModel no-argument constructor. (That happens
        // behind the scenes.)

        for( int i = 0; i < this.hoursWorked.length; i++ ) {
            this.hoursWorked[ i ] = new Integer( 0 );
        }
    }

    @Override
    public Class<?> getColumnClass( final int columnIndex )
    {
        // By default, every column is assigned an Object type. To
        // ensure that column 1 accepts only integer digits and that
        // column 2's progress cell renderer's getTableCellRenderer()
        // method always receives a value argument of Integer type,
        // Integer.class returns when columnIndex equals 1 (middle
        // column) or 2 (rightmost column). For consistency, when
        // columnIndex equals 0 (the leftmost column), String.class
        // returns.

        switch( columnIndex ) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;

                // The default case should never be reached. However, the
                // compiler complains without the default.

            default:
                return Object.class;
        }
    }

    @Override
    public String getColumnName( final int columnIndex )
    {
        // Return an appropriate column name for each columnIndex.

        switch( columnIndex ) {
            case 0:
                return "Name";
            case 1:
                return "Hours worked";
            case 2:
                return "% worked";

                // The default case should never be reached. However, the
                // compiler complains without the default.

            default:
                return "";
        }
    }

    @Override
    public int getColumnCount()
    {
        // There will be only three columns in this program's table.

        return 3;
    }

    @Override
    public int getRowCount()
    {
        // Return names.length rows instead of hard-coding a value because
        // you might want to add entries to the names field. (You will
        // probably not want to add columns, which is why 3 is hard-coded
        // in the previous getColumnCount() method).

        return this.names.length;
    }

    @Override
    public Object getValueAt( final int rowIndex, final int columnIndex )
    {
        // rowIndex should never be equal to or greater than the number
        // of table rows (as specified by names.length). The following
        // code is just a safety check.

        if( rowIndex >= this.names.length ) {
            throw new IllegalArgumentException( "" + rowIndex );
        }

        // Return the data at the appropriate rowIndex for each column.
        // Before Swing calls getTableCellRendererComponent() to return a
        // renderer for column 2, Swing calls getValueAt() with 2 as the
        // columnIndex to obtain the value that it will pass to the
        // getTableCellRendererComponent().

        switch( columnIndex ) {
            case 0:
                return this.names[ rowIndex ];
            case 1:
                return this.hoursWorked[ rowIndex ];
            case 2:
                return this.hoursWorked[ rowIndex ];

                // The default case should never be reached. However, the
                // compiler complains without the default.

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable( final int rowIndex, final int columnIndex )
    {
        // Allow only the middle column (columnIndex equals 1) to be
        // editable.

        return (columnIndex == 1) ? true : false;
    }

    @Override
    public void setValueAt( final Object v, final int rowIndex, final int columnIndex )
    {
        // rowIndex should never be equal to or greater than the number
        // of table rows (as specified by names.length). The following
        // code is just a safety check.

        if( rowIndex > this.names.length ) {
            throw new IllegalArgumentException( "" + rowIndex );
        }

        // Set the value at the appropriate rowIndex for column 1. Only
        // that column needs to be checked because isCellEditable(int
        // rowIndex, int columnIndex) identifies that column as editable.
        // Once the value has been set, Swing is told to fire a
        // TableModelEvent object to all TableModelListener objects (so
        // validation can be performed). (After all, we allow the user to
        // enter only values from 0 through 40, inclusive). That task is
        // accomplished by making a call to
        // fireTableCellUpdated(rowIndex, columnIndex);.

        switch( columnIndex ) {
            case 1:
                this.hoursWorked[ rowIndex ] = (Integer)v;
                fireTableCellUpdated( rowIndex, columnIndex );
        }
    }
}

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
