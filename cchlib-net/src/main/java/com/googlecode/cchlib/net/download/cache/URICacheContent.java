package com.googlecode.cchlib.net.download.cache;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    private Map<URI,URIDataCacheEntry> dataCache = new HashMap<URI,URIDataCacheEntry>();
    private Map<String,URI>            hashcache = new HashMap<String,URI>();

    @Override
    public void put( final URI uri, final URIDataCacheEntry data )
    {
        final String hash = data.getContentHashCode();

        if( ! hashcache.containsKey( hash ) ) {
            hashcache.put( hash, uri );
            dataCache.remove( uri );
            dataCache.put( uri, data );
            }

        debug();
    }

    private void debug()
    {
        final int dataCacheSize = dataCache.size();
        final int hashCacheSize = hashcache.size();

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
        return dataCache.size();
    }

    @Override
    public void clear()
    {
        dataCache.clear();
        hashcache.clear();
    }

    @Override
    public URIDataCacheEntry getDataCacheEntry( final URI uri )
    {
        return dataCache.get( uri );
    }

    @Override
    public URI getURI( final String hashcode )
    {
        return hashcache.get( hashcode );
    }

    @Override
    public Iterator<Entry<URI,URIDataCacheEntry>> iterator()
    {
        return dataCache.entrySet().iterator();
    }
}
