package samples.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;

/**
 * TODOC
 *
 */
class PolyURLDownloadFileURL extends DefaultDownloadFileURL
{
    private static final long serialVersionUID = 1L;
    private static final String INITIAL_VALUE_PROPERTY = "initialValue";
    private final List<URL> nextURLs;
    private int             nextURLIndex = 0;

    /**
     * TODOC
     * 
     * @param defaultURL
     * @param requestPropertyMap 
     * @param proxy
     * @param nextURLs
     * @param initialValue
     * 
     * @throws URISyntaxException 
     */
    public PolyURLDownloadFileURL(
        final URL                   defaultURL, 
        final Map<String,String>    requestPropertyMap,
        final Proxy                 proxy,
        final List<URL>             nextURLs,
        final String                initialValue 
        ) throws URISyntaxException
    {
        super( defaultURL, requestPropertyMap, proxy );
        
        this.nextURLs = nextURLs;
        
        setProperty( INITIAL_VALUE_PROPERTY, initialValue );
    }
    
    /**
     * TODOC
     */
    @Override
    public InputStream getInputStream() throws IOException
    {
        for(;;) {
            try {
                return super.getInputStream();
                }
            catch( IOException ioe ) {
                if( nextURLIndex < nextURLs.size() ) {
                    super.setURL( nextURLs.get( nextURLIndex++ ) );
                    }
                else {
                    throw ioe;
                    }
                }
            }
    }

    /**
     * TODOC
     * @return TODOC
     */
    public String getInitialURLString()
    {
        return String.class.cast(
            super.getProperty( INITIAL_VALUE_PROPERTY )
            );
    }
}