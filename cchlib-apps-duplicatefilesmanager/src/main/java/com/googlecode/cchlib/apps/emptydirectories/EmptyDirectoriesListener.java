package com.googlecode.cchlib.apps.emptydirectories;

import java.util.EventListener;

/**
 *
 *
 */
public interface EmptyDirectoriesListener
    extends EventListener
{
    /**
     * Returns true to cancel scan
     * @return true to cancel scan
     */
    public boolean isCancel();

    /**
     *
     * @param createEmptyFolder
     */
    public void newEntry( EmptyFolder createEmptyFolder );
    //public void newEntry( File emptyDirectoryFile );
    
    /**
     *
     */
    public void findStarted();

    /**
     *
     */
    public void findDone();

}
