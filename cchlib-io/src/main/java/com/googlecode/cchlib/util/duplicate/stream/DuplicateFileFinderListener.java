package com.googlecode.cchlib.util.duplicate.stream;

import com.googlecode.cchlib.util.duplicate.DigestEventListener;

/**
 * Use for each files inspect by {@link DuplicateFileFinder}
 *
 * @since 4.2
 */
public interface DuplicateFileFinderListener extends DigestEventListener {
    /**
     * Invoke when new data available.
     * <br/>
     * Default implementation is empty for backward compatibility.
     *
     * @param dataObserver Observer on actual state of internal Map.
     * @since 4.2
    */
    default void newDataAvailable( final DuplicateFileFinderDataObserver dataObserver ) {};
}
