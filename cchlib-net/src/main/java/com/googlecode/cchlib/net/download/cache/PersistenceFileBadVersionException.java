package com.googlecode.cchlib.net.download.cache;

/**
 * @since 4.1.7
 */
public class PersistenceFileBadVersionException extends Exception
{
    private static final long serialVersionUID = 1L;

    public PersistenceFileBadVersionException( final String message )
    {
        super( message );
    }
}
