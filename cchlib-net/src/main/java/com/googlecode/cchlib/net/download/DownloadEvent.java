package com.googlecode.cchlib.net.download;

/**
 * Events while downloading.
 *
 * @since 4.1.7
 */
public interface DownloadEvent
{
    /**
     * Indicate that download start for this {@code downloadURL}
     *
     * @param downloadURL The {@link DownloadURI}
     */
    void downloadStart( final DownloadURI downloadURL );

    /**
     * Indicate that download is done (success) for this {@code downloadURL}
     *
     * @param downloadURL The {@link DownloadURI}
     */
    void downloadDone( final DownloadURI downloadURL );

    /**
     * Indicate that download fail for this {@code downloadURL}
     *
     * @param downloadURLError The {@link DownloadIOException}
     */
    void downloadFail( final DownloadIOException downloadURLError );
}
