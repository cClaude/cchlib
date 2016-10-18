package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * Handle file filters for files and directories
 */
public abstract class HandleFilter {

    protected abstract FileFilter getFilesFileFilter();
    protected abstract FileFilter getDirectoriesFileFilter();

    protected boolean shouldRemoveFile( final File file )
    {
        boolean removeEntry = false;

        if( getDirectoriesFileFilter() != null ) {
            removeEntry = shouldRemoveEntryAccordingToDirectories( file );
        }
        if( !removeEntry && (getFilesFileFilter() != null) ) {
            removeEntry = shouldRemoveEntryAccordingToFiles( file );
        }

        return removeEntry;
    }

    private boolean shouldRemoveEntryAccordingToDirectories( final File file )
    {
        File currentParent = file.getParentFile();

        while( currentParent != null ) {

            if( ! this.getDirectoriesFileFilter().accept( currentParent ) ) {
                return true;
            }
            currentParent = currentParent.getParentFile();
        }

        return false;
    }

    private boolean shouldRemoveEntryAccordingToFiles( final File file )
    {
        return !this.getFilesFileFilter().accept( file );
    }
}
