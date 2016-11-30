// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

//NOT public
@SuppressWarnings("squid:MaximumInheritanceDepth")
class PreferencesJDialog extends JDialog
{
    private final class MyWindowListener extends WindowAdapter
    {
        private final AbstractPreferencesAction action;

        private MyWindowListener( final AbstractPreferencesAction action )
        {
            this.action = action;
        }

        @Override public void windowClosed( final WindowEvent e )
        {
            this.action.onCancel();
        }
    }

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
        this.contentPanel = new PreferencesJPanel( initParams, action );
        this.contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        getContentPane().add( this.contentPanel );

        action.setWindow( this );

        this.addWindowListener( new MyWindowListener( action ) );
    }
}
