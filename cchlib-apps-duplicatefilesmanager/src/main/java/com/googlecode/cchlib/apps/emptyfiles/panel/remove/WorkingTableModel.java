package com.googlecode.cchlib.apps.emptyfiles.panel.remove;

import com.googlecode.cchlib.apps.duplicatefiles.IconResources;
import com.googlecode.cchlib.apps.emptyfiles.bean.FileInfo;
import com.googlecode.cchlib.apps.emptyfiles.interfaces.FileInfoFormater;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.table.ForceColumnWidthModel;
import com.googlecode.cchlib.swing.table.JTableColumnsAutoSizer;
import com.googlecode.cchlib.util.iterable.Iterables;
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

/**
 *
 */
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
        private Columns( int forceColumnWidth ) { this.forceColumnWidth = forceColumnWidth; }
        public int getForceColumnWidth() { return forceColumnWidth; }
        };
    private int[] forceColumnWidths;
    @I18nString private String[] columnNames = {
            ".",
            "Delete",
            "Filename",
            "size",
            "date",
            "Attributs"
            };
    private Class<?>[]          columnTypes = new Class[] { Icon.class, Boolean.class, String.class, String.class, Date.class, String.class };
    private List<File>          fileList    = new ArrayList<>();
    private Map<File,FileInfo>  lasyInfoMap  = new HashMap<>();
    private boolean             selectedDefaultState = true; // FIXME : should be configurable

    private FileInfoFormater fileInfoFormater;
//    private Icon deletedFileIcon = IconResources.getDeletedFileIcon();
//    private Icon fileIcon = IconResources.getFileIcon();
    private IconResources iconResources = IconResources.getInstance();

    /**
     *
     */
    public WorkingTableModel( FileInfoFormater fileInfoFormater )
    {
        this.fileInfoFormater = fileInfoFormater;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount()
    {
        return fileList.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    @Override
    public String getColumnName( int columnIndex )
    {
        return columnNames[ columnIndex ];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass( int columnIndex )
    {
        return columnTypes[columnIndex];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex )
    {
        if( columnIndex == Columns.FILE_SELECTED.ordinal() ) {
            File     file = this.fileList.get( rowIndex );
            FileInfo fi   = getFileInfo( file );

            return ! fi.isDeleted();
            }
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt( int rowIndex, int columnIndex )
    {
        Columns c = Columns.values()[ columnIndex ];
        File file = this.fileList.get( rowIndex );

        switch( c ) {
            case FILE_ICON:
            {
                FileInfo fi = getFileInfo( file );
                
                //return fi.isDeleted() ? deletedFileIcon : fileIcon;
                return fi.isDeleted() ? iconResources.getDeletedFileIcon() : iconResources.getFileIcon();
            }
            case FILE_SELECTED :
                {
                    FileInfo fi = getFileInfo( file );

                    return Boolean.valueOf( fi.isDeleted() ? false : fi.isSelected() );
                }
            case FILE_FILE : return file;
            case FILE_DATE : return getFileInfo( file ).getLastModifiedDate();
            case FILE_SIZE : return getFileInfo( file ).getLengthString();
            case FILE_ATTR : return getFileInfo( file ).getFileAttributsString();
        }

        return null;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt( Object aValue, int rowIndex, int columnIndex )
    {
        if( Columns.values()[ columnIndex ] == Columns.FILE_SELECTED ) {
            File file = this.fileList.get( rowIndex );

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

        for( FileInfo fi : getFileInfos() ) {
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

    public void add( File file )
    {
        this.fileList.add( file );
    }

    public void addAll( Collection<? extends File> files )
    {
        this.fileList.addAll( files );
    }

    public void addAll( File[] files )
    {
        for( File file : files ) {
            this.fileList.add( file );
            }
    }

    public void doUnselectAll()
    {
        for( FileInfo value : getFileInfos() ) {
            value.setSelected( false );
            }

        fireTableDataChanged();
    }

    public void doSelectAll()
    {
        for( File file : this.fileList ) {
            getFileInfo( file ).setSelected( true );
            }

        fireTableDataChanged();
    }

    public boolean doDelete( int rowIndex ) // $codepro.audit.disable booleanMethodNamingConvention
    {
        File     file  = this.fileList.get( rowIndex );
        FileInfo value = getFileInfo( file );

        if( value.isSelected() ) {
            LOGGER.info( "doDelete: " + file );

            boolean deleted = file.delete();

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

    private FileInfo getFileInfo( File file )
    {
        FileInfo value = this.lasyInfoMap.get( file );

        if( value == null ) {
            value = new FileInfo( file, selectedDefaultState, fileInfoFormater );

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

        for( FileInfo value : getFileInfos() ) {
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

    public boolean isRowSelected( int rowIndex )
    {
        return getFileInfo( this.fileList.get( rowIndex ) ).isSelected();
    }

    private int[] getForceColumnWidths()
    {
        if( forceColumnWidths == null ) {
            forceColumnWidths = new int[ Columns.values().length ];

            for( int i = 0; i<forceColumnWidths.length; i++ ) {
                forceColumnWidths[ i ] = Columns.values()[ i ].getForceColumnWidth();
                }
            }
        return forceColumnWidths;
    }

    @Override//ForceColumnWidthModel
    public boolean isWidthFor( int columnIndex )
    {
        return getForceColumnWidths()[ columnIndex ] > 0;
    }

    @Override//ForceColumnWidthModel
    public int getWidthFor( int columnIndex )
    {
        return getForceColumnWidths()[ columnIndex ];
    }

    @Override//ForceColumnWidthModel
    public int getRemainingColumnIndex()
    {
        return Columns.FILE_FILE.ordinal();
    }
}
