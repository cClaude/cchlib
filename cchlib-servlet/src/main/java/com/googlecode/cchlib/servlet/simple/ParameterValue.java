package com.googlecode.cchlib.servlet.simple;


/**
 * Extension for {@link javax.servlet.http.HttpServletRequest} for a
 * predefine parameter name.
 *
 * @since 2.01
 * @see javax.servlet.http.HttpServletRequest
 * @see ParameterValueWrapper
 */
public interface ParameterValue
{
    /**
     * Returns true if parameter exist, false otherwise
     * @return true if parameter exist, false otherwise
     * @since 4.2
     */
    boolean exists();

    /**
     * Returns an array of String objects containing all of the values
     * the given request parameter has, or null if the parameter does not exist.
     * <p>
     * If the parameter has a single value, the array has a length of 1.
     *
     * @return an array of String objects containing the parameter's values
     * @see ParameterValueWrapper#asList(ParameterValue)
     * @see javax.servlet.http.HttpServletRequest#getParameterValues(String)
     */
    String[] toArray();

    /**
     * Returns the value of a request parameter as a String, or {@code defautValue}
     * if the parameter does not exist
     *
     * @param defautValue
     *            Value to return if the parameter does not exist
     * @return the value of a request parameter as a String, or {@code defautValue}
     * @see javax.servlet.http.HttpServletRequest#getParameter(String)
     */
    String toString( String defautValue );

    /**
     * Returns the value of a request parameter as a boolean, if the parameter does
     * not exist return false
     *
     * @return the value of a request parameter as a boolean, if the parameter does
     *         not exist return false
     */
    boolean booleanValue();

    /**
     * Returns the value of a request parameter as a boolean, if the parameter does
     * not exist return {@code defautValue}
     *
     * @param defautValue
     *            Value to return if the parameter does not exist
     * @return the value of a request parameter as a boolean, if the parameter does
     *         not exist return {@code defautValue}
     */
    boolean booleanValue( boolean defautValue );

    /**
     * Returns the value of a request parameter as a int, if the parameter does
     * not exist return 0
     *
     * @return the value of a request parameter as a int, if the parameter does
     *         not exist return 0
     */
    int intValue();

    /**
     * Returns the value of a request parameter as a int, if the parameter does
     * not exist return {@code defautValue}
     *
     * @param defautValue
     *            Value to return if the parameter does not exist
     * @return the value of a request parameter as a int, if the parameter does
     *         not exist return {@code defautValue}
     */
    int intValue( int defautValue );

    /**
     * Returns the value of a request parameter as a long, if the parameter does
     * not exist return 0L
     *
     * @return the value of a request parameter as a long, if the parameter does
     *         not exist return 0L
     */
    long longValue();

    /**
     * Returns the value of a request parameter as a long, if the parameter does
     * not exist return {@code defautValue}
     *
     * @param defautValue
     *            Value to return if the parameter does not exist
     * @return the value of a request parameter as a long, if the parameter does
     *         not exist return {@code defautValue}
     */
    long longValue( long defautValue );

    /**
     * Returns the value of a request parameter as a float, if the parameter does
     * not exist return 0F
     *
     * @return the value of a request parameter as a float, if the parameter does
     *         not exist return 0F
     */
    float floatValue();

    /**
     * Returns the value of a request parameter as a float, if the parameter does
     * not exist return {@code defautValue}
     *
     * @param defautValue
     *            Value to return if the parameter does not exist
     * @return the value of a request parameter as a float, if the parameter does
     *         not exist return {@code defautValue}
     */
    float floatValue( float defautValue );

    /**
     * Returns the value of a request parameter as a double, if the parameter does
     * not exist return 0D
     *
     * @return the value of a request parameter as a double, if the parameter does
     *         not exist return 0D
     */
    double doubleValue();

    /**
     * Returns the value of a request parameter as a double, if the parameter does
     * not exist return {@code defautValue}
     *
     * @param defautValue
     *            Value to return if the parameter does not exist
     * @return the value of a request parameter as a double, if the parameter does
     *         not exist return {@code defautValue}
     */
    double doubleValue( double defautValue );
}
