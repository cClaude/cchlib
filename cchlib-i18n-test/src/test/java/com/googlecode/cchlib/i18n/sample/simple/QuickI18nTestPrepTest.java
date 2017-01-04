package com.googlecode.cchlib.i18n.sample.simple;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.i18n.prep.I18nPrepStatResult;

public class QuickI18nTestPrepTest
{
    @Test
    public void quick_test() throws I18nPrepException
    {
        final QuickI18nTestFrame frame  = QuickI18nTestFrame.newQuickI18nTestFrame();
        final I18nPrepResult     result = QuickI18nTestPrep.runTest( frame );
        final I18nPrepStatResult stats  = result.getI18nPrepStatResult();

        assertThat( stats.getUnknow() ).as( "Unknow entry" ).isEqualTo( 1 );
        assertThat( stats.getFound() ).isEqualTo( 3 );
        assertThat( stats.getKnow() ).isEqualTo( 3 );

        assertThat( result.getNotUseCollector().isEmpty() ).isTrue();
        assertThat( result.getUsageStatCollector().size() ).isEqualTo( 0 );
        // TODO need to get stats values

        assertThat( frame.getMissingI18nEntry() ).isEqualTo( "" );
        assertThat( frame.getMissingI18nEntry() ).isEqualTo( "" );
        assertThat( frame.getMissingI18nEntry() ).isEqualTo( "" );

    }

}
