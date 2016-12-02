package com.googlecode.cchlib.net.download;

/**
 * @see DownloadExecutor
 * @since 4.1.7
 */
public class BadDownloadEventException
    extends DownloadConfigurationException
{
    private static final long serialVersionUID = 1L;

    /**
     * Create a BadDownloadEventException
     */
    public BadDownloadEventException()
    {
        // Empty
    }

    public BadDownloadEventException( final String message )
    {
        super( message );
    }
}
