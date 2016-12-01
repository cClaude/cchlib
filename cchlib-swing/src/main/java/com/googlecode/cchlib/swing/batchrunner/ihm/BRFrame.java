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
import com.googlecode.cchlib.swing.batchrunner.BRExitable;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;

/**
 * Default scalable {@link JFrame} for {@link BRPanel}
 *
 * @since 4.1.8
 */
@SuppressWarnings({
    "squid:MaximumInheritanceDepth" // Because swing
    })
public class BRFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( BRFrame.class );

    private BRPanelLocaleResources panelLocaleResources;
    private JPanel  contentPane;
    private BRPanel panel;
    private transient BRExitable exitFunction;
    private BRXLocaleResources xLocaleResources;

    @SuppressWarnings("unused") // for windows builder only
    private BRFrame()
    {
        createFrame( null );
    }

    /**
     * Prepare the frame.
     *
     * @param builder Builder to define Frame
     *
     * @see #createFrame(BRActionListener)
     */
    public BRFrame( final BRFrameBuilder builder )
    {
        this(
            builder.getBRPanelLocaleResources(),
            builder.getBRXLocaleResources(),
            builder.getBRExitable()
            );
    }

    /**
     * Prepare the frame.
     * *
     * @param panelLocaleResources
     * @param xLocaleResources
     * @param exitFunction
     *
     * @see #createFrame(BRActionListener)
     */
    public BRFrame(
        final BRPanelLocaleResources panelLocaleResources,
        final BRXLocaleResources     xLocaleResources,
        final BRExitable             exitFunction
        )
    {
        this.panelLocaleResources = panelLocaleResources;
        this.xLocaleResources     = xLocaleResources;
        this.exitFunction         = exitFunction;

        setTitle( xLocaleResources.getFrameTitle() );
        setIconImage( xLocaleResources.getFrameIconImage() );
    }

    /**
     * Create the frame.
     *
     * @param actionListener
     */
    public final void createFrame( final BRActionListener actionListener )
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
    public void setLayoutForContentPanel( final JPanel contentPane )
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
    public void addToContentPanel( final BRPanel panel )
    {
        this.contentPane.add(panel, BorderLayout.CENTER);
    }

    /**
     * This method is design to be use with a custom layout.
     * <BR>
     * Example :
     * <pre><code>
     *   {@link #addToContentPanel}( panel, BorderLayout.WEST );
     * </code></pre>
     *
     * @param comp the component to be added
     * @param constraints an object expressing layout constraints for this component
     *
     * @see #setLayoutForContentPanel(JPanel)
     * @see #addToContentPanel(BRPanel)
     */
    public void addToContentPanel( final Component comp, final Object constraints )
    {
        this.contentPane.add( comp, constraints );
    }

    private class SBRWindowListener implements WindowListener
    {
        @Override public void windowOpened( final WindowEvent event ) {
            /* ignore event */
            }
        @Override public void windowClosed( final WindowEvent event ) {
            /* ignore event */
            }
        @Override public void windowIconified( final WindowEvent event ) {
            /* ignore event */
            }
        @Override public void windowDeiconified( final WindowEvent event ) {
            /* ignore event */
            }
        @Override public void windowActivated( final WindowEvent event ) {
            /* ignore event */
            }
        @Override public void windowDeactivated( final WindowEvent event ) {
            /* ignore event */
            }

        @Override
        public void windowClosing( final WindowEvent event )
        {
            LOGGER.info( "windowClosing: " + event );

            handleWindowClosing( event );
        }
    }

    @SuppressWarnings({
        "squid:S1172" // event is not use (yet ?)
        })
    public void handleWindowClosing( final WindowEvent event )
    {
        if( (this.panel == null) || ! this.panel.getSBRActionListener().isRunning() ) {
            systemExit();
            }
        else {
            //Custom button text
            final Object[] options = {
                this.panelLocaleResources.getTextExitRequestYes(),
                this.panelLocaleResources.getTextExitRequestNo()
                };

            final int n = JOptionPane.showOptionDialog(
                this,
                this.panelLocaleResources.getTextExitRequestMessage(),
                this.panelLocaleResources.getTextExitRequestTitle(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
                );

            if( n == 0 ) {
                systemExit();
                }
            }
    }

    private void systemExit()
    {
        this.exitFunction.exit();
    }

    public String getProgressMonitorMessage()
    {
        return this.xLocaleResources.getProgressMonitorMessage();
    }
}
