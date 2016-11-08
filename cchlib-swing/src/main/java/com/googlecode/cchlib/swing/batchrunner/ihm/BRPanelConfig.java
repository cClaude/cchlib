package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.io.File;
import java.io.FileFilter;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.NeedDoc;

/**
 * Provided {@link BRPanel} configuration
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
    FileFilter getSourceFileFilter();

    /**
     *
     * @return TODOC
     * @see JFileChooser#setFileSelectionMode(int)
     */
    int getSourceFilesFileSelectionMode();

    /**
     *
     * @return TODOC
     * @see JFileChooser#setFileSelectionMode(int)
     */
    int getDestinationFolderFileSelectionMode();

    /**
     *
     * @return TODOC
     */
    File getDefaultSourceDirectoryFile();

    /**
     *
     * @return TODOC
     */
    File getDefaultDestinationDirectoryFile();
}
