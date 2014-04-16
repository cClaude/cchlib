package com.googlecode.cchlib.tools.sortfiles;

public class FileParserException extends Exception {
    private static final long serialVersionUID = 1L;

    public FileParserException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public FileParserException( String message )
    {
        super( message );
    }
}
