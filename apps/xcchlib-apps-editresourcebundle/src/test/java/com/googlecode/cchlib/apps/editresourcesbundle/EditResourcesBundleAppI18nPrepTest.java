package com.googlecode.cchlib.apps.editresourcesbundle;

import static org.fest.assertions.api.Assertions.assertThat;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;

public class EditResourcesBundleAppI18nPrepTest
{
    @Test
    public void test_EditResourcesBundleAppI18nPrep()
        throws InvocationTargetException, InterruptedException
    {
        final EditResourcesBundleAppI18nPrep instance = new EditResourcesBundleAppI18nPrep();
        final I18nPrepResult                 result   = instance.runDoPrep();

        assertThat( instance.isDone() ).isTrue();
        assertThat( instance.getDoneCause() ).isNull();
        assertThat( result.getNotUseCollector().isEmpty() ).isTrue();
        // TODO need to get stats values
    }
}
