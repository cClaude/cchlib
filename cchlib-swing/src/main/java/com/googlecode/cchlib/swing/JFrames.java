package com.googlecode.cchlib.swing;

import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JFrame;

/**
 * Tools for {@link JFrame}
 */
public final class JFrames
{
    private JFrames()
    {
        // All static
    }

    /**
     * Handle minimum size of JFrame
     *
     * @param jFrame the {@link JFrame}
     * @param minimumDimension the new minimum {@link Dimension} of this window
     *
      * @since 4.1.7
     */
    public static void handleMinimumSize(
        final Window    jFrame,
        final Dimension minimumDimension
        )
    {
        Windows.handleMinimumSize( jFrame, minimumDimension );
    }

    /**
     * Handle minimum size of JFrame
     *
     * @param jFrame the {@link JFrame}
     * @param width the specified width
     * @param height the specified height
     *
     * @since 4.1.7
     */
    public static void handleMinimumSize(
        final JFrame jFrame,
        final int    width,
        final int    height
        )
    {
        Windows.handleMinimumSize( jFrame, width, height );
    }
}
