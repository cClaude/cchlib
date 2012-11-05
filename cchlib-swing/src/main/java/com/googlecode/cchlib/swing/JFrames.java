package com.googlecode.cchlib.swing;

import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JFrame;

/**
 * Tools for {@link JFrame}
 */
public class JFrames
{
    /**
     * Handle minimum of JFrame
     *
     * @param jFrame the {@link JFrame}
     * @param minimumSize the new minimum size of this window
     * 
      * @since 4.1.7
     */
    public static void handleMinimumSize(
        final Window     jFrame,
        final Dimension  minimumSize
        )
    {
    	Windows.handleMinimumSize( jFrame, minimumSize );
    }
    
    /**
     * Handle minimum of JFrame
     * 
     * @param jFrame the {@link JFrame}
     * @param width the specified width
     * @param height the specified height
     * 
     * @since 4.1.7
     */
    public static void handleMinimumSize(
        final JFrame     jFrame,
        final int width,
        final int height
        )
    {
    	Windows.handleMinimumSize( jFrame, width, height );
    }

}
