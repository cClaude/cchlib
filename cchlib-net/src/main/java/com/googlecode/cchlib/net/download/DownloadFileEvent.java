package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;

/**
 * Events while downloading.
 *
 * @since 4.1.7
 */
public interface DownloadFileEvent extends DownloadEvent
{
    /**
     * Returns a new empty {@link File}
     * @return a new empty {@link File}
     * @throws IOException If a file could not be created
     */
    File createDownloadTmpFile() throws IOException;

    /**
     * Convert {@link DownloadURI} to a {@link ContentDownloadURI}&lt;File&gt;
     *
     * @param downloader {@link DownloadURI} for current {@link DownloadFileEvent}
     *
     * @return a {@link ContentDownloadURI}&lt;File&gt;
     */
    static ContentDownloadURI<File> getDownloader( final DownloadURI downloader )
    {
        @SuppressWarnings({"unchecked","squid:S1488"})
        final ContentDownloadURI<File> fileDownloader =  (ContentDownloadURI<File>)downloader;

        return fileDownloader;
    }
}
