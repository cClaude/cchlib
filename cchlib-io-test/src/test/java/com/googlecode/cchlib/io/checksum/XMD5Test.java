package com.googlecode.cchlib.io.checksum;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.test.FilesTestCaseHelper;

public class XMD5Test
{
    private static final Logger LOGGER = Logger.getLogger( XMD5Test.class );

    @SuppressWarnings("deprecation")
    private com.googlecode.cchlib.util.duplicate.XMessageDigestFile mdf;
    private Iterable<File>                                          fileList;

    @Before
    @SuppressWarnings("deprecation")
    public void setUp() throws Exception
    {
        this.mdf      = new com.googlecode.cchlib.util.duplicate.XMessageDigestFile( "MD5" );
        this.fileList = createTestFiles();
    }

    private static final Iterable<File> createTestFiles()
    {
        //final File fromDirectory = FileHelper.getTmpDirFile() - issues with /tmp
        // some files could change...
        final File fromDirectory = new File( "." );

        return FilesTestCaseHelper.getFiles( fromDirectory, FileFilterHelper.fileFileFilter() );
    }

    @Test
    public void test_getHashString() throws IOException
    {
        for( final File file : this.fileList ) {
            try {
                test_getHashString( file );
            }
            catch( final FileNotFoundException ignore ) {
                // Ignore
            }
        }
    }

    private void test_getHashString( final File file ) throws IOException
    {
        final long lastModified = file.lastModified();
        final long length       = file.length();

        final String hashString1 = MD5.getHashString( file ).toUpperCase();
        final String hashString2 = computeOldVersionHashString( file ).toUpperCase();

        if( ToolBox.fileNotChanged( file, lastModified, length ) ) {
            LOGGER.info(
                    "File: " + file +
                    " MD5(1)" + hashString1 +
                    " MD5(2)" + hashString2
                    );

            assertThat( hashString1 )
                .as( "Bad checksum for file: " + file )
                .isEqualTo( hashString2 );
        }
    }

    @SuppressWarnings("deprecation")
    private byte[] computeOldVersionHash( final File file ) throws IOException
    {
        return this.mdf.compute( file );
    }

    @SuppressWarnings("deprecation")
    private String computeOldVersionHashString( final File file ) throws IOException
    {
        final byte[] digestKey = computeOldVersionHash( file );

        return com.googlecode.cchlib.util.duplicate.XMessageDigestFile.computeDigestKeyString( digestKey );
    }

    @Test
    public void test_getHash() throws IOException
    {
        for( final File file : this.fileList ) {
            final long   lastModified = file.lastModified();
            final long   length       = file.length();

            try {
                final byte[] hash1     = MD5.getHash( file );
                final byte[] digestKey = computeOldVersionHash( file );

                if( ToolBox.fileNotChanged( file, lastModified, length ) ) {
                    LOGGER.info( "File: " + file );

                    assertThat( digestKey )
                        .as( "Hash arrays did not match for " + file )
                        .isEqualTo( hash1 );
                }
            }
            catch( final FileNotFoundException ignore ) {
                // Ignore
            }
        }
    }
}
