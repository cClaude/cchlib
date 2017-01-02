package com.googlecode.cchlib.i18n.prep;

import java.io.File;
import com.googlecode.cchlib.NeedDoc;

/**
 * Results for {@link I18nPrepHelper}
 */
@NeedDoc
public interface I18nPrepResult {
    /**
     * NEEDDOC
     * 
     * @return NEEDDOC
     */
    PrepCollector<Integer> getUsageStatCollector();

    /**
     * NEEDDOC
     * 
     * @return NEEDDOC
     */
    PrepCollector<String> getNotUseCollector();

    /**
     * NEEDDOC
     * 
     * @return NEEDDOC
     */
    File getOutputFile();

    /**
     * Returns number of value of each types for tests
     * 
     * @return number of value of each types for tests
     */
    I18nPrepStatResult getI18nPrepStatResult();
}
