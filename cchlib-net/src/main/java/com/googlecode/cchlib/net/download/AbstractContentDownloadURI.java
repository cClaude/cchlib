package com.googlecode.cchlib.net.download;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;

public abstract class AbstractContentDownloadURI<T>
    extends AbstractDownloadURI implements ContentDownloadURI<T>
{
    private static final long serialVersionUID = 1L;

    private HashMap<String,Object> properties;

    public AbstractContentDownloadURI( final URI uri, final Map<String, String> requestPropertyMap, final Proxy proxy ) throws MalformedURLException
    {
        super( uri, requestPropertyMap, proxy );
    }

    public AbstractContentDownloadURI( final URL url, final Map<String, String> requestPropertyMap, final Proxy proxy ) throws URISyntaxException
    {
        super( url, requestPropertyMap, proxy );
    }

    @Override
    public final void setProperty( final String name, final Object value )
    {
        if( this.properties == null ) {
            this.properties = new HashMap<>();
            }

        this.properties.put( name, value );
    }

    @Override
    public final Object getProperty( final String name )
    {
        if( this.properties == null ) {
            return null;
            }
        else {
            return this.properties.get( name );
            }
    }

    @Override
    public final String getStringProperty( final String name )
    {
        final Object object = getProperty( name );

        if( object instanceof String ) {
            return (String)object;
        }

        return null;
    }

    @Nonnull
    protected final Set<String> propertyNames()
    {
        if( this.properties != null ) {
            return Collections.unmodifiableSet( this.properties.keySet() );
        } else {
            return Collections.emptySet();
        }
    }

    protected static <T> String propertiesToString(
        final AbstractContentDownloadURI<T> instance
        )
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( '[' );
        boolean first = true;

        for( final String propertyName : instance.propertyNames() ) {
            if( first ) {
                first = false;
                }
            else {
                builder.append( ", " );
                }
            builder.append( propertyName )
                   .append( '=' )
                   .append( instance.getProperty( propertyName ) );
        }
        builder.append( ']' );

        return builder.toString();
    }
}
