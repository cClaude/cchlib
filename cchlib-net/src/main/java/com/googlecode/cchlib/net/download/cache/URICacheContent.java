package com.googlecode.cchlib.net.download.cache;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

/**
 * Workaround for generic warning when restore object
 * using standard serialization
 * @since 4.1.8
 */
// Not public
final class URICacheContent // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.doNotImplementSerializable
    implements Serializable, CacheContent
{
    private static final long serialVersionUID = 5L;
    private static final Logger LOGGER = Logger.getLogger( CacheContent.class );

    private final HashMap<URI,URIDataCacheEntry> dataCache = new HashMap<>();
    private final HashMap<String,URI>            hashcache = new HashMap<>();

    @Override
    public void put( final URI uri, final URIDataCacheEntry data )
    {
        final String hash = data.getContentHashCode();

        if( ! this.hashcache.containsKey( hash ) ) {
            this.hashcache.put( hash, uri );
            this.dataCache.remove( uri );
            this.dataCache.put( uri, data );
            }

        debug();
    }

    private void debug()
    {
        final int dataCacheSize = this.dataCache.size();
        final int hashCacheSize = this.hashcache.size();

        if( dataCacheSize != hashCacheSize ) {
            LOGGER.error( "CacheContent : cache size error * data:"
                + dataCacheSize
                + " hash:"
                + hashCacheSize
                );
            }
    }

    @Override
    public int size()
    {
        return this.dataCache.size();
    }

    @Override
    public void clear()
    {
        this.dataCache.clear();
        this.hashcache.clear();
    }

    @Override
    public URIDataCacheEntry getDataCacheEntry( final URI uri )
    {
        return this.dataCache.get( uri );
    }

    @Override
    public URI getURI( final String hashcode )
    {
        return this.hashcache.get( hashcode );
    }

    @Override
    public Iterator<Entry<URI,URIDataCacheEntry>> iterator()
    {
        return this.dataCache.entrySet().iterator();
    }
}
