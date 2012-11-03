package cx.ath.choisnet.servlet;

import java.util.EnumSet;
import javax.servlet.http.Cookie;

/**
 * TODOC
 */
public interface SimpleServletRequest
{
	/**
	 * TODOC
	 * @param s
	 * @return TODOC
	 */
    public abstract ParameterValue getParameter(String s);
    
    /**
     * TODOC
     * @return TODOC
     */
    public abstract EnumSet<UserAgent> getUserAgentDetails();
    
    /**
     * TODOC
     * @param s
     * @return TODOC
     */
    public abstract Cookie getCookie(String s);
}
