package com.googlecode.cchlib.i18n.sample.full;

import java.awt.BorderLayout;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;
import com.googlecode.cchlib.i18n.resources.I18nResourceFactory;

/**
 * See {@link FakePanelAppTest} for JUnit test
 */
public class FakePanelApp extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FakePanelApp.class );

    static final String MESSAGES_BUNDLE = "MessagesBundle";

    private final JPanel contentPane;
    private final FakePanel fakePanel;

    private FakePanelApp()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 800, 400 );
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        this.fakePanel = new FakePanel();

        this.contentPane.add( this.fakePanel, BorderLayout.CENTER );
    }

    public FakePanel getFakePanel()
    {
        return this.fakePanel;
    }

    private void doI18n( final String messageBundleFilenameBase )
    {
        final Locale   locale   = Locale.ENGLISH;
        final AutoI18n autoI18n = newAutoI18n( messageBundleFilenameBase, locale );

        this.fakePanel.performeI18n( autoI18n );
    }

    static AutoI18n newAutoI18n( final String messageBundleFilenameBase, final Locale locale )
    {
        final I18nResource i18nResource = I18nResourceFactory.newI18nResourceBundle(
                FakePanelApp.class.getPackage(),
                messageBundleFilenameBase,
                locale
                );
        return AutoI18nFactory.newAutoI18n( i18nResource );
    }

    /**
     * Launch the application.
     *
     * @param args Ignored
     */
    public static void main( final String[] args )
    {
        try {
            start( new RunnableSupplier<>( () -> newFakePanelApp_DO_I18N() ) );
        }
        catch( final ExecutionException cause ) {
            LOGGER.fatal( "invokeLater", cause );
        }

        try {
            start( new RunnableSupplier<>( () -> newFakePanelApp_NO_I18N() ) );
        }
        catch( final ExecutionException cause ) {
            LOGGER.fatal( "invokeLater", cause );
        }
    }

    static FakePanel start( final RunnableSupplier<FakePanelApp> runnableSupplier ) throws ExecutionException
    {
        final FakePanelApp result = RunnableSupplier.invokeLater( runnableSupplier, 1, TimeUnit.SECONDS );

        return result.getFakePanel();
    }

    static FakePanelApp newFakePanelApp_DO_I18N()
    {
        final FakePanelApp frame = new FakePanelApp();

        frame.doI18n( MESSAGES_BUNDLE );

        frame.setVisible( true );

        return frame;
    }

    static FakePanelApp newFakePanelApp_NO_I18N()
    {
        final FakePanelApp frame = new FakePanelApp();

        frame.setVisible( true );

        return frame;
    }
}
