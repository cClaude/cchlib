package samples.downloader;

import java.awt.Rectangle;
import java.io.File;
import java.net.URL;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.DownloadURL;

/**
 *
 */
public abstract class DisplayTableModel
    extends AbstractTableModel
        implements LoggerListener
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger( DisplayTableModel.class );
    // Note use ConcurrentHashMap to avoid java.util.ConcurrentModificationException
    private ConcurrentHashMap<Integer,DisplayTableModelEntry> list = new ConcurrentHashMap<>();
    private JTable jTable;

    /**
     *
     */
    public DisplayTableModel()
    {
        //empty !
    }

    public void setJTable( final JTable jTable )
    {
        this.jTable = jTable;
    }

    /**
     * Removes all of the elements from this model. The model will be empty after this call returns.
     */
    public void clear()
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

        throw new NoSuchElementException( url.toExternalForm() );
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override//AbstractTableModel
    public int getColumnCount()
    {
        return DisplayTableModelEntry.ENTRY_COLUMN_COUNT;
    }

    @Override//AbstractTableModel
    public String getColumnName( final int columnIndex )
    {
        System.err.println( "columnIndex: " + DisplayTableModelEntry.getColumnName( columnIndex ) );
        return DisplayTableModelEntry.getColumnName( columnIndex );
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override//AbstractTableModel
    public int getRowCount()
    {
        return list.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override//AbstractTableModel
    public Object getValueAt( int rowIndex, int columnIndex )
    {
        return list.get( rowIndex ).getColumn( columnIndex );
    }

    @Override
    public void downloadStart( final DownloadURL dURL )
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

    @Override
    public void downloadDone( final DownloadURL dURL )
    {
        final int index = findEntryIndex( dURL.getURL() );

        if( index == -1 ) {
            logger .fatal( "URL not in list: " + dURL );
            }
        else {
            list.get( index ).update( DisplayTableModelEntryState.DONE, dURL );

            super.fireTableRowsUpdated( index, index );
            }
    }

    @Override
    public void downloadCantRename(
            final DownloadURL   dURL,
            final File          tmpFile,
            final File          expectedCacheFile
            )
    {
        final int index = findEntryIndex( dURL.getURL() );

        if( index == -1 ) {
            logger .fatal( "URL not in list: " + dURL );
            }
        else {
            list.get( index ).update( DisplayTableModelEntryState.CANT_RENAME, dURL );

            super.fireTableRowsUpdated( index, index );
            }
    }

    @Override
    public void downloadStored( final DownloadURL dURL )
    {
        final int index = findEntryIndex( dURL.getURL() );

        if( index == -1 ) {
            logger .fatal( "URL not in list: " + dURL );
            }
        else {
            list.get( index ).update( DisplayTableModelEntryState.STORED, dURL );

            super.fireTableRowsUpdated( index, index );
            }
    }

}
