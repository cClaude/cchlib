package com.googlecode.cchlib.io.checksum;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.test.FilesTestCaseHelper;
import com.googlecode.cchlib.util.duplicate.XMessageDigestFile;

@SuppressWarnings({ "deprecation" })
public class XMD5FilterInputStreamTest
{
    private static final Logger LOGGER = Logger.getLogger( XMD5FilterInputStreamTest.class );
    private final byte[] buffer        = new byte[ 1024 ];

    private XMessageDigestFile mdf;
    private List<File>        fileList;

    @Before
    public void setUp() throws Exception
    {
        this.mdf      = new XMessageDigestFile( "MD5" );
        this.fileList = FilesTestCaseHelper.getFilesListFrom( FileHelper.getTmpDirFile(), FileFilterHelper.fileFileFilter() );
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
            final String hashString2 = computeOldNewVersion( file );
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

    private String computeOldNewVersion( final File file ) throws IOException
    {
        final byte[] digestKey = this.mdf.compute( file );

        return XMessageDigestFile.computeDigestKeyString( digestKey ).toUpperCase();
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
