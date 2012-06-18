package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.IOException;

/**
 * Events while downloading.
 * @since 4.1.7
 */
public interface DownloadFileEvent extends DownloadEvent
{
    /**
     *
     * @return
     * @throws IOException
     */
    public File getDownloadTmpFile() throws IOException;
}
