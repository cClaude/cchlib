// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

public class PreferencesJDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private final PreferencesJPanel contentPanel;

    /**
     * Create the dialog.
     * @param action
     * @param initParams
     */
    public PreferencesJDialog(
       final PreferencesDefaultsParametersValues initParams,
       final AbstractPreferencesAction           action
       )
    {
        setBounds( 100, 100, 450, 300 );
        getContentPane().setLayout( new BorderLayout() );
        contentPanel = new PreferencesJPanel( initParams, action );
        contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        getContentPane().add( contentPanel );

        action.setWindow( this );

        this.addWindowListener( new WindowListener() {
            @Override public void windowOpened( WindowEvent e ) {}
            @Override public void windowIconified( WindowEvent e ) {}
            @Override public void windowDeiconified( WindowEvent e ) {}
            @Override public void windowDeactivated( WindowEvent e ) {}
            @Override public void windowClosing( WindowEvent e ) {}
            @Override public void windowClosed( WindowEvent e )
            {
                action.onCancel();
            }
            @Override
            public void windowActivated( WindowEvent e ) {}
        } );
    }
}
