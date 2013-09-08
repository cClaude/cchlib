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
    public FileFilter getSourceFileFilter();

    /**
     *
     * @return TODOC
     * @see JFileChooser#setFileSelectionMode(int)
     */
    public int getSourceFilesFileSelectionMode();

    /**
     *
     * @return TODOC
     * @see JFileChooser#setFileSelectionMode(int)
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
