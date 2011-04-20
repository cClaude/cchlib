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
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Provide some tools for LookAndFeel
 * 
 * @author Claude CHOISNET
 */
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
    public static void setLookAndFeelNoException( JFrame frame, String lnfName )
    {
        try {
            setLookAndFeel( frame, lnfName );
        }
        catch( Exception e ) {
            // ClassNotFoundException
            // InstantiationException
            // IllegalAccessException
            // UnsupportedLookAndFeelException

            // TODO open a dialog !
            e.printStackTrace();
        }
    }

    /**
     * @param jFrame
     * @param jMenu
     */
    public static void buildLookAndFeelMenu( 
            final JFrame    jFrame, 
            final JMenu     jMenu 
            )
    {
        LookAndFeelInfo[] lookAndFeelInfos = UIManager
                .getInstalledLookAndFeels();

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
