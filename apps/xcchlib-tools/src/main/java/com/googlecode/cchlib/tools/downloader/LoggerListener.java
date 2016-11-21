package com.googlecode.cchlib.tools.downloader;

import java.io.File;
import java.net.URL;
import com.googlecode.cchlib.net.download.DownloadEvent;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadURL;

/**
 *
 */
public interface LoggerListener extends DownloadEvent
{
    /**
     *
     * @param url
     * @param file
     * @param cause
     */
    void error( URL url, File file, Throwable cause );

    /**
     *
     * @param event
     */
    void downloadStateInit( DownloadStateEvent event );

    /**
     *
     * @param event
     */
    void downloadStateChange( DownloadStateEvent event );

    /**
     *
     * @param dURL
     * @param tmpFile
     * @param expectedCacheFile
     */
    void downloadCantRename( DownloadURL dURL, File tmpFile, File expectedCacheFile );

    /**
     *
     * @param dURL
     */
    void downloadStored( DownloadURL dURL );

    /**
     * Invoke when a download file is out of constraints
     *
     * @param dfURL
     */
    void oufOfConstraints( DownloadFileURL dfURL );
}
