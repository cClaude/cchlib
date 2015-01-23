package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import com.googlecode.cchlib.util.HashMapSet;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * @since 4.2
 */
public interface DFFConfig {

    boolean isCancelProcess();
    boolean isIgnoreEmptyFiles();

    FileDigest getFileDigest();

    void setCancelProcess( boolean cancelProcess );

    /**
     * Values creates by pass1
     */
    HashMapSet<Long,File> getMapLengthFiles();

    /**
     * Values creates by pass2
     */
    HashMapSet<String,File> getMapHashFiles();

    int getDuplicateSetsCount();
    void duplicateSetsCountAdd( int sets2add );

    int getDuplicateFilesCount();
    void duplicateFilesCountAdd( int files2add );

    long getDuplicateBytesCount();
    void duplicateBytesCountAdd( long bytes2add );

    void clear();
}
