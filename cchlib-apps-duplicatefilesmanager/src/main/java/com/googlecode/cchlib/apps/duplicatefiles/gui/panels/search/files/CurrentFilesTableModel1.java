package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.Classes;

//NOT public
class CurrentFilesTableModel1 extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( CurrentFilesTableModel1.class );

    private final List<CurrentFile> currentFiles = new ArrayList<>();
    private final String[] columnNames = new String[ CurrentFile.COLUMN_COUNT ];

    CurrentFilesTableModel1()
    {
        // initialize fake content.
        currentFiles.add( new CurrentFile() );
    }

    @Override // table "unselectable" (step 1
    public boolean isCellEditable( final int rowIndex, final int columnIndex )
    {
        return false;
    }

    @Override
    public int getRowCount()
    {
        return currentFiles.size();
    }

    @Override
    public int getColumnCount()
    {
        return CurrentFile.COLUMN_COUNT;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex )
    {
        Class<?> value;

        switch( columnIndex ) {
            case CurrentFile.COLUMN_LABEL:
                value = String.class;
                break;

            case CurrentFile.COLUMN_FILE:
                value = CurrentFile.class;
                break;

            default:
                value = null;
                break;
        }
        return value;
    }

    @Override
    public String getColumnName(final int columnIndex )
    {
        return columnNames [ columnIndex ];
    }

    public void setColumnName( final int columnIndex, final String columnName ) {
        columnNames [ columnIndex ] = columnName;

        super.fireTableStructureChanged();
    }

    @Override
    public Object getValueAt( final int rowIndex, final int columnIndex )
    {
        final Object value;

        synchronized( currentFiles ) {
            if( rowIndex < currentFiles.size() ) {

                switch( columnIndex ) {
                    case 0:
                        value = getLabel( rowIndex );
                        assert value != null;
                        break;

                    case 1:
                        value = currentFiles.get( rowIndex );
                        assert value != null;
                       break;

                    default:
                        value = null;
                        LOGGER.warn( "GUI column not found : columnIndex=" + columnIndex, new Exception() );
                        break;
                }
            } else {
                LOGGER.warn( "GUI out of sync : rowIndex=" + rowIndex + " >= content.size()=" + currentFiles.size(), new Exception() );
                value = null;
            }
        }

        return value;
    }

    private String getLabel( final int rowIndex )
    {
        return Integer.toString( rowIndex + 1 );
    }

    @Override //
    public void setValueAt( final Object value, final int rowIndex, final int columnIndex )
    {
        boolean fireTableDataChanged;
        if( rowIndex >= currentFiles.size() ) {
            do {
                currentFiles.add( new CurrentFile() );
            } while( rowIndex >= currentFiles.size() );

            fireTableDataChanged = true;
        } else {
            fireTableDataChanged = false;
        }
        final CurrentFile oldValue = currentFiles.get( rowIndex );

        if( columnIndex == CurrentFile.COLUMN_FILE ) {
            if( value instanceof CurrentFile ) {
                if( value != oldValue ) {
                    currentFiles.set( rowIndex, (CurrentFile)value );
                }

                fireTable( fireTableDataChanged, rowIndex, columnIndex );
            } else if( value instanceof File ) {
                oldValue.setFile( (File)value );

                fireTable( fireTableDataChanged, rowIndex, columnIndex );
            } else if( value instanceof Long ) {
                final long lengthToInc = Long.class.cast( value ).longValue();

                synchronized( oldValue ) {
                    oldValue.setCurrentPos( oldValue.getCurrentPos() + lengthToInc );
                }

                fireTable( fireTableDataChanged, rowIndex, columnIndex );
            } else {
                LOGGER.warn( "Can not handle type: " + Classes.getClass( value ), new Exception() );
            }
        } else {
            LOGGER.warn( "Can not change value of column: " + columnIndex, new Exception() );
        }
     }

    private void fireTable( final boolean fireTableDataChanged, final int rowIndex, final int columnIndex )
    {
        if( fireTableDataChanged ) {
            super.fireTableDataChanged();
        } else {
            fireTableCellUpdated( rowIndex, columnIndex );
        }
    }

    public void clear()
    {
        currentFiles.clear();

        super.fireTableDataChanged();
    }
}
