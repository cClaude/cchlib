package com.googlecode.cchlib.io.checksum;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.test.FilesTestCaseHelper;
import com.googlecode.cchlib.util.duplicate.XMessageDigestFile;

@SuppressWarnings("deprecation")
public class XMD5Test
{
    private static final Logger LOGGER = Logger.getLogger( XMD5Test.class );
    private XMessageDigestFile  mdf;
    private List<File>          fileList;

    @Before
    public void setUp() throws Exception
    {
        this.mdf      = new XMessageDigestFile( "MD5" );
        this.fileList = FilesTestCaseHelper.getFilesListFrom( FileHelper.getTmpDirFile(), FileFilterHelper.fileFileFilter() );
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
        final long   lastModified = file.lastModified();
        final long   length       = file.length();

        final String hashString1 = MD5.getHashString( file ).toUpperCase();
        final String hashString2 = computeOldNewVersion( file );

        if( ToolBox.fileNotChanged( file, lastModified, length ) ) {
            LOGGER.info(
                    "File: " + file +
                    " MD5(1)" + hashString1 +
                    " MD5(2)" + hashString2
                    );

            assertThat( hashString1 )
                .isEqualTo( hashString2 )
                .as( "Bad checksum for file: " + file );
        }
    }

    private String computeOldNewVersion( final File file ) throws IOException
    {
        final byte[] digestKey = this.mdf.compute( file );

        return XMessageDigestFile.computeDigestKeyString( digestKey ).toUpperCase();
    }

    @Test
    public void test_getHash() throws IOException
    {
        for( final File file : this.fileList ) {
            final long   lastModified = file.lastModified();
            final long   length       = file.length();

            try {
                final byte[] hash1     = MD5.getHash( file );
                final byte[] digestKey = this.mdf.compute( file );

                if( ToolBox.fileNotChanged( file, lastModified, length ) ) {
                    LOGGER.info( "File: " + file );

                    Assert.assertArrayEquals( digestKey, hash1 );
                }
            }
            catch( final FileNotFoundException ignore ) {
                // Ignore
            }
        }
    }
}
