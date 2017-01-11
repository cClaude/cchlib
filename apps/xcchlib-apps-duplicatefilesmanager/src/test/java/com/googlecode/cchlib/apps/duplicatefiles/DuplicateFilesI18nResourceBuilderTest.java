package com.googlecode.cchlib.apps.duplicatefiles;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class DuplicateFilesI18nResourceBuilderTest
{
    @Test
    public void test_DuplicateFilesI18nPrep_runResourceBuilder() throws Exception
    {
        final I18nResourceBuilderResult result = DuplicateFilesI18nResourceBuilderApp.runResourceBuilder();

        I18nResourceBuilderHelper.fmtLocalizedFields( System.out, result );
        I18nResourceBuilderHelper.fmtMissingProperties( System.err, result );
        I18nResourceBuilderHelper.fmtUnusedProperties( System.out, result );

        assertThat( result.getLocalizedFields() ).as("LocalizedFields").hasSize( 186 );
        assertThat( result.getMissingProperties() ).as("MissingProperties").isEmpty();;
        assertThat( result.getUnusedProperties() ).as("UnusedProperties")
            .hasSize( 1 )
            .containsKey( "NOT_USED_KEY" );

        final Integer copyTxtCount = result.getLocalizedFields().get( "com.googlecode.cchlib.swing.textfield.XTextField.copyTxt" );
        assertThat( copyTxtCount ).isEqualTo( 4 );

        final Integer pasteTxtCount = result.getLocalizedFields().get( "com.googlecode.cchlib.swing.textfield.XTextField.pasteTxt" );
        assertThat( pasteTxtCount ).isEqualTo( 4 );

        // Don't really care about these values
        assertThat( result.getIgnoredFields() ).as( "IgnoredFields" ).hasSize( 293 );
    }
}
