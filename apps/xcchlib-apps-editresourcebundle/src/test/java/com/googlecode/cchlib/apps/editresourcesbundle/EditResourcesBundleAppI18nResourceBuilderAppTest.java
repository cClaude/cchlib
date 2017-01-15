package com.googlecode.cchlib.apps.editresourcesbundle;

import static org.fest.assertions.api.Assertions.assertThat;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import org.junit.Assume;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class EditResourcesBundleAppI18nResourceBuilderAppTest
{
    private static final int IGNORED_FIELDS     = 78;
    private static final int LOCALIZED_FIELDS   = 63;
    private static final int MISSING_PROPERTIES = 0;
    private static final int UNUSED_PROPERTIES  = 1; // (1 for tests)

    @Test
    public void test_EditResourcesBundleAppI18nResourceBuilder()
        throws InvocationTargetException, InterruptedException, ExecutionException
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final EditResourcesBundleAppI18nResourceBuilderApp instance
                = new EditResourcesBundleAppI18nResourceBuilderApp();
        final I18nResourceBuilderResult result
                = instance.doResourceBuilder();

        assertThat( result ).isNotNull();

        I18nResourceBuilderHelper.fmtAll( System.out, result );

        assertThat( instance.isDone() ).isTrue();
        assertThat( instance.getExecutionException() ).isNull();

        assertThat( result.getLocalizedFields()   ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getMissingProperties() ).hasSize( MISSING_PROPERTIES );
        assertThat( result.getUnusedProperties()  ).hasSize( UNUSED_PROPERTIES );
        assertThat( result.getIgnoredFields()     ).hasSize( IGNORED_FIELDS );
    }
}
