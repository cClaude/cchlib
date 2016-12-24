package cx.ath.choisnet.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Classe permettant de concerver un context lors d'echange de flux HTTP.
 * <p>
 * See :<br>
 * <a href="http://www.faqs.org/rfcs/rfc2068.html">RFC 2068</a><br>
 * <a href="http://www.faqs.org/rfcs/rfc2109.html">RFC 2109</a><br>
 * <p>
 * BUG: Ne gere pas *encore* les cookies multiples...
 *
 * @since 2.02
 */
public class URLContext implements Serializable
{
    private static final long   serialVersionUID = 1L;

    private Map<String, String> requestProperty;
    private Proxy               proxy;
    private Boolean             doInput          = null;
    private Boolean             doOutput         = null;

    public URLContext()
    {
        init( null, null );
    }

    public URLContext( final URLContext context )
    {
        init( context, null );
    }

    public URLContext( final URLContext context, final Proxy proxy )
    {
        init( context, proxy );
    }

    private void init( final URLContext context, final Proxy proxy )
    {
        if( context == null ) {
            this.requestProperty = new TreeMap<>();
        } else {
            this.requestProperty = new TreeMap<>( context.requestProperty );
        }

        setProxy( proxy );
    }

    public void setProxy( final Proxy proxy )
    {
        this.proxy = proxy;
    }

    /**
     ** Ouvre la connection et applique les RequestProperty de la connection courante.
     **
     ** @see URL#openConnection
     */
    public URLConnection openConnection( final URL url )
        throws IOException
    {
        URLConnection connection;

        if( this.proxy == null ) {
            connection = url.openConnection();
        } else {
            connection = url.openConnection( this.proxy );
        }

        for( final Map.Entry<String, String> entry : this.requestProperty.entrySet() ) {
            connection.setRequestProperty( entry.getKey(), entry.getValue() );
        }

        if( this.doInput != null ) {
            connection.setDoInput( this.doInput.booleanValue() );
        }

        if( this.doOutput != null ) {
            connection.setDoOutput( this.doOutput.booleanValue() );
        }

        return connection;
    }

    /**
     *
     * @param url NEEDDOC
     * @return NEEDDOC
     * @throws IOException NEEDDOC
     * @see #openConnection(URL)
     */
    public HttpURLConnection openHttpConnection( final URL url )
        throws IOException
    {
        return HttpURLConnection.class.cast( openConnection( url ) );
    }

    /**
     * update current URLContext from URLConnection
     *
     * @param connection
     *            URLConnection deje connectee.
     *
     * @see #openHttpConnection
     * @see #openConnection
     * @see URLConnection#connect()
     */
    public void update( final URLConnection connection )
    {
        // EX:

        // Set-Cookie: Customer="WILE_E_COYOTE"; Version="1"; Path="/acme"

        // Cookie: $Version="1"; Customer="WILE_E_COYOTE"; $Path="/acme"

        // Set-Cookie: Part_Number="Rocket_Launcher_0001"; Version="1";
        // Path="/acme"

        // Cookie: $Version="1";
        // Customer="WILE_E_COYOTE"; $Path="/acme";
        // Part_Number="Rocket_Launcher_0001"; $Path="/acme"

        String cookie = connection.getHeaderField( "Set-Cookie" ); // RFC 2109

        if( cookie != null ) {
            final int index = cookie.indexOf( ";" );

            if( index >= 0 ) {
                cookie = cookie.substring( 0, index );
            }

            this.requestProperty.put( "Cookie", cookie ); // RFC 2109
        }
    }

    /**
     * Permet de definir des proprietes utilise par toutes les requetes
     * emise e l'aide de l'objet courant.
     * <p>
     * Typiquement on initialisera un echange par<br>
     *
     * <pre>
     * URLContext ctxt = new URLContext();
     *
     * ctxt.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible)" );
     * ctxt.setRequestProperty( "Accept-Language", "en-us" );
     * ctxt.setRequestProperty( "Accept", "*"+"/*" );
     * ctxt.setRequestProperty( "Connection", "Keep-Alive");
     * ctxt.setRequestProperty( "Cache-Control", "no-cache");
     * <pre>
     *
     * @param key NEEDDOC
     * @param value NEEDDOC
     * @see URLConnection#setRequestProperty( String,String )
     */
    public void setRequestProperty( final String key, final String value )
    {
        this.requestProperty.put( key, value );
    }

    public void removeRequestProperty( final String key )
    {
        this.requestProperty.remove( key );
    }

    /**
     ** Returns an unmodifiable Set of properties which were defined on current context
     **
     ** @return an unmodifiable {@link Set} of {@link Entry}
     */
    public Set<Map.Entry<String, String>> getRequestProperties()
    {
        return Collections.unmodifiableSet( this.requestProperty.entrySet() );
    }

    /**
     * NEEDDOC
     * @param doinput NEEDDOC
     * @see URLConnection#setDoInput(boolean )
     */
    public void setDoInput( final boolean doinput )
    {
        this.doInput = Boolean.valueOf( doinput );
    }

    /**
     * NEEDDOC
     * @param dooutput NEEDDOC
     * @see URLConnection#setDoOutput(boolean )
     */
    public void setDoOutput( final boolean dooutput )
    {
        this.doOutput = Boolean.valueOf( dooutput );
    }

    @Override
    public String toString()
    {
        return "URLContext[" + this.requestProperty + "]";
    }
}
