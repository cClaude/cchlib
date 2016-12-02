package com.googlecode.cchlib.tools.downloader.common;

import java.io.File;
import java.net.URL;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.DownloadEvent;

public interface LoggerListener extends DownloadEvent
{
    void error( URL url, File file, Throwable cause );

    void downloadStateInit( DownloadStateEvent event );

    void downloadStateChange( DownloadStateEvent event );

    void downloadCantRename(
            ContentDownloadURI<File> downloader,
            File                     tmpFile,
            File                     expectedCacheFile
            );

    void downloadStored( ContentDownloadURI<File> downloader );

    /**
     * Invoke when a download file is out of constraints
     *
     * @param downloader related {@link ContentDownloadURI}
     */
    void oufOfConstraints( ContentDownloadURI<File> downloader );
}
