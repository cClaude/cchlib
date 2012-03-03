package com.googlecode.cchlib.awt;

/**
 * 
 * @since 4.1.6
 */
public class PlateformeDesktopNotSupportedException extends Exception
{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public PlateformeDesktopNotSupportedException()
    {
    }

    /**
     * @param message
     */
    public PlateformeDesktopNotSupportedException( String message )
    {
        super( message );
    }

    /**
     * @param cause
     */
    public PlateformeDesktopNotSupportedException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     * @param cause
     */
    public PlateformeDesktopNotSupportedException(
            String message,
            Throwable cause 
            )
    {
        super( message, cause );
    }
}
