package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @since 4.1.7
 */
public final class StringDownloadURL extends AbstractDownloadURL
{
    private String str;

    /**
     *
     * @param url
     */
    public StringDownloadURL( final URL url )
    {
        super( url );
    }

    /**
     *
     * @param url
     * @throws MalformedURLException
     */
    public StringDownloadURL( final String url ) throws MalformedURLException
    {
        super( new URL( url ) );
    }

    @Override
    public DownloadResultType getType() { return DownloadResultType.STRING; }

    @Override
    public String getResultAsString()
    {
        return str;
    }

    @Override
    public void setResultAsString( final String str )
    {
        this.str = str;
    }

    @Override
    public File getResultAsFile()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setResultAsFile( File file )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        return "StringDownloadURL [getURL()=" + getURL() + ", str=" + str + "]";
    }
}
