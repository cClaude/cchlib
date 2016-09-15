package com.googlecode.cchlib.net.download.cache;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map.Entry;

/**
 *
 * @since 4.1.7
 */
//Not public
class SimpleTextPersistenceManagerV1
    extends AbstractSimpleTextPersistenceManagerV1
        implements URICachePersistenceManager
{
    private static final String VERSION_STR = "V:1";

    public SimpleTextPersistenceManagerV1()
    {
        super( VERSION_STR );
    }

    @Override
    protected String convertEntryURL_URI_ToString(
        final Entry<URI, URIDataCacheEntry> entry
        )
    {
        final URI uri = entry.getKey();

        try {
            return uri.toURL().toExternalForm();
            }
        catch( final MalformedURLException e ) {
            throw new MalformedURLRuntimeException( e );
            }
    }

    @Override
    protected URI convertLineToURI( final String line ) //
        throws URISyntaxException, MalformedURLException
    {
        return new URL( line ).toURI();
    }
}
