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
import com.googlecode.cchlib.apps.duplicatefiles.MyStaticResources;
import com.googlecode.cchlib.apps.emptyfiles.bean.FileInfo;
import com.googlecode.cchlib.apps.emptyfiles.interfaces.FileInfoFormater;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrappeException;
import com.googlecode.cchlib.util.iterable.Iterables;

/**
 *
 */
@I18nName("emptyfiles.WorkingTableModel")
public class WorkingTableModel extends AbstractTableModel implements TableModel, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( WorkingTableModel.class );

    private enum Columns { 
        FILE_ICON, 
        FILE_SELECTED, 
        FILE_FILE,
        FILE_SIZE,
        FILE_DATE,
        FILE_ATTR
        };
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
    private Icon deletedFileIcon = MyStaticResources.getDeletedFileIcon();
    private Icon fileIcon = MyStaticResources.getFileIcon();

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

                return fi.isDeleted() ? deletedFileIcon : fileIcon;
            }
            case FILE_SELECTED :
                {
                    FileInfo fi = getFileInfo( file );

                    return fi.isDeleted() ? false : fi.isSelected();
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

            getFileInfo( file ).setSelected( (Boolean)aValue );

            if( logger.isTraceEnabled() ) {
                logger.trace( "selection is " + aValue + " for " + file );
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

    public boolean doDelete( int rowIndex )
    {
        File     file  = this.fileList.get( rowIndex );
        FileInfo value = getFileInfo( file );

        if( value.isSelected() ) {
            logger.info( "doDelete: " + file );

            boolean deleted = file.delete();

            if( deleted ) {
                value.setSelected( false );

                fireTableRowsUpdated( rowIndex, rowIndex );

                return true;
                }
            else {
                // Not deleted 
                logger.warn( "Can't delete : " + file );
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
        return Iterables.transform( this.fileList, new Wrappable<File,FileInfo>() {
            @Override
            public FileInfo wrap( File file ) throws WrappeException
            {
                return getFileInfo( file );
            }
        } );
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
}
