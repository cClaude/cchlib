package samples.downloader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
    private ArrayList<DisplayTableModelEntry> list = new ArrayList<>();

    /**
     *
     */
    public DisplayTableModel()
    {
        //empty !
    }

    private int findEntryIndex( final URL url )
    {
        int index = 0;

        for( DisplayTableModelEntry entry : list ) {
            if( entry.getURL().equals( url ) ) {
                return index;
                }
            index++;
            }

        return -1;
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

//    @Override
//    public void downloadFail( DownloadIOException e )
//    {
//        // TODO Auto-generated method stub
//
//    }

//    @Override
//    public void warn( String msg )
//    {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void info( String msg )
//    {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void error( URL url, File file, Throwable cause )
//    {
//        // TODO Auto-generated method stub
//
//    }

//    @Override
//    public void downloadStateInit( DownloadStateEvent event )
//    {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void downloadStateChange( DownloadStateEvent event )
//    {
//        // TODO Auto-generated method stub
//
//    }

    @Override
    public void downloadStart( final DownloadURL dURL )
    {
        this.list.add( new DisplayTableModelEntry( dURL ) );

        super.fireTableDataChanged();
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
