package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Default implementation of {@link DownloadURI}
 *
 * @since 4.1.7
 */
public abstract class AbstractDownloadURI
    implements DownloadURI, Serializable
{
    private static final long serialVersionUID = 4L;

    private static final Logger LOGGER = Logger.getLogger( AbstractDownloadURI.class );

    private       URI                uri;
    private       URL                url;
    private final Map<String,String> requestPropertyMap;
    private Proxy                    proxy;

    /**
     * Create an AbstractDownloadURL using giving {@link URL}
     *
     * @param url
     *            {@link URL} for this AbstractDownloadURL
     * @param requestPropertyMap
     *            A {@link Map} of request properties to put
     *            on {@link URLConnection} (could be null)
     * @param proxy
     *            {@link Proxy} to use for download (could be null)
     *
     * @throws NullPointerException
     *             if url is null
     */
    public AbstractDownloadURI(
        final URL                   url,
        final Map<String,String>    requestPropertyMap,
        final Proxy                 proxy
        ) throws URISyntaxException
    {
        setURI( url );

        this.requestPropertyMap = requestPropertyMap;
        this.proxy              = proxy;
    }

    /**
     * Create an AbstractDownloadURL using giving {@link URI}
     *
     * @param uri
     *            {@link URI} for this AbstractDownloadURL,
     *            must be absolute
     * @param requestPropertyMap
     *            A {@link Map} of request properties to put on
     *            {@link URLConnection} (could be null)
     * @param proxy
     *            {@link Proxy} to use for download (could be null)
     * @throws IllegalArgumentException
     *             If this URL is not absolute
     * @throws MalformedURLException
     *             If a protocol handler for the URL could not be
     *             found, or if some other error occurred while
     *             constructing the URL
     */
    public AbstractDownloadURI(
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
     * @param url
     *            {@link URL} to set
     * @throws NullPointerException
     *             if {@code url} is null
     * @throws URISyntaxException
     *             if {@code url} is not valid
     */
    protected final void setURL( final URL url ) throws URISyntaxException
    {
        setURI( url );
    }

    /**
     * Set the internal {@code uri}.
     *
     * @param url
     *            {@link URL} to set
     * @throws NullPointerException
     *             if {@code url} is null
     * @throws URISyntaxException
     *             if {@code url} is not valid
     */
    protected final void setURI( final URL url ) throws URISyntaxException
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
     * @param uri
     *            {@link URI} to set
     * @throws MalformedURLException
     *             if {@code uri} can not be converted into {@link URL}
     * @throws NullPointerException
     *             if {@code uri} is null
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

    /*
     * (non-Javadoc)
     * @see com.googlecode.cchlib.net.download.DownloadURI#getURL()
     *
     * This entry should be protected, public usage is deprecated
     */
    @Override
    public final URL getURL()
    {
        return this.url;
    }

    /**
     * Returns {@link URLConnection} for this {@link DownloadURI}
     * using {@link Proxy} if one is define
     *
     * @return {@link URLConnection} for this {@link DownloadURI}
     * @throws IOException
     *             if any
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

    private void writeObject( final ObjectOutputStream stream ) throws IOException
    {
        final Type type = this.proxy.type();
        stream.writeObject(type);

        final SocketAddress address = this.proxy.address();
        stream.writeObject(address);
    }

    private void readObject(final java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        final Type type = (Type) stream.readObject();
        final SocketAddress address = (SocketAddress) stream.readObject();

        this.proxy = new Proxy( type, address );
    }
}
