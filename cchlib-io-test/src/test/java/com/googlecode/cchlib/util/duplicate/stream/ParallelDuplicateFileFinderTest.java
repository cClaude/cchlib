package com.googlecode.cchlib.util.duplicate.stream;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinderUsingStream.DuplicateFileFinderListener;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinderUsingStream.MessageDigestFileBuilder;

public class ParallelDuplicateFileFinderTest extends DuplicateFileFinderTest_Common {
    private static final Logger LOGGER = Logger.getLogger( ParallelDuplicateFileFinderTest.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    protected Path[] getStartPaths()
    {
        // TODO implements a better solution to avoid very long test !
        return new Path[] { FileHelper.getUserHomeDirFile().toPath() };
    }

    @Override
    protected DuplicateFileFinderUsingStream newDuplicateFileFinder( final MessageDigestFileBuilder messageDigestFileBuilder, final DuplicateFileFinderListener listener )
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

    /*
    @Test
    public void test_computeHashParallel()
        throws IOException, IllegalStateException, NoSuchAlgorithmException, InterruptedException, ExecutionException
    {
        final Map<Long, Set<File>> mapSet = newHashMap(
                IO.createPNGTempFile(),
                IO.createPNGTempFile(),
                IO.createZipTempFile(),
                IO.createZipTempFile()
                );

        Assert.assertEquals( 2, mapSet.size() );
        Assert.assertEquals( 2, getEntry( mapSet, 0 ).size() );
        Assert.assertEquals( 2, getEntry( mapSet, 1 ).size() );

        final DuplicateFileFinderListener listener = newDuplicateFileFinderListener( "test_computeHash" );
        final DuplicateFileFinder dff = new ParallelDuplicateFileFinder( messageDigestFileBuilder, listener );

        final Map<String, Set<File>> result = dff.computeHash( mapSet );

        LOGGER.info( "result.size() = " + result.size() );

        Assert.assertEquals( 2, result.size() );
    }
*/

}
