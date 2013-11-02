package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Define a "downloadable" URL that should be store in a {@link File}
 * 
 * @since 4.1.7
 */
public class DefaultDownloadFileURL 
    extends AbstractDownloadURL
        implements DownloadFileURL
{
    private static final long serialVersionUID = 4L;
    private File file;
    private HashMap<String,Object> properties;

    /**
     * Define the {@link URL} for this {@link DownloadURL}
     * @param url                   The {@link URL}
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     * @throws URISyntaxException if this URL is not formatted
     *         strictly according to to RFC2396 and cannot be
     *         converted to a URI.
     */
    public DefaultDownloadFileURL(
        final URL                   url,
        final Map<String, String>   requestPropertyMap,
        final Proxy                 proxy
        ) throws URISyntaxException
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
     * @throws URISyntaxException if this URL is not formatted
     *         strictly according to to RFC2396 and cannot be
     *         converted to a URI.
     */
    public DefaultDownloadFileURL(
        final String                spec,
        final Map<String, String>   requestPropertyMap,
        final Proxy                 proxy
        ) throws MalformedURLException, URISyntaxException
    {
        super( new URL( spec ), requestPropertyMap, proxy );
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
    public void setProperty( final String name, final Object value )
    {
        if( properties == null ) {
            properties = new HashMap<String,Object>();
            }

        properties.put( name, value );
    }

    @Override
    public Object getProperty( final String name )
    {
        if( properties == null ) {
            return null;
            }
        else {
            return properties.get( name );
            }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "DefaultDownloadFileURL [getURL()=" );
        builder.append( getURL() );
        builder.append( "DefaultDownloadFileURL [file=" );
        builder.append( file );
        builder.append( ", properties=" );
        builder.append( (properties != null) ? toString( properties.entrySet() ) : null );
        builder.append( ']' );
        return builder.toString();
    }

    private String toString( Collection<?> collection )
    {
        StringBuilder builder = new StringBuilder();
        builder.append( '[' );
        boolean first = true;
        for( Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
            if( first ) {
                first = false;
                }
            else {
                builder.append( ", " );
                }
            builder.append( iterator.next() );
            }
        builder.append( ']' );
        return builder.toString();
    }

}
