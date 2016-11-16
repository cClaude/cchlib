package com.googlecode.cchlib.util.duplicate;

import java.security.NoSuchAlgorithmException;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * @since 4.2
 */
public class DFFConfigImpl2 extends DFFConfigImpl implements DFFConfig2
{
    /** Use by PASS2 */
    private final FileDigest[] fileDigests;

    /**
     * NEEDDOC XXX
     *
     * @param ignoreEmptyFiles
     * @param fileDigestFactory
     * @param maxParallelFiles
     * @throws NoSuchAlgorithmException
     * @throws IllegalArgumentException
     */
    public DFFConfigImpl2( //
        final boolean                    ignoreEmptyFiles, //
        @Nonnull final FileDigestFactory fileDigestFactory, //
        final int                        maxParallelFiles //
        ) throws NoSuchAlgorithmException, IllegalArgumentException
    {
        super( ignoreEmptyFiles, fileDigestFactory );

        if( maxParallelFiles < 1 ) {
            throw new IllegalArgumentException( "maxParalleleFiles must be >= 1 : current value = " + maxParallelFiles );
        }

        this.fileDigests    = new FileDigest[ maxParallelFiles ];
        this.fileDigests[0] = getFileDigest();

        for( int i = 1; i<maxParallelFiles; i++ ) {
            this.fileDigests[ i ] = fileDigestFactory.newInstance();
        }
    }

    @Override
    public int getFileDigestsCount()
    {
        return this.fileDigests.length;
    }

    @Override
    public FileDigest getFileDigest( final int index )
    {
        return this.fileDigests[ index ];
    }
}
