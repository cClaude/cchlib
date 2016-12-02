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
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURI;
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

    @I18nString private String columnNameURL;
    @I18nString private String columnNameState;
    @I18nString private String columnNameFile;
    @I18nString private String columnNameParent;

    /**
     *
     */
    public DisplayTableModel()
    {
        ensureNotStatic();
    }

    private void ensureNotStatic()
    {
        this.columnNameURL    = "URL";
        this.columnNameState  = "State";
        this.columnNameFile   = "File";
        this.columnNameParent = "Parent URL";
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
        this.list.clear();

        super.fireTableDataChanged();
    }

    private int findEntryIndex( final URL url ) throws URISyntaxException
    {
        final URI uri = url.toURI();

        for( final Entry<Integer, DisplayTableModelEntry> entry : this.list.entrySet() ) {
            if( entry.getValue().getURL().toURI().equals( uri ) ) {
                return entry.getKey().intValue();
                }
            }

        LOGGER.error( "NoSuchElement: " + url.toExternalForm() );

        return -1; // not found
    }

    @Override // AbstractTableModel
    public int getColumnCount()
    {
        return DisplayTableModelEntry.ENTRY_COLUMN_COUNT;
    }

    @Override // AbstractTableModel
    public String getColumnName( final int columnIndex )
    {
        switch( columnIndex ) {
            case 0 : return this.columnNameURL;
            case 1 : return this.columnNameState;
            case 2 : return this.columnNameFile;
            case 3 : return this.columnNameParent;
            default: return null;
        }
    }

    @Override // AbstractTableModel
    public synchronized int getRowCount()
    {
        return this.list.size();
    }

    @Override // AbstractTableModel
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
        return this.list.get( Integer.valueOf( rowIndex ) );
    }

    @Override // DownloadEvent
    public void downloadStart( final DownloadURI downloader )
    {
        final int index = this.list.size();

        this.list.put(
                Integer.valueOf( index ),
                new DisplayTableModelEntry( (ContentDownloadURI<?>)downloader )
                );

        super.fireTableDataChanged();

        SwingUtilities.invokeLater( () -> {
            this.jTable.getSelectionModel().setSelectionInterval( index, index );

            this.jTable.scrollRectToVisible(
                    new Rectangle( this.jTable.getCellRect( index, 0, true ) )
                    );
        });
    }

    private final synchronized void updateDisplay(
        final DownloadURI                 dURL,
        final DisplayTableModelEntryState state
        )
    {
        try {
            final int index = findEntryIndex( dURL.getURL() );

            if( index == -1 ) {
                LOGGER.fatal( "URL not in list: " + dURL );
                }
            else {
                this.list.get( Integer.valueOf( index ) ).setState( state );

                super.fireTableRowsUpdated( index, index );
                }
            }
        catch( final URISyntaxException e ) {
            LOGGER.fatal( "updateDisplay : ", e );
            }
    }

    @Override // DownloadEvent
    public synchronized void downloadDone( final DownloadURI dURL )
    {
        updateDisplay( dURL, DisplayTableModelEntryState.DONE );
    }

    @Override // LoggerListener
    public synchronized void oufOfConstraints(
            final ContentDownloadURI<File> dfURL
            )
    {
        updateDisplay( dfURL, DisplayTableModelEntryState.OUT_OF_CONSTRAINTS );
    }

    @Override // LoggerListener
    public void downloadCantRename(
        final ContentDownloadURI<File>  downloader,
        final File                      tmpFile,
        final File                      expectedCacheFile
        )
    {
        updateDisplay( downloader, DisplayTableModelEntryState.CANT_RENAME );
    }

    @Override // LoggerListener
    public void downloadStored( final ContentDownloadURI<File> downloader )
    {
        updateDisplay( downloader, DisplayTableModelEntryState.STORED );
    }

    @Override // LoggerListener
    public synchronized void downloadFail(
            final DownloadIOException dioe
            )
    {
        final DownloadURI dURL  = dioe.getDownloadURL();

        updateDisplay( dURL, DisplayTableModelEntryState.DOWNLOAD_ERROR );

        LOGGER.warn( "DownloadFail", dioe );
    }
}
