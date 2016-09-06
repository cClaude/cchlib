package com.googlecode.cchlib.servlet.simple;

import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Custom view of {@link HttpServletRequest}. Helper to have a easiest way
 * to get parameters values.
 */
public interface SimpleServletRequest
{
    /**
    * Return a {@link ParameterValue} for this <code>name<code>
    * @param name Name of the parameter you want
    * @return a {@link ParameterValue} for this <code>name<code>
    */
    ParameterValue getParameter(String name);

    /**
     * Try to identify user agent
     *
     * @return an {@link Set} of {@link UserAgent}
     */
    Set<UserAgent> getUserAgentDetails();

    /**
     * Retrieve a {@link Cookie} using name.
     * @param name Name of the cookie
     * @return a {@link Cookie} or null if there is non cookie for this name
     */
    Cookie getCookie(String name);

    /**
     * Optional operation
     * @return {@link HttpServletRequest} use to create this instance
     * @throws UnsupportedOperationException is operation is not supported
     */
    HttpServletRequest getHttpServletRequest()
            throws UnsupportedOperationException; // NOSONAR
}
