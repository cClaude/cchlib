package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.DuplicateFileFinderListener;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.MessageDigestFileBuilder;

public class DuplicateFileFinderTest {
    private static final Logger      LOGGER      = Logger.getLogger( DuplicateFileFinderTest.class );

    private static final int         BUFFER_SIZE = 40960;
    private static final String      MD5         = "MD5";
    private MessageDigestFileBuilder messageDigestFileBuilder;

    @Before
    public void setup() throws NoSuchAlgorithmException
    {
        messageDigestFileBuilder = MessageDigestFileBuilder.newMessageDigestFileBuilder( MD5, BUFFER_SIZE );
    }

    @Test
    public void integration_test() throws NoSuchAlgorithmException, IOException
    {
        final Path[] startPaths = { FileHelper.getUserHomeDirFile().toPath() };

        LOGGER.info( "*** PASS 1" );
        LOGGER.info( "*** PASS 1" );
        LOGGER.info( "*** PASS 1" );

        final Map<Long, Set<File>> mapSet = DuplicateFileBuilder.createFromFileVisitor( Helper.newFileVisitor(), startPaths ).compute();
        final DuplicateFileFinderListener listener = newDuplicateFileFinderListener( "integration_test" );

        final DuplicateFileFinder dff = new DuplicateFileFinder( messageDigestFileBuilder, listener );

        LOGGER.info( "*** PASS 2" );
        LOGGER.info( "*** PASS 2" );
        LOGGER.info( "*** PASS 2" );

        final Map<String, Set<File>> result = dff.computeHash( mapSet );

        LOGGER.info( "result.size() = " + result.size() );
    }

    @Test
    public void test_computeHash() throws IOException, IllegalStateException, NoSuchAlgorithmException
    {
        final Map<Long, Set<File>>        mapSet   = newHashMap( IO.createPNGTempFile(), IO.createPNGTempFile() );
        final DuplicateFileFinderListener listener = newDuplicateFileFinderListener( "test_computeHash" );

        Assert.assertEquals( 1, mapSet.size() );
        Assert.assertEquals( 2, mapSet.values().iterator().next().size() );

        final DuplicateFileFinder dff = new DuplicateFileFinder( messageDigestFileBuilder, listener );

        final Map<String, Set<File>> result = dff.computeHash( mapSet );

        LOGGER.info( "result.size() = " + result.size() );

        Assert.assertEquals( 1, result.size() );
    }

    private Map<Long, Set<File>> newHashMap( final File... files )
    {
        final Map<Long, Set<File>> mapSet = new HashMap<Long, Set<File>>();

        for( final File file : files ) {
            final Long size = Long.valueOf( file.length() );
            Set<File> set = mapSet.get( size );

            if( set == null ) {
                set = new HashSet<File>();
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
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( testName + " computeDigest:" + file );
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
                LOGGER.warn( testName + "IOE on " + file + " - " + ioe );
            }

            @Override
            public boolean isCancel()
            {
                return false;
            }
        };
    }
}
