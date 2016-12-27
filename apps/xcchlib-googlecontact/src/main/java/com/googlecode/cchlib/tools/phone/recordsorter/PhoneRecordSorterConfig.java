package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.File;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.swing.batchrunner.ihm.AbstractBRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelConfig;

/**
 *
 * @since 4.1.8
 */
public class PhoneRecordSorterConfig extends AbstractBRPanelConfig implements BRPanelConfig
{
    private static final long serialVersionUID = 1L;

    private final File defaultSourceDirectory;
    private final File defaultDestinationDirectoryFile;

    public PhoneRecordSorterConfig(
        final File sourceFolderFile,
        final File destinationFolders
        )
    {
        this.defaultSourceDirectory          = sourceFolderFile;
        this.defaultDestinationDirectoryFile = destinationFolders;
    }

    @Override
    public int getSourceFilesFileSelectionMode()
    {
        return JFileChooser.DIRECTORIES_ONLY;
    }

    @Override
    public File getDefaultSourceDirectoryFile()
    {
        return this.defaultSourceDirectory;
    }

    @Override
    public File getDefaultDestinationDirectoryFile()
    {
        return this.defaultDestinationDirectoryFile;
    }
}
