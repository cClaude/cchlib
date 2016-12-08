package com.googlecode.cchlib.apps.duplicatefiles.console.tasks;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFilterFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelper.PrintMode;
import com.googlecode.cchlib.json.JSONHelperException;

/**
 * Handle file filters for files and directories
 */
public abstract class TaskCommon<R> implements CommandTask<R>
{
    private final File       jsonOutputFile;
    private final FileFilter directoriesFileFilter;
    private final FileFilter filesFileFilter;
    private final boolean    notQuiet;
    private final boolean    onlyDuplicates;
    private final boolean    prettyJson;
    private final boolean    verbose;

    protected TaskCommon( final CLIParameters cli ) throws CLIParametersException
    {
        this.jsonOutputFile = cli.getJsonOutputFile();
        this.notQuiet       = !cli.isQuiet();
        this.onlyDuplicates = cli.isOnlyDuplicates();
        this.prettyJson     = cli.isPrettyJson();
        this.verbose        = cli.isVerbose();

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

    @SuppressWarnings({
        "squid:S1066", // Easiest to read with 2 statements
        "squid:S134"   // Optimization first
        })
    private void removeNonDuplicates( final HashFiles hashFiles )
    {
        final long           length   = hashFiles.getLength();
        final Iterator<File> iterator = hashFiles.getFiles().iterator();

        while( iterator.hasNext() ) {
            final File file = iterator.next();

            if( file.length() != length ) {
                // File not exist or file is empty.
                iterator.remove();
            } else {
                // Length ok, look for filters
                if( this.getFilesFileFilter() != null ) {
                    // There is a file filter
                    if( shouldRemoveFile( file ) ) {
                        iterator.remove();

                        if( isNotQuiet() ) {
                            CLIHelper.printMessage( "Ignore:" + file.getPath() );
                        }
                    }
                }
            }
        }
    }

    /**
     * Create parents directories of a file
     *
     * @param file File to create
     * @throws CLIParametersException
     */
    public static void createParentDirsOf( final File file )
        throws CLIParametersException
    {
        final File parentDirFile = file.getParentFile();

        if( ! parentDirFile.exists() ) {
            final Path dir = parentDirFile.toPath();
            try {
                Files.createDirectories( dir );
            }
            catch( final IOException e ) {
                throw new CLIParametersException(
                        "none (can not create parent dir)",
                        parentDirFile.getPath(),
                        e
                        );
            }
        }
    }

    @Override
    @SuppressWarnings("squid:S1066") // Easiest to read with an if in 2 statements
    public void saveResultIfRequired( final List<R> hashFilesList )
        throws CLIParametersException
    {
        if( hashFilesList != null ) {

            if( this.jsonOutputFile != null ) {
                createParentDirsOf( this.jsonOutputFile ); // TODO should be optional

                final Set<PrintMode> printMode;

                if( this.prettyJson ) {
                    printMode = JSONHelper.PRETTY_PRINT;
                } else {
                    printMode = JSONHelper.COMPACT_PRINT;
                }

                try {
                    JSONHelper.save(
                            this.jsonOutputFile,
                            hashFilesList,
                            printMode
                            );
                }
                catch( final JSONHelperException e ) {
                    CLIHelper.printError( "Error while writing JSON result", this.jsonOutputFile, e );
                }
            }
        }
    }
}
