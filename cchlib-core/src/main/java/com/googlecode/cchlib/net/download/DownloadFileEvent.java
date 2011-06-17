package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Events while downloading into a {@link File}
 *
 * @see DownloadToFile
 * @since 4.1.5
 */
public interface DownloadFileEvent extends DownloadEvent
{
    /**
     * This method is invoked when download start
     *
     * @param url {@link URL} of download
     * @throws IOException if any when creating temporary file
     */
    public File createTempFile( final URL url ) throws IOException;

    /**
     * This method is invoked when download is done
     *
     * @param url   {@link URL} of download
     * @param file  {@link File} that have download content
     */
    public void downloadDone( final URL url, final File file );
}
