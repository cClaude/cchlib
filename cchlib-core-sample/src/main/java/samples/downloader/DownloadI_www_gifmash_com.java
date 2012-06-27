package samples.downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 * http://www.gifmash.com/
 *
 * TODO:
 * http://picasion.com/gallery/24151868/
 * 4gifs.com
 */
public class DownloadI_www_gifmash_com extends AbstractExtentedDownloadInterface
{
    private ComboBoxConfig mainComboBoxConfig;

    protected DownloadI_www_gifmash_com()
    {
        super(
            "http://www.gifmash.com/", 10,
            100
            );

        String[] comboBoxValues = { "http://www.gifmash.com/blog/page/%d" };
        String[] labelStrings   = { "pics from blog" };

        mainComboBoxConfig =  new DefaultComboBoxConfig( "Main page", comboBoxValues, labelStrings );

        super.addComboBoxConfig( mainComboBoxConfig );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "www.gifmash.com";
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
        // <img class="event-item-lol-image" src="https://chzgifs.files.wordpress.com/2012/06/bald-eagle-in-slow-motion.gif" a
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
