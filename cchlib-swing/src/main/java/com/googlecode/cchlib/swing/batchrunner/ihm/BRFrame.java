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
import com.googlecode.cchlib.swing.batchrunner.misc.BRLocaleResourcesAgregator;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;

/**
 * Default scalable {@link JFrame} for {@link BRPanel}
 * 
 * @since 4.1.8
 */
public class BRFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( BRFrame.class );

    private BRPanelLocaleResources panelLocaleResources;
    private JPanel  contentPane;
    private BRPanel panel;

    @SuppressWarnings("unused") // for windows builder only
    private BRFrame()
    {
        createFrame( null );
    }

    /**
     * Prepare the frame.
     * *
     * @param panelLocaleResources
     * @param xLocaleResources
     *
     * @see #createFrame(BRActionListener)
     */
    public BRFrame( BRPanelLocaleResources panelLocaleResources, BRXLocaleResources xLocaleResources )
    {
        this.panelLocaleResources = panelLocaleResources;

        setTitle( xLocaleResources.getFrameTitle() );
        setIconImage( xLocaleResources.getFrameIconImage() );
    }
    
    /**
     * Prepare the frame.
     * *
     * @param localeResourcesAgregator
     *
     * @see #createFrame(BRActionListener)
     */
    public BRFrame( BRLocaleResourcesAgregator localeResourcesAgregator )
    {
        this( localeResourcesAgregator, localeResourcesAgregator );
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

        setLayoutForContentPanel( this.contentPane );
        setContentPane( this.contentPane );

        this.panel = new BRPanel( actionListener, this.panelLocaleResources );
        //$hide>>$
        actionListener.setSBRPanel( this.panel );
        //$hide<<$
        addToContentPanel( this.panel );
    }

    /**
     * This method is design to be override for a custom layout
     *
     * @param contentPane JPanel build by {@link #createFrame(BRActionListener)}
     * @see #addToContentPanel(BRPanel)
     * @see #addToContentPanel(Component, Object)
     */
    public void setLayoutForContentPanel( JPanel contentPane )
    {
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );
    }

    /**
     * This method is design to be override for a custom layout
     *
     * @param panel SBRPanel build by {@link #createFrame(BRActionListener)}
     * @see #setLayoutForContentPanel(JPanel)
     * @see #addToContentPanel(Component, Object)
     */
    public void addToContentPanel( BRPanel panel )
    {
        this.contentPane.add(panel, BorderLayout.CENTER);
    }

    /**
     * This method is design to be use with a custom layout.
     * <br/>
     * Example :
     * <pre><code>
     *   {@link #addToContentPanel}( panel, BorderLayout.WEST );
     * </code></pre>
     *
     * @param comp the component to be added
     * @see #setLayoutForContentPanel(JPanel)
     * @see #addToContentPanel(BRPanel)
     */
    public void addToContentPanel( Component comp, Object constraints )
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
            LOGGER.info( "windowClosing: " + event );

            handleWindowClosing( event );
        }
    }

    public void handleWindowClosing( WindowEvent event )
    {
        if( (this.panel == null) || ! this.panel.getSBRActionListener().isRunning() ) {
            systemExit();
            }
        else {
            //Custom button text
            Object[] options = { 
                this.panelLocaleResources.getTextExitRequestYes(), 
                this.panelLocaleResources.getTextExitRequestNo() 
                };

            int n = JOptionPane.showOptionDialog(
                this,
                this.panelLocaleResources.getTextExitRequestMessage(),
                this.panelLocaleResources.getTextExitRequestTitle(),
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
