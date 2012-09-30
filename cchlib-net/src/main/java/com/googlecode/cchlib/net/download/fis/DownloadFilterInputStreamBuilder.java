package com.googlecode.cchlib.net.download.fis;

import java.io.FilterInputStream;
import java.io.InputStream;
import com.googlecode.cchlib.net.download.DownloadFileURL;

/**
 *
 *
 */
public interface DownloadFilterInputStreamBuilder 
{
    /**
     * 
     * @param is
     * @return
     */
    public FilterInputStream createFilterInputStream( InputStream is );

    /**
     * 
     * @param filter
     * @param dURL
     */
    public void storeFilterResult( FilterInputStream filter, DownloadFileURL dURL );

}
