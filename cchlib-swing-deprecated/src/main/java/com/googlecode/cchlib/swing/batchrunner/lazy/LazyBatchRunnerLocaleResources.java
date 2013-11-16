package com.googlecode.cchlib.swing.batchrunner.lazy;

/**
 * Text for localization of {@link LazyBatchRunnerApp}
 *
 * @since 1.4.7
 */
@Deprecated
public interface LazyBatchRunnerLocaleResources
    extends com.googlecode.cchlib.swing.batchrunner.BatchRunnerPanelLocaleResources
{
    /**
     * @return localized text for end of batch message
     */
    String getTextEndOfBatch();

    /**
     * @return localized text for main frame title
     */
    String getTextFrameTitle();

    /**
     * @return localized text to say that an IO Exception occur during batch
     */
    String getTextIOExceptionDuringBatch();

    /**
     * @return localized text for buttons when an IO Exception occur
     *         during batch (2 buttons: [0]=Continue,[1]=Cancel)
     */
    String[] getTextIOExceptionDuringBatchButtons();

    /**
     * @return localized text to say that specified file (%s) is reading
     */
    String getTextProgressMonitorTitle_FMT();

}
