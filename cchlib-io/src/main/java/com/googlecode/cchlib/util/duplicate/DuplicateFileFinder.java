package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.util.Map;
import java.util.Set;

public interface DuplicateFileFinder {

    public interface Status
    {
        /**
         * Returns numbers of bytes need to be read by pass2
         * @return numbers of bytes need to be read by pass2
         */
        long getPass2Bytes();

        /**
         * Returns numbers of files need to be read by pass2
         * @return numbers of files need to be read by pass2
         */
        int getPass2Files();
    }

    void addEventListener( DuplicateFileFinderEventListener eventListener );

    int getDuplicateFilesCount();

    int getDuplicateSetsCount();

    Map<String, Set<File>> getFiles();

    Status getInitialStatus();

    boolean isCancelProcess();

    void addFile( File file );
    void addFiles( Iterable<File> files );

    void find();

    void cancelProcess();

    void clear();

    void removeEventListener( DuplicateFileFinderEventListener eventListener );
}
