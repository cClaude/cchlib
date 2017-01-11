package com.googlecode.cchlib.i18n.unit.parts;

import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextIgnoreTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextIgnoreTest.LOCALIZED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextIgnoreTest.TEXT_INIT;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextIgnoreTest.TOOLTIPTEXT_DEFAULT_BUNDLE;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipTextIgnoreTest.TOOLTIPTEXT_INIT;
import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.JButton;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public class I18nToolTipTextIgnorePart implements TestReference
{
    @I18nToolTipText // I18n for tool tip text on button
    @I18nIgnore  // But no I18n for button text
    private final JButton myButtonWithToolTipText1;

    public I18nToolTipTextIgnorePart()
    {
        this.myButtonWithToolTipText1 = new JButton( TEXT_INIT );
        this.myButtonWithToolTipText1.setToolTipText( TOOLTIPTEXT_INIT );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePerformeI18nTest()
    {
        assertThat( this.myButtonWithToolTipText1.getText() ).isEqualTo( TEXT_INIT );
        assertThat( this.myButtonWithToolTipText1.getToolTipText() ).isEqualTo( TOOLTIPTEXT_INIT );
    }

    @Override
    public void afterPerformeI18nTest_WithValidBundle()
    {
        assertThat( this.myButtonWithToolTipText1.getText() )
            .isEqualTo( TEXT_INIT );
        assertThat( this.myButtonWithToolTipText1.getToolTipText() )
            .isEqualTo( TOOLTIPTEXT_DEFAULT_BUNDLE );
    }

    @Override
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        beforePerformeI18nTest();
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
