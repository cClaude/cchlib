package com.googlecode.cchlib.tools.downloader.connector;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppUIResults;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.tools.downloader.common.AbstractDownloaderAppInterface;


/**
 *
 *
 *http://www.photofunia.com/output/4/1/I/V/c/IVcOXqVdzyhMjNamUoG1IA_s.jpg
 */
public class DownloadI_www_epins_fr
    extends AbstractDownloaderAppInterface
{
    private static final long serialVersionUID = 1L;
    /* http://www.epins.fr/pins/98699.png */
    /* http://www.epins.fr/pins/104639.png */
    private static final String serverRootURLString = "http://www.epins.fr";
    private static final String htmlURLFmt0         = serverRootURLString + "/pins/";
    //private static final String htmlURLFmt1         = "00000";
    private static final String htmlURLFmt2         = ".png";
    //private static final int    MIN = 0;
    //private static final int    MAX = 1000000;
    private static final int    MIN = 1000000;
    private static final int    MAX = 1100000;
    //private static final int    MAX = 110;

    private static final String SITE_NAME = "www.epins.fr";
    private static final int NUMBER_OF_PICTURES_BY_PAGE = 10;
    private static final int DEFAULT_MAX_PAGES = 10;

    public DownloadI_www_epins_fr()
    {
        super(
                SITE_NAME,
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES
                );
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return "fr.epins";
    }

    @Override
    public Collection<ContentDownloadURI<String>> getURLDownloadAndParseCollection()
            throws MalformedURLException
    {
        return Collections.emptyList();
    }

    @Override
    public Collection<ContentDownloadURI<File>> computeURLsAndGetDownloader(
            final GenericDownloaderAppUIResults   gdauir,
            final ContentDownloadURI<String>      content2Parse
            )
            throws MalformedURLException
    {
        return new AbstractCollection<ContentDownloadURI<File>>()
        {
            @Override
            public Iterator<ContentDownloadURI<File>> iterator()
            {
                return new Iterator<ContentDownloadURI<File>>()
                {
                    private final StringBuilder buildURL_sb1 = new StringBuilder();
                    //private StringBuilder buildURL_sb2 = new StringBuilder();
                    private int i = MIN;

                    @Override
                    public boolean hasNext()
                    {
                        return this.i<MAX;
                    }
                    @Override
                    public ContentDownloadURI<File> next()
                    {
                        try {
                            return buildDownloadURL( this.i++ );
                            }
                        catch( MalformedURLException | URISyntaxException e ) {
                            throw new RuntimeException( e );
                            }
                    }
                    @Override
                    public void remove()
                    {
                        throw new UnsupportedOperationException();
                    }
                    private ContentDownloadURI<File> buildDownloadURL( final int i ) throws MalformedURLException, URISyntaxException
                    {

                        this.buildURL_sb1.setLength( 0 );
                        //buildURL_sb2.setLength( 0 );

                        this.buildURL_sb1.append( htmlURLFmt0 );

                        this.buildURL_sb1.append( i );
                        //buildURL_sb2.append( htmlURLFmt1 ).append( i );
                        //int end     = buildURL_sb2.length();
                        //int start   = end - htmlURLFmt1.length();

                        //buildURL_sb1.append( buildURL_sb2.substring( start, end ) );
                        this.buildURL_sb1.append( htmlURLFmt2 );

                        return new DefaultDownloadFileURL( this.buildURL_sb1.toString(), null, getProxy() );
                    }
                };
            }
            @Override
            public int size()
            {
                return MAX - MIN;
            }
        };
    }

    @Override
    public ContentDownloadURI<String> getDownloadStringURL( final int pageNumber )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();// NOT USE
    }

    @Override
    public ContentDownloadURI<File> getDownloadURLFrom( final String src, final int regexpIndex )
            throws MalformedURLException
    {
        throw new UnsupportedOperationException();// NOT USE
    }

    @Override
    public GenericDownloaderAppButton getButtonConfig()
    {
        return null;
    }

    @Override
    public void doSelectedItems( final List<Item> selectedItems )
    {
        // TODO Not implemented
        Logger.getLogger( getClass() ).warn( "NOT IMPLEMENTED" );
    }
}
