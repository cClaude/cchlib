package com.googlecode.cchlib.net.download;

import java.net.URL;

/**
 * Full cache entry
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
