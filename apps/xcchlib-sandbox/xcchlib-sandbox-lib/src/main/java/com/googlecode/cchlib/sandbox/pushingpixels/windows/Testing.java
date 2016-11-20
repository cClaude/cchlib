package com.googlecode.cchlib.sandbox.pushingpixels.windows;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

@Deprecated
public class Testing
{
    public static void main(final String[] args)
    {
        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice[]    gs = ge.getScreenDevices();

        System.out.println(
            "\tCan create shaped windows: "
                + com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd.isTranslucencySupported(
                        com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd.Translucency.PERPIXEL_TRANSPARENT
                        )
                );

        System.out.println(
            "\tCan create translucent windows: "
                + com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd.isTranslucencySupported(
                        com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd.Translucency.TRANSLUCENT
                        )
                );

        System.out.println("\tCan create shaped translucent windows: "
                + com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd.isTranslucencySupported(
                        com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd.Translucency.PERPIXEL_TRANSLUCENT
                        )
                );

        for( int j = 0; j < gs.length; j++ ) {
            final GraphicsDevice          gd = gs[j];
            final GraphicsConfiguration[] gc = gd.getConfigurations();

            for (int i = 0; i < gc.length; i++) {
                System.out.println("Screen " + j + ", config " + i);
                System.out.println("\tTranslucency capable: "
                    + com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd.isTranslucencyCapable( gc[i] )
                    );
                }
        }
    }
}
