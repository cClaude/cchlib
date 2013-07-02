package com.googlecode.cchlib.i18n.unit;

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
import com.googlecode.cchlib.i18n.unit.utils.AutoI18nExceptionCollector;
import com.googlecode.cchlib.i18n.unit.utils.RunI18nTestInterface;
import com.googlecode.cchlib.i18n.unit.utils.TestUtils;

public class RunI18nTest
{
    private final static Logger logger = Logger.getLogger( RunI18nTest.class );

    @Ignore
    private static RunI18nTestInterface[] getTests()
    {
        return new RunI18nTestInterface[] {
                new I18nToolTipTextIgnoreTest(),
                new I18nToolTipTextTest(),
                new I18nStringTest(),
                new I18nDefaultTest(),
                new I18nForcedTest(),
                new I18nBaseNameTest(),
                new AutoI18nBasicInterfaceTest(),
                new I18nToolTipText_for_JTabbedPaneTest()
                };
    }

    @Test
    public void runPrepTest() throws FileNotFoundException, IOException
    {
        RunI18nTestInterface.PrepTest prepTest = TestUtils.createPrepTest();
        RunI18nTestInterface[]        tests    = getTests();
        int syntaxeExceptionCount = 0;
        int missingResourceExceptionCount = 0;

        // Value should not change (check before)
        for( RunI18nTestInterface test : tests ) {
            test.afterPrepTest();
            
            syntaxeExceptionCount += test.getSyntaxeExceptionCount();
            missingResourceExceptionCount += test.getMissingResourceExceptionCount();
            }

        for( RunI18nTestInterface test : tests ) {
            test.beforePrepTest( prepTest );
            }

        I18nPrepHelper.Result result = TestUtils.runPrepTest( prepTest );

        // Value should not change (check after)
        for( RunI18nTestInterface test : tests ) {
            test.afterPrepTest();
            }

        AutoI18nExceptionCollector collector = prepTest.getAutoI18nExceptionHandlerCollector();

        logger.info( "I18nSyntaxeException = " + collector.getI18nSyntaxeExceptionCollector().size() );
        logger.info( "IllegalAccessException = " + collector.getIllegalAccessExceptionCollector().size() );
        logger.info( "IllegalArgumentException = " + collector.getIllegalArgumentExceptionCollector().size() );
        logger.info( "InvocationTargetException = " + collector.getInvocationTargetExceptionCollector().size() );
        logger.info( "MissingKeyException = " + collector.getMissingKeyExceptionCollector().size() );
        logger.info( "MissingResourceException = " + collector.getMissingResourceExceptionCollector().size() );
        logger.info( "NoSuchMethodException = " + collector.getNoSuchMethodExceptionCollector().size() );
        logger.info( "MethodProviderSecurityException = " + collector.getMethodProviderSecurityExceptionCollector().size() );
        logger.info( "SecurityException = " + collector.getSecurityExceptionCollector().size() );
        logger.info( "SetFieldException = " + collector.getSetFieldExceptionCollector().size() );

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
            final ResourceBundle validMessageBundleResource = TestUtils.validMessageBundle.createResourceBundle( Locale.ENGLISH );

            existingSize= validMessageBundleResource.keySet().size();
            logger.info( "existingSize = " + existingSize );
            
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

        logger.info( "ALL runPrepTest() done" );
     }

    @Test
    public void runPerformeI18nTest()
    {
        RunI18nTestInterface[] tests = getTests();

        for( RunI18nTestInterface test : tests ) {
            test.runPerformeI18nTest();
            }
        logger.info( "ALL runPerformeI18nTest() done"  );
    }
}
