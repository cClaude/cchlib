package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.URL;

/**
 * Define a downloadable URL that should be store in a {@link File}
 * @since 4.1.7
 */
public final class FileDownloadURL extends AbstractDownloadURL
{
    private static final long serialVersionUID = 1L;
    private File file;

    /**
     * Define the {@link URL} for this {@link DownloadURL}
     * @param url The {@link URL}
     */
    public FileDownloadURL( final URL url )
    {
        super( url );
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
}
