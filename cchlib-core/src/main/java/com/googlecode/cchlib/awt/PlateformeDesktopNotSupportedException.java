package com.googlecode.cchlib.awt;

/**
 * Use by {@link DesktopHelper} if {@link java.awt.Desktop} is not
 * supported
 * 
 * @since 4.1.6
 */
public class PlateformeDesktopNotSupportedException extends Exception
{

    private static final long serialVersionUID = 1L;

    public PlateformeDesktopNotSupportedException()
    {
    }

    public PlateformeDesktopNotSupportedException( String message )
    {
        super( message );
    }

    public PlateformeDesktopNotSupportedException( Throwable cause )
    {
        super( cause );
    }

    public PlateformeDesktopNotSupportedException(
            String message,
            Throwable cause 
            )
    {
        super( message, cause );
    }
}
