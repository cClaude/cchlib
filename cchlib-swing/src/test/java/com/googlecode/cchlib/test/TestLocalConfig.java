package com.googlecode.cchlib.test;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 */
public class TestLocalConfig
    extends JFrame
        implements WindowListener
{

    private static final long serialVersionUID = 1L;

    private TestLocalConfig()
    {
        setTitle("TestLocalConfig");
        setSize( 320, 240 );

        final TestLocalConfigPanel contentPanel = new TestLocalConfigPanel( this );
        getContentPane().add( contentPanel );

        addWindowListener( this );
    }

    /**
     * Main entry of the class.
     * Note: This class is only created so that you can easily preview the result at runtime.
     * It is not expected to be managed by the designer.
     * You can modify it as you like.
     */
    public static void main( final String[] args )
    {
        SwingUtilities.invokeLater( ( ) -> {
            final TestLocalConfig frame = new TestLocalConfig();
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.setTitle( "TestLocalConfig" );
            frame.getContentPane().setPreferredSize( frame.getSize() );
            frame.pack();
            frame.setLocationRelativeTo( null );
            frame.setVisible( true );
        } );
    }

    @Override // WindowListener
    public void windowOpened( final WindowEvent e )
    {
        // Not use
    }
    @Override // WindowListener
    public void windowClosing( final WindowEvent e )
    {
        // Not use
    }
    @Override // WindowListener
    public void windowClosed( final WindowEvent e )
    {
        System.exit( 0 );
    }
    @Override // WindowListener
    public void windowIconified( final WindowEvent e )
    {
        // Not use
    }
    @Override // WindowListener
    public void windowDeiconified( final WindowEvent e )
    {
        // Not use
    }
    @Override // WindowListener
    public void windowActivated( final WindowEvent e )
    {
        // Not use
    }
    @Override // WindowListener
    public void windowDeactivated( final WindowEvent e )
    {
        // Not use
    }
}
