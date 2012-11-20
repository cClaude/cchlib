package com.googlecode.cchlib.xml;

public class XMLException extends Exception 
{
    private static final long serialVersionUID = 1L;

    public XMLException()
    {
        super();
    }

    public XMLException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public XMLException( String message )
    {
        super( message );
    }

    public XMLException( Throwable cause )
    {
        super( cause );
    }
}
