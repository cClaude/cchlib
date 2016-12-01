package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.io.FileFilter;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.io.filefilter.DirectoryFileFilter;
import com.googlecode.cchlib.io.filefilter.FileFileFilter;
import com.googlecode.cchlib.io.filefilter.TrueFileFilter;

/**
 * Common implentation.
 *
 * @since 4.1.8
 */
public abstract class AbstractBRPanelConfig implements BRPanelConfig
{
    private static final long serialVersionUID = 1L;

    private FileFilter fileFilter;
    private int lastSelectionMode;

    /**
     * {@inheritDoc}
     *
     * Returns a {@link FileFilter} based on result of {@link #getSourceFilesFileSelectionMode()}.
     *
     * @return a new {@link FileFilter} according
     * to {@link #getSourceFilesFileSelectionMode()} result.
     * @see FileFileFilter
     * @see DirectoryFileFilter
     * @see TrueFileFilter
     */
    @Override
    final public synchronized FileFilter getSourceFileFilter()
    {
        if( this.fileFilter == null ) {
            resolveSourceFileFilter();
            }
        else {
            final int selectionMode = getSourceFilesFileSelectionMode();

            if( this.lastSelectionMode != selectionMode ) {
                resolveSourceFileFilter();
                }
            }

        return this.fileFilter;
    }

    private void resolveSourceFileFilter()
    {
        this.lastSelectionMode = getSourceFilesFileSelectionMode();

        switch( this.lastSelectionMode ) {
            case JFileChooser.FILES_ONLY :
                this.fileFilter = new FileFileFilter();
                break;

            case JFileChooser.DIRECTORIES_ONLY :
                this.fileFilter = new DirectoryFileFilter();
                break;

            default:
                this.fileFilter = new TrueFileFilter();
                break;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link JFileChooser#FILES_ONLY}
     */
    @Override
    public int getSourceFilesFileSelectionMode()
    {
        return JFileChooser.FILES_ONLY;
    }

    /**
     *  {@inheritDoc}
     *
     * @return {@link JFileChooser#DIRECTORIES_ONLY}
     */
    @Override
    public int getDestinationFolderFileSelectionMode()
    {
        return JFileChooser.DIRECTORIES_ONLY;
    }
}
