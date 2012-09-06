package samples.downloader.display.table;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadURL;

/**
*
*/
//NOT public
enum DisplayTableModelEntryState {
    INIT, 
    SKIP,
    DONE, 
    CANT_RENAME,
    STORED,
    ERROR,
    };

/**
 *
 */
// NOT public
class DisplayTableModelEntry implements Serializable
{
    private static final long serialVersionUID = 1L;
    public final static int ENTRY_COLUMN_COUNT = 3;
    private URL url;
    private DisplayTableModelEntryState state;
    private File file;

    public DisplayTableModelEntry( final DownloadURL dURL )
    {
        this.url   = dURL.getURL();

        updateContent( DisplayTableModelEntryState.INIT, dURL );
    }

    public Object getColumn( final int columnIndex )
    {
        switch( columnIndex ) {
            case 0 : return url.toExternalForm();
            case 1 : return state.toString();
            case 2 : return file;
            default: return null;
        }
    }

    /**
     * @return the state
     */
    public DisplayTableModelEntryState getState()
    {
        return state;
    }

    /**
     * @param state the state to set
     */
    private void setState( final DisplayTableModelEntryState state )
    {
        this.state = state;
    }

    /**
     * @return the file
     */
    public File getFile()
    {
        return file;
    }

    /**
     * @param file the file to set
     */
    private void setFile( final File file )
    {
        this.file = file;
    }

    /**
     * @return the url
     */
    public URL getURL()
    {
        return url;
    }

    /**
     *
     * @param state
     * @param dURL
     */
    public void updateContent(
            final DisplayTableModelEntryState   state,
            final DownloadURL                   dURL
            )
    {
        setState( state );

        if( dURL instanceof DownloadFileURL ) {
            final DownloadFileURL dfURL = DownloadFileURL.class.cast( dURL );
            
            setFile( dfURL.getResultAsFile() );
            }
    }
}
