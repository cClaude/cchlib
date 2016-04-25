package com.googlecode.cchlib.util.duplicate.stream;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinderEventListener;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

public class DuplicateFileFinderTest extends DuplicateFileFinderTest_Common {
    private static final Logger LOGGER = Logger.getLogger( DuplicateFileFinderTest.class );

    @Override
    protected Path[] getStartPaths()
    {
        return StartPathsHelper.getStartPaths();
    }

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
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

    @Override
    protected DuplicateFileFinderUsingStream newDuplicateFileFinder( final FileDigestFactory fileDigestFactory, final DuplicateFileFinderEventListener listener )
    {
        return new DuplicateFileFinderUsingStream(fileDigestFactory, listener);
    }
}
