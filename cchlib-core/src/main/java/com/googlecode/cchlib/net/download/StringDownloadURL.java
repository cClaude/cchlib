package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Define a downloadable URL that should be store in a {@link String}
 * @since 4.1.7
 */
public final class StringDownloadURL extends AbstractDownloadURL
{
    private static final long serialVersionUID = 1L;
    private String str;

    /**
     * Define the {@link URL} for this {@link DownloadURL}
     * @param url The {@link URL}
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     */
    public StringDownloadURL(
        final URL                   url,
        final Map<String, String>   requestPropertyMap,
        final Proxy                 proxy
        )
    {
        super( url, requestPropertyMap, proxy );
    }

    /**
     * Define the {@link URL} for this {@link DownloadURL}
     * @param spec                  the {@link String} to parse as a URL.
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     * @throws MalformedURLException If the spec specifies an unknown protocol
     */
    public StringDownloadURL(
        final String                spec,
        final Map<String, String>   requestPropertyMap,
        final Proxy                 proxy
        ) throws MalformedURLException
    {
        super( new URL( spec ), requestPropertyMap, proxy );
    }

    @Override
    final public DownloadURLResultType getType() { return DownloadURLResultType.STRING; }

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

    /**
     * Not supported
     * @throws UnsupportedOperationException
     */
    @Override
    public File getResultAsFile()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getContentHashCode()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentHashCode( String hashString )
    {
        throw new UnsupportedOperationException();
    }
    /**
     * Not supported
     * @throws UnsupportedOperationException
     */
    @Override
    public void setResultAsFile( File file )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        //return "StringDownloadURL [getURL()=" + getURL() /**/+ ", str=" + str/**/ + "]";
        final StringBuilder sb = new StringBuilder();

        sb.append( "StringDownloadURL [getURL()=" ).append( getURL() );
        //sb.append( ", str=" ).append( str );
        sb.append( "]" );

        return sb.toString();
    }
}
