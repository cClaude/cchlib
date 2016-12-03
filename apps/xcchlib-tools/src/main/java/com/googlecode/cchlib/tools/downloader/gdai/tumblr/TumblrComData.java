package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import com.googlecode.cchlib.tools.downloader.common.DownloaderDataImpl;

public class TumblrComData extends DownloaderDataImpl
{
    private static final long serialVersionUID = 1L;

    static final String SITE_NAME_GENERIC = "*.tumblr.com";
   /*
     * http://[NAME].tumblr.com
     */
    private static final String SERVER_ROOT_URL_STR_FMT = "http://%s.tumblr.com";

    /*
     * http://[NAME].tumblr.com/
     * http://[NAME].tumblr.com/page/[NUMBER]
     */
    static final String HTML_URL_BASE1_FMT = SERVER_ROOT_URL_STR_FMT + "/page/%d";
    static final String HTML_URL_BASEx_FMT = SERVER_ROOT_URL_STR_FMT + "/page/%d";

    //private static final String SITE_NAME_ALL     = "www.tumblr.com";
    /*
     * [NAME].tumblr.com
     *
     * Use for label AND for cache directory name
     */
    static final String SITE_NAME_FMT = "%s.tumblr.com";
    static final int NUMBER_OF_PICTURES_BY_PAGE = -1;

    /** number of pages to explore */
    static final int DEFAULT_MAX_PAGES_BLOGS = 32;
    //private static final int DEFAULT_MAX_PAGES_ALL = 16;

    static final  int[] TUMBLR_COM_KNOWN_SIZES = {
            1280,
             500,
             400,
             250,
             100
           };

    public String getCurrentHostName()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
