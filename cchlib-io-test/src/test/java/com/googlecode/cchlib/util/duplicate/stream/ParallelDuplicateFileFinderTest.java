package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.io.IOTestHelper;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinderEventListener;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

public class ParallelDuplicateFileFinderTest extends DuplicateFileFinderTest_Common {

    private static final Logger LOGGER = Logger.getLogger( ParallelDuplicateFileFinderTest.class );

    public ParallelDuplicateFileFinderTest()
    {
        super( LOGGER );
    }

    @Override
    protected Path[] getStartPaths()
    {
        return StartPathsHelper.getStartPaths();
   }

    @Override
    protected DuplicateFileFinderUsingStream newDuplicateFileFinder( //
        final FileDigestFactory messageDigestFileBuilder, //
        final DuplicateFileFinderEventListener listener //
        )
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
    @Test // could take to much time / memory
    @Ignore
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


    @Test
    @Ignore
    public void test_computeHashParallel()
        throws IOException, IllegalStateException, NoSuchAlgorithmException, InterruptedException, ExecutionException
    {
        final Map<Long, Set<File>> mapSet = newHashMap(
                IOTestHelper.createPNGTempFile(),
                IOTestHelper.createPNGTempFile(),
                IOTestHelper.createZipTempFile(),
                IOTestHelper.createZipTempFile()
                );

        Assert.assertEquals( 2, mapSet.size() );
        Assert.assertEquals( 2, getEntry( mapSet, 0 ).size() );
        Assert.assertEquals( 2, getEntry( mapSet, 1 ).size() );

//        final DuplicateFileFinderListener listener = newDuplicateFileFinderListener( "test_computeHash" );
//        final DuplicateFileFinder dff = new ParallelDuplicateFileFinder( messageDigestFileBuilder, listener );
//
//        final Map<String, Set<File>> result = dff.computeHash( mapSet );
//
//        LOGGER.info( "result.size() = " + result.size() );
//
//        Assert.assertEquals( 2, result.size() );
    }
}
