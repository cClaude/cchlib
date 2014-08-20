/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/URLContext.java
** Description   :
** Encodage      : ANSI
**
**  2.02.040 2006.01.04 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.URLContext
**
**
** http://martin.nobilitas.com/java/cookies.html
**
*/
package cx.ath.choisnet.net;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
** <p>
** Classe permettant de concerver un context lors d'echange de
** flux HTTP.
**
** See :<br/>
** <a href="http://www.faqs.org/rfcs/rfc2068.html">RFC 2068</a><br/>
** <a href="http://www.faqs.org/rfcs/rfc2109.html">RFC 2109</a><br/>
** </p>
** <p>
** BUG: Ne gere pas *encore* les cookies multiples...
** </p>
**
** @author Claude CHOISNET
** @version 2.02.039
** @since   2.02.040
*/
public class URLContext
    implements java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private Map<String,String> requestProperty;

/**
** Proxy specifier a utiliser pour les connections.
*/
private Proxy proxy;

/** */
private Boolean doInput = null;

/** */
private Boolean doOutput = null;

/**
**
*/
public URLContext() // ----------------------------------------------------
{
 init( null, null );
}

/**
**
*/
public URLContext( URLContext context ) // --------------------------------
{
 init( context, null );
}

/**
**
** @param context   URLContext
** @param proxy
**
*/
public URLContext( URLContext context, Proxy proxy ) // -------------------
{
 init( context, proxy );
}

/**
**
*/
private void init( final URLContext context, final Proxy proxy ) // -------
{
 if( context == null ) {
    this.requestProperty = new TreeMap<String,String>();
    }
 else {
    this.requestProperty = new TreeMap<String,String>( context.requestProperty );
    }

 setProxy( proxy );
}

/**
**
*/
public void setProxy( final Proxy proxy ) // ------------------------------
{
 this.proxy = proxy;
}

/**
** Ouvre la connection et applique les RequestProperty de la connection
** courante.
**
** @see URL#openConnection
*/
public URLConnection openConnection( URL url ) // -------------------------
    throws java.io.IOException
{
 java.net.URLConnection connection;

 if( proxy == null ) {
    connection = url.openConnection();
    }
 else {
    connection = url.openConnection( proxy );
    }

 for( Map.Entry<String,String> entry : this.requestProperty.entrySet() ) {
    connection.setRequestProperty( entry.getKey(), entry.getValue() );
    }

 if( doInput != null ) {
    connection.setDoInput( doInput.booleanValue() );
    }

 if( doOutput != null ) {
    connection.setDoOutput( doOutput.booleanValue() );
    }

 return connection;
}

/**
**
** @throws ClassCastException si la connection ouverte n'est pas une
**                            HttpURLConnection
** @throws java.io.IOException
**
** @see #openConnection(URL)
*/
public HttpURLConnection openHttpConnection( URL url ) // -----------------
    throws java.io.IOException,
    ClassCastException
{
 return HttpURLConnection.class.cast( openConnection( url ) );
}

/**
** update current URLContext from URLConnection
**
** @param connection URLConnection deje connectee.
**
** @see #openHttpConnection
** @see #openConnection
** @see URLConnection#connect()
*/
public void update( final URLConnection connection ) // -------------------
{
 // EX:

 // Set-Cookie: Customer="WILE_E_COYOTE"; Version="1"; Path="/acme"

 // Cookie: $Version="1"; Customer="WILE_E_COYOTE"; $Path="/acme"

 // Set-Cookie: Part_Number="Rocket_Launcher_0001"; Version="1";
 //             Path="/acme"

 // Cookie: $Version="1";
 //         Customer="WILE_E_COYOTE"; $Path="/acme";
 //         Part_Number="Rocket_Launcher_0001"; $Path="/acme"

 String cookie = connection.getHeaderField( "Set-Cookie" ); // RFC 2109

 if( cookie != null ) {
    int index = cookie.indexOf(";");

    if( index >= 0 ) {
        cookie = cookie.substring( 0, index );
        }

     this.requestProperty.put( "Cookie", cookie  ); // RFC 2109
    }
}

/*
for( int n = 0;; n++ ) { // n=0 has no key, and the HTTP return status in the value field
    final String key   = connection.getHeaderFieldKey( n );
    final String value = connection.getHeaderField( n );

    if( value != null ) {
        if( key != null ) {
            //
            // Mise en place d'une nouvelle valeur ou remplacement
            //
            this.requestProperty.put( key, value );
            }
        else {
            //
            // On ignore l'entree
            //
            }
        }
    else { // value == null
        if( key != null ) {
            //
            // On supprime la valeur
            //
            this.requestProperty.remove( key );
            }
        else {
            //
            // On sort !
            //
            return;
            }

        }
    }
*/

/**
** <p>
** Permet de definir des proprietes utilise par toutes les requetes
** emise e l'aide de l'objet courant.
** </p>
** Typiquement on initialisera un echange par<br/>
** <pre>
** URLContext ctxt = new URLContext();
**
** ctxt.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible)" );
** ctxt.setRequestProperty( "Accept-Language", "en-us" );
** ctxt.setRequestProperty( "Accept", "*"+"/*" );
** ctxt.setRequestProperty( "Connection", "Keep-Alive");
** ctxt.setRequestProperty( "Cache-Control", "no-cache");
* <pre>
**
** @see URLConnection#setRequestProperty( String,String )
*/
public void setRequestProperty( final String key, final String value ) // -
{
 this.requestProperty.put( key, value );
}

/**
**
**
**
**
*/
public void removeRequestProperty( final String key ) // ------------------
{
 this.requestProperty.remove( key );
}

