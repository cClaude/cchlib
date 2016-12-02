package com.googlecode.cchlib.net.download.fis;

import java.io.FilterInputStream;
import java.io.InputStream;
import com.googlecode.cchlib.net.download.ContentDownloadURI;

/**
 * Factory for {@link FilterInputStream}
 *
 * @param <R>
 *            Result expected type for download
 */
public interface DownloadFilterInputStreamBuilder<R>
{
    /**
     * Create a {@link FilterInputStream}
     *
     * @param is Parent {@link InputStream}
     * @return a {@link FilterInputStream}
     */
    FilterInputStream createFilterInputStream( InputStream is );

    /**
     * Set the {@link FilterInputStream}
     *
     * @param filter the {@link FilterInputStream} to set
     * @param downloader Related {@link ContentDownloadURI}
     */
    void storeFilterResult(
        FilterInputStream     filter,
        ContentDownloadURI<R> downloader
        );

}
