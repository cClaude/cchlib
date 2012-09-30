package com.googlecode.cchlib.net.download.cache;

import java.net.URL;

/**
 * Full cache entry
 * @since 4.1.7
 */
// NOT public
interface URLFullCacheEntry extends URLDataCacheEntry
{
    /**
     * Returns the {@link URL} of this entry
     * @return the {@link URL} of this entry
     */
    public URL getURL();
}
