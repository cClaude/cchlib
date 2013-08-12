package com.googlecode.cchlib.awt;

import java.awt.Desktop;
import java.net.URL;

/**
 * Tools for allow to use {@link java.awt.Desktop} using jdk 1.5
 *
 * @since 4.1.6
 */
public class DesktopHelper
{
    private DesktopHelper()
    {
        // All Static
    }

    /**
     * @deprecated use {@link Desktop#isDesktopSupported()} instead
     */
    @Deprecated
    public static boolean isNativeDesktopSupported()
        throws PlateformeDesktopNotSupportedException
    {
        return Desktop.isDesktopSupported();
    }

    /**
     * TODOC
     *
     * @param url
     * @throws PlateformeDesktopNotSupportedException
     */
    public static void browse( final URL url )
        throws PlateformeDesktopNotSupportedException
    {
        if( isNativeDesktopSupported() ) {
            try {
                browseNative( url );
                }
            catch( Exception e ) {
                throw new PlateformeDesktopNotSupportedException( e );
                }
            }
        else {
            // N2H: Try to run using "start <url>" under windows.
            throw new PlateformeDesktopNotSupportedException(
                    "Error on browse action (fatal):" + url
                    );
            }
    }

    private static void browseNative( final URL url ) throws Exception
    {
        Desktop desktop     = Desktop.getDesktop();
        boolean isSupported = desktop.isSupported( Desktop.Action.BROWSE );

        if( ! isSupported ) {
            // Should not occur, should be tested before using this method
            throw new PlateformeDesktopNotSupportedException(
                    "Desktop doesn't support the browse action (fatal)"
                    );
            }

        desktop.browse( url.toURI() );
    }
}
