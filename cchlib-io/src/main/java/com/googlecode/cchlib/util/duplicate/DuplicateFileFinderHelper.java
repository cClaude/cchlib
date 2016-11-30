package com.googlecode.cchlib.util.duplicate;

import java.security.NoSuchAlgorithmException;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * Allow to create {@link DuplicateFileFinder} objects
 */
public final class DuplicateFileFinderHelper
{
    private DuplicateFileFinderHelper()
    {
        // All static
    }

    /**
     * Create a {@link DuplicateFileFinder}
     *
     * @param ignoreEmptyFiles true to ignore empty files
     * @param fileDigestFactory a valid {@link FileDigestFactory}
     * @return an initialized DuplicateFileFinder
     * @throws NoSuchAlgorithmException if algorithm on {@code fileDigestFactory}
     *         is not valid
     * @throws IllegalArgumentException if a parameter is not valid
     */
    @NeedDoc
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static DuplicateFileFinder newDuplicateFileFinder( //
            final boolean                    ignoreEmptyFiles, //
            @Nonnull final FileDigestFactory fileDigestFactory //
            ) throws
                NoSuchAlgorithmException,
                IllegalArgumentException
    {
        final DFFConfig  dffConfig = new DFFConfigImpl( ignoreEmptyFiles, fileDigestFactory );
        final DFFPass1   dffPass1  = new DFFPass1Impl( dffConfig );
        final DFFPass2   dffPass2  = new DFFPass2Impl( dffConfig );

        return new DefaultDuplicateFileFinder( dffConfig, dffPass1, dffPass2 );
    }

    /**
     * Create a {@link DuplicateFileFinder}
     *
     * @param ignoreEmptyFiles true to ignore empty files
     * @param fileDigestFactory a valid {@link FileDigestFactory}
     * @param maxParallelFiles NEEDDOC
     * @return an initialized DuplicateFileFinder
     * @throws NoSuchAlgorithmException if algorithm on {@code fileDigestFactory}
     *         is not valid
     * @throws IllegalArgumentException if a parameter is not valid
     */
    @NeedDoc
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static DuplicateFileFinder newDuplicateFileFinderAlgo2( //
            final boolean                       ignoreEmptyFiles, //
            @Nonnull final FileDigestFactory    fileDigestFactory, //
            final int                           maxParallelFiles //
        ) throws
            NoSuchAlgorithmException,
            IllegalArgumentException
    {
        final DFFConfig2 dffConfig = new DFFConfigImpl2( ignoreEmptyFiles, fileDigestFactory, maxParallelFiles );
        final DFFPass1   dffPass1  = new DFFPass1Impl( dffConfig );
        final DFFPass2   dffPass2  = new DFFPass2Impl2( dffConfig );

        return new DefaultDuplicateFileFinder( dffConfig, dffPass1, dffPass2 );
    }
}
