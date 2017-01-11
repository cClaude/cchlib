package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

//NOT public
@SuppressWarnings("squid:MaximumInheritanceDepth")
class PreferencesJDialog extends JDialog implements I18nAutoUpdatable
{
    private final class MyWindowListener extends WindowAdapter
    {
        private final AbstractPreferencesAction action;

        private MyWindowListener( final AbstractPreferencesAction action )
        {
            this.action = action;
        }

        @Override public void windowClosed( final WindowEvent event )
        {
            this.action.onCancel();
        }
    }

    private static final long serialVersionUID = 1L;
    private final PreferencesJPanel contentPanel;

    public PreferencesJDialog(
       final PreferencesValues         initParams,
       final AbstractPreferencesAction action
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

    @Override
    public void performeI18n( final AutoI18n autoI18n )
    {
        this.contentPanel.performeI18n( autoI18n );
    }
}
