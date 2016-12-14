package com.googlecode.cchlib.swing.lookandfeel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

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
     * @param frame
     *            Root frame
     * @param lnfName
     *            LookAndFeel name to set
     * @throws LookAndFeelModifyException
     *             if the LookAndFeel class could not be set
     */
    public static void setLookAndFeel( final JFrame frame, final String lnfName )
        throws LookAndFeelModifyException
    {
        try {
            setLookAndFeelRaw( frame, lnfName );
        }
        catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException cause ) {
            throw new LookAndFeelModifyException( lnfName, cause );
        }
    }

    /**
     * Change LookAndFeel. Based on
     * <br>
     * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     *
     * @param frame
     *            Root frame
     * @param lnfName
     *            LookAndFeel name to set
     * @throws ClassNotFoundException
     *             if the LookAndFeel class could not be found
     * @throws InstantiationException
     *             if a new instance of the class couldn't be created
     * @throws IllegalAccessException
     *             if the class or initializer isn't accessible
     * @throws UnsupportedLookAndFeelException
     *             if lnf.isSupportedLookAndFeel() is false
     * @throws ClassCastException
     *             if className does not identify a class that extends LookAndFeel
     */
    private static void setLookAndFeelRaw( final JFrame frame, final String lnfName )
        throws  ClassNotFoundException,
                InstantiationException,
                IllegalAccessException,
                UnsupportedLookAndFeelException
    {
        UIManager.setLookAndFeel( lnfName );
        SwingUtilities.updateComponentTreeUI( frame );
        frame.pack();
    }

    /**
     * Change LookAndFeel, but never goes to Exception. If any error occur,
     * just report exception stack trace to {@link System#err}.
     *
     * @param frame
     *            Root frame
     * @param lnfName
     *            LookAndFeel name to set
     * @return true if LookAndFeel has change, false otherwise
     */
    public static boolean setLookAndFeelNoException(
        final JFrame frame,
        final String lnfName
        )
    {
        try {
            setLookAndFeelRaw( frame, lnfName );

            return true;
            }
        catch( final Exception cause ) {
            // ClassNotFoundException
            // InstantiationException
            // IllegalAccessException
            // UnsupportedLookAndFeelException

            // Launch a thread outside event thread
            new Thread(
                (Runnable)() -> SwingUtilities.invokeLater(
                    () -> JOptionPane.showInternalConfirmDialog(
                            frame,
                            "Can not change LookAndFeel: "
                            )
                            ),
                "LookAndFeelHelper.setLookAndFeelNoException()" + cause.getLocalizedMessage()
                ).start();

            // TODO open a dialog !??
            SafeSwingUtilities.printStackTrace( cause );

            return false;
        }
    }
}
