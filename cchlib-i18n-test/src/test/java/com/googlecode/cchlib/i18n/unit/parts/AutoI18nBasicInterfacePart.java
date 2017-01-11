package com.googlecode.cchlib.i18n.unit.parts;

import static com.googlecode.cchlib.i18n.unit.parts.AutoI18nBasicInterfaceTest.DEFAULT_BUNDLE_TEXT;
import static com.googlecode.cchlib.i18n.unit.parts.AutoI18nBasicInterfaceTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.AutoI18nBasicInterfaceTest.INIT_TEXT;
import static com.googlecode.cchlib.i18n.unit.parts.AutoI18nBasicInterfaceTest.LOCALIZED_FIELDS;
import static org.fest.assertions.api.Assertions.assertThat;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public final class AutoI18nBasicInterfacePart
    implements I18nAutoUpdatable, TestReference
{
    private static final class MyAutoI18nBasicInterface implements AutoI18nBasicInterface
    {
        private String i18nString;

        public MyAutoI18nBasicInterface( final String i18nString )
        {
            this.i18nString = i18nString;
        }

        @Override
        public String getI18nString()
        {
            return this.i18nString;
        }

        @Override
        public void setI18nString( final String localString )
        {
            this.i18nString = localString;
        }
    }

    private final MyAutoI18nBasicInterface myAutoI18nBasicInterface;

    public AutoI18nBasicInterfacePart()
    {
        this.myAutoI18nBasicInterface = new MyAutoI18nBasicInterface( INIT_TEXT );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        assertThat( this.myAutoI18nBasicInterface.i18nString ).isEqualTo( INIT_TEXT );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
        assertThat( this.myAutoI18nBasicInterface.i18nString ).isEqualTo( DEFAULT_BUNDLE_TEXT );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        beforePerformeI18nTest(); // No Change
    }

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
