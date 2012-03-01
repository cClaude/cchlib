package com.googlecode.cchlib.net.download;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

/**
 * Cache entry
 */
//public
interface URLCacheEntry extends Serializable
{
    /**
     * Returns the {@link URL} of this entry
     * @return the {@link URL} of this entry
     */
    public URL getURL();
    /**
     * Returns the {@link Date} for this entry
     * @return the {@link Date} for this entry
     */
    public Date getDate();
    /**
     * Returns the filename of this entry
     * @return the filename of this entry
     */
    public String getFilename();
}
