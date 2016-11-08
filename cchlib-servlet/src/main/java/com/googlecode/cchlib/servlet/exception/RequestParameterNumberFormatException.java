package com.googlecode.cchlib.servlet.exception;

/**
 *
 *
 */
public class RequestParameterNumberFormatException extends ServletActionException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param parameterName Parameter name
     * @param value String value found for this parameter
     */
    public RequestParameterNumberFormatException( String parameterName, String value )
    {
        super( "[" + parameterName + "]+[" + value + "]" );
    }
}
