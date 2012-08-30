package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.util.EventListener;

/**
 *
 *
 */
public interface EmptyDirectoriesDeleterActionListener
    extends EventListener
{
    /**
     *
     * @param emptyFolderFile
     * @return
     */
    public void workingOn( File emptyFolderFile );
}
