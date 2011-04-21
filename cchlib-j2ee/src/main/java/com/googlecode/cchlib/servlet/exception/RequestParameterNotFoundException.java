/**
 *
 */
package com.googlecode.cchlib.servlet.exception;



/**
 *
 * @author Claude CHOISNET
 */
public class RequestParameterNotFoundException extends ServletActionException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param parameterName Parameter name
     */
    public RequestParameterNotFoundException( String parameterName )
    {
        super( parameterName );
    }
}
