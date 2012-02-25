package com.googlecode.cchlib.swing.batchrunner.lazy;

import com.googlecode.cchlib.swing.batchrunner.BatchRunnerPanelLocaleResources;

/**
 * Text for localization of {@link LazyBatchRunnerLocaleResources}
 *
 * @since 1.4.7
 */
public interface LazyBatchRunnerLocaleResources extends BatchRunnerPanelLocaleResources
{
    /**
     * @return localized text for end of batch message
     */
    public String getTextEndOfBatch();

    /**
     * @return localized text for main frame title
     */
    public String getTextFrameTitle();

    /**
     * @return localized text to say that a IO Exception occur during batch 
     */
    public String getTextIOExceptionDuringBatch();

    /**
     * @return localized text to say that specified file (%s) is reading
     */
    public String getTextProgressMonitorTitle_FMT();
}
