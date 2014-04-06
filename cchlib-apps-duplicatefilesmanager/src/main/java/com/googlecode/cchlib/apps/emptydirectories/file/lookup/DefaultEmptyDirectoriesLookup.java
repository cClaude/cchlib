package com.googlecode.cchlib.apps.emptydirectories.file.lookup;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.FolderFilter;
import com.googlecode.cchlib.apps.emptydirectories.FolderFilterHelper;
import com.googlecode.cchlib.apps.emptydirectories.Folders;
import com.googlecode.cchlib.apps.emptydirectories.ScanIOException;
import com.googlecode.cchlib.lang.Enumerable;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 * Find empty directories
 *
 */
public class DefaultEmptyDirectoriesLookup
    implements EmptyDirectoriesLookup, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultEmptyDirectoriesLookup.class );

    private List<File> rootFilesForScan;
    private FileFilter excludeDirectoriesFile;
    private List<EmptyDirectoriesListener> listeners = new ArrayList<EmptyDirectoriesListener>();

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
        this.rootFilesForScan = new ArrayList<File>( rootFiles.length );

        for( File f: rootFiles ) {
            this.rootFilesForScan.add( f );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     *
     * @param rootFiles {@link Enumerable} of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final Enumerable<File> rootFiles )
    {
        this.rootFilesForScan = new ArrayList<File>();

        Enumeration<File> enumeration = rootFiles.enumeration();

        while( enumeration.hasMoreElements() ) {
            this.rootFilesForScan.add( enumeration.nextElement() );
            }
    }

    public DefaultEmptyDirectoriesLookup( final Path...rootPaths )
    {
        this.rootFilesForScan = new ArrayList<File>( rootPaths.length );

        for( Path p: rootPaths ) {
            this.rootFilesForScan.add( p.toFile() );
            }
    }

    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     * @throws CancelRequestException if any listeners ask to cancel operation
     * @throws ScanIOException
     */
    @Override
    public void lookup() throws CancelRequestException, ScanIOException
    {
        lookup( FolderFilterHelper.falseFileFilter() );
    }

    /**
     * Clear previous list and compute current list of empty directories
     * @param excludeFolderFilter {@link FolderFilter} to identify directories to exclude.
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    @Override
    //public void lookup( final FileFilter excludeDirectoriesFile )
    public void lookup( final FolderFilter excludeFolderFilter )
        throws CancelRequestException, ScanIOException
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
            l.findStarted();
            }

        this.excludeDirectoriesFile = excludeFolderFilter.toFileFilter();

        for( File f : this.rootFilesForScan ) {
            doScan( f );
            }

        for( EmptyDirectoriesListener l : this.listeners ) {
            l.findDone();
            }
    }

    /**
     * Launch scan for this folder.
     * @param folder Folder file to scan
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    private void doScan( File folder ) throws CancelRequestException
    {
        LOGGER.debug( "doScan:" + folder );

        if( folder.isDirectory() ) {
            isEmpty( folder );
            }
        // else not a folder, no scan.
    }

    /**
     * Returns true if folder has no file
     * @param folder Folder to examine
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    private boolean isEmpty( final File folder ) throws CancelRequestException
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
            if( l.isCancel() ) {
                throw new CancelRequestException();
                }
            }

        if( excludeDirectoriesFile.accept( folder ) ) {
            return false;
            }

        File[] content = folder.listFiles();

        if( content == null ) {
            return false; // Unknown this file (not a folder or protected, so not empty)
            }

        if( content.length == 0 ) {
            add( folder, true );

            return true;
            }

        boolean reallyEmpty  = true;
        boolean couldBeEmpty = true;

        for( File f : content ) {
            if( f.isDirectory() ) {
                boolean nextIsEmpty = isEmpty( f );

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

        for( EmptyDirectoriesListener l : this.listeners ) {
            l.newEntry( emptyDirectory );
            }
    }

    /**
     * Add listener to this object.
     *
     * @param listener A valid {@link EmptyDirectoriesListener}
     * @throws NullPointerException if listener is null.
     */
    @Override
    public void addListener( final EmptyDirectoriesListener listener )
    {
        if( listener == null ) {
            throw new NullPointerException();
            }

        this.listeners.add( listener );
    }

    /**
     * Remove listener to this object
     *
     * @param listener A {@link EmptyDirectoriesListener} object
     */
    @Override
    public void removeListener( final EmptyDirectoriesListener listener )
    {
        this.listeners.remove( listener );
    }
}
