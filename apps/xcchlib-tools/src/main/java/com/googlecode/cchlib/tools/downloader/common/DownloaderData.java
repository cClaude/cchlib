package com.googlecode.cchlib.tools.downloader.common;

import java.net.Proxy;

public interface DownloaderData
{
    /**
     * Returns the site name (for UI)
     * @return the site name
     */
    String getSiteName();

    /**
     * Returns average pictures by page (for UI)
     * @return average pictures by page.
     */
    int getNumberOfPicturesByPage();

    /**
     * Returns number of page to download (for UI). Value must be greater
     * than 0 and less or equal than value return by {@link #getMaxPageCount()}.
     *
     * @return number of page to download
     */
    int getPageCount();

    /**
     * Set number of page to download
     *
     * @param pageCount
     *            NEEDDOC
     * @see #getPageCount()
     */
    void setPageCount( int pageCount );

    /**
     * Returns max number of page to download (for UI)
     * @return max number of page to download
     */
    int getMaxPageCount();

    /**
     * @return relative directory cache name
     */
    String getCacheRelativeDirectoryCacheName();

    Proxy getProxy();

    void setProxy( Proxy proxy );
}
