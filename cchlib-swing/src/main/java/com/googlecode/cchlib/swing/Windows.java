package com.googlecode.cchlib.swing;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Tools for {@link Window}
 */
public class Windows
{
    /**
     * Handle minimum of {@link Window}
     *
     * @param window the {@link Window}
     * @param minimumSize the new minimum size of this window
     *
      * @since 4.1.7
     */
    public static void handleMinimumSize(
        final Window     window,
        final Dimension  minimumSize
        )
    {
        window.setMinimumSize( minimumSize );
        window.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e )
            {
                Dimension d    = window.getSize();
                Dimension minD = window.getMinimumSize();

                if( d.width < minD.width ) {
                    d.width = minD.width;
                    }

                if( d.height < minD.height ) {
                    d.height = minD.height;
                    }

                window.setSize( d );
            }
        });
    }

    /**
     * Handle minimum of {@link Window}
     *
     * @param window the {@link Window}
     * @param width the specified width
     * @param height the specified height
     *
     * @since 4.1.7
     */
    public static void handleMinimumSize(
        final Window     window,
        final int        width,
        final int        height
        )
    {
        handleMinimumSize( window, new Dimension( width, height ) );
    }
}
