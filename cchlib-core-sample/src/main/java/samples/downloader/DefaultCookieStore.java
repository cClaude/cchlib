package samples.downloader;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public final class DefaultCookieStore implements CookieStore
{
    final Map<URI,List<HttpCookie>> cookies = new HashMap<>();

    /**
     *
     */
    public DefaultCookieStore()
    {
        // empty
    }

    @Override
    public void add( final URI uri, final HttpCookie cookie )
    {
        List<HttpCookie> values = cookies.get( uri );

        if( values == null ) {
            values = new ArrayList<>();
            cookies.put( uri, values  );
            }

        values.add( cookie );
    }

    @Override
    public List<HttpCookie> get( URI uri )
    {
        final List<HttpCookie> l = cookies.get( uri );

        if( l == null ) {
            return Collections.emptyList();
            }
        else {
            return Collections.unmodifiableList( l );
            }
    }

    @Override
    public List<HttpCookie> getCookies()
    {
        final List<HttpCookie> l = new ArrayList<>();

        for( List<HttpCookie> values : cookies.values() ) {
            l.addAll( values );
            }

        return Collections.unmodifiableList( l );
    }

    @Override
    public List<URI> getURIs()
    {
        return Collections.unmodifiableList(
            new ArrayList<URI>( cookies.keySet() )
            );
    }

    @Override
    public boolean remove( URI uri, HttpCookie cookie )
    {
        List<HttpCookie> l = cookies.get( uri );

        if( l == null ) {
            return false;
            }

        return l.remove( cookie );
    }

    @Override
    public boolean removeAll()
    {
        if( ! cookies.isEmpty() ) {
            cookies.clear();
            return true;
            }

        return false;
    }
}
