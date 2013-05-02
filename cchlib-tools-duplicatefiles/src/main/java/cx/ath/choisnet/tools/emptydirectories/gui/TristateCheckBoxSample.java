package cx.ath.choisnet.tools.emptydirectories.gui;

import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.UIManager;


public class TristateCheckBoxSample
{

    @SuppressWarnings("unused")
    public static void main( String args[] ) throws Exception
    {
        JFrame frame = new JFrame( "TristateCheckBoxTest" );

        frame.getContentPane().setLayout( new GridLayout( 0, 1, 5, 5 ) );

        final TristateCheckBox swingBox = new TristateCheckBox( "Testing the tristate checkbox" );
        swingBox.setMnemonic( 'T' );

        frame.getContentPane().add( swingBox );
        frame.getContentPane().add( new JCheckBox( "The normal checkbox" ) );

        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );

        final TristateCheckBox winBox = new TristateCheckBox(
                "Testing the tristate checkbox",
                TristateCheckBox.SELECTED
                );
        frame.getContentPane().add( winBox );

        final JCheckBox winNormal = new JCheckBox( "The normal checkbox" );
        frame.getContentPane().add( winNormal );

        // wait for 3 seconds, then enable all check boxes
        new Thread() {
            {
                start();
            }

            @Override
            public void run()
            {
                try {
                    winBox.setEnabled( false );
                    winNormal.setEnabled( false );
                    Thread.sleep( 3000 );
                    winBox.setEnabled( true );
                    winNormal.setEnabled( true );
                }
                catch( InterruptedException ex ) {}
            }
        };

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack();
        //frame.show();
        frame.setVisible(true);
    }
}
