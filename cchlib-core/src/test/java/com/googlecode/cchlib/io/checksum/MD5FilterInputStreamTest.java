package com.googlecode.cchlib.io.checksum;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import com.googlecode.cchlib.util.duplicate.MessageDigestFile;

public class MD5FilterInputStreamTest 
{
    private final static Logger logger = Logger.getLogger( MD5FilterInputStreamTest.class );
    private MessageDigestFile mdf;
    private List<File> fileList;
    private byte[] buffer = new byte[ 1024 ];

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    @Before
    public void setUp() throws Exception
    {
        this.mdf        = new MessageDigestFile( "MD5" );        
        this.fileList   = FilesTestCaseHelper.getFilesListFrom( FileHelper.getTmpDirFile(), FileFilterHelper.fileFileFilter() );
    }

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void test() throws IOException
    {        
        for( File f : fileList ) {
            try {
                test( f );
                }
            catch( FileNotFoundException ignore ) {} // $codepro.audit.disable emptyCatchClause, logExceptions
            }   
    }
    
    private void test( File f ) throws FileNotFoundException, IOException
    {
        final String hashString1;
        final String hashString2;
        
        {
            InputStream          is   = new BufferedInputStream( new FileInputStream( f ) );
            MD5FilterInputStream mfis = new MD5FilterInputStream( is );
            
            while( mfis.read( buffer ) != -1 ) { // $codepro.audit.disable emptyWhileStatement
                // do nothing !
                }
            is.close();
            mfis.close();
            
            hashString1 = mfis.getHashString().toUpperCase();
        }
        {
            byte[] digestKey = mdf.compute( f );
            hashString2 = MessageDigestFile.computeDigestKeyString( digestKey ).toUpperCase();
        }
        logger .info( "F:" + f + " MD5(1)" + hashString1 + " MD5(2)" + hashString2 );
        Assert.assertEquals( hashString2, hashString1 );
    }

}
