package com.googlecode.cchlib.net.download.cache;

import java.io.IOException;

/**
 * Error when load cache
 */
public class PersistenceFileBadFormatException
    extends IOException
{
    private static final long serialVersionUID = 1L;

    public PersistenceFileBadFormatException( final String message )
    {
        super( message );
    }

}
