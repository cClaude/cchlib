package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;

/**
 * Handle file filters for files and directories
 */
public abstract class HandleFilter
{
    private final FileFilter directoriesFileFilter;
    private final FileFilter filesFileFilter;
    private final boolean    notQuiet;
    private final boolean    onlyDuplicates;
    private final boolean    verbose;

    protected HandleFilter( final CLIParameters cli ) throws CLIParametersException
    {
        this.verbose        = cli.isVerbose();
        this.notQuiet       = !cli.isQuiet();
        this.onlyDuplicates = cli.isOnlyDuplicates();

        final FileFiltersConfig ffc = cli.getFileFiltersConfig();

        this.filesFileFilter       = FileFilterFactory.getFileFilterForFiles( ffc, this.isVerbose() );
        this.directoriesFileFilter = FileFilterFactory.getFileFilterForDirectories( ffc, this.isVerbose() );

        if( this.verbose ) {
            CLIHelper.trace( "Files FileFilter", this.filesFileFilter );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
    }

    protected FileFilter getDirectoriesFileFilter()
    {
        return this.directoriesFileFilter;
    }

    protected FileFilter getFilesFileFilter()
    {
        return this.filesFileFilter;
    }

    protected boolean isNotQuiet()
    {
        return this.notQuiet;
    }

    protected boolean isOnlyDuplicates()
    {
        return this.onlyDuplicates;
    }

    public boolean isVerbose()
    {
        return this.verbose;
    }

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

    protected void removeNonDuplicates( final List<HashFiles> hashFilesList )
    {
        final Iterator<HashFiles> iterator = hashFilesList.iterator();

        while( iterator.hasNext() ) {
            final HashFiles hashFiles = iterator.next();

            removeNonDuplicates( hashFiles );

            if( hashFiles.getFiles().size() < 2 ) {
                // No more a duplicate
                iterator.remove();
            }
        }
    }

    private void removeNonDuplicates( final HashFiles hashFiles )
    {
        final long           length   = hashFiles.getLength();
        final Iterator<File> iterator = hashFiles.getFiles().iterator();

        while( iterator.hasNext() ) {
            final File file = iterator.next();

            if( file.length() != length ) {
                iterator.remove();
            } else {
                // Length ok, look for filters
                if( this.getFilesFileFilter() != null ) {
                    // There is a file filter
                    if( shouldRemoveFile( file ) ) { // NOSONAR
                        iterator.remove();

                        if( isNotQuiet() ) { // NOSONAR
                            CLIHelper.printMessage( "Ignore:" + file.getPath() );
                        }
                    }
                }
            }
        }
    }
}
