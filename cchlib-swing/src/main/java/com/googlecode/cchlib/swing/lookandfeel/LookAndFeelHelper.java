package com.googlecode.cchlib.swing.lookandfeel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Provide some tools for LookAndFeel
 *
 * @since 1.4.7
 */
public final class LookAndFeelHelper
{
    private LookAndFeelHelper()
    {//All static
    }

    /**
     * Change LookAndFeel.
     *
     * @param frame    Root frame
     * @param lnfName  LookAndFeel name to set
     * @throws ClassNotFoundException if the LookAndFeel class could not be found
     * @throws InstantiationException if a new instance of the class couldn't be created
     * @throws IllegalAccessException if the class or initializer isn't accessible
     * @throws UnsupportedLookAndFeelException if lnf.isSupportedLookAndFeel() is false
     * @throws ClassCastException if className does not identify a class that extends LookAndFeel
     */
    public static void setLookAndFeel( JFrame frame, String lnfName )
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException,
               UnsupportedLookAndFeelException,
               ClassCastException
    {
        // http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        UIManager.setLookAndFeel(lnfName);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }

    /**
     * Change LookAndFeel, but never goes to Exception.
     * If any error occur, just report exception stack trace
     * to {@link System#err}.
     *
     * @param frame    Root frame
     * @param lnfName  LookAndFeel name to set
     */
    public static void setLookAndFeelNoException(
        final JFrame frame,
        final String lnfName
        )
    {
        try {
            setLookAndFeel( frame, lnfName );
            }
        catch( Exception e ) {
            // ClassNotFoundException
            // InstantiationException
            // IllegalAccessException
            // UnsupportedLookAndFeelException
            final String message =
                "Can not change LookAndFeel: "
                    + e.getLocalizedMessage();

            // Launch a thread outside event thread
            new Thread( new Runnable() {
                @Override
                public void run()
                {
                    // Append task to event thread.
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            JOptionPane.showInternalConfirmDialog(
                                    frame,
                                    message
                                    );
                        }
                    });
                }
            },"LookAndFeelHelper.setLookAndFeelNoException()").start();

            // TODO open a dialog !??
            e.printStackTrace();
        }
    }
}
