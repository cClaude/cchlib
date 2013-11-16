package com.googlecode.cchlib.net.download.cache;

import java.net.URI;
import java.util.Map;

public interface CacheContent extends Iterable<Map.Entry<URI,URIDataCacheEntry>>
{
    void put( URI uri, URIDataCacheEntry cacheEntry );

    URIDataCacheEntry getDataCacheEntry( URI uri );
    URI getURI( String hashCode );

    int size();

    void clear();

}
