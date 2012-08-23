package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Define a downloadable URL that should be store in a {@link File}
 * @since 4.1.7
 */
public final class FileDownloadURL extends AbstractDownloadURL
{
    private static final long serialVersionUID = 2L;
    private File file;
    private String hashString;

    /**
     * Define the {@link URL} for this {@link DownloadURL}
     * @param url                   The {@link URL}
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     */
    public FileDownloadURL(
        final URL                   url,
        final Map<String, String>   requestPropertyMap,
        final Proxy                 proxy
        )
    {
        super( url, requestPropertyMap, proxy );
    }

    /**
     * Define the {@link URL} for this {@link DownloadURL}
     * @param spec                  The {@link URL}
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     * @throws MalformedURLException If the spec specifies an unknown protocol
     */
    public FileDownloadURL(
        final String                spec,
        final Map<String, String>   requestPropertyMap,
        final Proxy                 proxy
        ) throws MalformedURLException
    {
        super( new URL( spec ), requestPropertyMap, proxy );
    }

    @Override
    final public DownloadURLResultType getType() { return DownloadURLResultType.FILE; }

    /**
     * Not supported
     * @throws UnsupportedOperationException
     */
    @Override
    public String getResultAsString()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported
     * @throws UnsupportedOperationException
     */
    @Override
    public void setResultAsString( String string )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public File getResultAsFile()
    {
        return file;
    }

    @Override
    public void setResultAsFile( final File file )
    {
        this.file = file;
    }

    @Override
    public String toString()
    {
        return "FileDownloadURL [getURL()=" + getURL() + ", file=" + file + "]";
    }

    @Override
    public String getContentHashCode()
    {
        return hashString;
    }

    @Override
    public void setContentHashCode( String hashString )
    {
        this.hashString = hashString;
    }
}
