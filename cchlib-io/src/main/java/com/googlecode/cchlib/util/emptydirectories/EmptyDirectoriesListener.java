package com.googlecode.cchlib.util.emptydirectories;

import java.util.EventListener;

/**
 * Listener for {@link EmptyDirectoriesLookup}
 */
public interface EmptyDirectoriesListener
    extends EventListener
{
    /**
     * Returns true to cancel scan
     * @return true to cancel scan
     */
    boolean isCancel();

    /**
     * Invoke each time a new {@link EmptyFolder} is found
     *
     * @param folder the {@link EmptyFolder}
     */
    void newEntry( EmptyFolder folder );

    /**
     * Invoke before scan start
     */
    void findStarted();

    /**
     * Invoke at the end of scan
     */
    void findDone();

}
