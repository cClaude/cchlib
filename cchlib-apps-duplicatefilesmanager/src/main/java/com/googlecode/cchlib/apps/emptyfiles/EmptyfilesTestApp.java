// $codepro.audit.disable
package com.googlecode.cchlib.apps.emptyfiles;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EmptyfilesTestApp extends JFrame
{
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( () -> {
            try {
                EmptyfilesTestApp frame = new EmptyfilesTestApp();
                frame.setVisible( true );
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public EmptyfilesTestApp()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 450, 300 );
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        //DFToolKit fake = new FakeDFToolKit();
        RemoveEmptyFilesJPanel testPanel = new RemoveEmptyFilesJPanel();
        this.contentPane.add( testPanel );
    }
}
