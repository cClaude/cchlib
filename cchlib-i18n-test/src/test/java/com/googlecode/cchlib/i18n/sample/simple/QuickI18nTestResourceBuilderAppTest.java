package com.googlecode.cchlib.i18n.sample.simple;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class QuickI18nTestResourceBuilderAppTest
{
    private static final Logger LOGGER = Logger.getLogger( QuickI18nTestResourceBuilderAppTest.class );

    @Test
    public void quick_test() throws IOException
    {
        // Build frame
        final I18nAutoCoreUpdatable frame = QuickI18nTestFrameApp.newQuickI18nTestFrame();
        final AutoI18nCore          i18n  = QuickI18nTestFrameApp.newAutoI18nCore();

        LOGGER.info( "I18nAutoCoreUpdatable = " + frame );
        LOGGER.info( "AutoI18nCore          = " + i18n );

        final I18nResourceBuilderResult result = QuickI18nTestResourceBuilderApp.runTest( frame, i18n, Locale.ENGLISH, AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS );

        assertThat( result.getLocalizedFields() ).as( "LocalizedFields" ).hasSize( 3 );
        assertThat( result.getIgnoredFields() ).as( "IgnoredFields" ).hasSize( 3 );
        assertThat( result.getMissingProperties() ).as( "MissingProperties" ).hasSize( 1 );

        assertThat( result.getUnusedProperties() ).as( "UnusedProperties values" )
            .containsKey( "test.key.is.not.use" )
            .containsValue( "Testing unused (key,value)" )
            .hasSize( 1 );
    }

    @Test
    public void quick_FR_test() throws IOException
    {
        // Build frame
        final I18nAutoCoreUpdatable frame = QuickI18nTestFrameApp.newQuickI18nTestFrame();
        final AutoI18nCore          i18n  = QuickI18nTestFrameApp.newAutoI18nCore();

        LOGGER.info( "I18nAutoCoreUpdatable = " + frame );
        LOGGER.info( "AutoI18nCore          = " + i18n );

        // Show that since resource bundle use the fall back resource, changing locale has
        // no effect.
        final I18nResourceBuilderResult result = QuickI18nTestResourceBuilderApp.runTest( frame, i18n, Locale.FRENCH );

        assertThat( result.getLocalizedFields() ).as( "LocalizedFields" ).hasSize( 3 );
        assertThat( result.getIgnoredFields() ).as( "IgnoredFields" ).hasSize( 3 );
        assertThat( result.getMissingProperties() ).as( "MissingProperties" ).hasSize( 1 );

        assertThat( result.getUnusedProperties() ).as( "UnusedProperties values" )
            .containsKey( "test.key.is.not.use" )
            .containsValue( "Testing unused (key,value)" )
            .hasSize( 1 );
    }
}
