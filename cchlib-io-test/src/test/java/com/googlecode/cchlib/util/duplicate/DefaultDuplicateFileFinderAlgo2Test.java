package com.googlecode.cchlib.util.duplicate;

import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * @since 4.2
 */
public class DefaultDuplicateFileFinderAlgo2Test extends DefaultDuplicateFileFinder_Common {

    private static final Logger LOGGER = Logger.getLogger( DefaultDuplicateFileFinderAlgo2Test.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    protected DuplicateFileFinder newDuplicateFileFinder( final boolean ignoreEmptyFiles, final FileDigestFactory fileDigestFactory, final int maxParallelFiles )
            throws NoSuchAlgorithmException
    {
        return DuplicateFileFinderHelper.newDuplicateFileFinderAlgo2( ignoreEmptyFiles, fileDigestFactory, maxParallelFiles );
    }
}
