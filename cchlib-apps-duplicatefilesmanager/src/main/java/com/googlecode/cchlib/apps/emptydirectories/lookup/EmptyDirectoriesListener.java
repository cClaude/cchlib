package com.googlecode.cchlib.apps.emptydirectories.lookup;

import java.util.EventListener;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;

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
     * Invoke when an {@link EmptyFolder} is found
     * 
     * @param emptyFolder New empty folder discover
     */
    public void newEntry( EmptyFolder emptyFolder );
    
    /**
     * Invoke when lookup start
     */
    public void findStarted();

    /**
     * Invoke when lookup finish
     */
    public void findDone();

}
