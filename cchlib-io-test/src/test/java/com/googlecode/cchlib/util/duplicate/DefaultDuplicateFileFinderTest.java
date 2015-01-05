package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.InitialStatus;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.Status;
import com.googlecode.cchlib.util.duplicate.digest.DefaultFileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * @since 4.2
 */
public class DefaultDuplicateFileFinderTest {

    private final class LoggerEventListener implements DuplicateFileFinderEventListener {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCancel()
        {
            return false;
        }

        @Override
        public void analysisStart( final File file )
        {
            // Analysis start
            LOGGER.info( "analysisStart for file: " + file );
        }

        @Override
        public void analysisStatus( final File file, final long length )
        {
            LOGGER.info( "analysisStatus for file: " + file + " , " + length );
        }

        @Override
        public void analysisDone( final File file, final String hashString )
        {
            LOGGER.info( "analysisDone for file: " + file + " : hash = " + hashString );
        }

        @Override
        public void ioError( final File file, final IOException ioe )
        {
            LOGGER.info( "ioError on file: " + file, ioe );
        }
    }

    private static final Logger LOGGER = Logger.getLogger( DefaultDuplicateFileFinderTest.class );

    private static final String DEFAULT_ALGORITHM = "MD5";
    private static final int DEFAULT_BUFFER_SIZE = 128;

    private final FileDigestFactory fileDigestFactory = new DefaultFileDigestFactory( DEFAULT_ALGORITHM, DEFAULT_BUFFER_SIZE );



    private DuplicateFileFinder newDefaultDuplicateFileFinder(final boolean ignoreEmptyFiles, final FileDigestFactory fileDigestFactory, final int maxParalleleFiles   ) throws NoSuchAlgorithmException
    {
        return DuplicateFileFinderHelper.newDuplicateFileFinder( ignoreEmptyFiles, fileDigestFactory, maxParalleleFiles );
    }

    @Test(expected=IllegalArgumentException.class)
    public void testIllegalArgumentException() throws NoSuchAlgorithmException
    {
        newDefaultDuplicateFileFinder( false, null, 0);
    }

    @Test(expected=NullPointerException.class)
    public void testNullPointerException() throws NoSuchAlgorithmException
    {
        newDefaultDuplicateFileFinder(false, null, 1 );
    }

    @Test
    public void testCompare2Duplicates_pass1() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDefaultDuplicateFileFinder(false, this.fileDigestFactory, 1 );

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createZipTempFile( "notduplicate" );

        dff.addFile( file1 );
        dff.addFile( file2 );

        InitialStatus initialStatus = dff.getInitialStatus();

        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );

        dff.addFile( file3 );

        initialStatus = dff.getInitialStatus();

        // new file
        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );
    }

    @Test(expected=IllegalStateException.class)
    public void testCompare2Duplicates_pass2_IllegalStateException() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDefaultDuplicateFileFinder( false, this.fileDigestFactory, 1 );

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );

        dff.addFile( file1 );
        dff.addFile( file2 );

        InitialStatus initialStatus = dff.getInitialStatus();

        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );

        dff.find();

        initialStatus = dff.getInitialStatus();
    }

    @Test
    public void testCompare2Duplicate_pass2() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDefaultDuplicateFileFinder(false, this.fileDigestFactory, 1 );

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createZipTempFile( "notduplicate" );

        final DuplicateFileFinderEventListener eventListener = new LoggerEventListener();
        dff.addEventListener( eventListener  );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 ); // this file is not a duplicate.

        check_getStatus( dff, 0L, 0, 0 );

        final InitialStatus initialStatus = dff.getInitialStatus();

        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        Assertions.assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        Assertions.assertThat( firstEntry.getKey() ).isEqualTo( IO.MD5_FOR_PNG_FILE );

        final Set<File> setOfFiles = firstEntry.getValue();
        Assertions.assertThat( setOfFiles.size() ).isEqualTo( 2 );
        Assertions.assertThat( setOfFiles ).containsOnly( file1, file2 );

        dff.removeEventListener( eventListener );

        check_getStatus( dff, file1.length() * 2, 2, 1 );

        dff.clear();

        check_getStatus( dff, 0L, 0, 0 );
   }

    private void check_getStatus( final DuplicateFileFinder dff, final Long expected_bytes, final int expected_files, final Integer expected_sets  )
    {
        final Status status = dff.getStatus();

        Assertions.assertThat( status.getBytes() ).isEqualTo( expected_bytes );
        Assertions.assertThat( status.getFiles() ).isEqualTo( expected_files );
        Assertions.assertThat( status.getSets() ).isEqualTo( expected_sets );
    }

    @Test
    public void testCompare3Duplicates_and_remove_one_v1() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDefaultDuplicateFileFinder(false, this.fileDigestFactory, 1);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createPNGTempFile( "dup-file3-removed" );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        InitialStatus initialStatus = dff.getInitialStatus();

        // File 3 is expected here.
        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 3 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 3 );

        // Delete file 3 now !
        file3.delete();

        initialStatus = dff.getInitialStatus();

        // File 3 no more expected here.
        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 2 );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        Assertions.assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        Assertions.assertThat( firstEntry.getKey() ).isEqualTo( IO.MD5_FOR_PNG_FILE );

        final Set<File> setOfFiles = firstEntry.getValue();
        Assertions.assertThat( setOfFiles.size() ).isEqualTo( 2 );
        Assertions.assertThat( setOfFiles ).containsOnly( file1, file2 );
    }

    @Test
    public void testCompare3Duplicates_and_remove_one_v2() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDefaultDuplicateFileFinder(false, this.fileDigestFactory, 1);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createPNGTempFile( "dup-file3-removed" );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        final InitialStatus initialStatus = dff.getInitialStatus();

        // Delete file 3 now !
        file3.delete();

        // File 3 is still expected here.
        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 3 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 3 );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        Assertions.assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        Assertions.assertThat( firstEntry.getKey() ).isEqualTo( IO.MD5_FOR_PNG_FILE );

        // File 3 is no more expected here.
        final Set<File> setOfFiles = firstEntry.getValue();
        Assertions.assertThat( setOfFiles.size() ).isEqualTo( 2 );
        Assertions.assertThat( setOfFiles ).containsOnly( file1, file2 );
    }

    @Test
    public void testCompareTwoDuplicate_xx() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDefaultDuplicateFileFinder(false, this.fileDigestFactory, 1);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createPNGTempFile( "dup-file3-removed" );

        final DuplicateFileFinderEventListener eventListener = new LoggerEventListener();
        dff.addEventListener( eventListener  );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        // Delete file 3 now !
        file3.delete();

        final InitialStatus initialStatus = dff.getInitialStatus();

        // File 3 is not expected here.
        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 2 );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        Assertions.assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        Assertions.assertThat( firstEntry.getKey() ).isEqualTo( IO.MD5_FOR_PNG_FILE );

        final Set<File> setOfFiles = firstEntry.getValue();
        Assertions.assertThat( setOfFiles.size() ).isEqualTo( 2 );
        Assertions.assertThat( setOfFiles ).containsOnly( file1, file2 );
    }
}
