package com.googlecode.cchlib.util.duplicate;

import java.security.NoSuchAlgorithmException;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;


public final class DuplicateFileFinderHelper {

    /** static usage */
    private DuplicateFileFinderHelper() {}

    /**
     *
     * @param ignoreEmptyFiles
     * @param fileDigestFactory
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IllegalArgumentException
     */
    @NeedDoc
    public static DuplicateFileFinder newDuplicateFileFinder( //
            final boolean                    ignoreEmptyFiles, //
            @Nonnull final FileDigestFactory fileDigestFactory //
            ) throws
                NoSuchAlgorithmException,
                IllegalArgumentException // NOSONAR
    {
        final DFFConfig  dffConfig = new DFFConfigImpl( ignoreEmptyFiles, fileDigestFactory );
        final DFFPass1   dffPass1  = new DFFPass1Impl( dffConfig );
        final DFFPass2   dffPass2  = new DFFPass2Impl( dffConfig );

        return new DefaultDuplicateFileFinder( dffConfig, dffPass1, dffPass2 );
    }

    /**
     *
     * @param ignoreEmptyFiles
     * @param fileDigestFactory
     * @param maxParallelFiles
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IllegalArgumentException
     */
    @NeedDoc
    public static DuplicateFileFinder newDuplicateFileFinderAlgo2( //
            final boolean                       ignoreEmptyFiles, //
            @Nonnull final FileDigestFactory    fileDigestFactory, //
            final int                           maxParallelFiles //
        ) throws
            NoSuchAlgorithmException,
            IllegalArgumentException // NOSONAR
    {
        final DFFConfig2 dffConfig = new DFFConfigImpl2( ignoreEmptyFiles, fileDigestFactory, maxParallelFiles );
        final DFFPass1   dffPass1  = new DFFPass1Impl( dffConfig );
        final DFFPass2   dffPass2  = new DFFPass2Impl2( dffConfig );

        return new DefaultDuplicateFileFinder( dffConfig, dffPass1, dffPass2 );
    }
}
