package cx.ath.choisnet.util.checksum.testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import junit.framework.TestCase;
import cx.ath.choisnet.lang.ByteArrayBuilder;
import cx.ath.choisnet.test.AssertHelper;
import cx.ath.choisnet.test.Assert;
import cx.ath.choisnet.test.SerializableTestCaseHelper;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

public class MessageDigestFileTest extends TestCase
{
    public void test_MessageDigestFile()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException,
                DigestException,
                ClassNotFoundException
    {
        test_MessageDigestFileClone(
                new MessageDigestFile() // MD5
                );
        test_MessageDigestFileClone(
                new MessageDigestFile( "MD2" )
                );
        test_MessageDigestFileClone(
                new MessageDigestFile( "SHA-1" )
                );
        test_MessageDigestFileClone(
                new MessageDigestFile( "SHA-256" )
                );
        test_MessageDigestFileClone(
                new MessageDigestFile( "SHA-384" )
                );
        test_MessageDigestFileClone(
                new MessageDigestFile( "SHA-512" )
                );
    }

    public void test_MessageDigestFileClone(
            MessageDigestFile mdf
            )
    throws  NoSuchAlgorithmException,
            FileNotFoundException,
            IOException,
            DigestException,
            ClassNotFoundException
    {
        test_MessageDigestFile(mdf);

        MessageDigestFile clone = SerializableTestCaseHelper.cloneOverSerialization( mdf );

        test_MessageDigestFile(clone);
    }

    public void test_MessageDigestFile(
            MessageDigestFile mdf
            )
    throws  NoSuchAlgorithmException,
            FileNotFoundException,
            IOException,
            DigestException,
            ClassNotFoundException
    {
        Iterator<File> iter = AssertHelper.getFilesFrom( new File(".").getAbsoluteFile(), null );
        int i = 0;
        while( iter.hasNext() ) {
            File f = iter.next();

            byte[] mdfKey1 = mdf.computeInputStream( f );
            test_File( mdf, f, mdfKey1);

            byte[] mdfKey2 = mdf.compute( f );
            test_File( mdf, f, mdfKey2);

            Assert.assertEquals("Error!",mdfKey1,mdfKey2);

            if( i++ > 10 ) {
                break;
            }
        }
    }

    private void test_File(
            MessageDigestFile   mdf,
            File                f,
            byte[]              mdfKey
            ) throws NoSuchAlgorithmException, IOException
    {
        String mdfKeyStr = MessageDigestFile.computeDigestKeyString( mdfKey );
        String algorithm = mdf.getAlgorithm();

        Assert.assertEquals(mdfKey,mdf.digest());
        String mdfHexStr = mdf.digestString();
        byte[] mdfKey2      = mdf.digest();
        String mdfKey2Str   = MessageDigestFile.computeDigestKeyString(mdfKey2);

        System.out.printf("MDF:%s - %s\n", mdfKeyStr, f );
        System.out.printf("MD_:%s (%s)\n",  mdfHexStr, algorithm );
        System.out.printf("MD2:%s\n", mdfKey2Str );

        if( algorithm.equals( "MD5" ) ) {
            String r2        = getMD5(f);
            System.out.printf("MD>:%s\n", r2 );

            assertEquals(mdfKeyStr,r2);
            assertEquals(mdfHexStr,r2);
            }

        assertEquals(
                "Must be equals",
                mdfKeyStr,
                mdfKey2Str
                );
        Assert.assertEquals(mdfKey,mdfKey2);

        byte[] bytesFromStr = MessageDigestFile.computeDigestKey( mdfHexStr );

        Assert.assertEquals("Error!",mdfKey,bytesFromStr);
    }

    private static String getMD5(byte[] input)
        throws NoSuchAlgorithmException
    {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input);
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                StringBuilder sb = new StringBuilder("0");
                sb.append( hashtext );
                hashtext = sb.toString();
                //hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
    }

    private static String getMD5(File input)
        throws  NoSuchAlgorithmException,
                IOException
    {
        FileInputStream  fis = new FileInputStream(input);
        ByteArrayBuilder bab = new ByteArrayBuilder((int)input.length());

        bab.append( fis );

        return getMD5(bab.array());
    }

}
