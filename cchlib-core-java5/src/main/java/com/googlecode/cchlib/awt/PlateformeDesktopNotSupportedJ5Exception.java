package com.googlecode.cchlib.awt;

/**
 * <p>This class is design to be able to run under java 1.5</p>
 * 
 * Use by {@link DesktopHelperJ5} if {@link java.awt.Desktop} is not supported.
 * 
 * @since 4.1.8
 */
public class PlateformeDesktopNotSupportedJ5Exception extends Exception
{

    private static final long serialVersionUID = 1L;

    public PlateformeDesktopNotSupportedJ5Exception()
    {
    }

    public PlateformeDesktopNotSupportedJ5Exception( String message )
    {
        super( message );
    }

    public PlateformeDesktopNotSupportedJ5Exception( Throwable cause )
    {
        super( cause );
    }

    public PlateformeDesktopNotSupportedJ5Exception(
            String message,
            Throwable cause 
            )
    {
        super( message, cause );
    }
}
