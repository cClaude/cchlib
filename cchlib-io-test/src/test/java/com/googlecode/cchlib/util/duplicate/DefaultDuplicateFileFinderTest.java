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
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.Status;

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

    @Test(expected=IllegalArgumentException.class)
    public void testIllegalArgumentException() throws NoSuchAlgorithmException
    {
        new DefaultDuplicateFileFinder(null, 0, false);
    }

    @Test(expected=NullPointerException.class)
    public void testNullPointerException() throws NoSuchAlgorithmException
    {
        new DefaultDuplicateFileFinder(null, 1, false);
    }

    @Test
    public void testCompare2Duplicates_pass1() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = new DefaultDuplicateFileFinder(this.fileDigestFactory, 1, false);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createZipTempFile( "notduplicate" );

        dff.addFile( file1 );
        dff.addFile( file2 );

        Status initialStatus = dff.getInitialStatus();

        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() + file2.length() );

        dff.addFile( file3 );

        initialStatus = dff.getInitialStatus();

        // new file
        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() + file2.length() );
    }


    @Test(expected=IllegalStateException.class)
    public void testCompare2Duplicates_pass2_IllegalStateException() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = new DefaultDuplicateFileFinder(this.fileDigestFactory, 1, false);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );

        dff.addFile( file1 );
        dff.addFile( file2 );

        Status initialStatus = dff.getInitialStatus();

        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() + file2.length() );

        dff.find();

        initialStatus = dff.getInitialStatus();
    }

    @Test
    public void testCompare2Duplicate_pass2() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = new DefaultDuplicateFileFinder(this.fileDigestFactory, 1, false);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createZipTempFile( "notduplicate" );

        final DuplicateFileFinderEventListener eventListener = new LoggerEventListener();
        dff.addEventListener( eventListener  );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 ); // this file is not a duplicate.

        final Status initialStatus = dff.getInitialStatus();

        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() + file2.length() );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        Assertions.assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        Assertions.assertThat( firstEntry.getKey() ).isEqualTo( IO.MD5_FOR_PNG_FILE );

        final Set<File> setOfFiles = firstEntry.getValue();
        Assertions.assertThat( setOfFiles.size() ).isEqualTo( 2 );
        Assertions.assertThat( setOfFiles ).containsOnly( file1, file2 );

        dff.removeEventListener( eventListener );
    }

    @Test
    public void testCompare3Duplicates_and_remove_one_v1() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = new DefaultDuplicateFileFinder(this.fileDigestFactory, 1, false);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createPNGTempFile( "dup-file3-removed" );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        Status initialStatus = dff.getInitialStatus();

        // File 3 is expected here.
        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 3 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() * 3 );

        // Delete file 3 now !
        file3.delete();

        initialStatus = dff.getInitialStatus();

        // File 3 no more expected here.
        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() * 2 );

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
        final DuplicateFileFinder dff = new DefaultDuplicateFileFinder(this.fileDigestFactory, 1, false);

        final File file1 = IO.createPNGTempFile( "dup-file1" );
        final File file2 = IO.createPNGTempFile( "dup-file2" );
        final File file3 = IO.createPNGTempFile( "dup-file3-removed" );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        final Status initialStatus = dff.getInitialStatus();

        // Delete file 3 now !
        file3.delete();

        // File 3 is still expected here.
        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 3 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() * 3 );

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
        final DuplicateFileFinder dff = new DefaultDuplicateFileFinder(this.fileDigestFactory, 1, false);

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

        final Status initialStatus = dff.getInitialStatus();

        // File 3 is not expected here.
        Assertions.assertThat( initialStatus.getPass2Files() ).isEqualTo( 2 );
        Assertions.assertThat( initialStatus.getPass2Bytes() ).isEqualTo( file1.length() * 2 );

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
