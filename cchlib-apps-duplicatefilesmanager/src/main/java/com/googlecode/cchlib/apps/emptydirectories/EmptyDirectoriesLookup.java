package com.googlecode.cchlib.apps.emptydirectories;

import java.io.FileFilter;
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
    public void lookup( FileFilter excludeDirectoriesFile )
        throws CancelRequestException;

}
