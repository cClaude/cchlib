package samples.downloader;

import java.io.File;
import java.net.URL;
import javax.swing.table.AbstractTableModel;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURL;

/**
 *
 */
public class DisplayTableModel
    extends AbstractTableModel
        implements LoggerListener
{
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public DisplayTableModel()
    {
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override//AbstractTableModel
    public int getColumnCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override//AbstractTableModel
    public int getRowCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override//AbstractTableModel
    public Object getValueAt( int rowIndex, int columnIndex )
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void downloadFail( DownloadIOException e )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void warn( String msg )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void info( String msg )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void error( URL url, File file, Throwable cause )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadStateInit( DownloadStateEvent event )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadStateChange( DownloadStateEvent event )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadStart( DownloadURL url )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadDone( DownloadURL url )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadCantRename( DownloadURL dURL, File tmpFile,
            File expectedCacheFile )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadStored( DownloadURL dURL )
    {
        // TODO Auto-generated method stub

    }
}
