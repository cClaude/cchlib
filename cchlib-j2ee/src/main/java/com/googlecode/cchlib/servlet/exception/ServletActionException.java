/**
 *
 */
package com.googlecode.cchlib.servlet.exception;

import javax.servlet.ServletException;

/**
 * @author Claude CHOISNET
 */
public class ServletActionException extends ServletException
{
    private static final long serialVersionUID = 1L;

    protected ServletActionException()
    {
        super();
    }

    public ServletActionException( String message )
    {
        super( message );
    }

    public ServletActionException( Throwable cause )
    {
        super( cause );
    }

    public ServletActionException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
