package com.googlecode.cchlib.util.duplicate.stream;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.DuplicateFileFinderListener;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.MessageDigestFileBuilder;

public class ParallelDuplicateFileFinderTest extends DuplicateFileFinderTestBase {
    private static final Logger LOGGER = Logger.getLogger( ParallelDuplicateFileFinderTest.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    protected DuplicateFileFinder newDuplicateFileFinder( final MessageDigestFileBuilder messageDigestFileBuilder, final DuplicateFileFinderListener listener )
    {
        final int nThreads = Runtime.getRuntime().availableProcessors();

        return new ParallelDuplicateFileFinder(messageDigestFileBuilder, listener, nThreads );
    }

    @Override
    @Before
    public void setup() throws NoSuchAlgorithmException
    {
        super.setup();
    }

    @Override
    @Test
    public void integration_test() throws NoSuchAlgorithmException, IOException, IllegalStateException, InterruptedException, ExecutionException
    {
        super.integration_test();
    }

    @Override
    @Test
    public void test_computeHash() throws IOException, IllegalStateException, NoSuchAlgorithmException, InterruptedException, ExecutionException
    {
        super.test_computeHash();
    }
}
