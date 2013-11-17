// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.lang.ByteArrayBuilder;
import com.googlecode.cchlib.test.ArrayAssert;
import com.googlecode.cchlib.test.FilesTestCaseHelper;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;

public class MessageDigestFileTest
{
    private static final long MAX_FILE_LENGTH = 10 * 1024 * 1024;

    @Test
    public void test_MessageDigestFile()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException,
                DigestException,
                ClassNotFoundException
    {
        doTest_MessageDigestFileClone(
                new MessageDigestFile() // MD5
                );
        doTest_MessageDigestFileClone(
                new MessageDigestFile( "MD2" )
                );
        doTest_MessageDigestFileClone(
                new MessageDigestFile( "SHA-1" )
                );
        doTest_MessageDigestFileClone(
                new MessageDigestFile( "SHA-256" )
                );
        doTest_MessageDigestFileClone(
                new MessageDigestFile( "SHA-384" )
                );
        doTest_MessageDigestFileClone(
                new MessageDigestFile( "SHA-512" )
                );
    }

    private void doTest_MessageDigestFileClone(
            MessageDigestFile mdf
            )
    throws  NoSuchAlgorithmException,
            FileNotFoundException,
            IOException,
            DigestException,
            ClassNotFoundException
    {
        doTest_MessageDigestFile(mdf);

        MessageDigestFile clone = SerializableTestCaseHelper.cloneOverSerialization( mdf );

        doTest_MessageDigestFile(clone);
    }

    private void doTest_MessageDigestFile(
            MessageDigestFile mdf
            )
    throws  NoSuchAlgorithmException,
            FileNotFoundException,
            IOException,
            DigestException,
            ClassNotFoundException
    {
        Iterator<File> iter = FilesTestCaseHelper.getFilesFrom( new File(".").getAbsoluteFile(), null );
        int i = 0;
        while( iter.hasNext() ) {
            File f = iter.next();

            if( f.length() > MAX_FILE_LENGTH ) {
                continue; // Skip big files to speed up testing
                }

            byte[] mdfKey1 = mdf.computeInputStream( f );
            doTest_File( mdf, f, mdfKey1);

            byte[] mdfKey2 = mdf.compute( f );
            doTest_File( mdf, f, mdfKey2);

            ArrayAssert.assertEquals("Error!",mdfKey1,mdfKey2);

            if( i++ > 10 ) {
                break;
                }
            }
    }

    private void doTest_File(
            MessageDigestFile   mdf,
            File                f,
            byte[]              mdfKey
            ) throws NoSuchAlgorithmException, IOException
    {
        String mdfKeyStr = MessageDigestFile.computeDigestKeyString( mdfKey );
        String algorithm = mdf.getAlgorithm();

        ArrayAssert.assertEquals(mdfKey,mdf.digest());
        String mdfHexStr = mdf.digestString();
        byte[] mdfKey2      = mdf.digest();
        String mdfKey2Str   = MessageDigestFile.computeDigestKeyString(mdfKey2);

        System.out.printf("MDF:%s - %s\n", mdfKeyStr, f );
        System.out.printf("MD_:%s (%s)\n",  mdfHexStr, algorithm );
        System.out.printf("MD2:%s\n", mdfKey2Str );

        if( algorithm.equals( "MD5" ) ) {
            String r2        = getMD5(f);
            System.out.printf("MD>:%s\n", r2 );

            Assert.assertEquals(mdfKeyStr,r2);
            Assert.assertEquals(mdfHexStr,r2);
            }

        Assert.assertEquals(
                "Must be equals",
                mdfKeyStr,
                mdfKey2Str
                );
        ArrayAssert.assertEquals(mdfKey,mdfKey2);

        byte[] bytesFromStr = MessageDigestFile.computeDigestKey( mdfHexStr );

        ArrayAssert.assertEquals("Error!",mdfKey,bytesFromStr);
    }

    private static String getMD5(byte[] input)
        throws NoSuchAlgorithmException
    {
        final StringBuilder sb = new StringBuilder(); 
        final MessageDigest md            = MessageDigest.getInstance("MD5");
        final byte[]        messageDigest = md.digest(input);
        final BigInteger    number        = new BigInteger(1, messageDigest);
        
        String  hashtext = number.toString(16);

        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            sb.setLength( 0 );
            sb.append( '0' ); 
            sb.append( hashtext );
            hashtext = sb.toString();
            }

        return hashtext.toUpperCase();
    }

    private static String getMD5( final File input )
        throws  NoSuchAlgorithmException,
                IOException
    {
        FileInputStream  fis = new FileInputStream( input );

        try {
            ByteArrayBuilder bab = new ByteArrayBuilder((int)input.length());

            bab.append( fis );

            return getMD5(bab.array());
            }
        finally {
            fis.close();
            }
    }

}
