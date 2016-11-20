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
     * @return NEEDDOC
     */
    FileFilter getSourceFileFilter();

    /**
     *
     * @return NEEDDOC
     * @see JFileChooser#setFileSelectionMode(int)
     */
    int getSourceFilesFileSelectionMode();

    /**
     *
     * @return NEEDDOC
     * @see JFileChooser#setFileSelectionMode(int)
     */
    int getDestinationFolderFileSelectionMode();

    /**
     *
     * @return NEEDDOC
     */
    File getDefaultSourceDirectoryFile();

    /**
     *
     * @return NEEDDOC
     */
    File getDefaultDestinationDirectoryFile();
}
