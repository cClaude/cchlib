package com.googlecode.cchlib.servlet;

import javax.servlet.http.HttpServletRequest;
import com.googlecode.cchlib.servlet.exception.RequestParameterNotFoundException;
import com.googlecode.cchlib.servlet.exception.RequestParameterNumberFormatException;

/**
 * NEEDDOC
 */
public final class HttpServletHelper
{
    private HttpServletHelper()
    {
        // All static
    }

    /**
     * Returns the value of a request parameter as a String.
     *
     * <p>
     * You should only use this method when you are sure the parameter
     * has only one value. If the parameter might have more than one
     * value, use getParameterValues(java.lang.String).
     *
     * @param request
     *            HttpServletRequest from servlet or JSP
     * @param name
     *            a String containing the name of the parameter whose
     *            value is requested
     * @return a String representing the single value of the parameter
     * @throws RequestParameterNotFoundException
     *             if the parameter does not exist.
     * @see HttpServletRequest#getParameter(String)
     * @see #getParameterValues(HttpServletRequest, String)
     */
    public static final String getParameter(
            final HttpServletRequest request,
            final String             name
            )
        throws RequestParameterNotFoundException
    {
        final String value = request.getParameter( name );

        if( value == null ) {
            throw new RequestParameterNotFoundException( name );
            }

        return value;
    }

    /**
     * Returns the value of a request parameter as an integer.
     *
     * @param request
     *            HttpServletRequest from servlet or JSP
     * @param name
     *            a String containing the name of the parameter whose
     *            value is requested
     * @return a String representing the single value of the parameter
     * @throws RequestParameterNotFoundException
     *             if the parameter does not exist.
     * @throws RequestParameterNumberFormatException
     *             if the value is not an integer
     */
    @SuppressWarnings({"squid:S1160"})
    public static final int getIntParameter(
            final HttpServletRequest request,
            final String             name
            )
        throws  RequestParameterNotFoundException,
                RequestParameterNumberFormatException
    {
        final String value = getParameter( request, name );

        try {
            return Integer.parseInt( value );
            }
        catch( final NumberFormatException e ) {
            throw new RequestParameterNumberFormatException( name, value );
            }
    }

    /**
     * Returns an array of String objects containing all of the values
     * the given request parameter has.
     *
     * @param request
     *            HttpServletRequest from servlet or JSP
     * @param name
     *            a String containing the name of the parameter whose
     *            value is requested
     * @return an array of String objects containing the parameter's values
     * @throws RequestParameterNotFoundException
     *             if the parameter does not exist.
     * @see HttpServletRequest#getParameter(String)
     * @see #getParameter(HttpServletRequest, String)
     */
    public static final String[] getParameterValues(
            final HttpServletRequest request,
            final String             name
            )
        throws RequestParameterNotFoundException
    {
        final String[] values = request.getParameterValues( name );

        if( values == null ) {
            throw new RequestParameterNotFoundException( name );
        }

        return values;
    }

    /**
     * Returns an array of int containing all of the values the
     * given request parameter has.
     *
     * @param request
     *            HttpServletRequest to use
     * @param name
     *            a String containing the name of the parameter
     *            whose value is requested
     * @return a String representing the single value of the parameter
     * @throws RequestParameterNotFoundException
     *             if the parameter does not exist.
     * @throws RequestParameterNumberFormatException
     *             if at least one value is not an integer
     */
    @SuppressWarnings({"squid:S1160"})
    public static final int[] getIntParameterValues(
            final HttpServletRequest request,
            final String             name
            )
        throws  RequestParameterNotFoundException,
                RequestParameterNumberFormatException
    {
        return toInt( getParameterValues( request, name ) );
    }

    /**
     * NEEDDOC
     *
     * @param request
     *            NEEDDOC
     * @param name
     *            NEEDDOC
     * @return NEEDDOC
     * @throws NumberFormatException
     *             NEEDDOC
     * @throws RequestParameterNotFoundException
     *             NEEDDOC
     * @throws RequestParameterNumberFormatException
     *             NEEDDOC
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public static final int getIntAttributeOrParameter(
        final HttpServletRequest request,
        final String             name
        ) throws  NumberFormatException,
                  RequestParameterNotFoundException,
                  RequestParameterNumberFormatException
    {
        final Object value = request.getAttribute( name );

        if( value != null ) {
            if( value instanceof Number ) {
                final Number n = (Number)value;
                return n.intValue();
                }
            else if( value instanceof String ) {
                return Integer.parseInt( (String)value );
                }
            else {
                throw new NumberFormatException();
                }
            }
        else {
            return getIntParameter( request, name );
            }
    }

    /**
     * NEEDDOC
     *
     * @param request
     *            NEEDDOC
     * @param name
     *            NEEDDOC
     * @param defaultValue
     *            NEEDDOC
     * @return NEEDDOC
     */
    @SuppressWarnings("squid:S1166")
    public static final int getIntAttributeOrParameter(
            final HttpServletRequest request,
            final String             name,
            final int                defaultValue
            )
    {
        try {
            return getIntAttributeOrParameter( request, name );
            }
        catch( final Exception e ) {
            return defaultValue;
            }
    }

    /**
     * Create a int Array from a String array
     *
     * @param values
     *            {@link String} array that must contain only number
     * @return a int Array
     * @throws NumberFormatException
     *             if any String is not a number
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static final int[] toInt( final String[] values )
        throws NumberFormatException
    {
        final int[] ivalues = new int[ values.length ];

        for(int i = 0;i<values.length; i++ ) {
            ivalues[ i ] = Integer.parseInt( values[ i ] );
            }

        return ivalues;
    }
}
