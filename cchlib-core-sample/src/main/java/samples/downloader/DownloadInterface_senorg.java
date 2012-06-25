package samples.downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 * http://picasion.com/gallery/24151868/
 * http://senorgif.memebase.com/page/5/ 1600
 */
public class DownloadInterface_senorg extends AbstractDownloadInterface
{
    private final static String[][] URLS_DB = {
        { "pages type 1", "http://senorgif.memebase.com/page/%d/" },
        { "pages type 2", "http://senorgif.memebase.com/vote/" },
        };

    protected DownloadInterface_senorg()
    {
        super( 5 );
    }

    @Override
    public String getSiteName()
    {
        return "senorgif";
    }

    @Override
    public int getNumberOfPicturesByPage()
    {
        return 5;
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "output/senorgif";
    }

    @Override
    public StringDownloadURL getStringDownloadURL( final int pageNumber ) throws MalformedURLException
    {
        return new StringDownloadURL(
            String.format(
                URLS_DB[ getExtraStringSelectedIndex() ][ 1 ],
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
        // <img class="event-item-lol-image" src="https://chzgifs.files.wordpress.com/2012/06/funny-gifs-tense-situation.gif" alt="" +
        // <img class='event-item-lol-image' src='http://chzgifs.files.wordpress.com/2012/06/funny-gifs-tense-situation.gif'
//        final String[] regexpsc = {
//            "\\<img class=\"event-item-lol-image\" src=\"",
//            "\\<img class='event-item-lol-image' src='"
//            };
//
        RegExgSplitter[] regexps = {
            new DefaultRegExgSplitter( "\\<img class=\"event-item-lol-image\" src=\"", '"' ),
            new DefaultRegExgSplitter( "\\<img class='event-item-lol-image' src='", '\'' ),
            };
        return super.getURLToDownloadCollection( gdauir, content2Parse, regexps   );
    }

    @Override
    public URL getURL( final String src, final int regexpIndex ) throws MalformedURLException
    {
        return new URL( src );
    }

    @Override
    public boolean isExtraStringValue()
    {
        return true;
    }

    @Override
    public String getExtraStringLabel()
    {
        return "Page type";
    }

    @Override
    public Collection<String> getExtraStringValues()
    {
        AbstractCollection<String> extraStringValues = new AbstractCollection<String>()
        {
            @Override
            public Iterator<String> iterator()
            {
                return new Iterator<String>()
                {
                    int index = 0;

                    @Override
                    public boolean hasNext()
                    {
                        return index < size();
                    }
                    @Override
                    public String next()
                    {
                        return URLS_DB[ index++ ][ 1 ];
                    }
                    @Override
                    public void remove()
                    {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            @Override
            public int size()
            {
                return URLS_DB.length;
            }
        };
        return extraStringValues;
    }

    @Override
    public String getExtraStringLabels( final int index )
    {
        return URLS_DB[ index ][ 0 ];
    }
}
