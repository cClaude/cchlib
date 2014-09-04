package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Current state of internal map
 *
 * @since 4.2
 */
public interface DuplicateFileFinderDataObserver {
    /**
     * This intermediate result must be use only for display propose.
     * @return an unmodifiable view of current data
     */
    Map<String, Set<File>> get();
}