package com.googlecode.cchlib.util.duplicate;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.Test;
import com.googlecode.cchlib.io.IOTestHelper;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.InitialStatus;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.Status;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

public abstract class DefaultDuplicateFileFinder_Common extends Base
{
    protected abstract DuplicateFileFinder newDuplicateFileFinder( //
            final boolean           ignoreEmptyFiles, //
            final FileDigestFactory fileDigestFactory //
            ) throws NoSuchAlgorithmException;

    @Test(expected=IllegalArgumentException.class)
    public void testIllegalArgumentException() throws NoSuchAlgorithmException
    {
        newDuplicateFileFinder(DO_NOT_IGNORE_EMPTY_FILE, (FileDigestFactory)null);
    }

    //@Test(expected=NullPointerException.class)
    @Test(expected=IllegalArgumentException.class)
    public void testNullPointerException() throws NoSuchAlgorithmException
    {
        newDuplicateFileFinder(DO_NOT_IGNORE_EMPTY_FILE, null);
    }

    @Test
    public void testCompare2Duplicates_pass1() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDuplicateFileFinder(DO_NOT_IGNORE_EMPTY_FILE, getFileDigestFactory());

        final File file1 = IOTestHelper.createPNGTempFile( "dup-file1" );
        final File file2 = IOTestHelper.createPNGTempFile( "dup-file2" );
        final File file3 = IOTestHelper.createZipTempFile( "notduplicate" );

        dff.addFile( file1 );
        dff.addFile( file2 );

        InitialStatus initialStatus = dff.getInitialStatus();

        assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );

        dff.addFile( file3 );

        initialStatus = dff.getInitialStatus();

        // new file
        assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );
    }

    @Test(expected=IllegalStateException.class)
    public void testCompare2Duplicates_pass2_IllegalStateException() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDuplicateFileFinder(DO_NOT_IGNORE_EMPTY_FILE, getFileDigestFactory());

        final File file1 = IOTestHelper.createPNGTempFile( "dup-file1" );
        final File file2 = IOTestHelper.createPNGTempFile( "dup-file2" );

        dff.addFile( file1 );
        dff.addFile( file2 );

        InitialStatus initialStatus = dff.getInitialStatus();

        assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );

        dff.find();

        initialStatus = dff.getInitialStatus();
    }

    @Test
    public void testCompare2Duplicate_pass2() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDuplicateFileFinder(DO_NOT_IGNORE_EMPTY_FILE, getFileDigestFactory());

        final File file1 = IOTestHelper.createPNGTempFile( "dup-file1" );
        final File file2 = IOTestHelper.createPNGTempFile( "dup-file2" );
        final File file3 = IOTestHelper.createZipTempFile( "notduplicate" );

        final DuplicateFileFinderEventListener eventListener = newLoggerEventListener();
        dff.addEventListener( eventListener  );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 ); // this file is not a duplicate.

        check_getStatus( dff, 0L, 0, 0 );

        final InitialStatus initialStatus = dff.getInitialStatus();

        assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() + file2.length() );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        assertThat( firstEntry.getKey() ).isEqualTo( IOTestHelper.MD5_FOR_PNG_FILE );

        final Set<File> setOfFiles = firstEntry.getValue();
        assertThat( setOfFiles.size() ).isEqualTo( 2 );
        assertThat( setOfFiles ).containsOnly( file1, file2 );

        dff.removeEventListener( eventListener );

        check_getStatus( dff, file1.length() * 2, 2, 1 );

        dff.clear();

        check_getStatus( dff, 0L, 0, 0 );
   }

    private void check_getStatus( final DuplicateFileFinder dff, final Long expected_bytes, final int expected_files, final Integer expected_sets  )
    {
        final Status status = dff.getStatus();

        assertThat( status.getBytes() ).isEqualTo( expected_bytes );
        assertThat( status.getFiles() ).isEqualTo( expected_files );
        assertThat( status.getSets() ).isEqualTo( expected_sets );
    }

    @Test
    public void testCompare3Duplicates_and_remove_one_v1() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDuplicateFileFinder(DO_NOT_IGNORE_EMPTY_FILE, getFileDigestFactory());

        final File file1 = IOTestHelper.createPNGTempFile( "dup-file1" );
        final File file2 = IOTestHelper.createPNGTempFile( "dup-file2" );
        final File file3 = IOTestHelper.createPNGTempFile( "dup-file3-removed" );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        InitialStatus initialStatus = dff.getInitialStatus();

        // File 3 is expected here.
        assertThat( initialStatus.getFiles() ).isEqualTo( 3 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 3 );

        // Delete file 3 now !
        file3.delete();

        initialStatus = dff.getInitialStatus();

        // File 3 no more expected here.
        assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 2 );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        assertThat( firstEntry.getKey() ).isEqualTo( IOTestHelper.MD5_FOR_PNG_FILE );

        final Set<File> setOfFiles = firstEntry.getValue();
        assertThat( setOfFiles.size() ).isEqualTo( 2 );
        assertThat( setOfFiles ).containsOnly( file1, file2 );
    }

    @Test
    public void testCompare3Duplicates_and_remove_one_v2() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDuplicateFileFinder(DO_NOT_IGNORE_EMPTY_FILE, getFileDigestFactory());

        final File file1 = IOTestHelper.createPNGTempFile( "dup-file1" );
        final File file2 = IOTestHelper.createPNGTempFile( "dup-file2" );
        final File file3 = IOTestHelper.createPNGTempFile( "dup-file3-removed" );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        final InitialStatus initialStatus = dff.getInitialStatus();

        // Delete file 3 now !
        file3.delete();

        // File 3 is still expected here.
        assertThat( initialStatus.getFiles() ).isEqualTo( 3 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 3 );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        assertThat( firstEntry.getKey() ).isEqualTo( IOTestHelper.MD5_FOR_PNG_FILE );

        // File 3 is no more expected here.
        final Set<File> setOfFiles = firstEntry.getValue();
        assertThat( setOfFiles.size() ).isEqualTo( 2 );
        assertThat( setOfFiles ).containsOnly( file1, file2 );
    }

    @Test
    public void testCompareTwoDuplicate_xx() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDuplicateFileFinder(
                DO_NOT_IGNORE_EMPTY_FILE,
                getFileDigestFactory()
                );

        final File file1 = IOTestHelper.createPNGTempFile( "dup-file1" );
        final File file2 = IOTestHelper.createPNGTempFile( "dup-file2" );
        final File file3 = IOTestHelper.createPNGTempFile( "dup-file3-removed" );

        final DuplicateFileFinderEventListener eventListener = newLoggerEventListener();
        dff.addEventListener( eventListener  );

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );

        // Delete file 3 now !
        file3.delete();

        final InitialStatus initialStatus = dff.getInitialStatus();

        // File 3 is not expected here.
        assertThat( initialStatus.getFiles() ).isEqualTo( 2 );
        assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 2 );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();
        assertThat( files.size() ).isEqualTo( 1 );

        final Entry<String, Set<File>> firstEntry = files.entrySet().iterator().next();
        assertThat( firstEntry.getKey() ).isEqualTo( IOTestHelper.MD5_FOR_PNG_FILE );

        final Set<File> setOfFiles = firstEntry.getValue();
        assertThat( setOfFiles.size() ).isEqualTo( 2 );
        assertThat( setOfFiles ).containsOnly( file1, file2 );
    }
}
