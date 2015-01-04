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
 * @since 4.2
 */
public interface DuplicateFileFinder {

    public interface Status
    {
        /**
         * Returns numbers of bytes need to be read by pass2
         * @return numbers of bytes need to be read by pass2
         */
        long getBytesToRead();

        /**
         * Returns numbers of files need to be read by pass2
         * @return numbers of files need to be read by pass2
         */
        int getFilesToRead();
    }

    void addEventListener( DuplicateFileFinderEventListener eventListener );
    void removeEventListener( DuplicateFileFinderEventListener eventListener );

    void cancelProcess();
    boolean isCancelProcess();

    void addFile( File file );

    void addFiles( Iterable<File> files );

    Status getInitialStatus();

    void find();

    Map<String, Set<File>> getFiles();

    void clear();

    // TODO
    // int getDuplicateSetsCount();

    // TODO
    // int getDuplicateFilesCount();
}
