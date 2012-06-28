package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Proxy;
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
public abstract class AbstractDownloadURL implements DownloadURL, Serializable
{
    private static final long serialVersionUID = 1L;
    private final static transient Logger logger = Logger.getLogger( AbstractDownloadURL.class );

    private final URL                   url;
    private final Map<String,String>    requestPropertyMap;
    private final Proxy                 proxy;

    /**
     * Create an AbstractDownloadURL using giving {@link URL}
     *
     * @param url                   {@link URL} for this AbstractDownloadURL
     * @param requestPropertyMap    A {@link Map} of request properties to put
     *                              on {@link URLConnection} (could be null)
     * @param proxy                 {@link Proxy} to use for download (could be null)
     */
    public AbstractDownloadURL(
        final URL                   url,
        final Map<String,String>    requestPropertyMap,
        final Proxy                 proxy
        )
    {
        this.url                = url;
        this.requestPropertyMap = requestPropertyMap;
        this.proxy              = proxy;
    }

    @Override
    public URL getURL()
    {
        return url;
    }

    /**
     * Returns {@link URLConnection} for this {@link DownloadURL}
     * using {@link Proxy} if one is define
     * @return {@link URLConnection} for this {@link DownloadURL}
     * @throws IOException if any
     */
    final protected URLConnection getURLConnection() throws IOException
    {
        if( proxy == null ) {
            return getURL().openConnection();
            }
        else {
            return getURL().openConnection( proxy );
            }
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        URLConnection uc = getURLConnection();

        if( requestPropertyMap != null ) {
            for( Map.Entry<String,String> prop : requestPropertyMap.entrySet() ) {
                uc.addRequestProperty( prop.getKey(), prop.getValue() );
                }
            }

        if( logger.isTraceEnabled() ) {
            logger.trace( "URLConnection: " + uc );
            logger.trace( "URLConnection.getHeaderFields() " );

            for( Map.Entry<String,List<String>> entry : uc.getHeaderFields().entrySet() ) {
                logger.trace( "Header name:" + entry.getKey() );

                for( String v : entry.getValue() ) {
                    logger.trace( "Header value:" + v );
                    }
                }
            }

        uc.connect();

        return uc.getInputStream();
    }
}
