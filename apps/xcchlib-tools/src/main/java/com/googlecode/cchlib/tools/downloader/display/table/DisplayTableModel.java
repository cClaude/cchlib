package com.googlecode.cchlib.tools.downloader.display.table;

import java.awt.Rectangle;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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
    private static final transient Logger LOGGER = Logger.getLogger( DisplayTableModel.class );
    // Note: use ConcurrentHashMap to avoid java.util.ConcurrentModificationException
    private final ConcurrentHashMap<Integer,DisplayTableModelEntry> list = new ConcurrentHashMap<>();
    private JTable jTable;

    @I18nString private final String columnNameURL    = "URL";
    @I18nString private final String columnNameState  = "State";
    @I18nString private final String columnNameFile   = "File";
    @I18nString private final String columnNameParent = "Parent URL";

    /**
     *
     */
    public DisplayTableModel()
    {
        //empty !
    }

    public final void setJTable( final JTable jTable )
    {
        this.jTable = jTable;
    }

    /**
     * Removes all of the elements from this model. The model will be empty after this call returns.
     */
    public final synchronized void clear()
    {
        list.clear();

        super.fireTableDataChanged();
    }

    private int findEntryIndex( final URL url ) throws URISyntaxException
    {
        final URI uri = url.toURI();

        for( final Entry<Integer, DisplayTableModelEntry> entry : list.entrySet() ) {
            if( entry.getValue().getURL().toURI().equals( uri ) ) {
                return entry.getKey().intValue();
                }
            }

        LOGGER.error( "NoSuchElement: " + url.toExternalForm() );

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
    public synchronized Object getValueAt( final int rowIndex, final int columnIndex )
    {
        return getRow( rowIndex ).getColumn( columnIndex );
    }

    /**
     * Returns row content.
     *
     * @param rowIndex index row in model view
     * @return row content.
     */
    public DisplayTableModelEntry getRow( final int rowIndex )
    {
        return list.get( Integer.valueOf( rowIndex ) );
    }

    @Override // LoggerListener
    final
    public synchronized void downloadStart( final DownloadURL dURL )
    {
        final int index = this.list.size();

        this.list.put( Integer.valueOf( index ), new DisplayTableModelEntry( dURL ) );

        super.fireTableDataChanged();

        SwingUtilities.invokeLater( ( ) -> {
            jTable.getSelectionModel().setSelectionInterval(index, index);
            jTable.scrollRectToVisible(new Rectangle(jTable.getCellRect(index, 0, true)));
        });
    }

    private final synchronized void updateDisplay(
        final DownloadURL                 dURL,
        final DisplayTableModelEntryState state
        )
    {
        try {
            final int index = findEntryIndex( dURL.getURL() );

            if( index == -1 ) {
                LOGGER.fatal( "URL not in list: " + dURL );
                }
            else {
                list.get( Integer.valueOf( index ) ).setState( state );

                super.fireTableRowsUpdated( index, index );
                }
            }
        catch( final URISyntaxException e ) {
            LOGGER.fatal( "updateDisplay : ", e );
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

        LOGGER.warn( "DownloadFail", dioe );
    }

}
