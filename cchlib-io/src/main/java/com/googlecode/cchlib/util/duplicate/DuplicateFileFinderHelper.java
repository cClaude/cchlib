package com.googlecode.cchlib.util.duplicate;

import java.security.NoSuchAlgorithmException;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;


public final class DuplicateFileFinderHelper {

    /** static usage */
    private DuplicateFileFinderHelper() {}

    /**
     *
     * @param ignoreEmptyFiles
     * @param fileDigestFactory
     * @param maxParalleleFiles
     * @return
     * @throws NoSuchAlgorithmException
     */
    @NeedDoc
    public static DuplicateFileFinder newDuplicateFileFinder( //
            final boolean ignoreEmptyFiles, //
            final FileDigestFactory fileDigestFactory, //
            final int maxParalleleFiles //
            ) throws NoSuchAlgorithmException
    {
        final DFFConfig dffConfig = new DFFConfigImpl( ignoreEmptyFiles, fileDigestFactory, maxParalleleFiles );
        final DFFPass1  dffPass1  = new DFFPass1Impl( dffConfig );
        final DFFPass2  dffPass2  = new DFFPass2Impl( dffConfig );

        return new DefaultDuplicateFileFinder( dffConfig, dffPass1, dffPass2 );
    }
}
