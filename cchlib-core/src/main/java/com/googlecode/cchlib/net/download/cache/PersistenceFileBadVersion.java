package com.googlecode.cchlib.net.download.cache;

/**
 *
 * @since 4.1.7
 */
public class PersistenceFileBadVersion extends Exception 
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public PersistenceFileBadVersion( String message )
    {
        super( message );
    }

}
