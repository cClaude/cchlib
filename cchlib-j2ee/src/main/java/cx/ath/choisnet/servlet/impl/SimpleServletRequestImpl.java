package cx.ath.choisnet.servlet.impl;

import cx.ath.choisnet.servlet.ParameterValue;
import cx.ath.choisnet.servlet.SimpleServletRequest;
import cx.ath.choisnet.servlet.UserAgent;
import java.util.EnumSet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SimpleServletRequestImpl
    implements SimpleServletRequest
{
    private final HttpServletRequest request;
    private Cookie[] cookies;

    public SimpleServletRequestImpl(final HttpServletRequest request)
    {
        this.request = request;
    }

    public ParameterValue getParameter(final String paramName)
    {
        return new ParameterValue()
        {
            @Override
            public String[] toArray()
            {
                return request.getParameterValues(paramName);
            }
            @Override
            public String toString()
            {
                return request.getParameter(paramName);
            }
            @Override
            public String toString(String defaultValue)
            {
                try {
                    String value = toString();

                    if( value.length() > 0 ) {
                        return value;
                        }
                    }
                catch(Exception ignore) {
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
            public boolean booleanValue(boolean defaultValue)
            {
                String value;

                try {
                    value = toString().toLowerCase();
                    if("on".equals(value) || "true".equals(value)) {
                        return true;
                        }
                    }
                catch(Exception e) {
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
            public int intValue(int defaultValue)
            {
                try {
                    return Integer.parseInt(toString());
                    }
                catch(Exception e) { // $codepro.audit.disable logExceptions
                    return defaultValue;
                    }
            }
            @Override
            public long longValue()
            {
                return longValue(0L);
            }
            @Override
            public long longValue(long defaultValue)
            {
                try {
                    return Long.parseLong(toString());
                    }
                catch(Exception e) { // $codepro.audit.disable logExceptions
                    return defaultValue;
                    }
            }
            @Override
            public float floatValue()
            {
                return floatValue(0.0F);
            }
            @Override
            public float floatValue(float defaultValue)
            {
                try {
                    return Float.parseFloat(toString());
                    }
                catch(Exception e) { // $codepro.audit.disable logExceptions
                    return defaultValue;
                    }
            }
            @Override
            public double doubleValue()
            {
                return doubleValue(0.0D);
            }
            @Override
            public double doubleValue(double defaultValue)
            {
                try {
                    return Double.parseDouble(toString());
                    }
                catch(Exception e) { // $codepro.audit.disable logExceptions
                    return defaultValue;
                    }
            }
        };
    }

    public EnumSet<UserAgent> getUserAgentDetails()
    {
        EnumSet<UserAgent> details = EnumSet.noneOf(UserAgent.class);

        String userAgent = request.getHeader("user-agent");
        String userAgentLowerCase = userAgent.toLowerCase();

        boolean isMozilla = userAgentLowerCase.indexOf("mozilla") != -1
                         && userAgentLowerCase.indexOf("spoofer") == -1
                         && userAgentLowerCase.indexOf("compatible") == -1
                         && userAgentLowerCase.indexOf("opera") == -1
                         && userAgentLowerCase.indexOf("webtv") == -1
                         && userAgentLowerCase.indexOf("hotjava") == -1;
        boolean isIE      = userAgentLowerCase.indexOf("mozilla") != -1
                         && userAgentLowerCase.indexOf("msie") != -1
                         && userAgentLowerCase.indexOf("compatible") != -1
                         && userAgentLowerCase.indexOf("opera") == -1;
        boolean isOPERA   = userAgentLowerCase.indexOf("mozilla") != -1
                         && userAgentLowerCase.indexOf("msie") != -1
                         && userAgentLowerCase.indexOf("compatible") != -1
                         && userAgentLowerCase.indexOf("opera") != -1;

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
            EnumSet<UserAgent> os = getOSForIE(userAgent);

            if(os.size() != 0) {
                details.addAll(os);
                }
            else {
                details.add(UserAgent.MSIE_UNKNOW_OS);
                }
            }
        else if( isMozilla ) {
            EnumSet<UserAgent> os = getOSForMozilla(userAgent);

            if(os.size() != 0) {
                details.addAll(os);
                }
            else {
                details.add(UserAgent.MOZILLA_UNKNOW_OS);
                }
            }
        else if( isOPERA ) {
            EnumSet<UserAgent> os = getOSForIE(userAgent);

            if(os.size() != 0) {
                details.addAll(os);
                }
            else {
                details.add(UserAgent.OPERA_UNKNOW_OS);
                }
            }
        return details;
    }

    private EnumSet<UserAgent> getOSForIE(String userAgent)
    {
        EnumSet<UserAgent> details = EnumSet.noneOf(UserAgent.class);

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

    private EnumSet<UserAgent> getOSForMozilla(String userAgent)
    {
        EnumSet<UserAgent> details = EnumSet.noneOf(UserAgent.class);

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

    private void initCookiesIfNeeded()
    {
        if(cookies == null) {
            cookies = request.getCookies();

            if(cookies == null) {
                cookies = new Cookie[0];
                }
            }
    }

    public Cookie getCookie(String cookieName)
    {
        initCookiesIfNeeded();

        Cookie arr$[] = cookies;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; i$++) {
            Cookie cookie = arr$[i$];

            if(cookie.getName().equals(cookieName)) {
                return cookie;
                }
            }

        return null;
    }

    @Deprecated
    public UserAgent getUserAgent()
    {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        boolean isMozilla = userAgent.indexOf("mozilla") != -1 && userAgent.indexOf("spoofer") == -1 && userAgent.indexOf("compatible") == -1 && userAgent.indexOf("opera") == -1 && userAgent.indexOf("webtv") == -1 && userAgent.indexOf("hotjava") == -1;
        boolean isIE = userAgent.indexOf("mozilla") != -1 && userAgent.indexOf("msie") != -1 && userAgent.indexOf("compatible") != -1 && userAgent.indexOf("opera") == -1;
        boolean isOPERA = userAgent.indexOf("mozilla") != -1 && userAgent.indexOf("msie") != -1 && userAgent.indexOf("compatible") != -1 && userAgent.indexOf("opera") != -1;
        if(isMozilla)
        {
            return UserAgent.MOZILLA;
        }
        if(isIE)
        {
            return UserAgent.MSIE;
        }
        if(isOPERA)
        {
            return UserAgent.OPERA;
        } else
        {
            return UserAgent.UNKOWN;
        }
    }

}
