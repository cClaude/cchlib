package com.googlecode.cchlib.apps.emptydirectories;

import java.util.EventListener;

/**
 * TODOC
 */
public interface EmptyDirectoriesDeleterActionListener
    extends EventListener
{
    /**
     * TODOC
     * @param emptyFolderFile TODOC
     */
    public void workingOn( EmptyFolder emptyFolderFile );
}
