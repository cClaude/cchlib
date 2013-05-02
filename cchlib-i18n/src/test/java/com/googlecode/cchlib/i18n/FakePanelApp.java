package com.googlecode.cchlib.i18n;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FakePanelApp extends JFrame 
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    FakePanelApp frame = new FakePanelApp();
                    frame.setVisible( true );
                }
                catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Create the frame.
     */
    public FakePanelApp()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 450, 300 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( contentPane );
        
        JPanel panel = new FakePanel();
        contentPane.add(panel, BorderLayout.CENTER);
    }

}
