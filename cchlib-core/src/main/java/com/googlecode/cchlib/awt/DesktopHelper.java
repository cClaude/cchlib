package com.googlecode.cchlib.awt;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Tools for {@link java.awt.Desktop}
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
     * Test using reflection if
     * {@link java.awt.Desktop#isDesktopSupported()}
     * return true.
     * @return true if java.awt.Desktop class is supported on the current platform;
     *         false otherwise
     * @throws PlateformeDesktopNotSupportedException if any
     *         error occur while trying to resolve
     *         {@link java.awt.Desktop#isDesktopSupported()}.
     *         That mean current JRE does not support
     *         {@link java.awt.Desktop}.
     */
    public static boolean isNativeDesktopSupported()
        throws PlateformeDesktopNotSupportedException
    {
        final String    className = "java.awt.Desktop";
        final String    methodName = "isDesktopSupported";
        Exception       returnException;

        try {
            Class<?>    c = Class.forName( className );
            Method      m = c.getMethod( methodName, new Class[0] );
            Object      r = m.invoke( null, new Object[0] );

            if( r instanceof Boolean ) {
                return Boolean.class.cast( r ).booleanValue();
                }
            return false;
            }
        catch( ClassNotFoundException e ) {
            returnException = e;
            }
        catch( NoSuchMethodException e ) {
            returnException = e;
            }
        catch( SecurityException e ) {
            returnException = e;
            }
        catch( IllegalAccessException e ) {
            returnException = e;
            }
        catch( IllegalArgumentException e ) {
            returnException = e;
            }
        catch( InvocationTargetException e ) {
            returnException = e;
            }

        throw new PlateformeDesktopNotSupportedException(
                "Desktop doesn't support the browse action (fatal)",
                returnException
                );
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
            browseNative( url );
            }
        else {
            // TODO: Try to run using "start <url>" under windows.
            throw new PlateformeDesktopNotSupportedException(
                    "Error on browse action (fatal):" + url
                    );
            }
    }

    private static void browseNative( final URL url )
        throws PlateformeDesktopNotSupportedException
    {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
            // Should not occur, should be tested before using this
            // method
            throw new PlateformeDesktopNotSupportedException(
                    "Desktop doesn't support the browse action (fatal)"
                    );
            }

        try {
            desktop.browse( url.toURI() );
            }
        catch( URISyntaxException e ) {
            throw new PlateformeDesktopNotSupportedException( e );
            }
        catch( IOException e ) {
            throw new PlateformeDesktopNotSupportedException( e );
        }
    }
}
