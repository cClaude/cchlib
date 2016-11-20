package com.googlecode.cchlib.servlet.simple;

import javax.servlet.ServletException;

public class SimpleServletContextException extends ServletException
{
    private static final long serialVersionUID = 1L;

    public SimpleServletContextException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SimpleServletContextException(String message)
    {
        super(message);
    }

    public SimpleServletContextException(Throwable cause)
    {
        super(cause);
    }
}
