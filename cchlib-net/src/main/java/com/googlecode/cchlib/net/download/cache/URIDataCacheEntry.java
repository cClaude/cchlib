package com.googlecode.cchlib.net.download.cache;

import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Cache entry
 */
public interface URIDataCacheEntry
{
    /**
     * Returns hash code for this entry
     * @return hash code for this entry
     */
    @Nonnull
    String getContentHashCode();

    /**
     * Returns {@link Date} for this entry
     * @return {@link Date} for this entry
     */
    @Nonnull
    Date getDate();

    /**
     * Returns local file name for this entry
     * @return local file name for this entry
     */
    @Nullable
    String getRelativeFilename();
}
