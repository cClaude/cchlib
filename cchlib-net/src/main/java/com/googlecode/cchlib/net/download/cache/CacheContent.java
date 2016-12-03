package com.googlecode.cchlib.net.download.cache;

import java.net.URI;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Describe {@link CacheContent}
 */
public interface CacheContent extends Iterable<Map.Entry<URI,URIDataCacheEntry>>
{
    /**
     * Add an entry in cache {@link URIDataCacheEntry}
     *
     * @param uri
     *            Related {@link URI}
     * @param cacheEntry
     *            Related {@link URIDataCacheEntry}
     */
    void put( @Nonnull URI uri, @Nonnull URIDataCacheEntry cacheEntry );

    /**
     * Retrieve {@link URIDataCacheEntry} using {@code uri}
     *
     * @param uri
     *            The {@link URI}
     * @return The {@link URIDataCacheEntry} for giving {@link URI}, or null
     *         if entry can not be found
     */
    @Nullable
    URIDataCacheEntry getDataCacheEntry( @Nonnull URI uri );

    /**
     * Retrieve {@link URI} using {@code hashCode}
     *
     * @param hashCode
     *            The hash code
     * @return The {@link URI} for giving {@code hashCode}, or null
     *         if entry can not be found
     */
    URI getURI( @Nonnull String hashCode );

    /**
     * Returns number of entries in cache
     * @return number of entries in cache
     */
    int size();

    /**
     * Clear cache
     */
    void clear();
}
