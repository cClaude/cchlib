package com.googlecode.cchlib.i18n.unit.strings;

import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.DEFAULT_BUNDLE_myGlobalStringID1;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.DEFAULT_BUNDLE_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.DEFAULT_BUNDLE_myString;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myGlobalStringID1;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myString;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myString1I18nTestContener2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myString2I18nTestContener2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.LOCALIZED_FIELDS;
import static org.fest.assertions.api.Assertions.assertThat;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public class I18nStringTestReference implements TestReference
{
    // This is a I18nAutoUpdatable object, should be
    // discovers by I18n process
    private final I18nStringTestContener objectToI18n;

    // This is a I18nAutoUpdatable object, should be ignored
    @I18nIgnore
    private final I18nTestContener2 objectNotToI18n;

    public I18nStringTestReference()
    {
        this.objectToI18n    = new I18nStringTestContener();
        this.objectNotToI18n = new I18nTestContener2();

        beforePerformeI18nTest();
    }

    private static final Logger getLogger()
    {
        return Logger.getLogger( I18nStringTestReference.class );
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

        assertThat( this.objectNotToI18n.getMyString1I18nTestContener2() )
            .isEqualTo( INIT_myString1I18nTestContener2 );
        assertThat( this.objectNotToI18n.getMyString2I18nTestContener2() )
            .isEqualTo( INIT_myString2I18nTestContener2 );
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

        assertThat( this.objectNotToI18n.getMyString1I18nTestContener2() )
            .isEqualTo( INIT_myString1I18nTestContener2 );
        assertThat( this.objectNotToI18n.getMyString2I18nTestContener2() )
            .isEqualTo( INIT_myString2I18nTestContener2 );
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

    @Override // I18nAutoUpdatable (required by tests and I18nResourceBuilder process)
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, I18nStringTestReference.class );
    }
}
