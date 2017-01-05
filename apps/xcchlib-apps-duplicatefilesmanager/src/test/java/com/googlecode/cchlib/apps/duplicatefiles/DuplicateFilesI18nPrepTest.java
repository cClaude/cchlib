package com.googlecode.cchlib.apps.duplicatefiles;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class DuplicateFilesI18nPrepTest
{
    @Test
    public void test_DuplicateFilesI18nPrep_runResourceBuilder() throws Exception
    {
        final I18nResourceBuilderResult result = DuplicateFilesI18nPrep.runResourceBuilder();

        I18nResourceBuilderHelper.fmtMissingProperties( System.err, result );
        I18nResourceBuilderHelper.fmtUnusedProperties( System.out, result );

        assertThat( result.getIgnoredFields() ).as( "IgnoredFields" ).hasSize( 215 );
        assertThat( result.getLocalizedFields() ).as("LocalizedFields").hasSize( 156 );
        assertThat( result.getMissingProperties() ).as("MissingProperties").isEmpty();;
        assertThat( result.getUnusedProperties() ).as("UnusedProperties").hasSize( 1 );
    }
}
