package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import javax.swing.table.AbstractTableModel;

class ProcessCellMyTableModel extends AbstractTableModel {
    // The following private field holds all names for the leftmost
    // column's rows.

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String[]    names            = { "John Doe", "Jane Smith", "Jack Jones", "Paul Finch", };

    // The following private field holds all hours worked values for
    // next-to-leftmost column's rows.

    private final Integer[]   hoursWorked      = new Integer[names.length];

    {
        // This instance block initializer is called when a MyTableModel
        // object is created. It is called just after the default
        // no-argument MyTableModel constructor calls its
        // AbstractTableModel no-argument constructor. (That happens
        // behind the scenes.)

        for( int i = 0; i < hoursWorked.length; i++ ) {
            hoursWorked[ i ] = new Integer( 0 );
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

        return names.length;
    }

    @Override
    public Object getValueAt( final int rowIndex, final int columnIndex )
    {
        // rowIndex should never be equal to or greater than the number
        // of table rows (as specified by names.length). The following
        // code is just a safety check.

        if( rowIndex >= names.length ) {
            throw new IllegalArgumentException( "" + rowIndex );
        }

        // Return the data at the appropriate rowIndex for each column.
        // Before Swing calls getTableCellRendererComponent() to return a
        // renderer for column 2, Swing calls getValueAt() with 2 as the
        // columnIndex to obtain the value that it will pass to the
        // getTableCellRendererComponent().

        switch( columnIndex ) {
            case 0:
                return names[ rowIndex ];
            case 1:
                return hoursWorked[ rowIndex ];
            case 2:
                return hoursWorked[ rowIndex ];

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

        if( rowIndex > names.length ) {
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
                hoursWorked[ rowIndex ] = (Integer)v;
                fireTableCellUpdated( rowIndex, columnIndex );
        }
    }
    
}