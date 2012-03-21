package cx.ath.choisnet.tools.emptydirectories;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import com.googlecode.cchlib.io.FileFilterHelper;
import cx.ath.choisnet.util.CancelRequestException;

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
     * Create an EmptyDirectoriesFinder object.
     * <br/>
     * Does not start search of empty directories, you must
     * call {@link #compute()} to start this task.
     *
     * @param rootFiles
     */
    public DefaultEmptyDirectoriesLookup( File...rootFiles )
    {
        this.rootFilesForScan = new ArrayList<File>( rootFiles.length );

        for( File f: rootFiles ) {
            this.rootFilesForScan.add( f );
            }
    }

    public DefaultEmptyDirectoriesLookup( Enumeration<File> rootFilesEnumeration )
    {
        this.rootFilesForScan = new ArrayList<File>();

        while( rootFilesEnumeration.hasMoreElements() ) {
            this.rootFilesForScan.add( rootFilesEnumeration.nextElement() );
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
    public void lookup() throws CancelRequestException
    {
        lookup( FileFilterHelper.falseFileFilter() );
    }


    /**
     * Clear previous list and compute current list of empty directories
     * @param excludeDirectoriesFile {@link FileFilter} to identify directories to exclude.
     * @return current object for initialization chaining
     * @throws CancelRequestException
     */
    @Override
    public void lookup( FileFilter excludeDirectoriesFile )
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
     * Returns true if folder has no file
     * @param folder Folder to examine
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
     * listeners: {@link EmptyDirectoriesListener#newEntry(File)}
     */
    private void add( final File emptyDirectoryFile )
    {
        for( EmptyDirectoriesListener l : this.listeners ) {
             l.newEntry( emptyDirectoryFile );
            }
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
