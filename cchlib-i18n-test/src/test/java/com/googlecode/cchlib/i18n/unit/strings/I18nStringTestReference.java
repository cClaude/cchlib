package com.googlecode.cchlib.i18n.unit.strings;

import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public class I18nStringTestReference implements TestReferenceDeprecated
{
    private static final Logger LOGGER = Logger.getLogger( I18nStringTestReference.class );

    static final String INIT_myString = "my-string-text-1";
    static final String DEFAULT_BUNDLE_myString = "OK(myString)";

    static final String INIT_myGlobalStringID1 = "my Global string 1 text";
    static final String DEFAULT_BUNDLE_myGlobalStringID1 = "OK(I18nStringTestContener_GlobalStringID1)";

    static final String INIT_myGlobalStringIDMethod2 = "my Global string 3 text";
    static final String DEFAULT_BUNDLE_myGlobalStringIDMethod2 = "OK(myGlobalStringIDMethod2)";

    private final I18nStringTestContener objectToI18n;

    public I18nStringTestReference()
    {
        this.objectToI18n = new I18nStringTestContener();
    }

    @Override
    public void beforePrepTest(final PrepTestPart prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this.objectToI18n  );
   }

    @Override
    public void afterPrepTest()
    {
        Assert.assertEquals( INIT_myString, this.objectToI18n.getMyString() );
        Assert.assertEquals( INIT_myString, this.objectToI18n.getMyStringIgnore() );

        Assert.assertEquals( INIT_myGlobalStringID1, this.objectToI18n.getMyGlobalStringID() );
    }

    @Override
    public void performeI18n()
    {
        afterPrepTest();

        // Also verify values using custom methods
        Assert.assertEquals( INIT_myGlobalStringIDMethod2, this.objectToI18n.getMyGlobalStringIDMethod2() );

        TestUtils.performeI18n( this.objectToI18n );

        LOGGER.info( "TEST RESULT: this.myString = " + this.objectToI18n.getMyString() );
        Assert.assertEquals( DEFAULT_BUNDLE_myString, this.objectToI18n.getMyString() );
        Assert.assertEquals( INIT_myString, this.objectToI18n.getMyStringIgnore() );

        LOGGER.info( "TEST RESULT: this.myGlobalStringID = " + this.objectToI18n.getMyGlobalStringID() );
        Assert.assertEquals( DEFAULT_BUNDLE_myGlobalStringID1, this.objectToI18n.getMyGlobalStringID() );

        LOGGER.info( "TEST RESULT: this.myGlobalStringIDMethod2 = " + this.objectToI18n.getMyGlobalStringIDMethod2() );
        Assert.assertEquals( DEFAULT_BUNDLE_myGlobalStringIDMethod2, this.objectToI18n.getMyGlobalStringIDMethod2() );
    }

    @Override
    public int getSyntaxeExceptionCount()
    {
        return 0;
    }

    @Override
    public int getMissingResourceExceptionCount()
    {
        return 0;
    }
}
