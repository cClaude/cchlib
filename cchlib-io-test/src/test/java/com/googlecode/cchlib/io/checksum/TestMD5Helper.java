package com.googlecode.cchlib.io.checksum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.test.FilesTestCaseHelper;

//Not public
final class TestMD5Helper
{
    private TestMD5Helper()
    {
        // All static
    }

    public static final Iterable<File> createTestFiles()
    {
        // Warn having some issues with /tmp because some files could change
        // so using "current" directory which is expected to be folder "cchlib"
        final File fromDirectory = new File( "." );

        return FilesTestCaseHelper.getFiles( fromDirectory, FileFilterHelper.fileFileFilter() );
    }

    @SuppressWarnings("deprecation")
    public static byte[] computeOldVersionHash(
        final com.googlecode.cchlib.util.duplicate.XMessageDigestFile mdf,
        final File                                                    file
        ) throws FileNotFoundException, IOException
    {
        return mdf.compute( file );
    }

    @SuppressWarnings("deprecation")
    public static String computeOldVersionHashString(
        final com.googlecode.cchlib.util.duplicate.XMessageDigestFile mdf,
        final File                                                    file
        ) throws FileNotFoundException, IOException
    {
        final byte[] digestKey = computeOldVersionHash( mdf, file );

        return com.googlecode.cchlib.util.duplicate.XMessageDigestFile.computeDigestKeyString( digestKey );
    }

}
