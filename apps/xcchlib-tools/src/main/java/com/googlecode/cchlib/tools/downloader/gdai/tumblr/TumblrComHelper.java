package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.StringDownloader;

public class TumblrComHelper
{
    private TumblrComHelper()
    {
        // Should not be static
    }

    static ContentDownloadURI<String> getDownloadStringURL(
            final String    hostname,
            final int       pageNumber,
            final Proxy     proxy
            )
            throws MalformedURLException, URISyntaxException
    {
        final String fmt;

        if( pageNumber == 1 ) {
            fmt = TumblrComData.HTML_URL_BASE1_FMT;
            }
        else {
            fmt = TumblrComData.HTML_URL_BASEx_FMT;
            }
        return new StringDownloader(
                String.format(
                    fmt,
                    hostname,
                    Integer.valueOf( pageNumber )
                    ),
                null,
                proxy
                );
    }

}
