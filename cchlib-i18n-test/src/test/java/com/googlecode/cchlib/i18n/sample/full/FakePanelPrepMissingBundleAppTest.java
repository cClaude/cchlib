package com.googlecode.cchlib.i18n.sample.full;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.swing.RunnableSupplier;
import com.googlecode.cchlib.swing.RunnableSupplierHelper;

public class FakePanelPrepMissingBundleAppTest
{
    private static final int IGNORED_FIELDS     = FakePanelPrepAppTest.IGNORED_FIELDS;
    private static final int LOCALIZED_FIELDS   = 0;
    private static final int MISSING_PROPERTIES = FakePanelPrepAppTest.LOCALIZED_FIELDS;
    private static final int UNUSED_PROPERTIES  = 0; // File is empty

    @Test
    public void test() throws ExecutionException, IOException
    {
        final String messagesBundle = "MessagesBundleMissing";
        final File   outputFile     = I18nResourceBuilderHelper.newOutputFile(
                FakePanelPrepMissingBundleAppTest.class.getPackage()
                );

        final RunnableSupplier<I18nResourceBuilderResult> runnableSupplier
            = RunnableSupplierHelper.newRunnableSupplier(
                    () -> FakePanelPrepApp.newFakePanelApp( messagesBundle, outputFile )
                    );

        final I18nResourceBuilderResult result =
                RunnableSupplierHelper.safeInvokeAndWait( runnableSupplier, 10, 1, TimeUnit.SECONDS );

        assertThat( result.getIgnoredFields()     ).hasSize( IGNORED_FIELDS     );
        assertThat( result.getLocalizedFields()   ).hasSize( LOCALIZED_FIELDS   );
        assertThat( result.getMissingProperties() ).hasSize( MISSING_PROPERTIES );
        assertThat( result.getUnusedProperties()  ).hasSize( UNUSED_PROPERTIES  );
    }
}
