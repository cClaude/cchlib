package com.googlecode.cchlib.apps.duplicatefiles.common;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * 
 */
public class AboutInternalFrame extends JInternalFrame 
{
    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                try {
                    AboutInternalFrame frame = new AboutInternalFrame();
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
    public AboutInternalFrame()
    {
        setBounds( 100, 100, 450, 300 );
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);

    }

}
