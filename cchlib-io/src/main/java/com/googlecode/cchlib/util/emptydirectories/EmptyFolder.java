package com.googlecode.cchlib.util.emptydirectories;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NotDirectoryException;

/**
 * Identify an empty folder
 */
public interface EmptyFolder extends Folder
{
    /**
     * Return true if folder contain nothing
     * @return true if folder contain nothing
     */
    boolean isEmpty();

    /**
     * Return true if folder contain only empty folders (no files)
     * @return true if folder contain  only empty folders
     */
    boolean isContaintOnlyEmptyFolders();

    /**
     * Verify folder
     *
     * @throws NotDirectoryException
     *      If object is no more a folder
     * @throws DirectoryNotEmptyException
     *      If object is no more empty
     */
    void check() throws IOException;
}
