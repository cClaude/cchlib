package cx.ath.choisnet.servlet;

import java.util.EnumSet;
import javax.servlet.http.Cookie;

public interface SimpleServletRequest
{
    public abstract ParameterValue getParameter(String s);
    public abstract EnumSet<UserAgent> getUserAgentDetails();
    public abstract Cookie getCookie(String s);

//    /**
//     * @deprecated Method getUserAgent is deprecated
//     */
//    public abstract cx.ath.choisnet.servlet.UserAgent getUserAgent();
}
