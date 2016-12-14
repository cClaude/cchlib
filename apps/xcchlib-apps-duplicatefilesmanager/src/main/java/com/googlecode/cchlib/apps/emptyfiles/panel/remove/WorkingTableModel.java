package com.googlecode.cchlib.apps.emptyfiles.panel.remove;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.IconResources;
import com.googlecode.cchlib.apps.emptyfiles.bean.FileInfo;
import com.googlecode.cchlib.apps.emptyfiles.interfaces.FileInfoFormater;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.table.ForceColumnWidthModel;
import com.googlecode.cchlib.swing.table.JTableColumnsAutoSizer;
import com.googlecode.cchlib.util.iterable.Iterables;

@I18nName("emptyfiles.WorkingTableModel")
public class WorkingTableModel
    extends AbstractTableModel
        implements TableModel, Serializable, ForceColumnWidthModel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( WorkingTableModel.class );

    private enum Columns {
        FILE_ICON( 16 + JTableColumnsAutoSizer.DEFAULT_COLUMN_MARGIN),
        FILE_SELECTED,
        FILE_FILE,
        FILE_SIZE,
        FILE_DATE,
        FILE_ATTR,
        ;
        private int forceColumnWidth;

        private Columns() { this.forceColumnWidth = 0; }
        private Columns( final int forceColumnWidth ) { this.forceColumnWidth = forceColumnWidth; }
        public int getForceColumnWidth() { return this.forceColumnWidth; }
        };
    private int[] forceColumnWidths;
    @I18nString private final String[] columnNames = {
            ".",
            "Delete",
            "Filename",
            "size",
            "date",
            "Attributs"
            };
    private final Class<?>[]          columnTypes = new Class[] { Icon.class, Boolean.class, String.class, String.class, Date.class, String.class };
    private final List<File>          fileList    = new ArrayList<>();
    private final Map<File,FileInfo>  lasyInfoMap  = new HashMap<>();
    private final boolean             selectedDefaultState = true; // FIXME : should be configurable

    private final FileInfoFormater fileInfoFormater;
    private final IconResources iconResources = IconResources.getInstance();

    public WorkingTableModel( final FileInfoFormater fileInfoFormater )
    {
        this.fileInfoFormater = fileInfoFormater;
    }

    @Override
    public int getRowCount()
    {
        return this.fileList.size();
    }

    @Override
    public int getColumnCount()
    {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName( final int columnIndex )
    {
        return this.columnNames[ columnIndex ];
    }

    @Override
    public Class<?> getColumnClass( final int columnIndex )
    {
        return this.columnTypes[columnIndex];
    }

    @Override
    public boolean isCellEditable( final int rowIndex, final int columnIndex )
    {
        if( columnIndex == Columns.FILE_SELECTED.ordinal() ) {
            final File     file = this.fileList.get( rowIndex );
            final FileInfo fi   = getFileInfo( file );

            return ! fi.isDeleted();
            }
        return false;
    }

    @Override
    public Object getValueAt( final int rowIndex, final int columnIndex )
    {
        final Columns c = Columns.values()[ columnIndex ];
        final File file = this.fileList.get( rowIndex );

        switch( c ) {
            case FILE_ICON:
            {
                final FileInfo fi = getFileInfo( file );

                //return fi.isDeleted() ? deletedFileIcon : fileIcon;
                return fi.isDeleted() ? this.iconResources.getDeletedFileIcon() : this.iconResources.getFileIcon();
            }
            case FILE_SELECTED :
                {
                    final FileInfo fi = getFileInfo( file );

                    return Boolean.valueOf( fi.isDeleted() ? false : fi.isSelected() );
                }
            case FILE_FILE : return file;
            case FILE_DATE : return getFileInfo( file ).getLastModifiedDate();
            case FILE_SIZE : return getFileInfo( file ).getLengthString();
            case FILE_ATTR : return getFileInfo( file ).getFileAttributsString();
        }

        return null;
    }

    @Override
    public void setValueAt( final Object aValue, final int rowIndex, final int columnIndex )
    {
        if( Columns.values()[ columnIndex ] == Columns.FILE_SELECTED ) {
            final File file = this.fileList.get( rowIndex );

            getFileInfo( file ).setSelected( ((Boolean)aValue).booleanValue() );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "selection is " + aValue + " for " + file );
                }

            super.fireTableCellUpdated( rowIndex, columnIndex );
            }
    }

    SelectionState getSelectionState()
    {
        boolean isAtLeastOneFileSelected = false;
        boolean isAtLeastOneFileUnSelected = false;

        for( final FileInfo fi : getFileInfos() ) {
            if( fi.isSelected() ) {
                isAtLeastOneFileSelected = true;
                }
            else {
                isAtLeastOneFileUnSelected = true;
                }
            }

        if( isAtLeastOneFileSelected ) {
            if( isAtLeastOneFileUnSelected ) {
                return SelectionState.AT_LEAST_ONE_FILE_SELECTED;
                }
            else {
                return SelectionState.ALL_SELECTED;
                }
            }
        else {
            return SelectionState.NONE_SELECTED;
        }
    }

    public void add( final File file )
    {
        this.fileList.add( file );
    }

    public void addAll( final Collection<? extends File> files )
    {
        this.fileList.addAll( files );
    }

    public void addAll( final File[] files )
    {
        for( final File file : files ) {
            this.fileList.add( file );
            }
    }

    public void doUnselectAll()
    {
        for( final FileInfo value : getFileInfos() ) {
            value.setSelected( false );
            }

        fireTableDataChanged();
    }

    public void doSelectAll()
    {
        for( final File file : this.fileList ) {
            getFileInfo( file ).setSelected( true );
            }

        fireTableDataChanged();
    }

    public boolean doDelete( final int rowIndex )
    {
        final File     file  = this.fileList.get( rowIndex );
        final FileInfo value = getFileInfo( file );

        if( value.isSelected() ) {
            LOGGER.info( "doDelete: " + file );

            final boolean deleted = file.delete();

            if( deleted ) {
                value.setSelected( false );

                fireTableRowsUpdated( rowIndex, rowIndex );

                return true;
                }
            else {
                // Not deleted
                LOGGER.warn( "Can't delete : " + file );
                }
            }

        return false;
    }

    private FileInfo getFileInfo( final File file )
    {
        FileInfo value = this.lasyInfoMap.get( file );

        if( value == null ) {
            value = new FileInfo( file, this.selectedDefaultState, this.fileInfoFormater );

            this.lasyInfoMap.put( file, value );
            }

        return value;
    }

    private Iterable<FileInfo> getFileInfos()
    {
        return Iterables.transform( this.fileList, this::getFileInfo);
    }

    public int getSelectedRowCount()
    {
        int count = 0;

        for( final FileInfo value : getFileInfos() ) {
            if( value.isSelected() ) {
                count ++;
                }
            }

        return count;
    }

    public void commit()
    {
        super.fireTableDataChanged();
    }

    public void clear()
    {
        this.fileList.clear();
        this.lasyInfoMap.clear();

        super.fireTableDataChanged();
   }

    public boolean isRowSelected( final int rowIndex )
    {
        return getFileInfo( this.fileList.get( rowIndex ) ).isSelected();
    }

    private int[] getForceColumnWidths()
    {
        if( this.forceColumnWidths == null ) {
            this.forceColumnWidths = new int[ Columns.values().length ];

            for( int i = 0; i<this.forceColumnWidths.length; i++ ) {
                this.forceColumnWidths[ i ] = Columns.values()[ i ].getForceColumnWidth();
                }
            }
        return this.forceColumnWidths;
    }

    @Override//ForceColumnWidthModel
    public boolean isWidthFor( final int columnIndex )
    {
        return getForceColumnWidths()[ columnIndex ] > 0;
    }

    @Override//ForceColumnWidthModel
    public int getWidthFor( final int columnIndex )
    {
        return getForceColumnWidths()[ columnIndex ];
    }

    @Override//ForceColumnWidthModel
    public int getRemainingColumnIndex()
    {
        return Columns.FILE_FILE.ordinal();
    }
}
