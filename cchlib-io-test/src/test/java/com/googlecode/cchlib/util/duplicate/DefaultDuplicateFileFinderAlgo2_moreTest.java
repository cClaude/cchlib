package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.InitialStatus;

/**
 * @since 4.2
 */
public class DefaultDuplicateFileFinderAlgo2_moreTest extends DefaultDuplicateFileFinderAlgo2_Data {

    private static final Logger LOGGER = Logger.getLogger( DefaultDuplicateFileFinderAlgo2_moreTest.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    private DuplicateFileFinder newDuplicateFileFinder() throws NoSuchAlgorithmException
    {
        return DuplicateFileFinderHelper.newDuplicateFileFinderAlgo2( DO_NOT_IGNORE_EMPTY_FILE, getFileDigestFactory(), MAX_PARALLEL_FILES );
    }

    @Test
    public void testCompare3Duplicates_and_remove_one_v2() throws NoSuchAlgorithmException, IOException
    {
        final DuplicateFileFinder dff = newDuplicateFileFinder();

        // 6 Files with same size : 3 diff file
        final File file1 = createPNGTempFile( "content1-file1" );
        final File file2 = createPNGTempFile( "content1-file2" ); // same that file1
        final File file3 = createPNGTempFile( "content1-file3" ); // same that file1

        final File file4 = createPNGTempFile2( "content2-file4" ); // not duplicate

        final File file5 = createPNGTempFile3( "content3-file5" );
        final File file6 = createPNGTempFile3( "content3-file6" ); // same that file5

        dff.addFile( file1 );
        dff.addFile( file2 );
        dff.addFile( file3 );
        dff.addFile( file4 );
        dff.addFile( file5 );
        dff.addFile( file6 );

        final InitialStatus initialStatus = dff.getInitialStatus();

        // quick check pass 1
        Assertions.assertThat( initialStatus.getFiles() ).isEqualTo( 6 );
        Assertions.assertThat( initialStatus.getBytes() ).isEqualTo( file1.length() * 6 );

        dff.find();

        final Map<String, Set<File>> files = dff.getFiles();

        // 2 different files
        Assertions.assertThat( files.size() ).isEqualTo( 2 );

        final Iterator<Entry<String, Set<File>>> iterator = files.entrySet().iterator();

        final Entry<String, Set<File>> firstEntry  = iterator.next();
        final Entry<String, Set<File>> secondEntry = iterator.next();

        final Entry<String, Set<File>> result1;
        final Entry<String, Set<File>> result2;

        if( IO.MD5_FOR_PNG_FILE.equals( firstEntry.getKey() ) ) {
            result1 = firstEntry;
            result2 = secondEntry;
        } else {
            result2 = firstEntry;
            result1 = secondEntry;
        }

        Assertions.assertThat( result1.getKey() ).isEqualTo( IO.MD5_FOR_PNG_FILE );
        Assertions.assertThat( result1.getValue() ).containsOnly( file1, file2, file3 );
        Assertions.assertThat( result2.getValue() ).containsOnly( file5, file6 );
    }

}
