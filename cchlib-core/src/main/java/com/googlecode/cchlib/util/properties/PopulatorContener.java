package com.googlecode.cchlib.util.properties;

/**
 * TODOC
 */
public interface PopulatorContener
{
    /**
     * Returns a string able to rebuild current object state.
     * @return String able to rebuild object state
     */
    String getConvertToString();

    /**
     * Initialize object state using 'str' parameter.
     * @param str a string able to rebuild current object state.
     */
    void setConvertToString( String str );
}
