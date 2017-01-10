package com.googlecode.cchlib.i18n.unit.parts;

import static org.fest.assertions.api.Assertions.assertThat;
import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public final class AutoI18nBasicInterfacePart
    implements I18nAutoUpdatable, TestReference, TestReferenceDeprecated
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

    private static final Logger LOGGER = Logger.getLogger( AutoI18nBasicInterfacePart.class );

    private static final String INIT_TEXT = "my MyAutoI18nBasicInterface text 1";
    private static final String DEFAULT_BUNDLE_TEXT = "OK(myAutoI18nBasicInterface)";

    private final MyAutoI18nBasicInterface myAutoI18nBasicInterface;

    public AutoI18nBasicInterfacePart()
    {
        this.myAutoI18nBasicInterface = new MyAutoI18nBasicInterface( INIT_TEXT );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override //TestReferenceDeprecated
    @Deprecated
    public void beforePrepTest(final PrepTestPart prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override //TestReferenceDeprecated
    @Deprecated
    public void afterPrepTest( final boolean firstRun )
    {
        Assert.assertEquals( INIT_TEXT, this.myAutoI18nBasicInterface.getI18nString() );
    }

    @Override //TestReferenceDeprecated
    @Deprecated
    public void performeI18n()
    {
        afterPrepTest( true );

        TestUtils.performeI18n( this );

        {
            final String text = this.myAutoI18nBasicInterface.getI18nString();
            LOGGER.info( "TEST RESULT: this.myAutoI18nBasicInterface.getI18nString() = " + text );
            Assert.assertEquals( DEFAULT_BUNDLE_TEXT, text );
        }
    }

    @Override //TestReferenceDeprecated
    @Deprecated
    public int getSyntaxeExceptionCount()
    {
        return 0;
    }

    @Override //TestReferenceDeprecated
    @Deprecated
    public int getMissingResourceExceptionCount()
    {
        return 1;
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

    private static final int LOCALIZED_FIELDS = 1;
    private static final int IGNORED_FIELDS   = 5;

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
