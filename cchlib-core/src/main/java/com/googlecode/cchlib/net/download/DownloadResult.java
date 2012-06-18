package com.googlecode.cchlib.net.download;

import java.io.File;


/**
 * TODOC
 *
 * @since 4.1.7
 */
public interface DownloadResult
{
    /**
     * TODOC
     * @return
     */
    public DownloadResultType getType();

    /**
     * TODOC
     * @return
     */
    public String getString();

    /**
     * TODOC
     * @return
     */
    public File getFile();
}
