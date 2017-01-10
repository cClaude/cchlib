package com.googlecode.cchlib.i18n.unit.strings;

import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.DEFAULT_BUNDLE_myGlobalStringID1;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.DEFAULT_BUNDLE_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.DEFAULT_BUNDLE_myString;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myGlobalStringID1;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myString;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.LOCALIZED_FIELDS;
import static org.fest.assertions.api.Assertions.assertThat;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public class I18nStringTestReference implements TestReference, com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated
{
    // This is a I18nAutoCoreUpdatable object, should be
    // discovers by I18n process
    private final I18nStringTestContener objectToI18n;

    public I18nStringTestReference()
    {
        this.objectToI18n = new I18nStringTestContener();

        beforePerformeI18nTest();
    }

    private static final Logger getLogger()
    {
        return Logger.getLogger( I18nStringTestReference.class );
    }

    @Override
    @Deprecated
    public void beforePrepTest(final com.googlecode.cchlib.i18n.unit.PrepTestPart prepTest)
    {
        com.googlecode.cchlib.i18n.unit.util.TestUtils.preparePrepTest( prepTest, this.objectToI18n );
   }

    @Override
    public void afterPrepTest( final boolean firstRun )
    {
        noChangeTest();

        if( firstRun ) {
            assertThat( this.objectToI18n.getMyGlobalStringIDMethod2() )
                .isEqualTo( INIT_myGlobalStringIDMethod2 );
        } else {
            assertThat( this.objectToI18n.getMyGlobalStringIDMethod2() )
                .isEqualTo( DEFAULT_BUNDLE_myGlobalStringIDMethod2 );
        }
   }

    @Override
    @Deprecated
    public void performeI18n()
    {
        beforePerformeI18nTest();

        // Also verify values using custom methods
        assertThat( this.objectToI18n.getMyGlobalStringIDMethod2() ).isEqualTo( INIT_myGlobalStringIDMethod2 );

        com.googlecode.cchlib.i18n.unit.util.TestUtils.performeI18n( this );

        afterPerformeI18nTest_WithValidBundle();
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

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        noChangeTest();

        assertThat( this.objectToI18n.getMyGlobalStringIDMethod2() ).isEqualTo( INIT_myGlobalStringIDMethod2 );
     }

    private void noChangeTest()
    {
        assertThat( this.objectToI18n.getMyString()                ).isEqualTo( INIT_myString );
        assertThat( this.objectToI18n.getMyStringIgnore()          ).isEqualTo( INIT_myString );
        assertThat( this.objectToI18n.getMyGlobalStringID()        ).isEqualTo( INIT_myGlobalStringID1 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
        getLogger().info(
            "afterPerformeI18nTest_WithValidBundle() RESULT: objectToI18n.getMyString() = "
                + this.objectToI18n.getMyString()
                );
        getLogger().info(
            "afterPerformeI18nTest_WithValidBundle() RESULT: objectToI18n.getMyStringIgnore() = "
                + this.objectToI18n.getMyStringIgnore()
                );
        getLogger().info(
            "afterPerformeI18nTest_WithValidBundle() RESULT: objectToI18n.getMyGlobalStringID() = "
                + this.objectToI18n.getMyGlobalStringID()
                );
        getLogger().info(
            "afterPerformeI18nTest_WithValidBundle() RESULT: objectToI18n.getMyGlobalStringIDMethod2() = "
                + this.objectToI18n.getMyGlobalStringIDMethod2()
                );

        assertThat( this.objectToI18n.getMyString() )
            .as( "getMyString():" + this.objectToI18n.getClass().getName() )
            .isEqualTo( DEFAULT_BUNDLE_myString );

        assertThat( this.objectToI18n.getMyStringIgnore() )
            .as( "getMyStringIgnore():" + this.objectToI18n.getClass().getName() )
            .isEqualTo( INIT_myString );

        assertThat( this.objectToI18n.getMyGlobalStringID() )
            .as( "getMyGlobalStringID():" + this.objectToI18n.getClass().getName() )
           .isEqualTo( DEFAULT_BUNDLE_myGlobalStringID1 );

        assertThat( this.objectToI18n.getMyGlobalStringIDMethod2() )
            .as( "getMyGlobalStringIDMethod2():" + this.objectToI18n.getClass().getName() )
            .isEqualTo( DEFAULT_BUNDLE_myGlobalStringIDMethod2 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        noChangeTest(); // No Change

        // But Change here
        assertThat( this.objectToI18n.getMyGlobalStringIDMethod2() ).isEqualTo(
                DEFAULT_BUNDLE_myGlobalStringIDMethod2
                );
    }

    @Override // TestReference
    public void afterResourceBuilderTest_WithValidBundle( final I18nResourceBuilderResult result )
    {
        assertThat( result.getIgnoredFields()     ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields()   ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getMissingProperties() ).hasSize( 0 );
        assertThat( result.getUnusedProperties()  ).hasSize( REF.size() - LOCALIZED_FIELDS );
    }

    @Override // TestReference
    public void afterResourceBuilderTest_WithNotValidBundle( final I18nResourceBuilderResult result )
    {
        assertThat( result.getIgnoredFields()     ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields()   ).hasSize( 0 );
        assertThat( result.getMissingProperties() ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getUnusedProperties()  ).hasSize( 0 );
     }

    @Override // I18nAutoCoreUpdatable (required by tests and I18nResourceBuilder process)
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, I18nStringTestReference.class );
    }
}
