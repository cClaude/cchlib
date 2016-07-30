package com.googlecode.cchlib.util.emptydirectories;

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
    boolean isCancel();

    /**
     *
     * @param folder
     */
    void newEntry( EmptyFolder folder );

    /**
     *
     */
    void findStarted();

    /**
     *
     */
    void findDone();

}
