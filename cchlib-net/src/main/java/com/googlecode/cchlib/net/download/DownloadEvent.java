package com.googlecode.cchlib.net.download;

/**
 * Events while downloading.
 *
 * @since 4.1.7
 */
public interface DownloadEvent
{
    /**
     *
     * @param dURL
     */
    void downloadStart( final DownloadURL dURL );

    /**
     *
     * @param dURL
     */
    void downloadDone( final DownloadURL dURL );

    /**
     *
     * @param dioe
     */
    void downloadFail( final DownloadIOException dioe );
}
