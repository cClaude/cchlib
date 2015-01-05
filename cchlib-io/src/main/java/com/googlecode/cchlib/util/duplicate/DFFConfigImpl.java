package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import com.googlecode.cchlib.util.HashMapSet;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * @since 4.2
 */
final class DFFConfigImpl implements DFFConfig {
    /** Use by PASS1 */
    private final boolean ignoreEmptyFiles;
    /** Use by PASS1 and PASS2 */
    private final HashMapSet<Long,File> mapLengthFiles;
    /** Use by PASS1 and PASS2 */
    private boolean cancelProcess;
    /** Use by PASS2 */
    private final HashMapSet<String,File> mapHashFiles;
    /** Use by PASS2 */
    private final FileDigest[] fileDigests;
    /** Use by PASS2 */
    private int duplicateSetsCount;
    /** Use by PASS2 */
    private int duplicateFilesCount;
    /** Use by PASS2 */
    private long duplicateBytesCount;

    public DFFConfigImpl( //
        final boolean           ignoreEmptyFiles, //
        final FileDigestFactory fileDigestFactory, //
        final int               maxParalleleFiles //
        ) throws NoSuchAlgorithmException
    {
        if( maxParalleleFiles < 1 ) {
            throw new IllegalArgumentException( "maxParalleleFiles must be >= 1 : current value = " + maxParalleleFiles );
        }

        this.ignoreEmptyFiles   = ignoreEmptyFiles;
        this.cancelProcess      = false;
        this.mapLengthFiles     = new HashMapSet<>();
        this.mapHashFiles       = new HashMapSet<>();

        this.fileDigests = new FileDigest[ maxParalleleFiles ];

        for( int i = 0; i<maxParalleleFiles; i++ ) {
            this.fileDigests[ i ] = fileDigestFactory.newInstance();
        }

    }

    @Override
    public boolean isIgnoreEmptyFiles()
    {
        return this.ignoreEmptyFiles;
    }

    @Override
    public boolean isCancelProcess()
    {
        return this.cancelProcess;
    }

    @Override
    public HashMapSet<Long, File> getMapLengthFiles()
    {
        return this.mapLengthFiles;
    }

    @Override
    public void setCancelProcess( final boolean cancelProcess )
    {
        this.cancelProcess = cancelProcess;
    }

    @Override
    public HashMapSet<String, File> getMapHashFiles()
    {
        return this.mapHashFiles;
    }

    @Override
    public int getFileDigestsCount()
    {
        return this.fileDigests.length;
    }

    @Override
    public FileDigest getFileDigests( final int index )
    {
        return this.fileDigests[ index ];
    }

    @Override
    public void duplicateSetsCountAdd( final int sets2add )
    {
        this.duplicateSetsCount += sets2add;
    }

    @Override
    public void duplicateFilesCountAdd( final int files2add )
    {
        this.duplicateFilesCount += files2add;
    }

    @Override
    public void duplicateBytesCountAdd( final long bytes2add )
    {
        this.duplicateBytesCount += bytes2add;
    }

    @Override
    public int getDuplicateSetsCount()
    {
        return this.duplicateSetsCount;
    }

    @Override
    public int getDuplicateFilesCount()
    {
        return this.duplicateFilesCount;
    }

    @Override
    public long getDuplicateBytesCount()
    {
        return this.duplicateBytesCount;
    }

    @Override
    public void clear()
    {
        this.mapLengthFiles.clear();
        this.cancelProcess = false;

        this.mapHashFiles.clear();
        this.duplicateSetsCount  = 0;
        this.duplicateFilesCount = 0;
        this.duplicateBytesCount = 0L;
    }

 }