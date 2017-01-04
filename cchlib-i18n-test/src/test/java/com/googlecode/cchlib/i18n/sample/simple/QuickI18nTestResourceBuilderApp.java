package com.googlecode.cchlib.i18n.sample.simple;

import java.io.File;
import java.io.PrintStream;
import java.util.Locale;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class QuickI18nTestResourceBuilderApp
{
    private static final Logger LOGGER = Logger.getLogger( QuickI18nTestResourceBuilderApp.class );

    static I18nResourceBuilderResult runTest(
        final I18nAutoCoreUpdatable frame,
        final AutoI18nCore          standardI18n
        ) throws I18nPrepException
    {
        final I18nResourceBuilder builder = I18nResourceBuilderFactory.newI18nResourceBuilder(
                standardI18n,
                Locale.ENGLISH,
                AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS
                );

        final I18nAutoCoreUpdatable[] i18nObjects = { frame };

        for( final I18nAutoCoreUpdatable i18nObject : i18nObjects ) {
            builder.append( i18nObject );
        }

        final File                      outputFile = I18nResourceBuilderHelper.newOutputFile( frame.getClass() );
        final I18nResourceBuilderResult result     = builder.buildResult( outputFile );

        // Define output
        final PrintStream notUsePrintStream = System.out;

        I18nResourceBuilderHelper.fmtAll( notUsePrintStream, result );

        return result;
    }

    public static void main( final String...args ) throws Exception
    {
        // Build frame
        final I18nAutoCoreUpdatable frame = QuickI18nTestFrame.newQuickI18nTestFrame();
        final AutoI18nCore          i18n  = QuickI18nTestFrame.newAutoI18nCore();

        LOGGER.info( "I18nAutoCoreUpdatable = " + frame );
        LOGGER.info( "AutoI18nCore          = " + i18n );

        runTest( frame, i18n );

        LOGGER.info( "Done" );
    }
}
