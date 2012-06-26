package samples.downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 * http://senorgif.memebase.com/page/5/ 1600
 */
public class DownloadI_senorg
    extends AbstractExtentedDownloadInterface
{
    private ComboBoxConfig mainComboBoxConfig;

    protected DownloadI_senorg()
    {
        super( "senorgif", 5, 15 );

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
        return "senorgif";
    }

    @Override
    public StringDownloadURL getStringDownloadURL( final int pageNumber ) throws MalformedURLException
    {
        return new StringDownloadURL(
            String.format(
                mainComboBoxConfig.getComboBoxSelectedValue(),
                pageNumber
                )
            );
    }

    @Override
    public Collection<URL> getURLToDownloadCollection(
            final GenericDownloaderAppUIResults gdauir,
            final String                        content2Parse
            ) throws MalformedURLException
    {
        RegExgSplitter[] regexps = {
            new DefaultRegExgSplitter( "\\<img class=\"event-item-lol-image\" src=\"", '"' ),
            new DefaultRegExgSplitter( "\\<img class='event-item-lol-image' src='", '\'' ),
            };
        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps   );
    }

    @Override
    public URL getURLToDownload( final String src, final int regexpIndex )
            throws MalformedURLException
    {
        return new URL( src );
    }
}
