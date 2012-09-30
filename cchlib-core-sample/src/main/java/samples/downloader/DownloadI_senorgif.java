package samples.downloader;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.net.download.DefaultDownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;

/**
 * http://senorgif.memebase.com/page/5/ 1600
 */
public class DownloadI_senorgif
    extends AbstractDownloaderAppInterface
{
    private static final String SITE_NAME = "senorgif";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 5;
    private static final int DEFAULT_MAX_PAGES = 15;

    private ComboBoxConfig mainComboBoxConfig;

    protected DownloadI_senorgif()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );

        String[] comboBoxValues = { "http://senorgif.memebase.com/page/%d/" , "http://senorgif.memebase.com/vote/" };
        String[] labelStrings   = { "pages type 1"                          , "pages type 2"};

        mainComboBoxConfig = new DefaultComboBoxConfig(
             "Page type",
             comboBoxValues,
             labelStrings
             );
        super.addComboBoxConfig( mainComboBoxConfig );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "com.memebase.senorgif";
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
