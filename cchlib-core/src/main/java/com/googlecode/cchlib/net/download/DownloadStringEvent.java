package com.googlecode.cchlib.net.download;

import java.net.URL;

/**
 * Events while downloading into a {@link String}
 *
 * @see DownloadToString
 * @since 4.1.5
 */
public interface DownloadStringEvent extends DownloadEvent
{
    /**
     * This method is invoked when download start
     *
     * @param url {@link URL} of download
     */
    public void downloadStart( URL url );

    /**
     * This method is invoked when download is done
     *
     * @param url       {@link URL} of download
     * @param content   Download content
     */
    public void downloadDone( URL url, String content );

    /**
     * This method is invoked if download fail
     *
     * @param url   {@link URL} of download
     * @param cause Abort cause
     */
    public void downloadFail( URL url, Throwable cause );

}
