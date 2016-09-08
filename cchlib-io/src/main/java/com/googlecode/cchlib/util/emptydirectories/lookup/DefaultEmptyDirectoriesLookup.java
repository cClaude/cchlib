package com.googlecode.cchlib.util.emptydirectories.lookup;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.Enumerable;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.util.Folders;

/**
 * Find empty directories solution base on {@link FileFilter}
 */
public class DefaultEmptyDirectoriesLookup
    extends AbstractEmptyDirectoriesLookup<ExcludeDirectoriesFileFilter>
        implements
            EmptyDirectoriesLookup<ExcludeDirectoriesFileFilter>,
            Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultEmptyDirectoriesLookup.class );

    private final List<File>             rootFilesForScan;
    private ExcludeDirectoriesFileFilter excludeDirectoriesFile;

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     * <br>
     * Does not start search of empty directories, you must
     * call {@link #lookup()} to start this task.
     *
     * @param rootFiles Array of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final File...rootFiles )
    {
        this.rootFilesForScan = new ArrayList<>( rootFiles.length );

        for( final File f: rootFiles ) {
            this.rootFilesForScan.add( f );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     *
     * @param rootPaths Array of root {@link Path} objects
     */
    public DefaultEmptyDirectoriesLookup( final Path...rootPaths )
    {
        this.rootFilesForScan = new ArrayList<>( rootPaths.length );

        for( final Path p: rootPaths ) {
            this.rootFilesForScan.add( p.toFile() );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     *
     * @param rootFiles {@link Enumerable} of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final Enumerable<File> rootFiles )
    {
        this.rootFilesForScan = new ArrayList<>();

        final Enumeration<File> enumeration = rootFiles.enumeration();

        while( enumeration.hasMoreElements() ) {
            this.rootFilesForScan.add( enumeration.nextElement() );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     *
     * @param rootFiles {@link Iterable} of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final Iterable<File> rootFiles )
    {
        this.rootFilesForScan = new ArrayList<>();

        for( final File f: rootFiles ) {
            this.rootFilesForScan.add( f );
            }
    }

    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     * @throws CancelRequestException if any listeners ask to cancel operation
     * @throws ScanIOException
     */
    @Override
    public void lookup() throws CancelRequestException
    {
        lookup( pathname -> false ); // False ExcludeDirectoriesFileFilter
    }

    @Override
    public void lookup(
            @Nonnull final ExcludeDirectoriesFileFilter excludeDirectoriesFileFilter
            )
        throws CancelRequestException
    {
        for( final EmptyDirectoriesListener l : getListeners() ) {
            l.findStarted();
            }

        this.excludeDirectoriesFile = excludeDirectoriesFileFilter;

        for( final File f : this.rootFilesForScan ) {
            doScan( f );
            }

        for( final EmptyDirectoriesListener l : getListeners() ) {
            l.findDone();
            }
    }

    /**
     * Launch scan for this folder.
     * @param folder Folder file to scan
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    private void doScan( final File folder ) throws CancelRequestException
    {
        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "doScan:" + folder );
        }

        if( folder.isDirectory() ) {
            isFolderEmpty( folder );
            }
        // else not a folder, no scan.
    }

    /**
     * Returns true if folder has no file
     * @param folder Folder to examine
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    private boolean isFolderEmpty( final File folder ) throws CancelRequestException
    {
        for( final EmptyDirectoriesListener l : getListeners() ) {
            if( l.isCancel() ) {
                throw new CancelRequestException();
                }
            }

        if( this.excludeDirectoriesFile.accept( folder ) ) {
            return false;
            }

        final File[] content = folder.listFiles();

        if( content == null ) {
            return false; // Unknown this file (not a folder or protected, so not empty)
            }

        if( content.length == 0 ) {
            add( folder, true );

            return true;
            }

        return isFolderCouldBeEmpty( folder, content );
    }

    private boolean isFolderCouldBeEmpty( final File folder, final File[] content )
            throws CancelRequestException
    {
        boolean reallyEmpty  = true;
        boolean couldBeEmpty = true;

        for( final File f : content ) {
            if( f.isDirectory() ) {
                final boolean nextIsEmpty = isFolderEmpty( f );

                if( ! nextIsEmpty ) {
                    couldBeEmpty = false;
                    }
                reallyEmpty = false;
                }
            else {
                couldBeEmpty = reallyEmpty = false;
                }
            }

        if( couldBeEmpty ) {
            add( folder, reallyEmpty );

            return true;
            }

        return false;
    }

    /**
     * Add empty directory to current set, and notify all
     * listeners: {@link EmptyDirectoriesListener#newEntry(File)}
     */
    private void add( final File emptyDirectoryFile, final boolean reallyEmpty )
    {
        EmptyFolder emptyDirectory;

        if( reallyEmpty ) {
            emptyDirectory = Folders.createEmptyFolder( emptyDirectoryFile );
            }
        else {
            emptyDirectory = Folders.createCouldBeEmptyFolder( emptyDirectoryFile );
            }

        notify( emptyDirectory );
    }
}
