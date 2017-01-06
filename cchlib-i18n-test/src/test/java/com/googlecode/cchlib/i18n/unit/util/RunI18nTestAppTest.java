package com.googlecode.cchlib.i18n.unit.util;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated;
import com.googlecode.cchlib.i18n.unit.parts.AutoI18nBasicInterfacePart;
import com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePart;
import com.googlecode.cchlib.i18n.unit.parts.I18nDefaultPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nForcedPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nStringPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextIgnorePart;
import com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPanePart;
import com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReference;
import com.googlecode.cchlib.i18n.unit.strings.errors.I18nStringWithErrorsTestReference;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

/**
 * Integration test for I18n
 */
public class RunI18nTestAppTest
{
    private static final Logger LOGGER = Logger.getLogger( RunI18nTestAppTest.class );

    // see REF.properties
    private static final int NUMBERS_OF_UNUSED_PROPERTIES = 3;
    private static final int NUMBERS_OF_SWING_PROPERTIES  = 30;

    // When this class is modify, you must test both configuration
    // (remove "& false" from comment)
    // to ensure all CI will be able to have same result
    private static final boolean DO_SWING = SafeSwingUtilities.isSwingAvailable() /*& false*/;

    private static Iterable<TestReferenceDeprecated> getTests()
    {
        final ArrayList<TestReferenceDeprecated> list = new ArrayList<>();

        if( DO_SWING ) {
            list.add( new I18nBaseNamePart() );
            list.add( new I18nDefaultPart() );
            list.add( new I18nForcedPart() );
            list.add( new I18nToolTipTextIgnorePart() );
            list.add( new I18nToolTipTextPart() );
            list.add( new I18nToolTipText_for_JTabbedPanePart() );
        }

        list.add( new AutoI18nBasicInterfacePart() );
        list.add( new I18nStringPart() );
        list.add( new I18nStringTestReference() );
        list.add( new I18nStringWithErrorsTestReference() );

        return list;
    }

    @Test
    public void runPrepTest() throws FileNotFoundException, IOException, I18nPrepException
    {
        final PrepTestPart   prepTest = TestUtils.newPrepTestPart();
        final Iterable<TestReferenceDeprecated> tests    = getTests();

        int syntaxeExceptionCount = 0;
        int missingResourceExceptionCount = 0;

        // Value should not change (check before)
        for( final TestReferenceDeprecated test : tests ) {
            test.afterPrepTest();

            syntaxeExceptionCount += test.getSyntaxeExceptionCount();
            missingResourceExceptionCount += test.getMissingResourceExceptionCount();
            }

        for( final TestReferenceDeprecated test : tests ) {
            test.beforePrepTest( prepTest );
            }

        final I18nPrepResult result = TestUtils.runPrepTest( prepTest );

        // Value should not change (check after)
        for( final TestReferenceDeprecated test : tests ) {
            test.afterPrepTest();
            }

        final AutoI18nExceptionCollector collector = prepTest.getAutoI18nExceptionHandlerCollector();

        LOGGER.info( "I18nSyntaxeException = " + collector.getI18nSyntaxeExceptionCollector().size() );
        LOGGER.info( "IllegalAccessException = " + collector.getIllegalAccessExceptionCollector().size() );
        LOGGER.info( "IllegalArgumentException = " + collector.getIllegalArgumentExceptionCollector().size() );
        LOGGER.info( "InvocationTargetException = " + collector.getInvocationTargetExceptionCollector().size() );
        LOGGER.info( "MissingKeyException = " + collector.getMissingKeyExceptionCollector().size() );
        LOGGER.info( "MissingResourceException = " + collector.getMissingResourceExceptionCollector().size() );
        LOGGER.info( "NoSuchMethodException = " + collector.getNoSuchMethodExceptionCollector().size() );
        LOGGER.info( "MethodProviderSecurityException = " + collector.getMethodProviderSecurityExceptionCollector().size() );
        LOGGER.info( "SecurityException = " + collector.getSecurityExceptionCollector().size() );
        LOGGER.info( "SetFieldException = " + collector.getSetFieldExceptionCollector().size() );

        assertThat( collector.getI18nSyntaxeExceptionCollector().size() ).isEqualTo( syntaxeExceptionCount );

        assertThat( collector.getIllegalAccessExceptionCollector().size() ).isZero();
        assertThat( collector.getIllegalArgumentExceptionCollector().size() ).isZero();
        assertThat( collector.getInvocationTargetExceptionCollector().size() ).isZero();
        assertThat( collector.getMissingKeyExceptionCollector().size() ).isZero();

        assertThat( collector.getMissingResourceExceptionCollector().size() ).isEqualTo( missingResourceExceptionCount );

        assertThat( collector.getNoSuchMethodExceptionCollector().size() ).isZero();
        assertThat( collector.getMethodProviderSecurityExceptionCollector().size() ).isZero();
        assertThat( collector.getSecurityExceptionCollector().size() ).isZero();
        assertThat( collector.getSetFieldExceptionCollector().size() ).isZero();

        final Map<String, String>  computeEntriesExistingInPropertiesMap = computeEntriesExistingInPropertiesMap();
        final int                  existingSize = computeEntriesExistingInPropertiesMap.size();
        LOGGER.info( "existing entries in properties = " + existingSize );

        assertThat( existingSize ).isPositive();

        final Map<String,String> entriesCreatedByPrepMap = computeEntriesCreatedByPrepMap( result );
        final int                createdSize = entriesCreatedByPrepMap.size();
        LOGGER.info( "created entries by Prep process = " + createdSize );

        final SetView<String> commonsValuesSet = Sets.intersection( computeEntriesExistingInPropertiesMap.keySet(), entriesCreatedByPrepMap.keySet() );

        final SetView<String> notFoundByPrepSet       = Sets.difference( computeEntriesExistingInPropertiesMap.keySet(), commonsValuesSet );
        final SetView<String> notFoundInPropertiesSet = Sets.difference( entriesCreatedByPrepMap.keySet(), commonsValuesSet );

        display( "existing entries in properties", computeEntriesExistingInPropertiesMap.keySet() );
        display( "created entries by Prep process", entriesCreatedByPrepMap.keySet() );

        display( "commons values", commonsValuesSet );

        display( "Not found in properties", notFoundInPropertiesSet );
        display( "Not found by Prep process", notFoundByPrepSet );

        LOGGER.info( "existing entries in properties = " + existingSize );
        LOGGER.info( "created entries by Prep process = " + createdSize );

        if( DO_SWING ) {
            assertThat( createdSize )
                .isEqualTo( existingSize - NUMBERS_OF_UNUSED_PROPERTIES );
        } else {
            assertThat( createdSize )
                .isEqualTo(
                    existingSize - NUMBERS_OF_UNUSED_PROPERTIES - NUMBERS_OF_SWING_PROPERTIES
                    );
        }

        LOGGER.info( "ALL runPrepTest() done" );
     }

