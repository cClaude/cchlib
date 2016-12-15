package com.googlecode.cchlib.servlet.simple;

public class ServletContextParamNotFoundException extends SimpleServletContextException
{
    private static final long serialVersionUID = 1L;

    public ServletContextParamNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public ServletContextParamNotFoundException(final String message)
    {
        super(message);
    }

    public ServletContextParamNotFoundException(final Throwable cause)
    {
        super(cause);
    }
}
