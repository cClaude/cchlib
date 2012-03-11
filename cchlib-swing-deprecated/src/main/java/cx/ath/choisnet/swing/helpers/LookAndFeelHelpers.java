/************************************************************************************
 *                                .                                                 *
 *                                .                                                 *
 ************************************************************************************/
package cx.ath.choisnet.swing.helpers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

@Deprecated
public class LookAndFeelHelpers
{
    private LookAndFeelHelpers()
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
     * @deprecated use {@link com.googlecode.cchlib.swing.lookandfeel.LookAndFeelHelper#setLookAndFeel(JFrame, String)} instead
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
     * @deprecated use {@link com.googlecode.cchlib.swing.lookandfeel.LookAndFeelHelper#setLookAndFeelNoException(JFrame, String)} instead
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
            }).start();

            // TODO open a dialog !n
            e.printStackTrace();
        }
    }

    /**
     * Add menu entries ({@link JRadioButtonMenuItem}) on giving
     * jMenu with a list of all LookAndFeel known by the jvm.
     * <br>
     * When a new item is selected, then call {@link #setLookAndFeelNoException(JFrame, String)}
     * with selected LookAndFeel.
     * <br>
     * If you need to do extra customization when LookAndFeel
     * is change you can use
     * {@link UIManager#addPropertyChangeListener(java.beans.PropertyChangeListener)}
     * and implement you custom initialization in method
     * {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
     *
     * @param jFrame {@link JFrame} that will be customize with
     *        selected LookAndFeel
     * @param jMenu {@link JMenu} where {@link JRadioButtonMenuItem}
     *        list will be added.
     * @see #setLookAndFeelNoException(JFrame, String)
     */
    public static void buildLookAndFeelMenu(
            final JFrame    jFrame,
            final JMenu     jMenu
            )
    {
        LookAndFeelInfo[] lookAndFeelInfos = UIManager
                .getInstalledLookAndFeels();

        buildLookAndFeelMenu( jFrame, jMenu, lookAndFeelInfos );
    }

    /**
     * Add menu entries ({@link JRadioButtonMenuItem}) on giving
     * jMenu with a list of all LookAndFeel known by the jvm.
     * <br>
     * When a new item is selected, then call {@link #setLookAndFeelNoException(JFrame, String)}
     * with selected LookAndFeel.
     * <br>
     * If you need to do extra customization when LookAndFeel
     * is change you can use
     * {@link UIManager#addPropertyChangeListener(java.beans.PropertyChangeListener)}
     * and implement you custom initialization in method
     * {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
     *
     * @param jFrame {@link JFrame} that will be customize with
     *        selected LookAndFeel
     * @param jMenu {@link JMenu} where {@link JRadioButtonMenuItem}
     *        list will be added.
     * @param lookAndFeelInfos Array
     * @see #setLookAndFeelNoException(JFrame, String)
     * @since 4.1.6
     */
    public static void buildLookAndFeelMenu(
            final JFrame            jFrame,
            final JMenu             jMenu,
            final LookAndFeelInfo...lookAndFeelInfos
            )
    {
        ButtonGroup buttonGroup = new ButtonGroup();
        LookAndFeel currentLookAndFeel = UIManager.getLookAndFeel();
        String      currentLookAndFeelClassName = currentLookAndFeel.getClass().getName();

        for( LookAndFeelInfo info : lookAndFeelInfos ) {
            JRadioButtonMenuItem jMenuItem = new JRadioButtonMenuItem();

            jMenuItem.setText( info.getName() );
            final String cname = info.getClassName();

            if( cname.equals( currentLookAndFeelClassName )) {
                jMenuItem.setSelected( true );
                }

            buttonGroup.add( jMenuItem );

            jMenuItem.addMouseListener( new MouseAdapter()
            {
                public void mousePressed( MouseEvent event )
                {
                    SwingUtilities.invokeLater( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            setLookAndFeelNoException( jFrame, cname );
                        }
                    });
                }
            });

            jMenu.add( jMenuItem );
        }
    }
}
