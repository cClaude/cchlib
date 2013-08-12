package com.googlecode.cchlib.awt;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

/**
 * <p>This class is design to be able to run under java 1.5</p>
 *
 * Tools for allow to use java.awt.Desktop under jdk 1.5
 *
 * @since 4.1.6
 */
public class DesktopHelperJ5
{
    private static final String DesktopClassName = "java.awt.Desktop";
    private static final String isDesktopSupportedMethodName = "isDesktopSupported";

    private static final String DesktopActionClassName = "java.awt.Desktop$Action";

    private DesktopHelperJ5()
    {
        // All Static
    }

    /**
     * Test using reflection if java.awt.Desktop#isDesktopSupported() return true.
     *
     * @return true if java.awt.Desktop class is supported on the current platform;
     *         false otherwise
     * @throws PlateformeDesktopNotSupportedJ5Exception if any
     *         error occur while trying to resolve java.awt.Desktop#isDesktopSupported().
     *         That mean current JRE does not support java.awt.Desktop.
     */
    public static boolean isNativeDesktopSupported()
        throws PlateformeDesktopNotSupportedJ5Exception
    {
        try {
            Class<?>    c = Class.forName( DesktopClassName );
            Method      m = c.getMethod( isDesktopSupportedMethodName, new Class[0] );
            Object      r = m.invoke( null, new Object[0] );

            if( r instanceof Boolean ) {
                return Boolean.class.cast( r ).booleanValue();
                }
            return false;
            }
        catch( Throwable e ) {
            throw new PlateformeDesktopNotSupportedJ5Exception(
                    "Desktop doesn't support the browse action (fatal)",
                    e
                    );
            }
    }

    /**
     * TODOC
     *
     * @param url
     * @throws PlateformeDesktopNotSupportedJ5Exception
     */
    public static void browse( final URL url )
        throws PlateformeDesktopNotSupportedJ5Exception
    {
        if( isNativeDesktopSupported() ) {
            try {
                browseNative( url );
                }
            catch( Exception e ) {
                throw new PlateformeDesktopNotSupportedJ5Exception( e );
                }
            }
        else {
            // N2H: Try to run using "start <url>" under windows.
            throw new PlateformeDesktopNotSupportedJ5Exception(
                    "Error on browse action (fatal):" + url
                    );
            }
    }

    private static void browseNative( final URL url )
        throws Exception
    {
        //java.awt.Desktop desktop = Desktop.getDesktop();
        Class<?>    desktopClass     = Class.forName( DesktopClassName );
        Method      getDesktopMethod = desktopClass.getMethod( "getDesktop", new Class[0] );
        Object      desktop          = getDesktopMethod.invoke( null, new Object[0] );

        // boolean isSupported = desktop.isSupported( Desktop.Action.BROWSE );
        Class<?> desktopActionClass = Class.forName( DesktopActionClassName );
        Enum<?>  enumParam          = null;

        if( desktopActionClass.isEnum() ) {
            Object[] enumConstants = desktopActionClass.getEnumConstants();

            for( Object enumConstant : enumConstants ) {
                Enum<?> v = (Enum<?>)enumConstant;

                if( "BROWSE".equals( v.name() ) ) {
                    enumParam = ((Enum<?>)enumConstant);
                    break;
                    }
                }
         }

        Method      isSupportedMethod = desktopClass.getMethod( "isSupported", new Class[]{ desktopActionClass } );
        Object      isSupported       = isSupportedMethod.invoke( desktop, new Object[]{ enumParam } );

        if( ! ((Boolean)isSupported).booleanValue() ) { //  if( ! isSupported )
            // Should not occur, should be tested before using this
            // method
            throw new PlateformeDesktopNotSupportedJ5Exception(
                    "Desktop doesn't support the browse action (fatal)"
                    );
            }

        // desktop.browse( url.toURI() );
        Method browseMethod = desktopClass.getMethod( "browse", new Class[]{ URI.class } );
        browseMethod.invoke( desktop, new Object[]{ url.toURI() } );
    }
}
