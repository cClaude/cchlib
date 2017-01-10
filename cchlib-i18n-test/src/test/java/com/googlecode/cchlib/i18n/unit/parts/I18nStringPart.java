package com.googlecode.cchlib.i18n.unit.parts;

import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.JButton;
import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.i18n.annotation.I18nCustomMethod;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public class I18nStringPart
    implements I18nAutoUpdatable, TestReference, TestReferenceDeprecated
{
    private static final Logger LOGGER = Logger.getLogger( I18nStringPart.class );
    private static final String INIT_myString = "my-string-text-1";
    private static final String DEFAULT_BUNDLE_myString = "OK(myString)";

    @I18nString private String myString = INIT_myString;

    @I18nString @I18nIgnore private String myStringIgnore = INIT_myString;
    @I18nString private Object thisNotAString1;
    @I18nString private JButton thisNotAString2;

    private static final String INIT_myGlobalStringID1 = "my Global string 1 text";
    private static final String DEFAULT_BUNDLE_myGlobalStringID1 = "OK(I18nStringPart_GlobalStringID1)";
    @I18nString(id="I18nStringPart_GlobalStringID1") private String myGlobalStringID = INIT_myGlobalStringID1;

    private static final String INIT_myGlobalStringIDMethod1 = "my Global string 2 text";
    private static final String DEFAULT_BUNDLE_myGlobalStringIDMethod1 = "OK(myGlobalStringIDMethod1)";
    @I18nString(id="I18nStringPart_GlobalStringID2",method="customizeString1") private String myGlobalStringIDMethod1 = INIT_myGlobalStringIDMethod1;

    private static final String INIT_myGlobalStringIDMethod2 = "my Global string 3 text";
    private static final String DEFAULT_BUNDLE_myGlobalStringIDMethod2 = "OK(myGlobalStringIDMethod2)";
    @I18nString(method="customizeString2") private String myGlobalStringIDMethod2 = INIT_myGlobalStringIDMethod2;

    public I18nStringPart()
    {
        shouldNotBeFinalStatic();
    }

    private void shouldNotBeFinalStatic()
    {
        this.myString         = INIT_myString;
        this.myStringIgnore   = INIT_myString;
        this.myGlobalStringID = INIT_myGlobalStringID1;
    }

    @I18nCustomMethod
    public void customizeString1()
    {
        this.myGlobalStringIDMethod1 = DEFAULT_BUNDLE_myGlobalStringIDMethod1;
    }

    @I18nCustomMethod
    public void customizeString2()
    {
        this.myGlobalStringIDMethod2 = DEFAULT_BUNDLE_myGlobalStringIDMethod2;
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    @Deprecated
    public void beforePrepTest(final PrepTestPart prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
   }

    @Override
    @Deprecated
    public void afterPrepTest( final boolean firstRun )
    {
        Assert.assertEquals( INIT_myString, this.myString );
        Assert.assertEquals( INIT_myString, this.myStringIgnore );

        Assert.assertEquals( INIT_myGlobalStringID1, this.myGlobalStringID );
        // These values change (always resolve)
        // Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );
        // Assert.assertEquals( INIT_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );
    }

    @Override
    @Deprecated
    public void performeI18n()
    {
        afterPrepTest( true );

        // Also verify values using custom methods
        Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );
        Assert.assertEquals( INIT_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );

        TestUtils.performeI18n( this );

        afterPerformeI18nTest_WithValidBundle();
    }

    @Override
    @Deprecated
    public int getSyntaxeExceptionCount()
    {
        return 3;
    }

    @Override
    @Deprecated
    public int getMissingResourceExceptionCount()
    {
        return 2;
    }

    public final String getMyString()
    {
        return this.myString;
    }

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        beforePerformeI18nTest_common();

        assertThat( this.myGlobalStringIDMethod2 ).isEqualTo( INIT_myGlobalStringIDMethod2 );
    }

    private void beforePerformeI18nTest_common()
    {
        assertThat( this.myString ).isEqualTo( INIT_myString );
        assertThat( this.myStringIgnore ).isEqualTo( INIT_myString );
        assertThat( this.myGlobalStringID ).isEqualTo( INIT_myGlobalStringID1 );
        assertThat( this.myGlobalStringIDMethod1 ).isEqualTo( INIT_myGlobalStringIDMethod1 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
        LOGGER.info( "TEST RESULT: this.myString = " + this.myString );
        LOGGER.info( "TEST RESULT: this.myGlobalStringID = " + this.myGlobalStringID );
        LOGGER.info( "TEST RESULT: this.myGlobalStringIDMethod1 = " + this.myGlobalStringIDMethod1 );
        LOGGER.info( "TEST RESULT: this.myGlobalStringIDMethod2 = " + this.myGlobalStringIDMethod2 );

        assertThat( this.myString ).isEqualTo( DEFAULT_BUNDLE_myString );
        assertThat( this.myStringIgnore ).isEqualTo( INIT_myString );

        assertThat( this.myGlobalStringID ).isEqualTo( DEFAULT_BUNDLE_myGlobalStringID1 );

        // No change, since there is a syntax exception
        assertThat( this.myGlobalStringIDMethod1 ).isEqualTo( INIT_myGlobalStringIDMethod1 );

        assertThat( this.myGlobalStringIDMethod2 ).isEqualTo( DEFAULT_BUNDLE_myGlobalStringIDMethod2 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        // No Change but custom methods ! Since content not in bundle !
        beforePerformeI18nTest_common();

        assertThat( this.myGlobalStringIDMethod2 ).isEqualTo( DEFAULT_BUNDLE_myGlobalStringIDMethod2 );
    }

    private static final int LOCALIZED_FIELDS = 2;
    private static final int IGNORED_FIELDS   = 12;

    @Override // TestReference
    public void afterResourceBuilderTest_WithValidBundle( final I18nResourceBuilderResult result )
    {
        assertThat( result.getIgnoredFields() ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields() ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getMissingProperties() ).hasSize( 0 );
        assertThat( result.getUnusedProperties() ).hasSize( REF.size() - LOCALIZED_FIELDS );
    }

    @Override // TestReference
    public void afterResourceBuilderTest_WithNotValidBundle( final I18nResourceBuilderResult result )
    {
        assertThat( result.getIgnoredFields() ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields() ).hasSize( 0 );
        assertThat( result.getMissingProperties() ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getUnusedProperties() ).hasSize( 0 );
     }
}
