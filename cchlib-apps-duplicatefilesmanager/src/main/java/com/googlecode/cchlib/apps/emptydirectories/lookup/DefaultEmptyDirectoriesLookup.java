package com.googlecode.cchlib.apps.emptydirectories.lookup;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folders;
import com.googlecode.cchlib.lang.Enumerable;
import com.googlecode.cchlib.nio.file.FilterHelper;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 * Find empty directories
 *
 */
public class DefaultEmptyDirectoriesLookup
    implements EmptyDirectoriesLookup, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( DefaultEmptyDirectoriesLookup.class );
    private List<Path> rootFilesForScan;
    //private FileFilter excludeDirectoriesFile;
    private DirectoryStream.Filter<Path> directoriesFilter;
    private List<EmptyDirectoriesListener> listeners = new ArrayList<EmptyDirectoriesListener>();
    private LinkOption[] linkOption = new LinkOption[0]; // TODO Future extension

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     * <br/>
     * Does not start search of empty directories, you must
     * call {@link #lookup()} to start this task.
     *
     * @param rootFiles Array of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final File...rootFiles )
    {
        this.rootFilesForScan = new ArrayList<Path>( rootFiles.length );

        for( File f: rootFiles ) {
            this.rootFilesForScan.add( f.toPath() );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     * <br/>
     * Does not start search of empty directories, you must
     * call {@link #lookup()} to start this task.
     *
     * @param rootPaths Array of root {@link Path} objects
     */
    public DefaultEmptyDirectoriesLookup( final Path...rootPaths )
    {
        this.rootFilesForScan = new ArrayList<Path>( rootPaths.length );

        for( Path p: rootPaths ) {
            this.rootFilesForScan.add( p );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     *
     * @param rootFiles {@link Enumerable} of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final Enumerable<File> rootFiles )
    {
        this.rootFilesForScan = new ArrayList<Path>();

        Enumeration<File> enumeration = rootFiles.enumeration();

        while( enumeration.hasMoreElements() ) {
            this.rootFilesForScan.add( enumeration.nextElement().toPath() );
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

    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    @Override
    public void lookup() throws CancelRequestException
    {
        //lookup( FileFilterHelper.falseFileFilter() );
        lookup( FilterHelper.newAcceptAllFilter() );
    }


    /**
     * Clear previous list and compute current list of empty directories
     * @param excludeDirectoriesFile {@link FileFilter} to identify directories to exclude.
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    @Override
    public void lookup( final Filter<Path> excludeDirectoriesFile )
        throws CancelRequestException
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
            l.findStarted();
            }

        this.directoriesFilter = FilterHelper.and(
                excludeDirectoriesFile,
                FilterHelper.not(
                    FilterHelper.newDirectoriesFilter()
                    )
                );

        for( Path p : this.rootFilesForScan ) {
            if( logger.isDebugEnabled() ) {
                logger.debug( "lookup start from : " + p );
                }

            //if( f.isDirectory() ) {
            if( Files.isDirectory(p, linkOption) ) {
                doScan( p );
                }
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
    private void doScan( final Path folderPath ) throws CancelRequestException
    {
        if( folderPath.toFile().isFile() ) {
            throw new IllegalStateException( "Not a directory: " + folderPath );
            }

        if( logger.isDebugEnabled() ) {
            logger.debug( "doScan:" + folderPath );
            }

        checkIfCouldBeEmpty( folderPath );
    }

    private boolean checkIfCouldBeEmpty( final Path folder ) throws CancelRequestException
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
            if( l.isCancel() ) {
                throw new CancelRequestException();
                }
            }

        try( DirectoryStream<Path> stream = Files.newDirectoryStream( folder, this.directoriesFilter ) ) {
            boolean isEmpty      = true;
            boolean couldBeEmpty = true;

            if( logger.isTraceEnabled() ) {
                logger.trace( "check if empty: " + folder );
                }

            for( Path entryPath : stream ) {
                isEmpty = false;

                if( entryPath.toFile().isDirectory() ) {
                    couldBeEmpty = checkIfCouldBeEmpty( entryPath );
                    }
                else {
                    couldBeEmpty = false;
                    }

                if( ! couldBeEmpty ) {
                    break;
                    }
                }

            if( couldBeEmpty ) {
                if( isEmpty ) {
                    notify( Folders.createEmptyFolder( folder ) );
                    }
                else {
                    notify( Folders.createCouldBeEmptyFolder( folder ) );
                    }
                return true;
                }
            else {
                return false;
                }
            }
        catch( NotDirectoryException e ) {
            if( logger.isDebugEnabled() ) {
                logger.debug( "Not a directory : " + folder, e );
                }

            return false;
            }
        catch( AccessDeniedException e ) {
            if( logger.isDebugEnabled() ) {
                logger.debug( "Denied access: " + folder, e );
                }

            return false;
            }
        catch( IOException e ) { // stream.close()
            // I/O error encounted during the iteration, the cause is an IOException
            if( logger.isDebugEnabled() ) {
                logger.debug( "Can not read: " + folder, e );
                }

            return false;
            }
    }

    private void notify( final EmptyFolder emptyFolder )
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
            l.newEntry( emptyFolder );
            }
    }
}
