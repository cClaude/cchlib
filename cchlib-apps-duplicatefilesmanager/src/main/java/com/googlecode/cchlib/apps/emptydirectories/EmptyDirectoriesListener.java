package com.googlecode.cchlib.apps.emptydirectories;

import java.util.EventListener;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;

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
     * @param emptyDirectoryFile
     */
    //public void newEntry( File emptyDirectoryFile );
    public void newEntry( EmptyFolder folder );


    /**
     *
     */
    public void findStarted();

    /**
     *
     */
    public void findDone();

}
