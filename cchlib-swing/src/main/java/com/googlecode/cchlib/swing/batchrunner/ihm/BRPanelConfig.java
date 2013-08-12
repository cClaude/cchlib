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
     * @return TODOC
     */
    public FileFilter getSourceFileFilter();

    /**
     *
     * @return TODOC
     */
    public int getSourceFilesFileSelectionMode();

    /**
     *
     * @return TODOC
     */
    public int getDestinationFolderFileSelectionMode();

    /**
     *
     * @return TODOC
     */
    public File getDefaultSourceDirectoryFile();

    /**
     *
     * @return TODOC
     */
    public File getDefaultDestinationDirectoryFile();
}
