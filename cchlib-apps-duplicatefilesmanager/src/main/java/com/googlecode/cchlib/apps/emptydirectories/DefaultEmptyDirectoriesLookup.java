package com.googlecode.cchlib.apps.emptydirectories;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import com.googlecode.cchlib.io.FileFilterHelper;
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
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( DefaultEmptyDirectoriesLookup.class );
    private List<File> rootFilesForScan;
    private FileFilter excludeDirectoriesFile;
    private List<EmptyDirectoriesListener> listeners = new ArrayList<EmptyDirectoriesListener>();

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     * <br/>
     * Does not start search of empty directories, you must
     * call {@link #lookup()} to start this task.
     *
     * @param rootFiles Array of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( File...rootFiles )
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
    public DefaultEmptyDirectoriesLookup( Enumerable<File> rootFiles )
    {
        this.rootFilesForScan = new ArrayList<File>();

        Enumeration<File> enumeration = rootFiles.enumeration();
        
        while( enumeration.hasMoreElements() ) {
            this.rootFilesForScan.add( enumeration.nextElement() );
            }
    }

    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    @Override
    public void lookup() throws CancelRequestException
    {
        lookup( FileFilterHelper.falseFileFilter() );
    }

    /**
     * Clear previous list and compute current list of empty directories
     * @param excludeDirectoriesFile {@link FileFilter} to identify directories to exclude.
     * @throws CancelRequestException if any listeners ask to cancel operation
     */
    @Override
    public void lookup( final FileFilter excludeDirectoriesFile )
        throws CancelRequestException
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
            l.findStarted();
            }

        this.excludeDirectoriesFile = excludeDirectoriesFile;

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
        logger.debug( "doScan:" + folder );

        if( folder.isDirectory() ) {
            //isEmpty( folder );
            try {
                couldBeEmpty( folder.toPath() );
                }
            catch( IOException e ) {
                logger.error( "Can not read:", e );
                }
            }
        // else not a folder, no scan.
    }

//    /**
//     * Returns true if folder has no file
//     * @param folder Folder to examine
//     * @return
//     * @throws CancelRequestException if any listeners ask to cancel operation
//     */
//    private boolean isEmpty( final File folder ) throws CancelRequestException
//    {
//        for( EmptyDirectoriesListener l : this.listeners ) {
//            if( l.isCancel() ) {
//                throw new CancelRequestException();
//                }
//            }
//
//        if( excludeDirectoriesFile.accept( folder ) ) {
//            return false;
//            }
//        
//        File[] content = folder.listFiles();
//
//        if( content == null ) {
//            return false; // Unknown this file (not a folder or protected, so not empty)
//            }
//
//        if( content.length == 0 ) {
//            add( folder );
//
//            return true;
//            }
//
//        boolean allEmpty = true;
//
//        for( File f : content ) {
//            if( f.isDirectory() ) {
//                if( ! isEmpty( f ) ) {
//                    allEmpty = false;
//                    }
//                }
//            else {
//                allEmpty = false;
//                }
//            }
//
//        if( allEmpty ) {
//            add( folder );
//
//            return true;
//            }
//
//        return false;
//    }

//    /**
//     * Add empty directory to current set, and notify all
//     * listeners: {@link EmptyDirectoriesListener#newEntry(File)}
//     */
//    private void add( final File emptyDirectoryFile )
//    {
//        final EmptyDirectoryFile edf = new EmptyDirectoryFile( emptyDirectoryFile );
//        
//        for( EmptyDirectoriesListener l : this.listeners ) {
//            l.newEntry( edf );
//            }
//    }

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

    
    private boolean couldBeEmpty( final Path folder ) throws IOException, CancelRequestException
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
            if( l.isCancel() ) {
                throw new CancelRequestException();
                }
            }
        
        if( excludeDirectoriesFile.accept( folder.toFile() ) ) {
            return false;
            }

        try( DirectoryStream<Path> stream = Files.newDirectoryStream( folder ) ) {
            boolean isEmpty      = true;
            boolean couldBeEmpty = true;
            
            for( Path entry: stream ) {
                isEmpty = false;
                
                if( ! couldBeEmpty( entry ) ) {
                    couldBeEmpty = false;
                    break;
                    }
                }
            
            if( isEmpty ) {
                add( EmptyFolder.createEmptyFolder( folder ) );
                }
            else if( couldBeEmpty ) {
                add( EmptyFolder.createCouldBeEmptyFolder( folder ) );
                }
            
            return couldBeEmpty;
            }
        catch( DirectoryIteratorException e ) {
            // I/O error encounted during the iteration, the cause is an IOException
            logger.warn( "Can not read: " + folder, e );
            
            return false;
            }
    }

    private Set<EmptyFolder> emptyFolderList = new HashSet<>();
    private void add( final EmptyFolder createEmptyFolder )
    {
        this.emptyFolderList.add( createEmptyFolder );
        
        for( EmptyDirectoriesListener l : this.listeners ) {
            l.newEntry( createEmptyFolder );
            }
    }
    
}
