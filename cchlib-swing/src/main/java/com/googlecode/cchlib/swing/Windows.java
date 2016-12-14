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
    private Windows()
    {
        // All static
    }

    /**
     * Handle minimum of {@link Window}
     *
     * @param window the {@link Window}
     * @param minimumDimension the new minimum {@link Dimension} of this window
     *
      * @since 4.1.7
     */
    public static void handleMinimumSize(
        final Window     window,
        final Dimension  minimumDimension
        )
    {
        window.setMinimumSize( minimumDimension );
        window.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentResized( final ComponentEvent e )
            {
                final Dimension d    = window.getSize();
                final Dimension minD = window.getMinimumSize();

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
