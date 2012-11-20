package com.googlecode.cchlib.apps.emptydirectories;

import java.util.EventListener;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;

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
