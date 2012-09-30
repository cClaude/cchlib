package com.googlecode.cchlib.net.download.cache;

import java.util.Date;

/**
 * Cache values store in {@link HashMap} for one URL entry
 *
 * @see URLFullCacheEntry
 * @since 4.1.7
 */
// NOT public
interface URLDataCacheEntry
{
    /**
     * Returns an hash code for this entry
     * @return an hash code for this entry
     */
    public String getContentHashCode();

    /**
     * Returns the {@link Date} for this entry
     * @return the {@link Date} for this entry
     */
    public Date getDate();

    /**
     * Returns the relative filename from cache root of this entry
     * @return the relative filename of this entry
     */
    public String getRelativeFilename();
}
