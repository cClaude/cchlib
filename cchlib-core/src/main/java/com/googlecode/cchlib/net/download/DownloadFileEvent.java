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
     * Returns a new empty {@link File}
     * @return a new empty {@link File}
     * @throws IOException If a file could not be created
     */
    public File createDownloadTmpFile() throws IOException;
}
