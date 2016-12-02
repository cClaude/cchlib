package com.googlecode.cchlib.net.download.fis;

import java.io.File;
import java.io.FilterInputStream;
import java.io.InputStream;
import com.googlecode.cchlib.io.checksum.MD5FilterInputStream;
import com.googlecode.cchlib.net.download.ContentDownloadURI;

/**
 * NEEDDOC
 *
 * @since 4.1.7
 */
public class MD5FilterInputStreamBuilder
    implements DownloadFilterInputStreamBuilder<File>
{
    /**
     *
     */
    public MD5FilterInputStreamBuilder()
    {
        // Empty
    }

    /**
     * Create a new {@link MD5FilterInputStream} based on
     * given {@link InputStream}
     * @param is underlying {@link InputStream}
     * @return a new {@link MD5FilterInputStream}
     */
    @Override
    public MD5FilterInputStream createFilterInputStream( final InputStream is )
    {
        return new MD5FilterInputStream( is );
    }

    /**
     * Set filter result on {@link ContentDownloadURI}.
     *
     * @param filter    Closed filter to use for result
     * @param dURL      DownloadFileURL that will received result.
     */
    @Override
    public void storeFilterResult(
        final FilterInputStream        filter,
        final ContentDownloadURI<File> dURL
        )
    {
        dURL.setProperty(
            DefaultFilterInputStreamBuilder.HASH_CODE,
            MD5FilterInputStream.class.cast( filter ).getHashString()
            );
    }
}
