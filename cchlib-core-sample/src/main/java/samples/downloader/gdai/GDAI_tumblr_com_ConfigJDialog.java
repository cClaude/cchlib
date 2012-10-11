package samples.downloader.gdai;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 *
 */
public class GDAI_tumblr_com_ConfigJDialog extends JDialog 
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        final GDAI_tumblr_com_Config config = new GDAI_tumblr_com_Config();
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                try {
                    GDAI_tumblr_com_ConfigJDialog frame 
                        = new GDAI_tumblr_com_ConfigJDialog( null, config );
                    
                    frame.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
                    frame.setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GDAI_tumblr_com_ConfigJDialog( 
        final Frame                     owner,
        final GDAI_tumblr_com_Config    config
        )
    {
        super( owner );
        
        setBounds( 100, 100, 450, 300 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( contentPane );
        
        GDAI_tumblr_com_ConfigJPanel panel = new GDAI_tumblr_com_ConfigJPanel( config )
        {
            private static final long serialVersionUID = 1L;
            
            @Override
            void cancelClicked()
            {
                GDAI_tumblr_com_ConfigJDialog.this.dispose();
            }
            @Override
            void okClicked()
            {
                try {
                    config.save();
                    }
                catch( IOException e ) {
                    // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                GDAI_tumblr_com_ConfigJDialog.this.dispose();
            }
            
        };
        contentPane.add(panel, BorderLayout.CENTER);
    }

}
