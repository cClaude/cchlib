package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;

/**
 *
 * @since 4.1.8
 */
public class BRFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( BRFrame.class );

    private BRLocaleResources resources;
    private JPanel   contentPane;
    private BRPanel panel;

    @SuppressWarnings("unused") // for windows builder only
    private BRFrame()
    {
        createFrame( null );
    }

    /**
     * Prepare the frame.
     * *
     * @param resources
     *
     * @see #createFrame(BRActionListener)
     */
    public BRFrame( BRLocaleResources resources )
    {
        this.resources = resources;
    }

    /**
     * Create the frame.
     *
     * @param actionListener
     */
    public final void createFrame( BRActionListener actionListener )
    {
        setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
        this.addWindowListener( new SBRWindowListener() );
        setSize( 450, 300 );
        this.contentPane = new JPanel();

        contentPanelSetLayout( this.contentPane );
        setContentPane( this.contentPane );

        this.panel = new BRPanel( actionListener, resources );
        //$hide>>$
        actionListener.setSBRPanel( this.panel );
        //$hide<<$
        contentPanelAdd( this.panel );
    }

    /**
     * This method is design to be override for a custom layout
     *
     * @param contentPane JPanel build by {@link #createFrame(BRActionListener)}
     * @see #contentPanelAdd(BRPanel)
     * @see #contentPanelAdd(Component, Object)
     */
    public void contentPanelSetLayout( JPanel contentPane )
    {
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );
    }

    /**
     * This method is design to be override for a custom layout
     *
     * @param panel SBRPanel build by {@link #createFrame(BRActionListener)}
     * @see #contentPanelSetLayout(JPanel)
     * @see #contentPanelAdd(Component, Object)
     */
    public void contentPanelAdd( BRPanel panel )
    {
        this.contentPane.add(panel, BorderLayout.CENTER);
    }

    /**
     * This method is design to be use with a custom layout
     *
     * @param comp the component to be added
     * @see #contentPanelSetLayout(JPanel)
     * @see #contentPanelAdd(BRPanel)
     */
    public void contentPanelAdd( Component comp, Object constraints )
    {
        this.contentPane.add( comp, constraints );
    }

    private class SBRWindowListener implements WindowListener
    {
        @Override public void windowOpened( WindowEvent event ) {}
        @Override public void windowClosed( WindowEvent event ) {}
        @Override public void windowIconified( WindowEvent event ) {}
        @Override public void windowDeiconified( WindowEvent event ) {}
        @Override public void windowActivated( WindowEvent event ) {}
        @Override public void windowDeactivated( WindowEvent event ) {}

        @Override
        public void windowClosing( WindowEvent event )
        {
            logger.info( "windowClosing: " + event );

            handleWindowClosing( event );
        }
    }

    public void handleWindowClosing( WindowEvent event )
    {
        if( this.panel == null || ! this.panel.getSBRActionListener().isRunning() ) {
            systemExit();
            }
        else {
            //Custom button text
            Object[] options = { this.resources.getTextExitRequestYes(), this.resources.getTextExitRequestNo() };

            int n = JOptionPane.showOptionDialog(
                this,
                this.resources.getTextExitRequestMessage(),
                this.resources.getTextExitRequestTitle(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

            if( n == 0 ) {
                systemExit();
                }
            }
    }

    private void systemExit()
    {
        System.exit( 0 );
    }
}
