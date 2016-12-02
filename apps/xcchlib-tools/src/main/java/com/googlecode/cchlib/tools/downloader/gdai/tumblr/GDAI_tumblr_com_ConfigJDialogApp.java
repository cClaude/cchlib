package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.json.JSONHelperException;

@SuppressWarnings({"squid:S00101","squid:MaximumInheritanceDepth"})
public class GDAI_tumblr_com_ConfigJDialogApp extends JDialog
{
    // Not static
    @SuppressWarnings("squid:MaximumInheritanceDepth")
    private final class CustomConfigJPanel extends GDAI_tumblr_com_ConfigJPanel
    {
        private static final long serialVersionUID = 1L;
        private final Config config;

        private CustomConfigJPanel( final Config config, final Config config2 )
        {
            super( config );

            this.config = config2;
        }

        @Override
        void cancelClicked()
        {
            GDAI_tumblr_com_ConfigJDialogApp.this.dispose();
        }

        @Override
        void okClicked()
        {
            try {
                ConfigHelper.save( this.config );

                LOGGER.info( "Configuration saved." );
                }
            catch( final ConfigIOException e ) {
                LOGGER.error( "Can not save configuration", e );
                }

            GDAI_tumblr_com_ConfigJDialogApp.this.dispose();
        }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( GDAI_tumblr_com_ConfigJDialogApp.class );
    private final JPanel contentPane;

    /**
     * Create the frame.
     */
    public GDAI_tumblr_com_ConfigJDialogApp(
        final Frame     owner,
        final Config    config
        )
    {
        super( owner );

        setBounds( 100, 100, 450, 300 );
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        final GDAI_tumblr_com_ConfigJPanel panel = new CustomConfigJPanel( config, config );
        this.contentPane.add(panel, BorderLayout.CENTER);
    }

    /**
     * Launch the application.
     *
     * @throws JSONHelperException if configuration can not be loaded
     */
    public static void main( final String[] args ) throws JSONHelperException
    {
        final Config config = ConfigHelper.load();

        EventQueue.invokeLater( () -> runMain( config ) );
    }

    private static void runMain( final Config config )
    {
        try {
            final GDAI_tumblr_com_ConfigJDialogApp frame
                = new GDAI_tumblr_com_ConfigJDialogApp( null, config );

            frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
            frame.setVisible( true );
            }
        catch( final Exception e ) {
            LOGGER.error( "Can not load application", e );
            }
    }
}