    private void display( final String message, final Set<String> set )
    {
        LOGGER.info( " *** " + message + " ***" );

        final StringBuilder sb = new StringBuilder();
        sb.append( "values = \n" );
        set.stream().sorted().forEach( k -> sb.append( k ).append( '\n' ) );
        LOGGER.info( sb.toString() );

        LOGGER.info( " *** " );
    }

    private Map<String, String> computeEntriesCreatedByPrepMap( final I18nPrepResult result ) //
            throws FileNotFoundException, IOException
    {
        final Properties prop = new Properties();

        try( final Reader reader = new FileReader( result.getOutputFile() ) ) {
            prop.load( reader );
        }

        final Set<String>         propertiyNames = prop.stringPropertyNames();
        final Map<String, String> map            = new HashMap<>( propertiyNames.size() );

        propertiyNames.forEach( t -> map.put( t, prop.getProperty( t ) ) );

        return Collections.unmodifiableMap( map );
    }

    private Map<String, String> computeEntriesExistingInPropertiesMap()
    {
        final ResourceBundle validMessageBundleResource = TestUtils.createResourceBundle( TestUtils.VALID_MESSAGE_BUNDLE, Locale.ENGLISH );

        final Set<String>         propertiyNames = validMessageBundleResource.keySet();
        final Map<String, String> map            = new HashMap<>( propertiyNames.size() );

        propertiyNames.forEach( t -> map.put( t, validMessageBundleResource.getString( t ) ) );

        return Collections.unmodifiableMap( map );
    }

    @Test
    public void runPerformeI18nTest()
    {
        final Iterable<TestReferenceDeprecated> tests = getTests();

        for( final TestReferenceDeprecated test : tests ) {
            LOGGER.info( "testing " + test  );
            test.performeI18n();
            }

        LOGGER.info( "ALL runPerformeI18nTest() done"  );
    }

    @Test
    public void launchI18nStringOldTest()
    {
        new I18nStringPart().performeI18n();
    }

    @Test
    public void launch_I18nStringTestReference()
    {
        new I18nStringTestReference().performeI18n();
    }

    @Test
    public void launch_I18nStringWithErrorsTestReference()
    {
        new I18nStringWithErrorsTestReference().performeI18n();
    }
}
