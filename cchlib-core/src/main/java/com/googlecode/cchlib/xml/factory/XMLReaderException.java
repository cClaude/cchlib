package com.googlecode.cchlib.xml.factory;

import com.googlecode.cchlib.xml.XMLException;

public class XMLReaderException extends XMLException 
{
    private static final long serialVersionUID = 1L;

    public XMLReaderException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public XMLReaderException( String message )
    {
        super( message );
    }
}
