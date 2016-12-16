package com.googlecode.cchlib.swing;

import java.awt.GraphicsEnvironment;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 * A collection of utility methods for Swing.
 */
public final class SafeSwingUtilities
{
    private static final Logger LOGGER = Logger.getLogger( SafeSwingUtilities.class );
    private static Boolean swingAvailable;

    private SafeSwingUtilities()
    {
        // All static
    }

    /**
     * Make sure to be outside swing even threads and log errors if any
     *
     * @param target
     *            the object whose run method is invoked when this thread
     *            is started. If
     * @param threadName
     *            Thread name to use
     */
    @SuppressWarnings("squid:S1172") // Is used !
    public static void invokeLater(
        final Runnable target,
        final String   threadName
        )
    {
        new Thread( (Runnable)() -> SafeSwingUtilities.safeRun( target ), threadName ).start();
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Is used !
    private static void safeRun( final Runnable doRun )
    {
        try {
            SwingUtilities.invokeLater( doRun );
            }
        catch( final Exception e ) {
            LOGGER.warn( "Unexpected error", e );
            }
    }

    /**
     * Tests whether or not a display, keyboard, and mouse can be supported in
     * this environment. If this method returns true, a HeadlessException is
     * thrown from areas of the Toolkit and GraphicsEnvironment that are
     * dependent on a display, keyboard, or mouse.
     * <p>
     * This just an alias for
     * <pre>
     *  GraphicsEnvironment.isHeadless()
     * </pre>
     *
     * @return true if this environment cannot support a display, keyboard,
     *         and mouse; false otherwise
     * @see GraphicsEnvironment
     * @since 4.2
     * @see #isSwingAvailable()
     */
    public static boolean isHeadless()
    {
        return GraphicsEnvironment.isHeadless();
    }

    /**
     * Return true is swing is available, return true if swing is really
     * supported, false otherwise
     *
     * @return true is swing is available, false otherwise
     * @since 4.2
     * @see #isHeadless()
     * @see #isHeadlessCorrect()
     */
    public static boolean isSwingAvailable()
    {
        if( swingAvailable == null ) {
            computeSwingAvailable();
        }

        return swingAvailable.booleanValue();
    }

    /**
     * Ensure property {@code java.awt.headless} is correctly set according to
     * environment configuration.
     *
     * @return true is environment support to graphical objects
     *         creation
     */
    public static boolean isHeadlessCorrect()
    {
        if( swingAvailable == null ) {
            computeSwingAvailable();
        }

        return swingAvailable.booleanValue() ? true : isHeadless();
    }

    private static void computeSwingAvailable()
    {
        synchronized( SafeSwingUtilities.class ) {
            if( swingAvailable == null ) {
                swingAvailable = Boolean.valueOf( tryToCreateJButton() );
            }
        }
    }

    @SuppressWarnings("squid:S1166") // don't are this exception
    private static boolean tryToCreateJButton()
    {
        try {
            final JButton button = new JButton();
            button.setText( "testJButton" );
            return true;
        }
        catch( final Exception | NoClassDefFoundError dontCareThisException ) {
            // don't are this exception
        }

        return false;
    }

    /**
     * This is a private method, for swing tools to display some
     * deep exceptions.
     *
     * @param cause The cause
     */
    @SuppressWarnings("squid:S1148")
    public static void printStackTrace( final Exception cause )
    {
        // TODO improve this to be able to configure a LOGGER throw
        // JVM properties, but keep this has the default choice, this
        // how the swing frame work did.
        cause.printStackTrace();
    }
}
