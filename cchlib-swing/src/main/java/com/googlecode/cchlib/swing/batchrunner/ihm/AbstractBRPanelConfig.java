package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.io.FileFilter;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.io.filefilter.DirectoryFileFilter;
import com.googlecode.cchlib.io.filefilter.FileFileFilter;
import com.googlecode.cchlib.io.filefilter.TrueFileFilter;

/**
 *
 * @since 4.1.8
 */
public abstract class AbstractBRPanelConfig implements BRPanelConfig
{
    private FileFilter fileFilter;
    private int lastSelectionMode;

    /**
     * @return a new {@link FileFilter} according 
     * to {@link #getSourceFilesFileSelectionMode()} result.
     */
    @Override
    public synchronized FileFilter getSourceFileFilter()
    {
        if( fileFilter == null ) {
            resolveSourceFileFilter();
            }
        else {
            int selectionMode = getSourceFilesFileSelectionMode();

            if( lastSelectionMode != selectionMode ) {
                resolveSourceFileFilter();
                }
            }

        return fileFilter;
    }

    private void resolveSourceFileFilter()
    {
        lastSelectionMode = getSourceFilesFileSelectionMode();

        switch( lastSelectionMode ) {
            case JFileChooser.FILES_ONLY :
                fileFilter = new FileFileFilter();
                break;

            case JFileChooser.DIRECTORIES_ONLY :
                fileFilter = new DirectoryFileFilter();
                break;

            default:
                fileFilter = new TrueFileFilter();
                break;
        }
    }

    /**
     * @return {@link JFileChooser#FILES_ONLY}
     */
    @Override
    public int getSourceFilesFileSelectionMode()
    {
        return JFileChooser.FILES_ONLY;
    }

    /**
     * @return {@link JFileChooser#DIRECTORIES_ONLY}
     */
    @Override
    public int getDestinationFolderFileSelectionMode()
    {
        return JFileChooser.DIRECTORIES_ONLY;
    }
}
