package com.googlecode.cchlib.servlet.simple;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.googlecode.cchlib.servlet.simple.impl.SimpleServletContextImpl;

/**
 * Give an easy access to init parameters of {@link HttpServletRequest}
 *
 * @since   3.02
 *
 * @see ServletContext
 * @see SimpleServletRequest
 * @see SimpleServletContextImpl
 */
public interface SimpleServletContext
{
    /**
     * Return value of the specified parameter.
     *
     * @param paramName
     *            Name of the parameter to retrieved from
     * @return value of the specified parameter
     * @throws ServletContextParamNotFoundException
     *             if parameter does not exist
     */
    String getInitParameter( String paramName )
        throws ServletContextParamNotFoundException;

    /**
     * Return value of the specified parameter.
     *
     * @param paramName
     *            Name of the parameter to retrieved from
     * @param defaultValue
     *            Value to return is parameter does not exist
     * @return value of the specified parameter
     */
    String getInitParameter( String paramName, String defaultValue );
}
