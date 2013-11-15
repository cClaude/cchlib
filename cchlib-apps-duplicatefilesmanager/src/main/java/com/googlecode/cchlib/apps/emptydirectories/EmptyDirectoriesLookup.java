package com.googlecode.cchlib.apps.emptydirectories;

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
     * @throws ScanIOException
     */
    void lookup() throws CancelRequestException, ScanIOException;

    /**
     * Clear previous list and compute current list of empty directories
     *
     * @param excludeDirectoriesFile {@link FolderFilter} to identify directories <b>to exclude</b>.
     * @throws CancelRequestException
     * @throws ScanIOException
     */
    void lookup( FolderFilter excludeDirectoriesFile )
            throws CancelRequestException, ScanIOException;

    /**
     * TODOC
     * @param listener
     */
    void addListener( EmptyDirectoriesListener listener );

    /**
     * TODOC
     * @param listener
     */
    void removeListener( EmptyDirectoriesListener listener );


}
