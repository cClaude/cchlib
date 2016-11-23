// $codepro.audit.disable
package com.googlecode.cchlib.io.checksum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.test.FilesTestCaseHelper;
import com.googlecode.cchlib.util.duplicate.XMessageDigestFile;

@SuppressWarnings("deprecation")
public class XMD5Test
{
    private static final Logger LOGGER = Logger.getLogger( XMD5Test.class );
    private XMessageDigestFile   mdf;
    private List<File>          fileList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    @Before
    public void setUp() throws Exception
    {
        this.mdf        = new XMessageDigestFile( "MD5" );
        this.fileList   = FilesTestCaseHelper.getFilesListFrom( FileHelper.getTmpDirFile(), FileFilterHelper.fileFileFilter() );
    }

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void test_getHashString() throws IOException
    {
        for( final File f : this.fileList ) {
            try {
                final String hashString1 = MD5.getHashString( f ).toUpperCase();

                final byte[] digestKey = this.mdf.compute( f );
                final String hashString2 = XMessageDigestFile.computeDigestKeyString( digestKey ).toUpperCase();

                LOGGER.info( "F:" + f + " MD5(1)" + hashString1 + " MD5(2)" + hashString2 );

                Assert.assertEquals( hashString2, hashString1 );
                }
            catch( final FileNotFoundException ignore ) {} // $codepro.audit.disable emptyCatchClause, logExceptions
            }
    }
    @Test

    public void test_getHash() throws IOException
    {
        for( final File f : this.fileList ) {
            try {
                final byte[] hash1 = MD5.getHash( f );
                final byte[] digestKey = this.mdf.compute( f );

                LOGGER.info( "F:" + f );

                Assert.assertArrayEquals( digestKey, hash1 );
                }
            catch( final FileNotFoundException ignore ) {} // $codepro.audit.disable emptyCatchClause, logExceptions
            }
    }

}
