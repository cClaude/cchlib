package com.googlecode.cchlib.servlet.simple;

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
    ParameterValue getParameter(String s);

    /**
     * TODOC
     * @return TODOC
     */
    EnumSet<UserAgent> getUserAgentDetails();

    /**
     * TODOC
     * @param s
     * @return TODOC
     */
    Cookie getCookie(String s);
}
