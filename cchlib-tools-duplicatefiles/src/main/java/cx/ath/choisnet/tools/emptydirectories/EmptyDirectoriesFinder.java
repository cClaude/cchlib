package cx.ath.choisnet.tools.emptydirectories;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import cx.ath.choisnet.io.FileFilterHelper;
import cx.ath.choisnet.util.CancelRequestException;

/**
 * Find empty directories
 *
 */
public class EmptyDirectoriesFinder
    implements EmptyDirectoriesFinderInterface, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( EmptyDirectoriesFinder.class );
    private List<File> rootFilesForScan;
    private FileFilter excludeDirectoriesFile;
    private List<EmptyDirectoriesListener> listeners = new ArrayList<EmptyDirectoriesListener>();

    /**
     * Create an EmptyDirectoriesFinder object.
     * <br/>
     * Does not start search of empty directories, you must
     * call {@link #compute()} to start this task.
     *
     * @param rootFiles
     */
    public EmptyDirectoriesFinder( File...rootFiles )
    {
        this.rootFilesForScan = new ArrayList<File>( rootFiles.length );

        for( File f: rootFiles ) {
            this.rootFilesForScan.add( f );
            }
    }

    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     *
     * @return current object for initialization chaining
     * @throws CancelRequestException
     */
    @Override
    public void find() throws CancelRequestException
    {
        find( FileFilterHelper.falseFileFilter() );
    }


    /**
     * Clear previous list and compute current list of empty directories
     * @param excludeDirectoriesFile {@link FileFilter} to identify directories to exclude.
     * @return current object for initialization chaining
     * @throws CancelRequestException
     */
    @Override
    public void find( FileFilter excludeDirectoriesFile )
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
     * @throws CancelRequestException
     */
    private void doScan( File folder ) throws CancelRequestException
    {
        logger.debug( "doScan:" + folder );

        if( folder.isDirectory() ) {
            isEmpty( folder );
            }
        // else not a folder, no scan.
    }

    /**
     *
     * @param folder
     * @return
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
            add( folder );

            return true;
            }

        boolean allEmpty = true;

        for( File f : content ) {
            if( f.isDirectory() ) {
                if( ! isEmpty( f ) ) {
                    allEmpty = false;
                    }
                }
            else {
                allEmpty = false;
                }
            }

        if( allEmpty ) {
            add( folder );

            return true;
            }

        return false;
    }

    /**
     * Add empty directory to current set, and notify all
     * listeners ({@link EmptyDirectoriesListener#addEntry(File, boolean)})
     *
     * {@inheritDoc}
     */
    private void/*boolean*/ add( final File emptyDirectoryFile )
    {
//        boolean r = emptyFoldersSet.add( emptyDirectoryFile );

        for( EmptyDirectoriesListener l : this.listeners ) {
            //l.addEntry( emptyDirectoryFile, r );
            l.newEntry( emptyDirectoryFile );
            }

//        return r;
    }

    /**
     * Add listener to this object.
     *
     * @param listener A valid {@link EmptyDirectoriesListener}
     * @throws NullPointerException if listener is null.
     */
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
    public void removeListener( final EmptyDirectoriesListener listener )
    {
        this.listeners.remove( listener );
    }

}
