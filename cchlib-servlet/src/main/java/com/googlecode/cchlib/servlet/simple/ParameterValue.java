package com.googlecode.cchlib.servlet.simple;

/**
 *
 * @since 2.01
 * @see javax.servlet.http.HttpServletRequest
 * @see ParameterValueWrapper
 */
public interface ParameterValue
{
    String[] toArray();
    String toString( String defautValue );

    boolean booleanValue();
    boolean booleanValue( boolean defautValue );

    int intValue();
    int intValue( int defautValue );

    long longValue();
    long longValue( long defautValue );

    float floatValue();
    float floatValue( float defautValue );

    double doubleValue();
    double doubleValue( double defautValue );
}
