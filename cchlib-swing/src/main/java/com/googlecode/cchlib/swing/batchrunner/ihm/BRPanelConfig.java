package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.cchlib.NeedDoc;

/**
 * Configuration for {@link BRPanel}
 *
 * @since 4.1.8
 */
@NeedDoc
public interface BRPanelConfig
{
    /**
     *
     * @return
     */
    public FileFilter getSourceFileFilter();

    /**
     *
     * @return
     */
    public int getSourceFilesFileSelectionMode();

    /**
     *
     * @return
     */
    public int getDestinationFolderFileSelectionMode();

    /**
     *
     * @return
     */
    public File getDefaultSourceDirectoryFile();

    /**
     *
     * @return
     */
    public File getDefaultDestinationDirectoryFile();
}
