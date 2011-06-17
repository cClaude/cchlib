package com.googlecode.cchlib.net.download;

import java.net.URL;

/**
 * {@link Runnable} that support an {@link URL} getter.
 */
public interface RunnableDownload extends Runnable
{
    /**
     * Returns {@link URL} of this download task
     * @return {@link URL} of this download task
     */
    public URL getURL();
}
