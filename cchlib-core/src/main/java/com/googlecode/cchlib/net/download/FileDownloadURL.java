package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.URL;

/**
 *
 * @since 4.1.7
 */
public final class FileDownloadURL extends AbstractDownloadURL
{
    private File file;

    /**
     *
     * @param url
     */
    public FileDownloadURL( final URL url )
    {
        super( url );
    }

    @Override
    public DownloadResultType getType() { return DownloadResultType.FILE; }

    @Override
    public String getResultAsString()
    {
        throw new UnsupportedOperationException();
    }

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
