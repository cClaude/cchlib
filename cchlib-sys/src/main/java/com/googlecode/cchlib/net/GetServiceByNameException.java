package com.googlecode.cchlib.net;

import com.googlecode.cchlib.lang.UnsupportedSystemException;

/**
 * Invoke when an error occur while reading /etc/services
 */
public class GetServiceByNameException extends UnsupportedSystemException
{
    private static final long serialVersionUID = 1L;

    protected GetServiceByNameException()
    {
    }

    public GetServiceByNameException( String message )
    {
        super( message );
    }

    public GetServiceByNameException( Throwable cause )
    {
        super( cause );
    }

    public GetServiceByNameException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
