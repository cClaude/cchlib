package com.googlecode.cchlib.i18n.unit.parts;

import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.DEFAULT_BUNDLE_myGlobalStringID1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.DEFAULT_BUNDLE_myGlobalStringIDMethod1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.DEFAULT_BUNDLE_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.DEFAULT_BUNDLE_myString;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.INIT_myGlobalStringID1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.INIT_myGlobalStringIDMethod1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.INIT_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.INIT_myString;
import static com.googlecode.cchlib.i18n.unit.parts.I18nStringTest.LOCALIZED_FIELDS;
import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.JButton;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nCustomMethod;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public class I18nStringPart
    implements I18nAutoUpdatable, TestReference
{
    private static final Logger LOGGER = Logger.getLogger( I18nStringPart.class );

    @I18nString private String myString;
    @I18nString @I18nIgnore private String myStringIgnore;
    @I18nString private Object thisNotAString1;
    @I18nString private JButton thisNotAString2;

    @I18nString(id="I18nStringPart_GlobalStringID1")
    private String myGlobalStringID;

    @I18nString(id="I18nStringPart_GlobalStringID2",method="customizeString1")
    private String myGlobalStringIDMethod1 = INIT_myGlobalStringIDMethod1;

    @I18nString(method="customizeString2")
    private String myGlobalStringIDMethod2 = INIT_myGlobalStringIDMethod2;

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
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
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
