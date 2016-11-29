package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import com.googlecode.cchlib.util.HashMapSet;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * @since 4.2
 */
public interface DFFConfig
{
    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    boolean isCancelProcess();

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    boolean isIgnoreEmptyFiles();

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    FileDigest getFileDigest();

    /**
     * NEEDDOC
     * @param cancelProcess NEEDDOC
     */
    void setCancelProcess( boolean cancelProcess );

    /**
     * Values creates by pass1
     * @return NEEDDOC
     */
    HashMapSet<Long,File> getMapLengthFiles();

    /**
     * Values creates by pass2
     * @return NEEDDOC
     */
    HashMapSet<String,File> getMapHashFiles();

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    int getDuplicateSetsCount();

    /**
     * NEEDDOC
     * @param sets2add NEEDDOC
     */
    void duplicateSetsCountAdd( int sets2add );

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    int getDuplicateFilesCount();

    /**
     * NEEDDOC
     * @param files2add NEEDDOC
     */
    void duplicateFilesCountAdd( int files2add );

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    long getDuplicateBytesCount();

    /**
     * NEEDDOC
     * @param bytes2add NEEDDOC
     */
    void duplicateBytesCountAdd( long bytes2add );

    /**
     * NEEDDOC
     */
    void clear();
}
