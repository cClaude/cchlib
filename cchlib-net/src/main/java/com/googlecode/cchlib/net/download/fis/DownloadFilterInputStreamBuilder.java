package com.googlecode.cchlib.net.download.fis;

import java.io.FilterInputStream;
import java.io.InputStream;
import com.googlecode.cchlib.net.download.DownloadFileURL;

/**
 * TODOC
 *
 */
public interface DownloadFilterInputStreamBuilder
{
    /**
     * TODOC
     * @param is
     * @return TODOC
     */
    FilterInputStream createFilterInputStream( InputStream is );

    /**
     * TODOC
     * @param filter
     * @param dURL
     */
    void storeFilterResult( FilterInputStream filter, DownloadFileURL dURL );

}
