/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/StringURL.java
** Description   :
** Encodage      : ANSI
**
**  2.01.000 2005.09.30 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.StringURL
**  3.02.036 2006.08.04 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.StringURL
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.StringURL
*/
package cx.ath.choisnet.html.util;

import java.util.LinkedList;
import java.net.URLEncoder;

/**
** <p>Class permettant de construire proprement une URL de maniére simple.</p>
**
** ex 1: <br>
** <pre>
**  StringURL aStringURL = new StringURL( "http://www.web.site1/forward" );
**
**  aStringURL.append( "URL", "http://www.web.site2/something" );
**  aStringURL.append( "ID", 12 );
**
**  value = aStringURL.toString( "UTF-8" );
** </pre>
** <br />
** ex 2: <br>
** <pre>
**  String url = new StringURL( "http://www.web.site1/forward" )
**                  .append( "URL", "http://www.web.site2/something" )
**                  .append( "ID", 12 )
**                  .toHTML( response.getCharacterEncoding() );
** </pre>
**
** @author Claude CHOISNET
** @since   2.01.000
** @version 3.02.036
*/
public class StringURL
    implements
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private String baseURL;

/** */
private LinkedList<String[]> params;

/**
**
**
*
** @see java.nio.charset.Charset
** @see javax.servlet.ServletResponse#getCharacterEncoding()
*/
public StringURL( final String baseURL ) // -------------------------------
{
 this.params    = new LinkedList<String[]>();
 this.baseURL   = baseURL;
}

/**
**
*/
public StringURL append( // -----------------------------------------------
    final String paramName,
    final String paramValue
    )
{
 final String[] entry = { paramName, paramValue };

 params.add( entry );

 return this;
}

/**
**
*/
public StringURL append( // -----------------------------------------------
    final String paramName,
    final int    paramValue
    )
{
 return append( paramName, Integer.toString( paramValue ) );
}

/**
**
*/
public StringURL append( // -----------------------------------------------
    final String paramName,
    final long   paramValue
    )
{
 return append( paramName, Long.toString( paramValue ) );
}

/**
**
*/
public StringURL append( // -----------------------------------------------
    final String    paramName,
    final boolean   paramValue
    )
{
 return append( paramName, Boolean.toString( paramValue ) );
}

/**
**
*/
private String toString( // -----------------------------------------------
    final String paramsSeparator,
    final String charsetName
    )
    throws java.io.UnsupportedEncodingException
{
 boolean first;

 if( this.baseURL.indexOf( '?' ) == -1 ) {
    //
    // Pas de paramétre dans 'baseURL'
    //
    first = true;
    }
 else {
    first = false;
    }

 final StringBuilder sb = new StringBuilder( this.baseURL );

 for( String[] param : this.params ) {
    if( first ) {
        first = false;
        sb.append( '?' );
        }
    else {
        sb.append( paramsSeparator );
        }

    sb.append( param[ 0 ] );
    sb.append( '=' );
    sb.append( URLEncoder.encode( param[ 1 ], charsetName ) );
    }

 return sb.toString();
}

/**
**
*/
public String toString( final String charsetName ) // ---------------------
    throws java.io.UnsupportedEncodingException
{
 return toString( "&", charsetName );
}

/**
**
*/
public String toHTML( final String charsetName ) // -----------------------
    throws java.io.UnsupportedEncodingException
{
 return toString( "&amp;", charsetName );
}

} // class


