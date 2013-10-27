/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/impl/SimpleServletRequestImpl.java
** Description   :
** Encodage      : ANSI
**
**  2.01.030 2005.11.10 Claude CHOISNET - Version initiale
**                      Le code de getUserAgent() est exp�rimental.
**  3.01.018 2006.04.11 Claude CHOISNET
**                      La m�thode getUserAgent() est deprecated,
**                      Ajout de getUserAgentDetails()
**  3.01.033 2006.04.11 Claude CHOISNET
**                      Ajout de getCookie(String)
**  3.02.036 2006.08.04 Claude CHOISNET
**                      Suppression de: getUserAgentDetails()
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.impl.SimpleServletRequestImpl
**
**
*/
package cx.ath.choisnet.servlet.impl;

import cx.ath.choisnet.servlet.ParameterValue;
import cx.ath.choisnet.servlet.SimpleServletRequest;
import cx.ath.choisnet.servlet.UserAgent;
import java.util.EnumSet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
** <p>
** Impl�mentation basique de {@link cx.ath.choisnet.servlet.SimpleServletRequest}
** </p>
**
** @see javax.servlet.http.HttpServletRequest
**
** @author Claude CHOISNET
** @since   2.01.030
** @version 3.02.036
*/
public class SimpleServletRequestImpl
    implements SimpleServletRequest
{
/** */
private final HttpServletRequest request;

/** @see #initCookiesIfNeeded() */
private transient Cookie[] cookies;

/**
**
*/
public SimpleServletRequestImpl( // ---------------------------------------
    final HttpServletRequest request
    )
{
 this.request = request;
}

/**
** Return value of the specified parameter.
**
** @return a valid SimpleServletRequestParameterValue.
**
** @see HttpServletRequest#getParameterValues(String)
*/
@Override
public ParameterValue getParameter( final String paramName ) // -----------
{
 return new ParameterValue()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public String[] toArray() //- - - - - - - - - - - - - - - - - - - -
        {
            return SimpleServletRequestImpl.this.request.getParameterValues( paramName );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public String toString() // - - - - - - - - - - - - - - - - - - - -
        {
            return SimpleServletRequestImpl.this.request.getParameter( paramName );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public String toString( String defaultValue ) //- - - - - - - - - -
        {
            try {
                final String value = toString();

                if( value.length() > 0 ) {
                    return value;
                    }
                }
            catch( Exception ignore ) {
                //
                // ignore
                //
                }

            return defaultValue;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public boolean booleanValue() //- - - - - - - - - - - - - - - - - -
        {
            return booleanValue( false );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public boolean booleanValue( boolean defaultValue ) //- - - - - - -
        {
            try {
                final String value = toString().toLowerCase();

                if( "on".equals( value ) || "true".equals( value ) ) {
                    return true;
                    }

                return Integer.parseInt( value ) > 0;
                }
            catch( Exception e ) {
                return defaultValue;
                }
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public int intValue() //- - - - - - - - - - - - - - - - - - - - - -
        {
            return intValue( 0 );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public int intValue( int defaultValue ) //- - - - - - - - - - - - -
        {
            try {
                return Integer.parseInt( toString() );
                }
            catch( Exception e ) {
                return defaultValue;
                }
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public long longValue() //- - - - - - - - - - - - - - - - - - - - -
        {
            return longValue( 0 );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public long longValue( long defaultValue ) // - - - - - - - - - - -
        {
            try {
                return Long.parseLong( toString() );
                }
            catch( Exception e ) {
                return defaultValue;
                }
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public float floatValue() //- - - - - - - - - - - - - - - - - - - -
        {
            return floatValue( 0 );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public float floatValue( float defaultValue ) //- - - - - - - - - -
        {
            try {
                return Float.parseFloat( toString() );
                }
            catch( Exception e ) {
                return defaultValue;
                }
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public double doubleValue() //- - - - - - - - - - - - - - - - - - -
        {
            return doubleValue( 0 );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public double doubleValue( double defaultValue ) // - - - - - - - -
        {
            try {
                return Double.parseDouble( toString() );
                }
            catch( Exception e ) {
                return defaultValue;
                }
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    };
}

/**
** <p>
** Le code de cette m�thode est encore exp�rimental.
** </p>
**
**
** @return an {@link EnumSet} of {@link UserAgent}.
*/
@Override
public EnumSet<UserAgent> getUserAgentDetails() // ------------------------
{
 //
 // user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0) Opera 7.54  [en]
 // user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 1.1.4322)
 // user-agent: Mozilla/5.0 (Windows NT 5.0; U) Opera 7.54  [en]
 // User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.8.0.1) Gecko/20060111 Firefox/1.5.0.1
 // user-agent: Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.8a2) Gecko/20040714
 // user-agent: Opera/7.54 (Windows NT 5.0; U)  [en]
 //
 final EnumSet<UserAgent> details = EnumSet.noneOf( UserAgent.class );

 final String   userAgent           = request.getHeader( "user-agent" );
 final String   userAgentLowerCase  = userAgent.toLowerCase();
 final boolean  isMozilla = (
                (userAgentLowerCase.indexOf("mozilla")!=-1)
                  && (userAgentLowerCase.indexOf("spoofer")==-1)
                  && (userAgentLowerCase.indexOf("compatible")==-1)
                  && (userAgentLowerCase.indexOf("opera")==-1)
                  && (userAgentLowerCase.indexOf("webtv")==-1)
                  && (userAgentLowerCase.indexOf("hotjava")==-1)
                  );

 final boolean  isIE = (
                (userAgentLowerCase.indexOf("mozilla")!=-1)
                  && (userAgentLowerCase.indexOf("msie")!=-1)
                  && (userAgentLowerCase.indexOf("compatible")!=-1)
                  && (userAgentLowerCase.indexOf("opera")==-1)
                  );

 final boolean  isOPERA = (
                (userAgentLowerCase.indexOf("mozilla")!=-1)
                  && (userAgentLowerCase.indexOf("msie")!=-1)
                  && (userAgentLowerCase.indexOf("compatible")!=-1)
                  && (userAgentLowerCase.indexOf("opera")!=-1)
                  );

 //
 // Identification du navigateur
 //
 if( isMozilla ) {
    details.add( UserAgent.MOZILLA );

    if( userAgent.indexOf( "Firefox" ) != -1 ) {
        details.add( UserAgent.MOZILLA_FIREFOX );
        }
    }
 else if( isIE ) {
    details.add( UserAgent.MSIE );
    }
 else if( isOPERA ) {
    details.add( UserAgent.OPERA );
    }
 else {
    //
    // Navigateur inconnu !
    //
    }

 //
 // Traitement de l'identification de l'OS
 //
 if( isIE ) {
    final EnumSet<UserAgent> os = getOSForIE( userAgent );

    if( os.size() != 0 ) {
        details.addAll( os );
        }
    else {
        //
        // OS inconnu !
        //
        details.add( UserAgent.MSIE_UNKNOW_OS );
        }
    }
 else if( isMozilla ) {
    final EnumSet<UserAgent> os = getOSForMozilla( userAgent );

    if( os.size() != 0 ) {
        details.addAll( os );
        }
    else {
        //
        // OS inconnu !
        //
        details.add( UserAgent.MOZILLA_UNKNOW_OS );
        }
    }
 else if( isOPERA ) {
    final EnumSet<UserAgent> os = getOSForIE( userAgent );

    if( os.size() != 0 ) {
        details.addAll( os );
        }
    else {
        //
        // OS inconnu !
        //
        details.add( UserAgent.OPERA_UNKNOW_OS );
        }
    }
 else {
    //
    // OS inconnu !
    //
    }

 return details;
}

/**
** Bas� sur la documentation Micr*s*ft :
** <a href="http://msdn.microsoft.com/workshop/author/dhtml/overview/aboutuseragent.asp">
** http://msdn.microsoft.com/workshop/author/dhtml/overview/aboutuseragent.asp
** </a>
*/
private EnumSet<UserAgent> getOSForIE( final String userAgent ) // --------
{
// user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0) Opera 7.54  [en]
// user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 1.1.4322)
 final EnumSet<UserAgent> details = EnumSet.noneOf( UserAgent.class );

 if( userAgent.indexOf( "Windows NT 6.0" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_VISTA );
    }
 else if( userAgent.indexOf( "Windows NT 5.2" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_2003 );
    }
 else if( userAgent.indexOf( "Windows NT 5.1" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_XP );
    }
 else if( userAgent.indexOf( "Windows NT 5.1" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_XP );
    }
 else if( userAgent.indexOf( "Windows NT 5.01" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_2000_SP1 );
    details.add( UserAgent.WINDOWS_2000 );
    }
 else if( userAgent.indexOf( "Windows NT 5.0" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_2000 );
    }
 else if( userAgent.indexOf( "Windows NT 4.0" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_NT );
    }
 else if( userAgent.indexOf( "Windows 98; Win 9x 4.90" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_98 );
    details.add( UserAgent.WINDOWS_ME );
    }
 else if( userAgent.indexOf( "Windows 98" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_98 );
    }
 else if( userAgent.indexOf( "Windows 95" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_95 );
    }
 else if( userAgent.indexOf( "Windows CE" ) != -1 ) {
    details.add( UserAgent.WINDOWS );
    details.add( UserAgent.WINDOWS_CE );
    }
 else {
    //
    // $$$$ OS NON IDENTIFIE
    //
    }

 return details;
}

/**
** Bas� sur la documentation :
** <a href="http://www.mozilla.org/build/revised-user-agent-strings.html">
** http://www.mozilla.org/build/revised-user-agent-strings.html
** </a>
**
*/
private EnumSet<UserAgent> getOSForMozilla( final String userAgent ) // ---
{
// User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.8.0.1) Gecko/20060111 Firefox/1.5.0.1
// user-agent: Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.8a2) Gecko/20040714
 final EnumSet<UserAgent> details = EnumSet.noneOf( UserAgent.class );

 if( userAgent.indexOf( "Windows" ) != -1 ) {
    details.add( UserAgent.WINDOWS );

    //
    // Valeurs ignor�es :
    //
    // * Win3.11 for Windows 3.11
    // * WinNT3.51 for Windows NT 3.11
    //

    if( userAgent.indexOf( "WinNT4.0" ) != -1 ) {
        details.add( UserAgent.WINDOWS_NT );
        }
    else if( userAgent.indexOf( "Windows NT 5.0" ) != -1 ) {
        details.add( UserAgent.WINDOWS_2000 );
        }
    else if( userAgent.indexOf( "Win95" ) != -1 ) {
        details.add( UserAgent.WINDOWS_95 );
        }
    else if( userAgent.indexOf( "Win 9x 4.90" ) != -1 ) {
        details.add( UserAgent.WINDOWS_ME );
        details.add( UserAgent.WINDOWS_98 );
        }
    else if( userAgent.indexOf( "Win98" ) != -1 ) {
        details.add( UserAgent.WINDOWS_98 );
        }
    }
 else if( userAgent.indexOf( "Macintosh" ) != -1 ) {
    details.add( UserAgent.MACOS );

    if( userAgent.indexOf( "68K" ) != -1 ) {
        details.add( UserAgent.MACOS_68K ); // for 68k hardware
        }
    else if( userAgent.indexOf( "PPC" ) != -1 ) {
        details.add( UserAgent.MACOS_PPC ); // for PowerPC hardware
        }
    }
 else if( userAgent.indexOf( "X11" ) != -1 ) {
    details.add( UserAgent.X11 );
    }
 else {
    //
    // $$$$ OS NON IDENTIFIE
    //
    }

 return details;
}


/**
** Initialise le tableau de cookies si cela n'a pas �t� fait. Cette m�thode
** permet d'�tre ind�pendant de l'impl�mentation de {@link HttpServletRequest}
**
** @since 3.01.033
*/
private void initCookiesIfNeeded() // -------------------------------------
{
 if( this.cookies == null ) {
    this.cookies = request.getCookies();

    if( this.cookies == null ) {
        //
        // Pas de coolies de d�fini
        //
        this.cookies = new Cookie[ 0 ];
        }
    }
}

/**
** Return value of the specified cookie.
**
** @return a {@link Cookie} or null if the cookie does not exist.
**
** @since 3.01.033
*/
@Override
public Cookie getCookie( final String cookieName ) // ---------------------
{
 initCookiesIfNeeded();

 for( Cookie cookie : this.cookies ) {
    if( cookie.getName().equals( cookieName ) ) {
        return cookie;
        }
    }

 return null;
}


} // class

/**
** @deprecated use {@link #getUserAgentDetails()}
@Deprecated
public UserAgent getUserAgent() // ----------------------------------------
{
 final String   userAgent = request.getHeader( "user-agent" ).toLowerCase();
 final boolean  isMozilla = (
                (userAgent.indexOf("mozilla")!=-1)
                  && (userAgent.indexOf("spoofer")==-1)
                  && (userAgent.indexOf("compatible")==-1)
                  && (userAgent.indexOf("opera")==-1)
                  && (userAgent.indexOf("webtv")==-1)
                  && (userAgent.indexOf("hotjava")==-1)
                  );

 final boolean  isIE = (
                (userAgent.indexOf("mozilla")!=-1)
                  && (userAgent.indexOf("msie")!=-1)
                  && (userAgent.indexOf("compatible")!=-1)
                  && (userAgent.indexOf("opera")==-1)
                  );

 final boolean  isOPERA = (
                (userAgent.indexOf("mozilla")!=-1)
                  && (userAgent.indexOf("msie")!=-1)
                  && (userAgent.indexOf("compatible")!=-1)
                  && (userAgent.indexOf("opera")!=-1)
                  );

 if( isMozilla ) {
    return UserAgent.MOZILLA;
    }
 else if( isIE ) {
    return UserAgent.MSIE;
    }
 else if( isOPERA ) {
    return UserAgent.OPERA;
    }

 return UserAgent.UNKOWN;
}
*/
