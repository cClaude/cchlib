package com.googlecode.cchlib.net.download.cache;

/**
 *
 * @since 4.1.7
 */
public class PersistenceFileBadVersionException // $codepro.audit.disable serializableUsage
    extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public PersistenceFileBadVersionException( String message )
    {
        super( message );
    }

}
