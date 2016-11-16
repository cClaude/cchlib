package com.googlecode.cchlib.net.download.fis;

import java.io.FilterInputStream;
import java.io.InputStream;
import com.googlecode.cchlib.net.download.DownloadFileURL;

/**
 * NEEDDOC
 */
public interface DownloadFilterInputStreamBuilder
{
    /**
     * NEEDDOC
     *
     * @param is
     * @return NEEDDOC
     */
    FilterInputStream createFilterInputStream( InputStream is );

    /**
     * NEEDDOC
     *
     * @param filter
     * @param dURL
     */
    void storeFilterResult( FilterInputStream filter, DownloadFileURL dURL );

}
