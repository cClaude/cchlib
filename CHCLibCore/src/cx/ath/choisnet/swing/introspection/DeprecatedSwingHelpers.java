/************************************************************************************
 *                                .                                                 *
 *                                .                                                 *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

/**
 * DO NOT USE !
 */
@Deprecated
class DeprecatedSwingHelpers
{
    /*
    * Unregisters the zipfldr.dll fixes JDialog slowness
    * issues. (Should be made optional)
    */
    public static void applyFileChooserTweak()
    {
        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( !osName.contains( "windows" ) ) return;
        try {
            System.out.print( "Unregistering zipfldr.dll to speed up program (don't worry, windows will reset this)..." );
            Runtime.getRuntime().exec( "regsvr32 /s %windir%/system32/zipfldr.dll" );
            System.out.println( "success" );
        }
        catch( Exception e ) {
            System.out.println( "failed" );
        }
    }

    /**
     * @param parent
     * @param title
     * @param exception
     */
    public static void showMessageDialog(
        final Component     parent,
        final String        title,
        final Throwable     exception
        )
    {
        // http://download.oracle.com/javase/tutorial/uiswing/components/dialog.html
        JOptionPane.showMessageDialog(
            parent,
            exception.getMessage(),
            title,
            JOptionPane.ERROR_MESSAGE
            );
    }

//    /**
//     * @param frame
//     * @param lnfName
//     * @throws ClassNotFoundException
//     * @throws InstantiationException
//     * @throws IllegalAccessException
//     * @throws UnsupportedLookAndFeelException
//     */
//    @Deprecated
//    public static void setLookAndFeel( JFrame frame, String lnfName ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
//    {
//        // http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//        UIManager.setLookAndFeel(lnfName);
//        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
//    }
//
//    /**
//     * @param frame
//     * @param lnfName
//     */
//    @Deprecated
//    public static void setLookAndFeelNoException( JFrame frame, String lnfName )
//    {
//        try {
//            setLookAndFeel( frame, lnfName );
//        }
//        catch( Exception e ) {
//            // ClassNotFoundException
//            // InstantiationException
//            // IllegalAccessException
//            // UnsupportedLookAndFeelException
//
//            // TODO open a dialog !
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param jFrame
//     * @param jMenu
//     */
//    @Deprecated
//    public static void buildLookAndFeelMenu( final JFrame jFrame, final JMenu jMenu )
//    {
//        LookAndFeelInfo[] lookAndFeelInfos = UIManager
//                .getInstalledLookAndFeels();
//
//        ButtonGroup buttonGroup = new ButtonGroup();
//        LookAndFeel currentLookAndFeel = UIManager.getLookAndFeel();
//        String      currentLookAndFeelClassName = currentLookAndFeel.getClass().getName();
//        // TODO: keep jMenuItem list (use JRadioButtonMenuItem or
//        // JCheckBoxMenuItem)
//
//        for( LookAndFeelInfo info : lookAndFeelInfos ) {
//            JRadioButtonMenuItem jMenuItem = new JRadioButtonMenuItem();
//
//            jMenuItem.setText( info.getName() );
//            final String cname = info.getClassName();
//            
//            if( cname.equals( currentLookAndFeelClassName )) {
//                jMenuItem.setSelected( true );
//            }
//            
//            buttonGroup.add( jMenuItem );
//            
//            jMenuItem.addMouseListener( new MouseAdapter() {
//                public void mousePressed( MouseEvent event )
//                {
//                    SwingHelpers.setLookAndFeelNoException( jFrame, cname );
//                }
//            } );
//
//            jMenu.add( jMenuItem );
//        }
//    }

    /**
     * Alternative of javax.swing.ButtonGroup
     * @param from
     * @param to
     * @see javax.swing.ButtonGroup
     */
    public static void setInvertSelection( AbstractButton from, AbstractButton to )
    {
        to.setSelected( !from.isSelected() );
    }

    /**
     *
     * @param min - minimum value in array
     * @param max - maximum value in array
     * @param minLength - minimum length of Strings
     * @param isRightAligned - should right align String content?
     * @return an Object array within String Objects
     */
    public final static Object[] initObjectArray( 
            final int       min,
            final int       max, 
            final int       minLength,
            final boolean   isRightAligned
            )
    {
        final StringBuilder padding = new StringBuilder();

        if( minLength > 0 ) {
            for( int i = 0; i<minLength; i++ ) {
                padding.append( ' ' );
            }
        }

        final String padStr = padding.toString();

        final int      len = max - min + 1;
        final Object[] object = new Object[ len ];

        for( int i = 0; i<len; i++ ) {
            String s = Integer.toString( min + i );
            int    l = s.length();

            if( l < minLength ) {
                if( isRightAligned ) {
                    s = padStr.substring( 0, minLength - l ) + s;
                } else {
                    s = s + padStr.substring( 0, minLength - l );
                }
            }

            object[ i ] = s;
        }

        return object;
    }

}
