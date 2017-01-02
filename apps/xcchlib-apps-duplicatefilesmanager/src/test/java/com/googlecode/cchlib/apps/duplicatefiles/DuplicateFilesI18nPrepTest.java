package com.googlecode.cchlib.apps.duplicatefiles;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;

public class DuplicateFilesI18nPrepTest
{
    @Test
    public void test_DuplicateFilesI18nPrep_main() throws Exception
    {
        final I18nPrepResult result = DuplicateFilesI18nPrep.runDoPrep();

        assertThat( result.getNotUseCollector().isEmpty() ).isTrue();
    }

}
