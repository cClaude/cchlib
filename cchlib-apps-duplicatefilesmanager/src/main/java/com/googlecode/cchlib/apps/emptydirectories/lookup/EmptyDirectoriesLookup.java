package com.googlecode.cchlib.apps.emptydirectories.lookup;

import java.io.FileFilter;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 * Find empty directories
 */
public interface EmptyDirectoriesLookup
{
    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     *
     * @throws CancelRequestException
     */
    public void lookup() throws CancelRequestException;

    /**
     * Clear previous list and compute current list of empty directories
     *
     * @param excludeDirectoriesFile {@link FileFilter} to identify directories to exclude.
     * @throws CancelRequestException
     */
//    public void lookup( FileFilter excludeDirectoriesFile )
//        throws CancelRequestException;
    public void lookup( Filter<Path> excludeDirectoriesFile )
            throws CancelRequestException;

    /**
     * TODOC
     * @param listener
     */
    public void addListener( EmptyDirectoriesListener listener );

    /**
     * TODOC
     * @param listener
     */
    public void removeListener( EmptyDirectoriesListener listener );


}