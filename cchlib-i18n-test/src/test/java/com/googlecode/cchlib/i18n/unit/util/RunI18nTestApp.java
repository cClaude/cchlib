package com.googlecode.cchlib.i18n.unit.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.unit.PrepTestPartInterface;
import com.googlecode.cchlib.i18n.unit.TestPartInterface;
import com.googlecode.cchlib.i18n.unit.parts.AutoI18nBasicInterfacePart;
import com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePart;
import com.googlecode.cchlib.i18n.unit.parts.I18nDefaultPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nForcedPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nStringPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextIgnorePart;
import com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextPart;
import com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPanePart;

public class RunI18nTestApp
{
    private static final Logger LOGGER = Logger.getLogger( RunI18nTestApp.class );

    @Ignore
    private static TestPartInterface[] getTests()
    {
        return new TestPartInterface[] {
                new AutoI18nBasicInterfacePart(),
                new I18nBaseNamePart(),
                new I18nDefaultPart(),
                new I18nForcedPart(),
                new I18nStringPart(),
                new I18nToolTipTextIgnorePart(),
                new I18nToolTipTextPart(),
                new I18nToolTipText_for_JTabbedPanePart(),
                };
    }

    @Test
    public void runPrepTest() throws FileNotFoundException, IOException
    {
        PrepTestPartInterface prepTest = TestUtils.createPrepTest();
        TestPartInterface[]        tests    = getTests();
        int syntaxeExceptionCount = 0;
        int missingResourceExceptionCount = 0;

        // Value should not change (check before)
        for( TestPartInterface test : tests ) {
            test.afterPrepTest();

            syntaxeExceptionCount += test.getSyntaxeExceptionCount();
            missingResourceExceptionCount += test.getMissingResourceExceptionCount();
            }

        for( TestPartInterface test : tests ) {
            test.beforePrepTest( prepTest );
            }

        I18nPrepHelper.Result result = TestUtils.runPrepTest( prepTest );

        // Value should not change (check after)
        for( TestPartInterface test : tests ) {
            test.afterPrepTest();
            }

        AutoI18nExceptionCollector collector = prepTest.getAutoI18nExceptionHandlerCollector();

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

        int existingSize;
        {
            final ResourceBundle validMessageBundleResource = TestUtils.VALID_MESSAGE_BUNDLE.createResourceBundle( Locale.ENGLISH );

            existingSize= validMessageBundleResource.keySet().size();
            LOGGER.info( "existingSize = " + existingSize );

            Assert.assertTrue( existingSize > 0 );
       }

        int createdSize;
        {
            Properties prop   = new Properties();
            Reader     reader = new FileReader( result.getOutputFile() );

            try {
                prop.load( reader );
            } finally {
                reader.close();
            }

            createdSize = prop.size();
        }

        Assert.assertEquals( existingSize, createdSize );

        LOGGER.info( "ALL runPrepTest() done" );
     }

    @Test
    public void runPerformeI18nTest()
    {
        TestPartInterface[] tests = getTests();

        for( TestPartInterface test : tests ) {
            test.runPerformeI18nTest();
            }

        LOGGER.info( "ALL runPerformeI18nTest() done"  );
    }
}
