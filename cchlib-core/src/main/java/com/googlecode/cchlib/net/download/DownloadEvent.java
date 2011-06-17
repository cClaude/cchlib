package com.googlecode.cchlib.net.download;

import java.net.URL;

/**
 * Events while downloading.
 * @since 4.1.5
 */
public interface DownloadEvent
{
    /**
     * This method is invoked if download fail
     *
     * @param url   {@link URL} of download
     * @param cause Abort cause
     */
    public void downloadFail( URL url, Throwable cause );
}