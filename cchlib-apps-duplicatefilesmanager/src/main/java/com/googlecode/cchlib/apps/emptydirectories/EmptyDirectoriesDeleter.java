package com.googlecode.cchlib.apps.emptydirectories;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 * Find empty directories
 *
 */
public class EmptyDirectoriesDeleter
    implements EmptyDirectoriesDeleterInterface, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( EmptyDirectoriesDeleter.class );
    private DefaultEmptyDirectoriesLookup emptyDirectoriesFinder;
    private SortedSet<EmptyFolder>        emptyFoldersSet = new TreeSet<>();

    /**
     * Create an EmptyDirectoriesFinder object.
     * <br/>
     * Does not start search of empty directories, you must
     * call {@link #lookup()} to start this task.
     *
     * @param rootFiles
     */
    public EmptyDirectoriesDeleter( final File...rootFiles )
    {
        this.emptyDirectoriesFinder = new DefaultEmptyDirectoriesLookup( rootFiles );
        this.emptyDirectoriesFinder.addListener(
                new EmptyDirectoriesListener()
                {
                    @Override
                    public boolean isCancel()
                    {
                        return false;
                    }
                    @Override
                    public void newEntry( EmptyFolder emptyDirectoryFile )
                    //public void newEntry( File emptyDirectoryFile )
                    {
                        emptyFoldersSet.add( emptyDirectoryFile );
                    }
                    @Override public void findStarted(){}
                    @Override public void findDone() {}
                }
            );
    }

    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     *
     * @throws CancelRequestException
     */
    @Override
    public void lookup() throws CancelRequestException
    {
        emptyDirectoriesFinder.lookup();
    }

    /**
     * Clear previous list and compute current list of empty directories
     * @param excludeDirectoriesFile {@link FileFilter} to identify directories to exclude.
     * @throws CancelRequestException
     */
    @Override
    public void lookup( final FileFilter excludeDirectoriesFile )
        throws CancelRequestException
    {
        emptyFoldersSet.clear();
        emptyDirectoriesFinder.lookup( excludeDirectoriesFile);
    }

    /**
     * Add listener to this object.
     *
     * @param listener A valid {@link EmptyDirectoriesListener}
     * @throws NullPointerException if listener is null.
     */
    public void addListener( final EmptyDirectoriesListener listener )
    {
        this.emptyDirectoriesFinder.addListener( listener );
    }

    /**
     * Remove listener to this object
     *
     * @param listener A {@link EmptyDirectoriesListener} object
     */
    public void removeListener( final EmptyDirectoriesListener listener )
    {
        this.emptyDirectoriesFinder.removeListener( listener );
    }

    @Override
    public int doAction( final EmptyDirectoriesDeleterAction action )
    {
        int count = 0;

        final Iterator<EmptyFolder> iter = emptyFoldersSet.iterator();
        
        while( iter.hasNext() ) {
            EmptyFolder f = iter.next();

            if( action.doAction( f ) ) {
                logger.debug( "doAction:" + f );
                iter.remove();
                count++;
                }
            }

        emptyFoldersSet.clear();

        return count;
    }

    @Override
    public int doDelete( final EmptyDirectoriesDeleterActionListener listener )
    {
        return doAction(
                new EmptyDirectoriesDeleterAction()
                {
                    @Override
                    //public boolean doAction( File emptyFolderFile )
                    public boolean doAction( final EmptyFolder emptyFolderFile )
                    {
                        listener.workingOn( emptyFolderFile );

                        logger.debug( "delete:" + emptyFolderFile );

                        return true; //emptyFolderFile.delete();
                    }
                }
            );
    }

    @Override
    public int doDelete()
    {
        return doDelete(
                new EmptyDirectoriesDeleterActionListener()
                {
                    @Override
                    public void workingOn( EmptyFolder emptyFolderFile )
                    {
                        // Empty
                    }
                }
            );
    }

    @Override
    public Set<EmptyFolder> getCollection()
    {
        return Collections.unmodifiableSet( emptyFoldersSet );
    }

}
