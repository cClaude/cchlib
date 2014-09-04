package com.googlecode.cchlib.util.duplicate.stream;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class DuplicateFileFinderTest extends DuplicateFileFinderTestBase {
    private static final Logger LOGGER = Logger.getLogger( DuplicateFileFinderTest.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    protected DuplicateFileFinder newDuplicateFileFinder( final MessageDigestFileBuilder messageDigestFileBuilder, final DuplicateFileFinderListener listener )
    {
        return new DuplicateFileFinder( messageDigestFileBuilder, listener, null );
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
