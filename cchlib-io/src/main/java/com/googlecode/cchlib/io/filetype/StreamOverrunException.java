package com.googlecode.cchlib.io.filetype;

import java.io.IOException;

/**
 *
 */
public class StreamOverrunException extends IOException
{
    private static final long serialVersionUID = 1L;

    public StreamOverrunException()
    {
    }

    public StreamOverrunException( String message )
    {
        super( message );
    }
}
