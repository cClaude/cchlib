package com.googlecode.cchlib.awt;

/**
 * Use by {@link DesktopHelper} if {@link java.awt.Desktop} is not
 * supported
 * 
 * @since 4.1.6
 */
public class PlatformDesktopNotSupportedException extends Exception
{

    private static final long serialVersionUID = 1L;

    public PlatformDesktopNotSupportedException()
    {
    }

    public PlatformDesktopNotSupportedException( String message )
    {
        super( message );
    }

    public PlatformDesktopNotSupportedException( Throwable cause )
    {
        super( cause );
    }

    public PlatformDesktopNotSupportedException(
            String message,
            Throwable cause 
            )
    {
        super( message, cause );
    }
}
