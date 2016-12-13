package com.googlecode.cchlib.util.duplicate;

import java.security.NoSuchAlgorithmException;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;
import com.googlecode.cchlib.util.duplicate.single.DFFConfigImpl;
import com.googlecode.cchlib.util.duplicate.single.DFFPass2Impl;

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

        return new DefaultDuplicateFileFinder(
                dffConfig,
                new DFFPass1Impl( dffConfig ),
                new DFFPass2Impl( dffConfig )
                );
    }
}
