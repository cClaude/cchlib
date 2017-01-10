package com.googlecode.cchlib.apps.editresourcesbundle;

import static org.fest.assertions.api.Assertions.assertThat;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public class EditResourcesBundleAppI18nPrepTest
{
    private static final int IGNORED_FIELDS     = 79;
    private static final int LOCALIZED_FIELDS   = 63;
    private static final int MISSING_PROPERTIES = 0;
    private static final int UNUSED_PROPERTIES  = 1; // (1 for tests)

    @Test
    public void test_EditResourcesBundleAppI18nPrep()
        throws InvocationTargetException, InterruptedException
    {
        final EditResourcesBundleAppI18nPrepApp instance = new EditResourcesBundleAppI18nPrepApp();
        final I18nResourceBuilderResult         result   = instance.doResourceBuilder();

        I18nResourceBuilderHelper.fmtAll( System.out, result );

        assertThat( instance.isDone() ).isTrue();
        assertThat( instance.getDoneCause() ).isNull();

        assertThat( result.getIgnoredFields()     ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields()   ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getMissingProperties() ).hasSize( MISSING_PROPERTIES );
        assertThat( result.getUnusedProperties()  ).hasSize( UNUSED_PROPERTIES );
    }
}
