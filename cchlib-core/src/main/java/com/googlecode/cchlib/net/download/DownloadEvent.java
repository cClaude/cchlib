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
    public void downloadStart( final DownloadURL dURL );

    /**
     *
     * @param dURL
     */
    public void downloadDone( final DownloadURL dURL );

    /**
     *
     * @param dioe
     */
    public void downloadFail( final DownloadIOException dioe );
}
