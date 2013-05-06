package com.googlecode.cchlib.sandbox.pushingpixels;

import java.awt.GraphicsConfiguration;
import java.awt.Shape;
import java.awt.Window;

@Deprecated
public class AWTUtilitiesFrontEnd
{
    public enum Translucency {
        PERPIXEL_TRANSPARENT,
        TRANSLUCENT,
        PERPIXEL_TRANSLUCENT
        };

    @SuppressWarnings("restriction")
    public static boolean isTranslucencySupported( Translucency translucency )
    {
        return com.sun.awt.AWTUtilities.isTranslucencySupported( convertTranslucency( translucency ) );
    }

    @SuppressWarnings("restriction")
    private static com.sun.awt.AWTUtilities.Translucency convertTranslucency(
            Translucency translucency )
    {
        switch( translucency ) {
            case PERPIXEL_TRANSPARENT:
                return com.sun.awt.AWTUtilities.Translucency.PERPIXEL_TRANSPARENT;

            case TRANSLUCENT:
                return com.sun.awt.AWTUtilities.Translucency.TRANSLUCENT;

            case PERPIXEL_TRANSLUCENT:
                return com.sun.awt.AWTUtilities.Translucency.PERPIXEL_TRANSLUCENT;
            }
        return null;
    }

    @SuppressWarnings("restriction")
    public static boolean isTranslucencyCapable( GraphicsConfiguration graphicsConfiguration )
    {
        return com.sun.awt.AWTUtilities.isTranslucencyCapable( graphicsConfiguration );
    }

    @SuppressWarnings("restriction")
    public static void setWindowShape( Window window, Shape shape )
    {
        com.sun.awt.AWTUtilities.setWindowShape( window, shape );
    }

    @SuppressWarnings("restriction")
    public static void setWindowOpaque( Window window, boolean b )
    {
        com.sun.awt.AWTUtilities.setWindowOpaque( window, b );
    }

    @SuppressWarnings("restriction")
    public static void setWindowOpacity( Window window, float f )
    {
        com.sun.awt.AWTUtilities.setWindowOpacity( window, f );
    }

}
