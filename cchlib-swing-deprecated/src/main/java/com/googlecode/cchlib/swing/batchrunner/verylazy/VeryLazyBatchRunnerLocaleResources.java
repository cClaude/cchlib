package com.googlecode.cchlib.swing.batchrunner.verylazy;

import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerLocaleResources;

/**
 * Text for localization of {@link VeryLazyBatchRunnerApp}
 *
 * @since 1.4.7
 */
@Deprecated
public interface VeryLazyBatchRunnerLocaleResources
    extends LazyBatchRunnerLocaleResources
{
    /**
     * @return localized text for question if file already exist
     */
    public String getTextFileExistShouldReplaceIt_FMT();

    /**
     * @return localized text for question title if file already exist
     */
    public String getTextFileExistTitle();

    /**
     * @return localized text for response buttons if file already exist
     */
    public String[] getTextFileExistChoices();

}
