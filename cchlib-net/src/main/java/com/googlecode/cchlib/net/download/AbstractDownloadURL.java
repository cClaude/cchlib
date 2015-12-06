package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Default implementation of {@link DownloadURL}
 *
 * @since 4.1.7
 */
public abstract class AbstractDownloadURL // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.doNotImplementSerializable
    implements DownloadURL, Serializable
{
    private static final long serialVersionUID = 3L;
    private static final Logger LOGGER = Logger.getLogger( AbstractDownloadURL.class );

    private       URI                   uri;
    private       URL                   url;
    private final Map<String,String>    requestPropertyMap;
    private final Proxy                 proxy;

    /**
     * Create an AbstractDownloadURL using giving {@link URL}
     *
     * @param url                   {@link URL} for this AbstractDownloadURL
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     *
     * @throws NullPointerException if url is null
     */
    public AbstractDownloadURL(
        final URL                   url,
        final Map<String,String>    requestPropertyMap,
        final Proxy                 proxy
        ) throws URISyntaxException // $codepro.audit.disable unnecessaryExceptions
    {
        setURL( url );

        this.requestPropertyMap = requestPropertyMap;
        this.proxy              = proxy;
    }

    /**
     * Create an AbstractDownloadURL using giving {@link URI}
     *
     * @param uri                   {@link URI} for this AbstractDownloadURL,
     *                              must be absolute
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     * @throws IllegalArgumentException If this URL is not absolute
     * @throws MalformedURLException    If a protocol handler for
     *         the URL could not be found, or if some other error
     *         occurred while constructing the URL
     */
    public AbstractDownloadURL(
        final URI                   uri,
        final Map<String,String>    requestPropertyMap,
        final Proxy                 proxy
        ) throws MalformedURLException
    {
        setURI( uri );

        this.requestPropertyMap = requestPropertyMap;
        this.proxy              = proxy;
    }

    /**
     * Set the url.
     *
     * @param url {@link URL} to set
     * @throws URISyntaxException
     * @throws NullPointerException if url is null
     */
    protected final void setURL( final URL url ) throws URISyntaxException
    {
        if( url == null ) {
            throw new NullPointerException();
            }

        this.url = url;
        this.uri = url.toURI();
    }

    /**
     * Set the uri.
     *
     * @param uri {@link URI} to set
     * @throws MalformedURLException
     *
     * @throws NullPointerException if uri is null
     */
    protected final void setURI( final URI uri ) throws MalformedURLException
    {
        if( uri == null ) {
            throw new NullPointerException();
            }

        this.uri = uri;
        this.url = uri.toURL();
    }

    @Override
    public final URI getURI()
    {
        return this.uri;
    }

    @Override
    public final URL getURL()
    {
        return this.url;
    }

    /**
     * Returns {@link URLConnection} for this {@link DownloadURL}
     * using {@link Proxy} if one is define
     * @return {@link URLConnection} for this {@link DownloadURL}
     * @throws IOException if any
     */
    protected URLConnection getURLConnection() throws IOException
    {
        if( this.proxy == null ) {
            return getURL().openConnection();
            }
        else {
            return getURL().openConnection( this.proxy );
            }
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        final URLConnection uc = getURLConnection();

        if( this.requestPropertyMap != null ) {
            for( final Map.Entry<String,String> prop : this.requestPropertyMap.entrySet() ) {
                uc.addRequestProperty( prop.getKey(), prop.getValue() );
                }
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "URLConnection: " + uc );
            LOGGER.trace( "URLConnection.getHeaderFields() " );

            for( final Map.Entry<String,List<String>> entry : uc.getHeaderFields().entrySet() ) {
                LOGGER.trace( "Header name:" + entry.getKey() );

                for( final String v : entry.getValue() ) {
                    LOGGER.trace( "Header value:" + v );
                    }
                }
            }

        uc.connect();

        return uc.getInputStream();
    }
}
