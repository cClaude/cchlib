package com.googlecode.cchlib.servlet.exception;


/**
 *
 */
public class ServletActionAssertException extends ServletActionException
{
    private static final long serialVersionUID = 1L;

    protected ServletActionAssertException()
    {
        super();
    }

    public ServletActionAssertException( String message )
    {
        super( message );
    }

    public ServletActionAssertException( Throwable cause )
    {
        super( cause );
    }

    public ServletActionAssertException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
