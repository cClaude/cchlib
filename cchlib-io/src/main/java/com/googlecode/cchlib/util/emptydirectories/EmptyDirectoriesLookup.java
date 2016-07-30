package com.googlecode.cchlib.util.emptydirectories;

import java.nio.file.DirectoryStream.Filter;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 * Find empty directories
 */
public interface EmptyDirectoriesLookup<FILTER>
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
     * @param excludeDirectoriesFileFilter {@link FileFilter} to identify directories <b>to exclude</b>.
     * @throws CancelRequestException
     * @throws ScanIOException
    void lookup( FileFilter excludeDirectoriesFileFilter ) throws CancelRequestException, ScanIOException;
     */

    /**
     * Clear previous list and compute current list of empty directories
     *
     * @param filter {@link Filter} to identify directories <b>to exclude</b>.
     * @throws CancelRequestException
     * @throws ScanIOException
     */
    void lookup( FILTER filter ) throws CancelRequestException, ScanIOException;

    /**
     * Add a listener
     * @param listener Listener to add
     */
    void addListener( EmptyDirectoriesListener listener );

    /**
     * Remove a listener
     * @param listener Listener to remove
     */
    void removeListener( EmptyDirectoriesListener listener );
}
