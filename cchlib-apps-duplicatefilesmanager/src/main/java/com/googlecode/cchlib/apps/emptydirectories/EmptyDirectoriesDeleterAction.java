package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;

/**
 *
 *
 */
public interface EmptyDirectoriesDeleterAction
{
    /**
     *
     * @param emptyFolderFile
     */
    public boolean doAction( File emptyFolderFile );
}
