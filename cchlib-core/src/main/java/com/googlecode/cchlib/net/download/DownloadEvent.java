package com.googlecode.cchlib.net.download;

/**
 * Events while downloading.
 * @since 4.1.5
 */
public interface DownloadEvent
{
//    /**
//     * Returns expected result type
//     * @return expected result type
//     * @since 4.1.7
//     */
//    public DownloadResultType getDownloadResultType();

    /**
     *
     * @param url
     * @since 4.1.7
     */
    public void downloadStart( final DownloadURL url );

    /**
     *
     * @param e
     * @since 4.1.7
     */
    public void downloadFail( final DownloadIOException e );

    /**
     *
     * @param url
     * @param result
     * @since 4.1.7
     */
    public void downloadDone( final DownloadURL url );
}
