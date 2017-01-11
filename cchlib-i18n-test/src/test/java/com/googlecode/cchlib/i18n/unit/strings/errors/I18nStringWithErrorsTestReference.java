package com.googlecode.cchlib.i18n.unit.strings.errors;

import static com.googlecode.cchlib.i18n.unit.strings.errors.I18nStringWithErrorsTestReferenceTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.strings.errors.I18nStringWithErrorsTestReferenceTest.INIT_myGlobalStringIDMethod1;
import static com.googlecode.cchlib.i18n.unit.strings.errors.I18nStringWithErrorsTestReferenceTest.LOCALIZED_FIELDS;
import static org.fest.assertions.api.Assertions.assertThat;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public class I18nStringWithErrorsTestReference implements TestReference
{
    private final I18nStringWithErrorsTestContener objectToI18n;

    public I18nStringWithErrorsTestReference()
    {
        this.objectToI18n = new I18nStringWithErrorsTestContener();
    }

    @Override // I18nAutoUpdatable (required by tests and I18nResourceBuilder process)
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePerformeI18nTest()
    {
        assertThat( this.objectToI18n.getMyGlobalStringIDMethod1() )
            .isEqualTo( INIT_myGlobalStringIDMethod1 );
    }

    @Override
    public void afterPerformeI18nTest_WithValidBundle()
    {
        assertThat( this.objectToI18n.getMyGlobalStringIDMethod1() )
            .isEqualTo( INIT_myGlobalStringIDMethod1 );
   }

    @Override
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        assertThat( this.objectToI18n.getMyGlobalStringIDMethod1() )
            .isEqualTo( INIT_myGlobalStringIDMethod1 );
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
}
