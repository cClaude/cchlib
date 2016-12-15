package com.googlecode.cchlib.swing;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Tools for {@link Window}
 */
public final class Windows
{
    private Windows()
    {
        // All static
    }

    /**
     * Handle minimum size of {@link Window}
     *
     * @param window
     *            the {@link Window}
     * @param minimumDimension
     *            the new minimum {@link Dimension} of this window
     * @since 4.1.7
     */
    public static void handleMinimumSize(
        final Window    window,
        final Dimension minimumDimension
        )
    {
        window.setMinimumSize( minimumDimension );
        window.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentResized( final ComponentEvent event )
            {
                final Dimension dimension = window.getSize();
                final Dimension minimum   = window.getMinimumSize();

                if( dimension.width < minimum.width ) {
                    dimension.width = minimum.width;
                    }

                if( dimension.height < minimum.height ) {
                    dimension.height = minimum.height;
                    }

                window.setSize( dimension );
            }
        });
    }

    /**
     * Handle minimum size of {@link Window}
     *
     * @param window
     *            the {@link Window}
     * @param minimumWidth
     *            the specified width
     * @param minimumHeight
     *            the specified height
     * @since 4.1.7
     */
    public static void handleMinimumSize(
        final Window window,
        final int    minimumWidth,
        final int    minimumHeight
        )
    {
        handleMinimumSize(
            window,
            new Dimension( minimumWidth, minimumHeight )
            );
    }
}
