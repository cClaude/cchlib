// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

//NOT public
class PreferencesJDialog extends JDialog
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
            @Override public void windowOpened( final WindowEvent e ) {}
            @Override public void windowIconified( final WindowEvent e ) {}
            @Override public void windowDeiconified( final WindowEvent e ) {}
            @Override public void windowDeactivated( final WindowEvent e ) {}
            @Override public void windowClosing( final WindowEvent e ) {}
            @Override public void windowClosed( final WindowEvent e )
            {
                action.onCancel();
            }
            @Override
            public void windowActivated( final WindowEvent e ) {}
        } );
    }
}
