package com.googlecode.cchlib.servlet.simple;

/**
 * TODOC
 *
 */
public interface SimpleServletContext
{
    /**
    * TODOC
    *
    * @param s
    * @return TODOC
    * @throws com.googlecode.cchlib.servlet.simple.ServletContextParamNotFoundException
    */
    String getInitParameter(String s)
        throws com.googlecode.cchlib.servlet.simple.ServletContextParamNotFoundException;

    /**
    * TODOC
     *
     * @param s
     * @param s1
     * @return TODOC
     */
    String getInitParameter(String s, String s1);
}
