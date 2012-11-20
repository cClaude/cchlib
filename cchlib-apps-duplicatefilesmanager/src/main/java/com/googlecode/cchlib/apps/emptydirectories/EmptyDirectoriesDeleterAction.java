package com.googlecode.cchlib.apps.emptydirectories;

import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;

/**
 * TODOC
 */
public interface EmptyDirectoriesDeleterAction
{
    /**
     * TODOC
     * @param emptyFolderFile TODOC
     */
    public boolean doAction( EmptyFolder emptyFolderFile );
}
