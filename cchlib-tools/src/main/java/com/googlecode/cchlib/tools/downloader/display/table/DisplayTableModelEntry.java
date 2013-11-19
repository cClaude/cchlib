package com.googlecode.cchlib.tools.downloader.display.table;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadURL;
import com.googlecode.cchlib.tools.downloader.AbstractDownloaderAppInterface;

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
    DOWNLOAD_ERROR,
    OUT_OF_CONSTRAINTS,
    };

/**
 *
 */
// NOT public
class DisplayTableModelEntry implements Serializable
{
    private static final long serialVersionUID = 2L;
    public static final int ENTRY_COLUMN_COUNT = 3;
    private DownloadURL dURL;
    private DisplayTableModelEntryState state;

    public DisplayTableModelEntry( final DownloadURL dURL )
    {
        this.dURL   = dURL;

        setState( DisplayTableModelEntryState.INIT );
    }

    public Object getColumn( final int columnIndex )
    {
        switch( columnIndex ) {
            case 0 : return dURL.getURL().toExternalForm();
            case 1 : return state.toString();
            case 2 : return getFile();      // Could be null
            case 3 : return getParentURL(); // Could be null
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
    public void setState( final DisplayTableModelEntryState state )
    {
        this.state = state;
    }

    /**
     * @return the file for this row, if exist
     */
    public File getFile()
    {
        if( this.dURL instanceof DownloadFileURL ) {
            return DownloadFileURL.class.cast( dURL ).getResultAsFile();
            }

        return null;
    }

    /**
     * @return the parent URL for this row, if exist
     */
    public URL getParentURL()
    {
        if( this.dURL instanceof DownloadFileURL ) {
            Object value = DownloadFileURL.class.cast( dURL ).getProperty(
                AbstractDownloaderAppInterface.DownloadFileURL_PARENT_URL_PROPERTY
                );

            if( value instanceof URL ) {
                return URL.class.cast( value );
                }
            }

        return null;
    }

    /**
     * @return the DownloadURL
     */
    public DownloadURL getDownloadURL()
    {
        return this.dURL;
    }

    /**
     * @return the url
     */
    public URL getURL()
    {
        return this.dURL.getURL();
    }
}
