package com.googlecode.cchlib.io.checksum;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestListener;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

public class XMD5Test
{
    private static final Logger LOGGER = Logger.getLogger( XMD5Test.class );

    private static final int bufferSize = 2048;

    @SuppressWarnings("deprecation")
    private com.googlecode.cchlib.util.duplicate.XMessageDigestFile mdf;
    private Iterable<File>                                          fileList;
    private FileDigest                                              fdMd5;

    @Before
    @SuppressWarnings("deprecation")
    public void setUp() throws Exception
    {
        this.mdf      = new com.googlecode.cchlib.util.duplicate.XMessageDigestFile( "MD5" );
        this.fileList = TestMD5Helper.createTestFiles();
        this.fdMd5    = MessageDigestAlgorithms.MD5.newFileDigest( bufferSize );
    }

    @Test
    public void test_getHashString() throws IOException, CancelRequestException
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

    private void test_getHashString( final File file ) throws IOException, CancelRequestException
    {
        final long lastModified = file.lastModified();
        final long length       = file.length();

        final String hashString1 = MD5.getHashString( file ).toUpperCase();
        final String hashString2 = computeOldVersionHashString( file ).toUpperCase();
        final String hashString3 = computeFileDigest( file ).toUpperCase();

        if( ToolBox.fileNotChanged( file, lastModified, length ) ) {
            LOGGER.info(
                    "File: " + file +
                    " MD5(1)" + hashString1 +
                    " MD5(2)" + hashString2 +
                    " MD5(3)" + hashString3
                    );

            assertThat( hashString1 )
                .as( "Bad checksum for file: " + file )
                .isEqualTo( hashString2 )
                .isEqualTo( hashString3 );
        }
    }

    private String computeFileDigest( final File file ) throws IOException, CancelRequestException
    {
        final FileDigestListener listener = new FileDigestListener()
        {
            @Override
            public void computeDigest( final File file, final int length )
            {
                // Empty
            }

            @Override
            public boolean isCancel()
            {
                return false;
            }

        };

        this.fdMd5.computeFile( file, listener );

        return this.fdMd5.digestString();
    }

    private byte[] computeOldVersionHash( final File file ) throws IOException
    {
        return TestMD5Helper.computeOldVersionHash( this.mdf, file );
    }

    private String computeOldVersionHashString( final File file ) throws IOException
    {
        return TestMD5Helper.computeOldVersionHashString( this.mdf, file );
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
