package com.googlecode.cchlib.i18n.unit.strings.errors;

import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.i18n.unit.PrepTestPartInterface;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public class I18nStringWithErrorsTestReference implements TestReference
{
    private static final Logger LOGGER = Logger.getLogger( I18nStringWithErrorsTestReference.class );

    static final String INIT_myGlobalStringIDMethod1 = "my Global string 2 text";
    static final String DEFAULT_BUNDLE_myGlobalStringIDMethod1 = "OK(myGlobalStringIDMethod1)";

    private final I18nStringWithErrorsTestContener objectToI18n;

    public I18nStringWithErrorsTestReference()
    {
        this.objectToI18n = new I18nStringWithErrorsTestContener();
    }

    @Override
    public void beforePrepTest(final PrepTestPartInterface prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this.objectToI18n  );
   }

    @Override
    public void afterPrepTest()
    {
        // Nothing
    }

    @Override
    public void performeI18n()
    {
        afterPrepTest();

        // Also verify values using custom methods
        Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.objectToI18n.getMyGlobalStringIDMethod1() );

        TestUtils.performeI18n( this.objectToI18n );

        // No change, since there is a syntax error
        LOGGER.info( "TEST RESULT: this.myGlobalStringIDMethod1 = " + this.objectToI18n.getMyGlobalStringIDMethod1() );
        Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.objectToI18n.getMyGlobalStringIDMethod1() );
    }

    @Override
    public int getSyntaxeExceptionCount()
    {
        return 3;
    }

    @Override
    public int getMissingResourceExceptionCount()
    {
        return 2;
    }
}
