package com.googlecode.cchlib.net.download;

/**
 * {@link Runnable} that support an {@link DownloadURI} getter.
 *
 * @param <R>
 *            Result expected type for download
 *
 * @since 4.1.7
 */
public interface RunnableDownload<R> extends Runnable
{
    /**
     * Returns {@link DownloadURI} of this download task
     * @return {@link DownloadURI} of this download task
     */
    ContentDownloadURI<R> getDownloadURL();
}
