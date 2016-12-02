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
    extends AbstractDownloadURI
        implements ContentDownloadURI<File>
{
    private static final long serialVersionUID = 4L;
    private File file;
    private HashMap<String,Object> properties;

    /**
     * Define the {@link URL} for this {@link DownloadURI}
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
     * Define the {@link URL} for this {@link DownloadURI}
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
    public Class<File> getType()
    {
        return File.class;
    }

    @Override
    public File getResult()
    {
        return this.file;
    }

    @Override
    public void setResult( final File file )
    {
        this.file = file;
    }

    @Override
    public void setProperty( final String name, final Object value )
    {
        if( this.properties == null ) {
            this.properties = new HashMap<>();
            }

        this.properties.put( name, value );
    }

    @Override
    public Object getProperty( final String name )
    {
        if( this.properties == null ) {
            return null;
            }
        else {
            return this.properties.get( name );
            }
    }

    @Override
    public String getStringProperty( final String name )
    {
        final Object object = getProperty( name );

        if( object instanceof String ) {
            return (String)object;
        }

        return null;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "DefaultDownloadFileURL [getURL()=" );
        builder.append( getURL() );
        builder.append( "DefaultDownloadFileURL [file=" );
        builder.append( this.file );
        builder.append( ", properties=" );
        builder.append( (this.properties != null) ? toString( this.properties.entrySet() ) : null );
        builder.append( ']' );

        return builder.toString();
    }

    private String toString( final Collection<?> collection )
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( '[' );
        boolean first = true;
        for( final Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
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
