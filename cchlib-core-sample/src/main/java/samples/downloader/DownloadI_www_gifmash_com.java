package samples.downloader;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;

/**
 * http://www.gifmash.com/
 *
 * TODO:
 * http://picasion.com/gallery/24151868/
 * 4gifs.com
 */
public class DownloadI_www_gifmash_com extends AbstractDownloaderAppInterface
{
    private static final String SITE_NAME = "www.gifmash.com";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 10;
    private static final int DEFAULT_MAX_PAGES = 100;
    private ComboBoxConfig mainComboBoxConfig;

    protected DownloadI_www_gifmash_com()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        String[] comboBoxValues = { "http://www.gifmash.com/blog/page/%d" };
        String[] labelStrings   = { "pics from blog" };

        mainComboBoxConfig =  new DefaultComboBoxConfig( "Main page", comboBoxValues, labelStrings );

        super.addComboBoxConfig( mainComboBoxConfig );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "com.gifmash.www";
    }

    @Override
    public DownloadStringURL getDownloadStringURL( final int pageNumber ) throws MalformedURLException, URISyntaxException
    {
        return new DefaultDownloadStringURL(
            String.format(
                mainComboBoxConfig.getComboBoxSelectedValue(),
                pageNumber
                ),
            null,
            getProxy()
            );
    }

    @Override
    public Collection<DownloadFileURL> getURLToDownloadCollection(
            final GenericDownloaderAppUIResults gdauir,
            final DownloadStringURL             content2Parse
            ) throws MalformedURLException
    {
        // <img class="event-item-lol-image" src="https://chzgifs.files.wordpress.com/2012/06/bald-eagle-in-slow-motion.gif" a
        RegExgSplitter[] regexps = {
            new DefaultRegExgSplitter( "\\<img class=\"event-item-lol-image\" src=\"", '"' ),
            new DefaultRegExgSplitter( "\\<img class='event-item-lol-image' src='", '\'' ),
            };
        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps   );
    }

    @Override
    public DownloadFileURL getDownloadURLFrom( String src, int regexpIndex )
            throws MalformedURLException, URISyntaxException
    {
        return new DefaultDownloadFileURL( src, null, getProxy() );
    }
}
