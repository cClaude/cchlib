package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

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
     */
    public StringDownloadURL( final URL url )
    {
        super( url );
    }

    /**
     * Define the {@link URL} for this {@link DownloadURL}
     * @param spec the {@link String} to parse as a URL.
     * @throws MalformedURLException If the string specifies an unknown protocol.
     */
    public StringDownloadURL( final String spec ) throws MalformedURLException
    {
        super( new URL( spec ) );
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
