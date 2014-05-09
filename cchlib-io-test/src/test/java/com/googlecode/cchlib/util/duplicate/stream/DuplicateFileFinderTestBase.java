package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.DuplicateFileFinderListener;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.MessageDigestFileBuilder;

abstract class DuplicateFileFinderTestBase {

    private static final int         BUFFER_SIZE = 40960;
    private static final String      MD5         = "MD5";
    private MessageDigestFileBuilder messageDigestFileBuilder;

    protected abstract Logger getLogger();
    protected abstract DuplicateFileFinder newDuplicateFileFinder( MessageDigestFileBuilder messageDigestFileBuilder, DuplicateFileFinderListener listener );

    public void setup() throws NoSuchAlgorithmException
    {
        messageDigestFileBuilder = MessageDigestFileBuilder.newMessageDigestFileBuilder( MD5, BUFFER_SIZE );
    }

    public void integration_test() throws NoSuchAlgorithmException, IOException, IllegalStateException, InterruptedException, ExecutionException
    {
        final Path[] startPaths = { FileHelper.getUserHomeDirFile().toPath() };


        getLogger().info( "*** PASS 1" );
        getLogger().info( "*** PASS 1" );
        final long beginNanoTime = System.nanoTime();
        getLogger().info( "*** PASS 1 +++ beginNanoTime " + beginNanoTime );

        final Map<Long, Set<File>>        mapSet   = DuplicateFileBuilder.createFromFileVisitor( Helper.newFileVisitor(), true, startPaths ).compute();
        final DuplicateFileFinderListener listener = newDuplicateFileFinderListener( "integration_test" );

        getLogger().info( "*** PASS 2" );
        getLogger().info( "*** PASS 2" );

        final DuplicateFileFinder dff = newDuplicateFileFinder( messageDigestFileBuilder, listener );
        getLogger().info( "*** PASS 2" );
        getLogger().info( "*** PASS 2" );

        final Map<String, Set<File>> result = dff.computeHash( mapSet );

        final long endNanoTime = System.nanoTime();
        getLogger().info( "result.size() = " + result.size() );
        getLogger().info( "endNanoTime = " + endNanoTime );
        getLogger().info( "diff = " + (endNanoTime - beginNanoTime) );
    }

    public void test_computeHash() throws IOException, IllegalStateException, NoSuchAlgorithmException, InterruptedException, ExecutionException
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
        final DuplicateFileFinder dff = newDuplicateFileFinder( messageDigestFileBuilder, listener );

        final Map<String, Set<File>> result = dff.computeHash( mapSet );

        getLogger().info( "result.size() = " + result.size() );

        Assert.assertEquals( 2, result.size() );
    }

    private Set<File> getEntry( final Map<Long, Set<File>> mapSet, final int entryNumber )
    {
        assert mapSet.size() > entryNumber;

        final Iterator<Set<File>> iterator = mapSet.values().iterator();
        Set<File> value = null;

        for( int i = 0; i<=entryNumber && iterator.hasNext(); i++ ) {
            value  = iterator.next();
        }

        assert value != null;

        return value;

    }

    private Map<Long, Set<File>> newHashMap( final File... files )
    {
        final Map<Long, Set<File>> mapSet = new HashMap<>();

        for( final File file : files ) {
            final Long size = Long.valueOf( file.length() );
            Set<File> set = mapSet.get( size );

            if( set == null ) {
                set = new HashSet<>();
                mapSet.put( size, set );
            }
            set.add( file );
        }

        return mapSet;
    }

    private DuplicateFileFinderListener newDuplicateFileFinderListener( final String testName )
    {
        return new DuplicateFileFinderListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void computeDigest( final File file )
            {
                // GUI display name, length bytes to be read, ...
                if( getLogger().isDebugEnabled() ) {
                    getLogger().debug( testName + " computeDigest:" + file );
                }
            }

            @Override
            public void computeDigest( final File file, final long length )
            {
                // GUI display length already read
            }

            @Override
            public void ioError( final IOException ioe, final File file )
            {
                getLogger().warn( testName + "IOE on " + file + " - " + ioe );
            }

            @Override
            public boolean isCancel()
            {
                return false;
            }

            @Override
            public void hashString( final File file, final String hashString )
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void computeDigest( final long threadId, final File file )
            {
                // GUI display name, length bytes to be read, ...
                if( getLogger().isDebugEnabled() ) {
                    getLogger().debug(  testName + " computeDigest:" + threadId  + " * "+ file );
                }
            }
        };
    }
}
