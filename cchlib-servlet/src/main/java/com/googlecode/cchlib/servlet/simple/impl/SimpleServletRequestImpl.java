package com.googlecode.cchlib.servlet.simple.impl;

import java.util.EnumSet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import com.googlecode.cchlib.servlet.simple.ParameterValue;
import com.googlecode.cchlib.servlet.simple.SimpleServletRequest;
import com.googlecode.cchlib.servlet.simple.UserAgent;

/**
 * Default implementation of {@link SimpleServletRequest}
 */
public class SimpleServletRequestImpl
    implements SimpleServletRequest
{
    private final class DefaultParameterValue implements ParameterValue
    {
        private final String paramName;

        private DefaultParameterValue( final String paramName )
        {
            this.paramName = paramName;
        }

        @Override
        public String[] toArray()
        {
            return SimpleServletRequestImpl.this.request.getParameterValues(this.paramName);
        }

        @Override
        public String toString()
        {
            return SimpleServletRequestImpl.this.request.getParameter(this.paramName);
        }

        @Override
        public String toString(final String defaultValue)
        {
            try {
                final String value = toString();

                if( value.length() > 0 ) {
                    return value;
                    }
                }
            catch(final Exception ignore) {
                // ???
                }

            return defaultValue;
        }

        @Override
        public boolean booleanValue()
        {
            return booleanValue(false);
        }

        @Override
        public boolean booleanValue(final boolean defaultValue)
        {
            String value;

            try {
                value = toString().toLowerCase();
                if("on".equals(value) || "true".equals(value)) {
                    return true;
                    }
                }
            catch(final Exception e) {
                return defaultValue;
                }
            return Integer.parseInt(value) > 0;
        }

        @Override
        public int intValue()
        {
            return intValue(0);
        }

        @Override
        public int intValue(final int defaultValue)
        {
            try {
                return Integer.parseInt(toString());
                }
            catch(final Exception e) { // $codepro.audit.disable logExceptions
                return defaultValue;
                }
        }

        @Override
        public long longValue()
        {
            return longValue(0L);
        }

        @Override
        public long longValue(final long defaultValue)
        {
            try {
                return Long.parseLong(toString());
                }
            catch(final Exception e) { // $codepro.audit.disable logExceptions
                return defaultValue;
                }
        }

        @Override
        public float floatValue()
        {
            return floatValue(0.0F);
        }

        @Override
        public float floatValue(final float defaultValue)
        {
            try {
                return Float.parseFloat(toString());
                }
            catch(final Exception e) { // $codepro.audit.disable logExceptions
                return defaultValue;
                }
        }

        @Override
        public double doubleValue()
        {
            return doubleValue(0.0D);
        }

        @Override
        public double doubleValue(final double defaultValue)
        {
            try {
                return Double.parseDouble(toString());
                }
            catch(final Exception e) { // $codepro.audit.disable logExceptions
                return defaultValue;
                }
        }
    }

    private final HttpServletRequest request;
    private Cookie[] cookiesIfNeeded;

    public SimpleServletRequestImpl(final HttpServletRequest request)
    {
        this.request = request;
    }

    @Override
    public HttpServletRequest getHttpServletRequest()
    {
        return this.request;
    }

    @Override
    public ParameterValue getParameter(final String paramName)
    {
        return new DefaultParameterValue( paramName );
    }

    @Override
    public EnumSet<UserAgent> getUserAgentDetails()
    {
        final EnumSet<UserAgent> details = EnumSet.noneOf(UserAgent.class);

        final String userAgent = this.request.getHeader("user-agent");
        final String userAgentLowerCase = userAgent.toLowerCase();

        final boolean isMozilla = (userAgentLowerCase.indexOf("mozilla") != -1)
                         && (userAgentLowerCase.indexOf("spoofer") == -1)
                         && (userAgentLowerCase.indexOf("compatible") == -1)
                         && (userAgentLowerCase.indexOf("opera") == -1)
                         && (userAgentLowerCase.indexOf("webtv") == -1)
                         && (userAgentLowerCase.indexOf("hotjava") == -1);
        final boolean isIE      = (userAgentLowerCase.indexOf("mozilla") != -1)
                         && (userAgentLowerCase.indexOf("msie") != -1)
                         && (userAgentLowerCase.indexOf("compatible") != -1)
                         && (userAgentLowerCase.indexOf("opera") == -1);
        final boolean isOPERA   = (userAgentLowerCase.indexOf("mozilla") != -1)
                         && (userAgentLowerCase.indexOf("msie") != -1)
                         && (userAgentLowerCase.indexOf("compatible") != -1)
                         && (userAgentLowerCase.indexOf("opera") != -1);

        if(isMozilla) {
            details.add(UserAgent.MOZILLA);

            if(userAgent.indexOf("Firefox") != -1) {
                details.add(UserAgent.MOZILLA_FIREFOX);
                }
            }
        else if(isIE) {
            details.add(UserAgent.MSIE);
            }
        else if(isOPERA) {
            details.add(UserAgent.OPERA);
            }

        if( isIE ) {
            final EnumSet<UserAgent> os = getOSForIE(userAgent);

            add( details, os, UserAgent.MSIE_UNKNOW_OS );
           }
        else if( isMozilla ) {
            final EnumSet<UserAgent> os = getOSForMozilla(userAgent);

            add( details, os, UserAgent.MOZILLA_UNKNOW_OS );
           }
        else if( isOPERA ) {
            final EnumSet<UserAgent> os = getOSForIE(userAgent);

            add( details, os, UserAgent.OPERA_UNKNOW_OS );
            }

        return details;
    }

    private static void add(
            final EnumSet<UserAgent> details,
            final EnumSet<UserAgent> os,
            final UserAgent          defaultUserAgent
            )
    {
        if( os.isEmpty() ) {
            details.add( defaultUserAgent );
        }
        else {
            details.addAll(os);
            }
    }

    private EnumSet<UserAgent> getOSForIE(final String userAgent)
    {
        final EnumSet<UserAgent> details = EnumSet.noneOf(UserAgent.class);

        if(userAgent.indexOf("Windows NT 6.0") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_VISTA);
            }
        else if(userAgent.indexOf("Windows NT 5.2") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_2003);
            }
        else if(userAgent.indexOf("Windows NT 5.1") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_XP);
            }
        else if(userAgent.indexOf("Windows NT 5.01") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_2000_SP1);
            details.add(UserAgent.WINDOWS_2000);
            }
        else if(userAgent.indexOf("Windows NT 5.0") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_2000);
            }
        else if(userAgent.indexOf("Windows NT 4.0") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_NT);
            }
        else if(userAgent.indexOf("Windows 98; Win 9x 4.90") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_98);
            details.add(UserAgent.WINDOWS_ME);
            }
        else if(userAgent.indexOf("Windows 98") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_98);
            }
        else if(userAgent.indexOf("Windows 95") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_95);
            }
        else if(userAgent.indexOf("Windows CE") != -1) {
            details.add(UserAgent.WINDOWS);
            details.add(UserAgent.WINDOWS_CE);
            }

        return details;
    }

    private EnumSet<UserAgent> getOSForMozilla(final String userAgent)
    {
        final EnumSet<UserAgent> details = EnumSet.noneOf(UserAgent.class);

        if(userAgent.indexOf("Windows") != -1) {
            details.add(UserAgent.WINDOWS);

            if(userAgent.indexOf("WinNT4.0") != -1) {
                details.add(UserAgent.WINDOWS_NT);
                }
            else if(userAgent.indexOf("Windows NT 5.0") != -1) {
                details.add(UserAgent.WINDOWS_2000);
                }
            else if(userAgent.indexOf("Win95") != -1) {
                details.add(UserAgent.WINDOWS_95);
                }
            else if(userAgent.indexOf("Win 9x 4.90") != -1) {
                details.add(UserAgent.WINDOWS_ME);
                details.add(UserAgent.WINDOWS_98);
                }
            else if(userAgent.indexOf("Win98") != -1) {
                details.add(UserAgent.WINDOWS_98);
                }
            }
        else if(userAgent.indexOf("Macintosh") != -1) {
            details.add(UserAgent.MACOS);

            if(userAgent.indexOf("68K") != -1) {
                details.add(UserAgent.MACOS_68K);
                }
            else if(userAgent.indexOf("PPC") != -1) {
                details.add(UserAgent.MACOS_PPC);
                }
            }
        else if(userAgent.indexOf("X11") != -1) {
            details.add(UserAgent.X11);
            }

        return details;
    }

    private Cookie[] getCookies()
    {
        if(this.cookiesIfNeeded == null) {
            this.cookiesIfNeeded = this.request.getCookies();

            if(this.cookiesIfNeeded == null) {
                this.cookiesIfNeeded = new Cookie[0];
                }
            }

        return this.cookiesIfNeeded;
    }

    @Override
    public Cookie getCookie( final String cookieName )
    {
        for( final Cookie cookie : getCookies() ) {
            if(cookie.getName().equals(cookieName)) {
                return cookie;
                }
            }

        return null;
    }

//    @Deprecated
//    public UserAgent getUserAgent()
//    {
//        final String userAgent = this.request.getHeader("user-agent").toLowerCase();
//        final boolean isMozilla = (userAgent.indexOf("mozilla") != -1) && (userAgent.indexOf("spoofer") == -1) && (userAgent.indexOf("compatible") == -1) && (userAgent.indexOf("opera") == -1) && (userAgent.indexOf("webtv") == -1) && (userAgent.indexOf("hotjava") == -1);
//        final boolean isIE = (userAgent.indexOf("mozilla") != -1) && (userAgent.indexOf("msie") != -1) && (userAgent.indexOf("compatible") != -1) && (userAgent.indexOf("opera") == -1);
//        final boolean isOPERA = (userAgent.indexOf("mozilla") != -1) && (userAgent.indexOf("msie") != -1) && (userAgent.indexOf("compatible") != -1) && (userAgent.indexOf("opera") != -1);
//        if(isMozilla)
//        {
//            return UserAgent.MOZILLA;
//        }
//        if(isIE)
//        {
//            return UserAgent.MSIE;
//        }
//        if(isOPERA)
//        {
//            return UserAgent.OPERA;
//        } else
//        {
//            return UserAgent.UNKOWN;
//        }
//    }

}
