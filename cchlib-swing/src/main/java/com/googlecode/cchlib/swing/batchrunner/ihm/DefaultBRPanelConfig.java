package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.io.File;

/**
 * Default implementation of {@link BRPanelConfig}, can be use at is when
 * not customization is required.
 *
 * @since 4.1.8
 * @see AbstractBRPanelConfig
 */
public class DefaultBRPanelConfig extends AbstractBRPanelConfig
{
    /**
     * {@inheritDoc}
     * @return this implementation returns null (no specific default folder).
     */
    @Override
    public File getDefaultSourceDirectoryFile() { return null; }

    /**
     * {@inheritDoc}
     * @return this implementation returns null (no specific default folder).
     */
    @Override
    public File getDefaultDestinationDirectoryFile() { return null; }
}
