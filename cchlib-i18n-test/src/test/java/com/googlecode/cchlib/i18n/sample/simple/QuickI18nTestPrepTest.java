package com.googlecode.cchlib.i18n.sample.simple;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;

public class QuickI18nTestPrepTest
{
    @Test
    public void quick_test() throws I18nPrepException
    {
        final I18nPrepResult result = QuickI18nTestPrep.runTest();

        assertThat( result.getNotUseCollector().isEmpty() ).isTrue();
        assertThat( result.getUsageStatCollector().size() ).isEqualTo( 0 );
        // TODO need to get stats values
    }

}
