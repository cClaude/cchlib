package com.googlecode.cchlib.io.checksum;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class XMD5FilterInputStreamTest
{
    private static final Logger LOGGER = Logger.getLogger( XMD5FilterInputStreamTest.class );
    private final byte[] buffer        = new byte[ 1024 ];

    @SuppressWarnings("deprecation")
    private com.googlecode.cchlib.util.duplicate.XMessageDigestFile mdf;
    private Iterable<File>                                          fileList;

    @Before
    @SuppressWarnings("deprecation")
    public void setUp() throws Exception
    {
        this.mdf      = new com.googlecode.cchlib.util.duplicate.XMessageDigestFile( "MD5" );
        this.fileList = TestMD5Helper.createTestFiles();
    }

    @Test
    public void testMD5FilterInputStream() throws IOException
    {
        for( final File f : this.fileList ) {
            try {
                testMD5FilterInputStream( f );
            }
            catch( final FileNotFoundException ignore ) {
                // ignore
            }
        }
    }

    private void testMD5FilterInputStream( final File file )
        throws IOException
    {
        final long   lastModified = file.lastModified();
        final long   length       = file.length();

        final String hashString1 = computeHashNewVersion( file, this.buffer );

        if( ToolBox.fileNotChanged( file, lastModified, length ) ) {
            final String hashString2 = computeOldVersionHashString( file ).toUpperCase();
            final String hashString3 = MD5.getHashString( file ).toUpperCase();

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
    }

    private String computeOldVersionHashString( final File file ) throws IOException
    {
        return TestMD5Helper.computeOldVersionHashString( this.mdf, file );
    }

    private static String computeHashNewVersion( final File file, final byte[] buffer )
        throws IOException
    {
        try( final InputStream is = new BufferedInputStream( new FileInputStream( file ) ) ) {
            try( final MD5FilterInputStream mfis = new MD5FilterInputStream( is ) ) {
                while( mfis.read( buffer ) != -1 ) {
                    // do nothing !
                }
                return mfis.getHashString().toUpperCase();
            }
        }
    }

}
