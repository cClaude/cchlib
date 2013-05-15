package com.googlecode.cchlib.apps.emptyfiles;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptyfiles.bean.FileInfo;
import com.googlecode.cchlib.apps.emptyfiles.interfaces.FileInfoFormater;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.lang.StringHelper;

/**
 *
 */
public class WorkingTableModel extends AbstractTableModel implements TableModel, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( WorkingTableModel.class );

    private static final int FILE_SELECTED = 0;
    private static final int FILE_FILE = 1;
    private static final int FILE_SIZE = 2;
    private static final int FILE_DATE = 3;
    private static final int FILE_ATTR = 4;

    @I18nString private String[] columnNames = { 
            "Delete", 
            "Filename",
            "size", 
            "date",
            "Attributs" 
            };
    private Class<?>[]          columnTypes = new Class[] { Boolean.class, String.class, String.class, Date.class, String.class };
    private List<File>          fileList    = new ArrayList<>();
    private Map<File,FileInfo>  infoMap     = new HashMap<>();
    private boolean             selectedDefaultState = true; // FIXME : should be configurable

    private FileInfoFormater fileInfoFormater;

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
        return columnIndex == FILE_SELECTED;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt( int rowIndex, int columnIndex )
    {
        File file = this.fileList.get( rowIndex );

        switch( columnIndex ) {
            case FILE_SELECTED :
                {
                    FileInfo fi = getFileInfo( file );

                    return fi.isDeleted() ? null : fi.isSelected();
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
        if( columnIndex == FILE_SELECTED ) {
            File file = this.fileList.get( rowIndex );
            getFileInfo( file ).setSelected( (Boolean)aValue );
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
        for( FileInfo value : this.infoMap.values() ) {
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
            // TODO : delete file
            value.setFileAttributsString( fileInfoFormater.formatAttributsDelete() );
            value.setLengthString( StringHelper.EMPTY );
            value.setSelected( false );
            value.setDeleted( true );

            fireTableRowsUpdated( rowIndex, rowIndex );

            return true;
            }

        return false;
   }

    private FileInfo getFileInfo( File file )
    {
        FileInfo value = this.infoMap.get( file );

        if( value == null ) {
            value = new FileInfo( file, selectedDefaultState, fileInfoFormater );

            this.infoMap.put( file, value );
            }

        return value;
    }

    public int getSelectedRowCount()
    {
        int count = 0;

        for( FileInfo value : this.infoMap.values() ) {
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
        this.infoMap.clear();
        
        super.fireTableDataChanged();
   }

    public boolean isRowSelected( int rowIndex )
    {
        return this.infoMap.get( this.fileList.get( rowIndex ) ).isSelected();
    }
}
