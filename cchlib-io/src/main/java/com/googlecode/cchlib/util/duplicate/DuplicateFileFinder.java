package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Perform 2 passes to find duplicate files.
 * <p>
 * To use more than once this this object, call
 * {@link #clear()} at the end of process.
 *
 * @see DuplicateFileFinderHelper
 * @see DefaultDuplicateFileFinder
 * @since 4.2
 */
public interface DuplicateFileFinder {

    /**
     * @since 4.2
     */
    public interface InitialStatus
    {
        /**
         * Returns numbers maximum bytes need to be read by {@link DuplicateFileFinder#find()}
         * @return numbers maximum bytes need to be read.
         */
        long getBytes();

        /**
         * Returns numbers maximum files need to be read by {@link DuplicateFileFinder#find()}
         * @return numbers maximum files need to be read.
         */
        int getFiles();
    }

    /**
     * Current status
     */
    public interface Status extends InitialStatus {
        /**
         * Returns numbers bytes already read by {@link DuplicateFileFinder#find()}
         * @return numbers bytes already read.
         */
        @Override
        long getBytes();

        /**
         * Returns numbers files already read by {@link DuplicateFileFinder#find()}
         * @return numbers files already read.
         */
        @Override
        int getFiles();

        /**
         * Returns numbers set of duplicate already read by {@link DuplicateFileFinder#find()}
         * @return numbers set of duplicate already found.
         */
        int getSets();
    }

    /**
     * Add a {@link DuplicateFileFinderEventListener}
     *
     * @param eventListener eventListener to add
     */
    void addEventListener( DuplicateFileFinderEventListener eventListener );

    /**
     * Remove a {@link DuplicateFileFinderEventListener}
     *
     * @param eventListener eventListener to remove
     */
    void removeEventListener( DuplicateFileFinderEventListener eventListener );

    /** Ask to cancel process as soon as possible */
    void cancelProcess();

    /**
     * Returns cancel status
     * @return cancel status
     */
    boolean isCancelProcess();

    /**
     * Add a {@link File} to list of files to check
     *
     * @param file file to add
     */
    void addFile( File file );

    /**
     * Add a collection of files to list of files to check
     *
     * @param files collection to add
     */
    void addFiles( Iterable<File> files );

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    InitialStatus getInitialStatus();

    /**
     * Launch finder
     */
    void find();

    /**
     * Return {@link Map} of hash code with corresponding files set.
     * @return {@link Map} of hash code with corresponding files set.
     */
    Map<String, Set<File>> getFiles();

    /**
     * Clear internal results
     */
    void clear();

    /**
     * Return current status
     * @return current status
     */
    Status getStatus();
}
