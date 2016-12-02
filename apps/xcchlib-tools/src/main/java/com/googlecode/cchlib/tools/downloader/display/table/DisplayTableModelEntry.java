package com.googlecode.cchlib.tools.downloader.display.table;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface;

/**
 *
 */
// NOT public
class DisplayTableModelEntry implements Serializable
{
    private static final long serialVersionUID = 3L;

    public static final int ENTRY_COLUMN_COUNT = 3;

    private final ContentDownloadURI<?> downloader;

    private DisplayTableModelEntryState state;

    public <T> DisplayTableModelEntry( final ContentDownloadURI<T> downloader )
    {
        this.downloader = downloader;

        setState( DisplayTableModelEntryState.INIT );
    }

    public Object getColumn( final int columnIndex )
    {
        switch( columnIndex ) {
            case 0 : return this.downloader.getURL().toExternalForm();
            case 1 : return this.state.toString();
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
        return this.state;
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
        final Object result = this.downloader.getResult();

        if( result instanceof File ) {
            return (File)result;
        }

        return null;
    }

    /**
     * @return the parent URL for this row, if exist
     */
    public URL getParentURL()
    {
        final Object objectURL = this.downloader.getProperty( AbstractDownloaderAppInterface.DownloadFileURL_PARENT_URL_PROPERTY );

        if( objectURL instanceof URL ) {
            return (URL)objectURL;
        }

        return null;
    }

    public ContentDownloadURI<?> getDownloader()
    {
        return this.downloader;
    }

    /**
     * @return the url
     */
    public URL getURL()
    {
        return this.downloader.getURL();
    }
}
