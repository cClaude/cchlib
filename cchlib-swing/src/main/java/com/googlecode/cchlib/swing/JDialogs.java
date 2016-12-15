package com.googlecode.cchlib.swing;

import java.awt.Dimension;
import javax.swing.JDialog;

/**
 * Tools for {@link JDialog}
 */
public final class JDialogs
{
    private JDialogs()
    {
        // All static
    }

    /**
     * Handle minimum of {@link JDialog}
     *
     * @param jDialog the {@link JDialog}
     * @param minimumSize the new minimum size of this window
     *
      * @since 4.1.7
     */
    public static void handleMinimumSize(
        final JDialog    jDialog,
        final Dimension  minimumSize
        )
    {
        Windows.handleMinimumSize( jDialog, minimumSize );
    }

    /**
     * Handle minimum of {@link JDialog}
     *
     * @param jDialog the {@link JDialog}
     * @param width the specified width
     * @param height the specified height
     *
     * @since 4.1.7
     */
    public static void handleMinimumSize(
        final JDialog jDialog,
        final int     width,
        final int     height
        )
    {
        Windows.handleMinimumSize( jDialog, width, height );
    }
}
