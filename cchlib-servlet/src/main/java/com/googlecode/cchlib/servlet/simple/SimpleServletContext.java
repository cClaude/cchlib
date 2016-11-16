package com.googlecode.cchlib.servlet.simple;

/**
 * NEEDDOC
 *
 */
public interface SimpleServletContext
{
    /**
    * NEEDDOC
    *
    * @param s
    * @return NEEDDOC
    * @throws com.googlecode.cchlib.servlet.simple.ServletContextParamNotFoundException
    */
    String getInitParameter(String s)
        throws com.googlecode.cchlib.servlet.simple.ServletContextParamNotFoundException;

    /**
    * NEEDDOC
     *
     * @param s
     * @param s1
     * @return NEEDDOC
     */
    String getInitParameter(String s, String s1);
}
