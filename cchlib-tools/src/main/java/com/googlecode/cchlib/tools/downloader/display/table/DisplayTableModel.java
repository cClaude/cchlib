package com.googlecode.cchlib.tools.downloader.display.table;

import java.awt.Rectangle;
import java.io.File;
import java.net.URL;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;

import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURL;
import com.googlecode.cchlib.tools.downloader.LoggerListener;

/**
 *
 */
public abstract class DisplayTableModel
    extends AbstractTableModel
        implements LoggerListener
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger( DisplayTableModel.class );
    // Note: use ConcurrentHashMap to avoid java.util.ConcurrentModificationException
    private ConcurrentHashMap<Integer,DisplayTableModelEntry> list = new ConcurrentHashMap<>();
    private JTable jTable;

    @I18nString private String columnNameURL    = "URL";
    @I18nString private String columnNameState  = "State";
    @I18nString private String columnNameFile   = "File";
    @I18nString private String columnNameParent = "Parent URL";

    /**
     *
     */
    public DisplayTableModel()
    {
        //empty !
    }

    final
    public void setJTable( final JTable jTable )
    {
        this.jTable = jTable;
    }

    /**
     * Removes all of the elements from this model. The model will be empty after this call returns.
     */
    final
    public synchronized void clear()
    {
        list.clear();

        super.fireTableDataChanged();
    }

    private int findEntryIndex( final URL url )
    {
        for( Entry<Integer, DisplayTableModelEntry> entry : list.entrySet() ) {
            if( entry.getValue().getURL().equals( url ) ) {
                return entry.getKey();
                }
            }

        logger.error( "NoSuchElement: " + url.toExternalForm() );

        return -1; // not found
    }

    @Override // AbstractTableModel
    final
    public int getColumnCount()
    {
        return DisplayTableModelEntry.ENTRY_COLUMN_COUNT;
    }

    @Override // AbstractTableModel
    final
    public String getColumnName( final int columnIndex )
    {
        switch( columnIndex ) {
            case 0 : return columnNameURL;
            case 1 : return columnNameState;
            case 2 : return columnNameFile;
            case 3 : return columnNameParent;
            default: return null;
        }
    }

    @Override // AbstractTableModel
    final
    public synchronized int getRowCount()
    {
        return list.size();
    }

    @Override // AbstractTableModel
    final
    public synchronized Object getValueAt( int rowIndex, int columnIndex )
    {
        return list.get( rowIndex ).getColumn( columnIndex );
    }

    /**
     * Returns row content.
     *
     * @param rowIndex index row in model view
     * @return row content.
     */
    public DisplayTableModelEntry getRow( final int rowIndex )
    {
        return list.get( rowIndex );
    }

    @Override // LoggerListener
    final
    public synchronized void downloadStart( final DownloadURL dURL )
    {
        final int index = this.list.size();

        this.list.put( index, new DisplayTableModelEntry( dURL ) );

        super.fireTableDataChanged();

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                jTable.getSelectionModel().setSelectionInterval(index, index);
                jTable.scrollRectToVisible(new Rectangle(jTable.getCellRect(index, 0, true)));
            }
        });
    }

    final private synchronized void updateDisplay(
        final DownloadURL                 dURL,
        final DisplayTableModelEntryState state
        )
    {
        final int index = findEntryIndex( dURL.getURL() );

        if( index == -1 ) {
            logger .fatal( "URL not in list: " + dURL );
            }
        else {
            list.get( index ).setState( state );

            super.fireTableRowsUpdated( index, index );
            }
    }

    @Override // LoggerListener
    final
    public synchronized void downloadDone( final DownloadURL dURL )
    {
        updateDisplay( dURL, DisplayTableModelEntryState.DONE );
    }

    @Override
    public synchronized void oufOfConstraints(
            final DownloadFileURL dfURL
            )
    {
        updateDisplay( dfURL, DisplayTableModelEntryState.OUT_OF_CONSTRAINTS );
    }

    @Override // LoggerListener
    final
    public synchronized void downloadCantRename(
            final DownloadURL   dURL,
            final File          tmpFile,
            final File          expectedCacheFile
            )
    {
        updateDisplay( dURL, DisplayTableModelEntryState.CANT_RENAME );
    }

    @Override // LoggerListener
    final
    public synchronized void downloadStored( final DownloadURL dURL )
    {
        updateDisplay( dURL, DisplayTableModelEntryState.STORED );
    }

    @Override // LoggerListener
    final
    public synchronized void downloadFail(
            final DownloadIOException dioe
            )
    {
        final DownloadURL dURL  = dioe.getDownloadURL();

        updateDisplay( dURL, DisplayTableModelEntryState.DOWNLOAD_ERROR );

        logger.warn( "DownloadFail", dioe );
    }

}
