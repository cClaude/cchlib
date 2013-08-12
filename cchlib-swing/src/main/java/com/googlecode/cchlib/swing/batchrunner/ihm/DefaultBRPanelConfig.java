package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.io.File;

/**
 * Default implementation of {@link BRPanelConfig}
 *
 * @since 4.1.8
 */
public class DefaultBRPanelConfig extends AbstractBRPanelConfig
{
    /**
     * @return this implementation returns null (no specific default folder).
     */
    @Override
    public File getDefaultSourceDirectoryFile() { return null; }

    /**
     * @return this implementation returns null (no specific default folder).
     */
    @Override
    public File getDefaultDestinationDirectoryFile() { return null; }
}
