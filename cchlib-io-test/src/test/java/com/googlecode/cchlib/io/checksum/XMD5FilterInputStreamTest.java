package com.googlecode.cchlib.io.checksum;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.test.FilesTestCaseHelper;
import com.googlecode.cchlib.util.duplicate.XMessageDigestFile;

@SuppressWarnings({ "resource", "deprecation" })
public class XMD5FilterInputStreamTest
{
    private static final Logger LOGGER = Logger.getLogger( XMD5FilterInputStreamTest.class );
    private XMessageDigestFile mdf;
    private List<File> fileList;
    private final byte[] buffer = new byte[ 1024 ];

    @Before
    public void setUp() throws Exception
    {
        this.mdf        = new XMessageDigestFile( "MD5" );
        this.fileList   = FilesTestCaseHelper.getFilesListFrom( FileHelper.getTmpDirFile(), FileFilterHelper.fileFileFilter() );
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

    private void testMD5FilterInputStream( final File file ) throws FileNotFoundException, IOException
    {
        final long lastModified = file.lastModified();
        final String hashString1;
        final String hashString2;

        {
            MD5FilterInputStream mfis;
            try (InputStream is = new BufferedInputStream( new FileInputStream( file ) )) {
                mfis = new MD5FilterInputStream( is );
                while( mfis.read( this.buffer ) != -1 ) { // $codepro.audit.disable emptyWhileStatement
                    // do nothing !
                }
            }
            mfis.close();

            hashString1 = mfis.getHashString().toUpperCase();
        }

        if( lastModified == file.lastModified() ) {
            final byte[] digestKey = this.mdf.compute( file );
            hashString2 = XMessageDigestFile.computeDigestKeyString( digestKey ).toUpperCase();

            if( lastModified == file.lastModified() ) {
                LOGGER.info( "F:" + file + " MD5(1)" + hashString1 + " MD5(2)" + hashString2 );
                Assert.assertEquals( hashString2, hashString1 );
            } // else ignore this file if modified
        } // else ignore this file if modified
    }

}
