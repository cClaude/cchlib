package com.googlecode.cchlib.apps.emptyfiles.panel.remove;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
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
    private enum Columns {
        FILE_ICON(
            16 + JTableColumnsAutoSizer.DEFAULT_COLUMN_MARGIN,
            (m,f) -> m.getIconValue( f ),
            Icon.class
            ),
        FILE_SELECTED( (m,f) -> m.getSelectedValue( f ), Boolean.class ),
        FILE_FILE( (m,f) -> f, String.class ),
        FILE_SIZE( (m,f) -> m.getFileInfo( f ).getLengthString(), String.class ),
        FILE_DATE( (m,f) -> m.getFileInfo( f ).getLastModifiedDate(), Date.class ),
        FILE_ATTR( (m,f) -> m.getFileInfo( f ).getFileAttributsString(), String.class ),
        ;
        private final int                                       forceColumnWidth;
        private final BiFunction<WorkingTableModel,File,Object> valueShowView;
        private final Class<?>                                  type;

        private Columns(
            final BiFunction<WorkingTableModel,File,Object> valueShowView,
            final Class<?>                                        type
            )
        {
            this( 0, valueShowView, type );
        }

        private Columns(
            final int                                       forceColumnWidth,
            final BiFunction<WorkingTableModel,File,Object> valueShowView,
            final Class<?>                                        type
            )
        {
            this.forceColumnWidth = forceColumnWidth;
            this.valueShowView    = valueShowView;
            this.type             = type;
        }

        public int getForceColumnWidth()
        {
            return this.forceColumnWidth;
        }

        public Object getValue( final WorkingTableModel model, final File file )
        {
            return this.valueShowView.apply( model, file );
        }

        public Class<?> getColumnClass()
        {
            return this.type;
        }

        public String getLabel( final String[] columnNames )
        {
            return columnNames[ super.ordinal() ];
        }
    }
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( WorkingTableModel.class );

    private int[] forceColumnWidths;
    @I18nString private String[] columnNames;
    private final List<File>          fileList    = new ArrayList<>();
    private final Map<File,FileInfo>  lasyInfoMap  = new HashMap<>();
    private final boolean             selectedDefaultState = true; // FIXME : should be configurable

    private final FileInfoFormater fileInfoFormater;
    private final IconResources iconResources = IconResources.getInstance();

    public WorkingTableModel( final FileInfoFormater fileInfoFormater )
    {
        this.fileInfoFormater = fileInfoFormater;

        i18nNotFinalStatic();
    }

    @SuppressWarnings("squid:S3346") // assert usage
    private void i18nNotFinalStatic()
    {
        this.columnNames = new String[] {
                ".",
                "Delete",
                "Filename",
                "size",
                "date",
                "Attributs"
                };

        assert this.columnNames.length == Columns.values().length;
    }

    private Columns getColumns( final int columnIndex )
    {
        return Columns.values()[ columnIndex ];
    }

    @Override
    public int getRowCount()
    {
        return this.fileList.size();
    }

    @Override
    public int getColumnCount()
    {
        return Columns.values().length;
    }

    @Override
    public String getColumnName( final int columnIndex )
    {
        return getColumns( columnIndex ).getLabel( this.columnNames );
    }

    @Override
    public Class<?> getColumnClass( final int columnIndex )
    {
        return getColumns( columnIndex ).getColumnClass();
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
        final Columns column = Columns.values()[ columnIndex ];
        final File    file   = this.fileList.get( rowIndex );

        return column.getValue( this, file );
    }

    private Icon getIconValue( final File file )
    {
        final FileInfo fi = getFileInfo( file );

        return fi.isDeleted() ?
                this.iconResources.getDeletedFileIcon()
                :
                this.iconResources.getFileIcon();
    }

    private boolean getSelectedValue( final File file )
    {
        final FileInfo fi = getFileInfo( file );

        return Boolean.valueOf( fi.isDeleted() ? false : fi.isSelected() );
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
        boolean isAtLeastOneFileSelected   = false;
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
        return Iterables.transform( this.fileList, this::getFileInfo );
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
