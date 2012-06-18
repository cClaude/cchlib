package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Exception generate when a error occur during download process
 */
public class DownloadIOException extends Exception
{
    private static final long serialVersionUID = 1L;
    private URL url;
    private File file;

    public DownloadIOException( URL url, Throwable cause )
    {
        super( url.toExternalForm(), cause );

        this.url = url;
    }

    public DownloadIOException( URL url, File file, IOException cause )
    {
        this( url, cause );

        this.file = file;
    }

    /**
     * @return the url
     */
    public URL getUrl()
    {
        return url;
    }

    /**
     * @return the temporary file or null
     */
    public File getFile()
    {
        return file;
    }
}
