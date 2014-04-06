package com.googlecode.cchlib.awt;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Tools for allow to use {@link java.awt.Desktop} using jdk 1.5
 *
 * @since 4.1.6
 */
public final class DesktopHelper
{
    private DesktopHelper()
    {
        // All Static
    }

//    /**
//     * @deprecated use {@link Desktop#isDesktopSupported()} instead
//     */
//    @Deprecated
//    public static boolean isNativeDesktopSupported()
//        throws PlatformDesktopNotSupportedException // $codepro.audit.disable unnecessaryExceptions
//    {
//        return Desktop.isDesktopSupported();
//    }

    /**
     * Launches the default browser to display a URL
     *
     * @param url URL to browse
     * @throws PlatformDesktopNotSupportedException if platform did not support Desktop
     */
    public static void browse( final URL url )
        throws PlatformDesktopNotSupportedException
    {
        if( Desktop.isDesktopSupported() ) {
            try {
                browseNative( url );
                }
            catch( Exception e ) {
                throw new PlatformDesktopNotSupportedException( e );
                }
            }
        else {
            // N2H: Try to run using "start <url>" under windows.
            throw new PlatformDesktopNotSupportedException(
                    "Error on browse action (fatal):" + url
                    );
            }
    }

    private static void browseNative( final URL url ) throws PlatformDesktopNotSupportedException, IOException, URISyntaxException
    {
        Desktop desktop     = Desktop.getDesktop();
        boolean isSupported = desktop.isSupported( Desktop.Action.BROWSE );

        if( ! isSupported ) {
            // Should not occur, should be tested before using this method
            throw new PlatformDesktopNotSupportedException(
                    "Desktop doesn't support the browse action (fatal)"
                    );
            }

        desktop.browse( url.toURI() );
    }
}
