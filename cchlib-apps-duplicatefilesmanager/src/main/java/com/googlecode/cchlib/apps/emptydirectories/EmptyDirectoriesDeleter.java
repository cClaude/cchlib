package com.googlecode.cchlib.apps.emptydirectories;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import cx.ath.choisnet.util.CancelRequestException;

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
    private SortedSet<File> emptyFoldersSet = new TreeSet<File>();

    /**
     * Create an EmptyDirectoriesFinder object.
     * <br/>
     * Does not start search of empty directories, you must
     * call {@link #compute()} to start this task.
     *
     * @param rootFiles
     */
    public EmptyDirectoriesDeleter( File...rootFiles )
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
                    public void newEntry( File emptyDirectoryFile )
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
     * @return current object for initialization chaining
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
     * @return current object for initialization chaining
     * @throws CancelRequestException
     */
    @Override
    public void lookup( FileFilter excludeDirectoriesFile )
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
//    @Override
    public void addListener( final EmptyDirectoriesListener listener )
    {
        this.emptyDirectoriesFinder.addListener( listener );
    }

    /**
     * Remove listener to this object
     *
     * @param listener A {@link EmptyDirectoriesListener} object
     */
//  @Override
    public void removeListener( final EmptyDirectoriesListener listener )
    {
        this.emptyDirectoriesFinder.removeListener( listener );
    }

    @Override
    public int doAction( EmptyDirectoriesDeleterAction action )
    {
        int count = 0;

//        for( File f : emptyFoldersSet ) {
//            if( f.delete() ) {
//                logger.debug( "deleted:" + f );
//                count++;
//                }
//            }
//
//        emptyFoldersSet.clear();

        Iterator<File> iter = emptyFoldersSet.iterator();
        while( iter.hasNext() ) {
            File f = iter.next();

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
                    public boolean doAction( File emptyFolderFile )
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
                    public void workingOn( File emptyFolderFile )
                    {
                        // Empty
                    }
                }
            );
    }

    @Override
    public Collection<File> getCollection()
    {
        return Collections.unmodifiableSet( emptyFoldersSet );
    }

}