/**
** Return a nonmodifiable Set of properties which were defined on current context
**
** @return an unmodifiable {@link Set} of {@link Entry}
*/
public Set<Map.Entry<String,String>> getRequestProperties() // ------------
{
 return java.util.Collections.unmodifiableSet(
    this.requestProperty.entrySet()
    );
}

/**
**
**
**
** @see URLConnection#setDoInput( boolean )
*/
public void setDoInput( boolean doinput ) // ------------------------------
{
 this.doInput = Boolean.valueOf( doinput );
}

/**
**
**
**
** @see URLConnection#setDoOutput( boolean )
*/
public void setDoOutput( boolean dooutput ) // ----------------------------
{
 this.doOutput = Boolean.valueOf( dooutput );
}

/**
**
*/
@Override
public String toString() // -----------------------------------------------
{
// final StringBuilder sb = new StringBuilder( "URLContext[" + this.requestProperty );
//
// for( Map.Entry<String,String> entry : this.requestProperty.entrySet() ) {
//    connection.setRequestProperty( entry.getKey(), entry.getValue() );
//    }
//
// return sb.toString();
 return "URLContext[" + this.requestProperty + "]";
}


/*
final static void test( java.net.URL targetURL )
    throws java.io.IOException
{
 java.net.URLConnection connection = targetURL.openConnection();

//displayHeader( connection );

 // connection.setRequestMethod( "GET" ); // This is the default request method
 connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible)");
 connection.setRequestProperty("Accept-Language", "en-us");
 connection.setRequestProperty("Accept", "*"+"/"+"*");
 connection.setRequestProperty("Connection", "Keep-Alive");
 connection.setRequestProperty("Cache-Control", "no-cache");

// if( currentCookies != null ) {
//   connection.setRequestProperty("Cookie", currentCookies );
// }

 connection.setDoInput( true );
 connection.setDoOutput( false );
 connection.connect();

 System.out.println("Connection up and running");

 displayHeader( connection );

 java.net.HttpURLConnection httpCon = (java.net.HttpURLConnection)connection;

 int responseCode = httpCon.getResponseCode();

 System.out.println( "responseCode = " + responseCode );
 System.out.println( "msg: " + httpCon.getResponseMessage() );

 if( responseCode == HttpURLConnection.HTTP_OK ) {
    displayStream( connection );

    System.out.println( "getContent(): " + connection.getContent().toString() );
    }

 displayHeader( connection );
}


final static void testAndUpdate( URLContext anURLContext, URL anURL )
    throws java.io.IOException
{
 URLConnection connection = anURLContext.openConnection( anURL );

 // connection.setRequestMethod( "GET" ); // This is the default request method

// if( currentCookies != null ) {
//   connection.setRequestProperty("Cookie", currentCookies );
// }

 connection.setDoInput( true );
 connection.setDoOutput( false );
 connection.connect();

System.out.println("Connection up and running");

 anURLContext.update( connection );
 displayHeader( connection );

 java.net.HttpURLConnection httpCon = (java.net.HttpURLConnection)connection;

 int responseCode = httpCon.getResponseCode();

System.out.println( "responseCode = " + responseCode );
System.out.println( "msg: " + httpCon.getResponseMessage() );

 if( responseCode == HttpURLConnection.HTTP_OK ) {
    displayStream( connection );

    System.out.println( "getContent(): " + connection.getContent().toString() );
    }

displayHeader( connection );
}
*/

/**
**
static void displayStream( final URLConnection connection )
    throws java.io.IOException
{
 final java.io.InputStream  in  = connection.getInputStream();

System.out.println( "-Stream->-----------------" );

 try {
    final byte[]               buf = new byte[ 4096 ];
    int                        len;

    while( (len = in.read( buf ) ) != -1 ) {
        System.out.print( new String( buf, 0, len ) );
        }
    }
 finally {
    try { in.close(); } catch( Exception ignore ) {}
    }

 System.out.println( "\n-<-Stream------------------" );
}
*/

/**
**
static void displayHeader( final URLConnection connection )
{
int     n       = 0; // n=0 has no key, and the HTTP return status in the value field
boolean done    = false;

System.out.println( "\n-HEADER->-----------------" );
while( !done ) {
    String headerKey = connection.getHeaderFieldKey(n);
    String headerVal = connection.getHeaderField(n);

    if( headerKey != null || headerVal != null ) {
        System.out.println( headerKey + " = " + headerVal );
        }
    else {
        done = true;
        }
    n++;
    }
System.out.println( "-<-HEADER------------------" );
}
*/

/**
** cch.URLContext
public final static void main( final String[] args ) throws Exception // --
{
 final String URLBase = "http://drs-master:6969/DRS/config/ConfigBackupMasterInitReplication.properties.jsp?STATE=";
 final URL[] urls = {
        new URL( "http://127.0.0.1/" ),
        new URL( URLBase ),
        new URL( URLBase + "2" )
        };

// for( URL url : urls ) {
//    System.out.println( "***url : " + url );
//    test( url );
//    }

 URLContext anURLContext = new URLContext();

 anURLContext.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible)" );
 anURLContext.setRequestProperty( "Accept-Language", "en-us" );
 anURLContext.setRequestProperty( "Accept", "*"+"/"+"*" );
 anURLContext.setRequestProperty( "Connection", "Keep-Alive" );
 anURLContext.setRequestProperty( "Cache-Control", "no-cache" );

 for( URL url : urls ) {
    System.out.println( anURLContext );
    System.out.println( "***url : " + url );
    testAndUpdate( anURLContext, url );
    System.out.println( anURLContext );
    }
}
*/

} // class

