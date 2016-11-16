package com.googlecode.cchlib.i18n.unit.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;
import com.googlecode.cchlib.i18n.unit.PrepTestPartInterface;
import com.googlecode.cchlib.i18n.unit.TestReference;
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

/**
 * Integration test for I18n
 */
public class RunI18nTestAppTest
{
    private static final Logger LOGGER = Logger.getLogger( RunI18nTestAppTest.class );
    private static final int NUMBERS_OF_UNUSED = 2;

    private static TestReference[] getTests()
    {
        return new TestReference[] {
                new AutoI18nBasicInterfacePart(),
                new I18nBaseNamePart(),
                new I18nDefaultPart(),
                new I18nForcedPart(),
                new I18nStringPart(),
                new I18nStringTestReference(),
                new I18nStringWithErrorsTestReference(),
                new I18nToolTipTextIgnorePart(),
                new I18nToolTipTextPart(),
                new I18nToolTipText_for_JTabbedPanePart(),
                };
    }

    @Test
    public void runPrepTest() throws FileNotFoundException, IOException, I18nPrepException
    {
        final PrepTestPartInterface prepTest = TestUtils.createPrepTest();
        final TestReference[]       tests    = getTests();

        int syntaxeExceptionCount = 0;
        int missingResourceExceptionCount = 0;

        // Value should not change (check before)
        for( final TestReference test : tests ) {
            test.afterPrepTest();

            syntaxeExceptionCount += test.getSyntaxeExceptionCount();
            missingResourceExceptionCount += test.getMissingResourceExceptionCount();
            }

        for( final TestReference test : tests ) {
            test.beforePrepTest( prepTest );
            }

        final I18nPrepHelper.Result result = TestUtils.runPrepTest( prepTest );

        // Value should not change (check after)
        for( final TestReference test : tests ) {
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

        Assert.assertEquals( syntaxeExceptionCount , collector.getI18nSyntaxeExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getIllegalAccessExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getIllegalArgumentExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getInvocationTargetExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getMissingKeyExceptionCollector().size() );
        Assert.assertEquals( missingResourceExceptionCount, collector.getMissingResourceExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getNoSuchMethodExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getMethodProviderSecurityExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getSecurityExceptionCollector().size() );
        Assert.assertEquals(  0, collector.getSetFieldExceptionCollector().size() );

        final Map<String, String>  computeEntriesExistingInPropertiesMap = computeEntriesExistingInPropertiesMap();
        final int                  existingSize = computeEntriesExistingInPropertiesMap.size();
        LOGGER.info( "existing entries in properties = " + existingSize );

        Assert.assertTrue( existingSize > 0 );

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

        Assert.assertEquals( existingSize, createdSize + NUMBERS_OF_UNUSED );

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

    private Map<String, String> computeEntriesCreatedByPrepMap( final Result result ) //
            throws FileNotFoundException, IOException
    {
        final Properties prop   = new Properties();
        try (final Reader reader = new FileReader( result.getOutputFile() )) {
            prop.load( reader );
        }

        final Set<String>         propertiyNames = prop.stringPropertyNames();
        final Map<String, String> map           = new HashMap<String, String>( propertiyNames.size() );

        propertiyNames.forEach( t -> map.put( t, prop.getProperty( t ) ) );

        return Collections.unmodifiableMap( map );
    }

    private Map<String, String> computeEntriesExistingInPropertiesMap()
    {
        final ResourceBundle validMessageBundleResource = TestUtils.VALID_MESSAGE_BUNDLE.createResourceBundle( Locale.ENGLISH );

        final Set<String>         propertiyNames = validMessageBundleResource.keySet();
        final Map<String, String> map           = new HashMap<String, String>( propertiyNames.size() );

        propertiyNames.forEach( t -> map.put( t, validMessageBundleResource.getString( t ) ) );

        return Collections.unmodifiableMap( map );
    }

    @Test
    public void runPerformeI18nTest()
    {
        final TestReference[] tests = getTests();

        for( final TestReference test : tests ) {
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
