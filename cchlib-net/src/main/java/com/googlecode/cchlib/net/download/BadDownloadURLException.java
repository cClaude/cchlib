package com.googlecode.cchlib.net.download;

/**
 * @see DownloadExecutor
 *
 * @since 4.1.7
 */
public class BadDownloadURLException
    extends DownloadConfigurationException
{
    private static final long serialVersionUID = 1L;
    /**
     * Create a BadDownloadURLException
     */
    public BadDownloadURLException()
    {
        // Empty
    }

    public BadDownloadURLException( final String message )
    {
        super( message );
    }
}
