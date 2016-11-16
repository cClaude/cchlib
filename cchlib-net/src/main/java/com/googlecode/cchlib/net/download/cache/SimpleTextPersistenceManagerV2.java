package com.googlecode.cchlib.net.download.cache;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map.Entry;

//Not public
class SimpleTextPersistenceManagerV2 extends AbstractSimpleTextPersistenceManagerV1 implements URICachePersistenceManager
{
    private static final String VERSION_STR = "V:2";

    public SimpleTextPersistenceManagerV2()
    {
        super( VERSION_STR );
    }

    @Override
    protected String convertEntryURL_URI_ToString( final Entry<URI, URIDataCacheEntry> entry )
    {
        // First line is a valid URI
        // Can not be null (use of toASCIIString())
        final URI uri = entry.getKey();

        return uri.toASCIIString();
    }

    @Override
    protected URI convertLineToURI( final String line ) //
        throws URISyntaxException, MalformedURLException
    {
        URI uri;
        uri = new URI( line );

        assert uri.equals( new URL( line ).toURI() );
        return uri;
    }

}
