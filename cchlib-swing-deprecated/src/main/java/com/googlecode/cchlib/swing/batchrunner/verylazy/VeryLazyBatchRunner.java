package com.googlecode.cchlib.swing.batchrunner.verylazy;

import java.awt.Window;
import java.io.File;

/**
 * Customizable methods needed by {@link VeryLazyBatchRunnerApp}.
 * <br/>
 * Default methods are already provided by {@link VeryLazyBatchRunnerApp}.
 *
 * @since 1.4.7
 */
@Deprecated
public interface VeryLazyBatchRunner
{
    /**
     * Returns a non existing {@link File} based on giving sourceFile
     * @param outputFile {@link File} to use to build new {@link File}
     * @return a non existing {@link File} based on giving sourceFile
     */
    File buildNotUsedOuputFile(File outputFile);

    /**
     * Returns main {@link Window} object
     * @return main {@link Window} object
     */
    Window getTopLevelWindow();
}
