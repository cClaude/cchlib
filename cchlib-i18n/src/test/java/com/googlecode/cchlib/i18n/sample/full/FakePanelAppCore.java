// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.i18n.sample.full;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.EnumSet;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

public class FakePanelAppCore extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        start( false );
        start( true  );
    }

    public static void start( final boolean doI18n )
    {
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    FakePanelAppCore frame = new FakePanelAppCore( doI18n );
                    frame.setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    }
            }
        } );
    }

    /**
     * Create the frame.
     */
    public FakePanelAppCore( boolean doI18n )
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 800, 400 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( contentPane );

        FakePanel panel = new FakePanel();

        if( doI18n ) {
            Locale locale = Locale.ENGLISH;

            EnumSet<AutoI18nConfig> config                   = null;
            I18nResourceBundleName  i18nResourceBundleName   = createI18nResourceBundleName();
            AutoI18nCore            autoI18n                 = AutoI18nCoreFactory.createAutoI18nCore( config, i18nResourceBundleName, locale );

            //autoI18n.setLocale( locale );
            panel.performeI18n( autoI18n );
            }

        contentPane.add(panel, BorderLayout.CENTER);
    }

    public static I18nResourceBundleName createI18nResourceBundleName( final String messageBundleBaseName)
    {
        Class<FakePanelAppCore> packageMessageBundleBase = FakePanelAppCore.class;

        return new DefaultI18nResourceBundleName(packageMessageBundleBase, messageBundleBaseName);
    }

    public static I18nResourceBundleName createI18nResourceBundleName()
    {
        final String messageBundleBaseName = "MessagesBundle";

        return createI18nResourceBundleName( messageBundleBaseName );
    }
}
