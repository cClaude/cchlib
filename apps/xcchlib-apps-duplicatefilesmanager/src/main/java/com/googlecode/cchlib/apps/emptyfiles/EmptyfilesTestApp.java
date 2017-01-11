package com.googlecode.cchlib.apps.emptyfiles;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;

@SuppressWarnings("squid:MaximumInheritanceDepth") // Because swing
public class EmptyfilesTestApp extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( EmptyfilesTestApp.class );

    private final JPanel contentPane;

    /**
     * Create the frame.
     */
    private EmptyfilesTestApp()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 450, 300 );

        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        final RemoveEmptyFilesJPanel testPanel = new RemoveEmptyFilesJPanel();

        this.contentPane.add( testPanel );
    }

    /**
     * Launch the application.
     *
     * @param args Parameter from CLI
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( () -> {
            try {
                final EmptyfilesTestApp frame = new EmptyfilesTestApp();
                frame.setVisible( true );
            }
            catch( final Exception cause ) {
                LOGGER.fatal( EmptyfilesTestApp.class, cause );
            }
        });
    }
}
