package com.googlecode.cchlib.i18n.sample.full;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class FakePanelPrepAppTest
{
    static final int IGNORED_FIELDS     = 15;
    static final int LOCALIZED_FIELDS   = 19;
    static final int MISSING_PROPERTIES = 0;
    static final int UNUSED_PROPERTIES  = 1;

    @Test
    public void test() throws ExecutionException, IOException
    {
        final I18nResourceBuilderResult result = FakePanelPrepApp.start();

        assertThat( result.getIgnoredFields()     ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields()   ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getMissingProperties() ).hasSize( MISSING_PROPERTIES );
        assertThat( result.getUnusedProperties()  ).hasSize( UNUSED_PROPERTIES );
    }
}
