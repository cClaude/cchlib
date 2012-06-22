package com.googlecode.cchlib.net.download;

/**
 * {@link Runnable} that support an {@link DownloadURL} getter.
 *
 * @since 4.1.7
 */
public interface RunnableDownload extends Runnable
{
    /**
     * Returns {@link DownloadURL} of this download task
     * @return {@link DownloadURL} of this download task
     */
    public DownloadURL getDownloadURL();
}
