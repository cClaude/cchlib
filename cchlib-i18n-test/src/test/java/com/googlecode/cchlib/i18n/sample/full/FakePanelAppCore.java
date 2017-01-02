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
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory;

/**
 * See {@link FakePanelAppCoreTest} for JUnit test
 */
public class FakePanelAppCore extends JFrame
{
    private enum Mode { DO_I18N, NO_I18N }
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    private FakePanelAppCore( final Mode mode )
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 800, 400 );
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        final FakePanel panel = new FakePanel();

        if( mode == Mode.DO_I18N ) {
            final Locale locale = Locale.ENGLISH;

            final EnumSet<AutoI18nConfig> config                   = null;
            final I18nResourceBundleName  i18nResourceBundleName   = createI18nResourceBundleName();
            final AutoI18nCore            autoI18n                 = AutoI18nCoreFactory.newAutoI18nCore( config, i18nResourceBundleName, locale );

            //autoI18n.setLocale( locale );
            panel.performeI18n( autoI18n );
            }

        this.contentPane.add(panel, BorderLayout.CENTER);
    }

    public static I18nResourceBundleName createI18nResourceBundleName( final String messageBundleBaseName)
    {
        final Class<FakePanelAppCore> packageMessageBundleBase = FakePanelAppCore.class;

        return I18nResourceBundleNameFactory.newI18nResourceBundleName(
                packageMessageBundleBase,
                messageBundleBaseName
                );
    }

    public static I18nResourceBundleName createI18nResourceBundleName()
    {
        final String messageBundleBaseName = "MessagesBundle";

        return createI18nResourceBundleName( messageBundleBaseName );
    }

    /**
     * Launch the application.
     *
     * @param args Ignored
     */
    public static void main( final String[] args )
    {
        start( Mode.NO_I18N );
        start( Mode.DO_I18N );
    }

    public static void start( final Mode mode )
    {
        EventQueue.invokeLater( () -> {
            try {
                final FakePanelAppCore frame = new FakePanelAppCore( mode );
                frame.setVisible( true );
            }
            catch( final Exception e ) {
                e.printStackTrace();
            }
        });
    }
}
