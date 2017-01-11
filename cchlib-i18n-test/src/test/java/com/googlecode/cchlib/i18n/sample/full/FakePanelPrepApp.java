package com.googlecode.cchlib.i18n.sample.full;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class FakePanelPrepApp
{
    private static final Logger LOGGER = Logger.getLogger( FakePanelPrepApp.class );

    private FakePanelPrepApp()
    {
    }

    static I18nResourceBuilderResult start(
        final I18nAutoUpdatable[] i18nConteners,
        final Locale              locale,
        final String              messagesBundle,
        final File                outputFile
        ) throws IOException
    {
        final I18nResourceBuilder builder = I18nResourceBuilderFactory.newI18nResourceBuilder(
                FakePanelApp.newAutoI18n( messagesBundle, locale ),
                locale,
                AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS
                );

        for( final I18nAutoUpdatable i18nAutoUpdatable : i18nConteners ) {
            builder.append( i18nAutoUpdatable );
        }

        builder.saveMissingResourceBundle( outputFile );

        return builder.getResult();
    }

    public static void main( final String...args ) throws Exception
    {
        start();

        LOGGER.info( "Done" );
    }

    public static I18nResourceBuilderResult start() throws ExecutionException, IOException
    {
        final String messagesBundle = FakePanelApp.MESSAGES_BUNDLE;
        final File   outputFile     = I18nResourceBuilderHelper.newOutputFile( FakePanelPrepApp.class.getPackage() );

       final RunnableSupplier<I18nResourceBuilderResult> runnableSupplier
            = new RunnableSupplier<>( () -> newFakePanelApp( messagesBundle, outputFile ) );

        return RunnableSupplier.invokeLater( runnableSupplier, 1, TimeUnit.SECONDS );
    }

    public static I18nResourceBuilderResult newFakePanelApp(
        final String messagesBundle,
        final File   outputFile
        )
    {
        try {
            return newFakePanelApp_IOException( messagesBundle, outputFile );
        }
        catch( final IOException e ) {
            throw new RuntimeException( e );
        }
     }

    private static I18nResourceBuilderResult newFakePanelApp_IOException(
        final String messagesBundle,
        final File   outputFile
        ) throws IOException
    {
        // Build frame
        final FakePanel frameOrPanel = new FakePanel();

        // Other frames,panel,... if any
        final I18nAutoUpdatable[] i18nConteners = { frameOrPanel };

        final Locale locale         = Locale.ENGLISH;

        final I18nResourceBuilderResult result = start( i18nConteners, locale, messagesBundle, outputFile );

        I18nResourceBuilderHelper.fmtAll( System.out, result );

        return result;
    }
}
