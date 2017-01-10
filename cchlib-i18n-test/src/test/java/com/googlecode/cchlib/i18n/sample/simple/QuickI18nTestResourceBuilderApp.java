package com.googlecode.cchlib.i18n.sample.simple;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class QuickI18nTestResourceBuilderApp
{
    private static final Logger LOGGER = Logger.getLogger( QuickI18nTestResourceBuilderApp.class );

    static I18nResourceBuilderResult runTest(
        final I18nAutoUpdatable frame,
        final AutoI18n          standardI18n,
        final Locale                locale,
        final AutoI18nConfig ...    configExtension
        ) throws IOException
    {
        final I18nResourceBuilder builder = I18nResourceBuilderFactory.newI18nResourceBuilder(
                standardI18n,
                locale,
                configExtension
                );

        final I18nAutoUpdatable[] i18nObjects = { frame };

        for( final I18nAutoUpdatable i18nObject : i18nObjects ) {
            builder.append( i18nObject );
        }

        final File outputFile = I18nResourceBuilderHelper.newOutputFile(
                frame.getClass()
                );

        builder.saveMissingResourceBundle( outputFile );

        final PrintStream               printStream = System.out; // Define output
        final I18nResourceBuilderResult result      = builder.getResult();

        I18nResourceBuilderHelper.fmtAll( printStream, result );

        return result;
    }

    public static void main( final String...args ) throws Exception
    {
        // Build frame
        final I18nAutoUpdatable frame = QuickI18nTestFrameApp.newQuickI18nTestFrame();
        final AutoI18n          i18n  = QuickI18nTestFrameApp.newAutoI18n();

        LOGGER.info( "I18nAutoUpdatable = " + frame );
        LOGGER.info( "AutoI18n          = " + i18n );

        runTest( frame, i18n, Locale.ENGLISH );

        LOGGER.info( "Done" );
    }
}
