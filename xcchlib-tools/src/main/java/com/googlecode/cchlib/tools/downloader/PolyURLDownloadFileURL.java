package com.googlecode.cchlib.tools.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.googlecode.cchlib.net.download.DefaultDownloadFileURL;

/**
 * TODOC
 *
 */
public class PolyURLDownloadFileURL extends DefaultDownloadFileURL
{
    private static final long serialVersionUID = 1L;
    private static final String INITIAL_VALUE_PROPERTY = "initialValue";
    private final List<URI> nextURIs;
    private int             nextURIIndex = 0;

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
        final Collection<URI>       nextURIs,
        final String                initialValue
        ) throws URISyntaxException
    {
        super( defaultURL, requestPropertyMap, proxy );

        this.nextURIs = new ArrayList<>( nextURIs );

        setProperty( INITIAL_VALUE_PROPERTY, initialValue );
    }

    /**
     * TODOC
     * @throws URISyntaxException
     */
    @Override
    public InputStream getInputStream() throws IOException
    {
        for(;;) {
            try {
                return super.getInputStream();
                }
            catch( IOException ioe ) {
                if( nextURIIndex < nextURIs.size() ) {
                    super.setURI( nextURIs.get( nextURIIndex++ ) );
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
