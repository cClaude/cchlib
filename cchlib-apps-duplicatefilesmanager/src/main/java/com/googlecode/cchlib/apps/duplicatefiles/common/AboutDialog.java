package com.googlecode.cchlib.apps.duplicatefiles.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.Resources;

/**
 * Display about dialog
 */
public class AboutDialog extends JDialog 
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( AboutDialog.class );
    private final AboutPanel contentPanel;

    /**
     * Launch the application.
     */
    public static void showDialog( final DFToolKit dfToolKit )
    {
        try {
            AboutDialog dialog = new AboutDialog( dfToolKit.getResources() );
            dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            dialog.setVisible( true );
            }
        catch( Exception e ) {
            logger.error( "showDialog", e );
            }
    }

    /**
     * Create the dialog.
     * @param resources  
     */
    public AboutDialog( Resources resources )
    {
        contentPanel = new AboutPanel(resources );
        
        setBounds( 100, 100, 450, 300 );
        getContentPane().setLayout( new BorderLayout() );
        contentPanel.setLayout( new FlowLayout() );
        contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        getContentPane().add( contentPanel, BorderLayout.CENTER );
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
            getContentPane().add( buttonPane, BorderLayout.SOUTH );
            {
                JButton okButton = new JButton( "OK" );
                okButton.setActionCommand( "OK" );
                buttonPane.add( okButton );
                getRootPane().setDefaultButton( okButton );
            }
            {
                JButton cancelButton = new JButton( "Cancel" );
                cancelButton.setActionCommand( "Cancel" );
                buttonPane.add( cancelButton );
            }
        }
    }

}
