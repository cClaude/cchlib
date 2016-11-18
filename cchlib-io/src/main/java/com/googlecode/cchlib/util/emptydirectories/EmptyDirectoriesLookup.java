package com.googlecode.cchlib.util.emptydirectories;

import java.nio.file.DirectoryStream.Filter;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.emptydirectories.lookup.ExcludeDirectoriesFileFilter;

/**
 * Find empty directories
 *
 * @param <FILTER> Specify how to filter directory for lookup
 *       (see {@link ExcludeDirectoriesFileFilter}) *
 */
@SuppressWarnings("squid:S00119") // Type one char only ! Why ?
public interface EmptyDirectoriesLookup<FILTER>
{
    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     *
     * @throws CancelRequestException
     * @throws ScanIOException
     */
    void lookup() throws CancelRequestException;

    /**
     * Clear previous list and compute current list of empty directories
     *
     * @param filter {@link Filter} to identify directories <b>to exclude</b>.
     * @throws CancelRequestException
     * @throws ScanIOException
     */
    void lookup( FILTER filter ) throws CancelRequestException;

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
