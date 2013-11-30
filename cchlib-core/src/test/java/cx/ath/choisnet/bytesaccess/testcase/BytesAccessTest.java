// $codepro.audit.disable numericLiterals
package cx.ath.choisnet.bytesaccess.testcase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import cx.ath.choisnet.bytesaccess.BytesAccess;
import cx.ath.choisnet.bytesaccess.BytesAccessException;

public class BytesAccessTest
{
    private static final Logger LOGGER = Logger.getLogger(BytesAccessTest.class);
    private static final Object NULL_OBJECT = null;
    private static final Random RND = new Random(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.useOfRandom
    private static final byte[] BYTES = {
            (byte)0xDE, (byte)0xAD,
            (byte)0xBE, (byte)0xFF,
            (byte)0xBA, (byte)0xBE,
            0x00,       (byte)0xFF,
            (byte)0xAA, (byte)~0xAA,
    };

    @Test
    public void testFileConstructor() throws IOException
    {
        // Create a File
        final File f = File.createTempFile( '~' + getClass().getName(), ".tmp" );

        // Fill it
        final int         length = 50;
        final InputStream is     = getRandomInputStream( length );
        try {
            final FileOutputStream fos = new FileOutputStream( f );

            try {
                int c;

                while( (c = is.read()) >= 0 ) {
                    fos.write( c );
                    }
                }
            finally {
                fos.close();
                }
            }
        finally {
            is.close();
            }
        Assert.assertEquals( "File Bad length!", length, f.length() );

        //
        BytesAccess ba = new TstOnlyBytesAccess( f, length );
        Assert.assertEquals( "Bad length!", length, ba.getBytesCopy().length );

        ba = new TstOnlyBytesAccess( f, length / 2 );
        Assert.assertEquals( "Bad length!", length/2, ba.getBytesCopy().length );

        try {
            ba = new TstOnlyBytesAccess( f, length * 2 );
            Assert.fail( "Should fail here !" );
            }
        catch( BytesAccessException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
            // OK
            }

        // It there any lock ?
        boolean isDelete = f.delete();

        Assert.assertTrue( "Can't delete file [" + f + ']', isDelete );
    }

    @Test
    public void testCompareTo() throws NullPointerException, BytesAccessException, IOException
    {
        int           bytesLength = 100;
        BytesAccess[] tests       = buildBytesAccessArray( bytesLength );

        LOGGER.info( "testCompareTo() - step 1 : cmpToCount=" + tests.length );
        for( BytesAccess ba : tests ) {
            TstOnlyBytesAccess baCopy = new TstOnlyBytesAccess( ba );

            testCompareTo( ba, baCopy );
            testEquals( ba, baCopy );

            testCompareTo( ba, getConstantBytesAccess() );
            testEquals( ba, getConstantBytesAccess() );
            }

        LOGGER.info( "testCompareTo() - step 2" );
        testCompareTo(
                new TstOnlyBytesAccess( BYTES, 0, BYTES.length ),
                getConstantBytesAccess()
                );
        LOGGER.info( "testCompareTo() - step 3" );
        testCompareTo( // not equal !
                new TstOnlyBytesAccess( BYTES, 0, BYTES.length ),
                new TstOnlyBytesAccess( getRandomInputStream(BYTES.length), BYTES.length )
                );
    }

    private static BytesAccess[] buildBytesAccessArray(
            int bytesLength
             )
        throws  NullPointerException,
                BytesAccessException,
                IOException
    {
    BytesAccess test = new TstOnlyBytesAccess(
            getRandomInputStream(bytesLength),
            bytesLength
            );
    BytesAccess[] tests = {
        new TstOnlyBytesAccess( bytesLength ),
        test,
        new TstOnlyBytesAccess( test ),
        new TstOnlyBytesAccessDebug( bytesLength ),
        new TstOnlyBytesAccessDebug(
                getRandomInputStream(bytesLength),
                bytesLength
                ),
        new TstOnlyBytesAccessDebug( test )
        };

        return tests;
    }

    private static  void testCompareTo( final BytesAccess ba, final BytesAccess compareTo )
    {
        byte[] compareToBytes = compareTo.getBytesCopy();

        String r1 = ba.advanceCompareTo( compareTo );
        String r2 = ba.advanceCompareTo( compareToBytes );

        StringBuilder debug = new StringBuilder();

        debug.append( "ba1:" );
        for( Byte b : ba.getBytesCopy()) {
            debug.append( String.format( "%1$02X ", b ) );
            }
        LOGGER.info( debug );
        debug.setLength( 0 );
        debug.append( "ba2:" );
        for( Byte b : compareToBytes) {
            debug.append( String.format( "%1$02X ", b ) );
            }
        LOGGER.info( debug );

        LOGGER.info( "r1: " + r1  );
        LOGGER.info( "r2: " + r2  );

        // Verify than r(n) give similar result
        Assert.assertTrue( "mismatch advanceCompareTo(BytesAccess)=" + r1
                    + " advanceCompareTo(byte[])=" + r2,
                (r1==null)?
                        ((r2==null)?true:r2.equals( r1 ))
                        :
                        r1.equals( r2 )
                );

        if( ba.getBytesCopy().length != compareToBytes.length ) {
            // Can't Continue !
            try {
                int  r3 = ba.compareTo( compareTo );
                Assert.fail("Should fail here : r3 = ba.compareTo( compareTo ): " + r3 );
                }
            catch( IllegalArgumentException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                }
            try {
                int r4 = ba.compareTo( compareToBytes );
                Assert.fail("Should fail here : r4 = ba.compareTo( compareToBytes ): " + r4 );
                }
            catch( IllegalArgumentException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                }
            try {
                long r5 =BytesAccess.compare( ba.getBytesCopy(), compareToBytes );
                Assert.fail("Should fail here : r5 = BytesAccess.compare( ba.getBytesCopy(), compareToBytes ): " + r5);
                }
            catch( IllegalArgumentException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                }
            return;
        }

        int    r3 = ba.compareTo( compareTo );
        int    r4 = ba.compareTo( compareToBytes );
        long   r5 = BytesAccess.compare( ba.getBytesCopy(), compareToBytes );
        LOGGER.info( String.format( "r3: %1$08X", r3) );
        LOGGER.info( String.format( "r4: %1$08X", r4) );
        LOGGER.info( String.format( "r5: %1$016X", r5) );

        Assert.assertTrue( "mismatch compareTo(BytesAccess)=" + r3
                    + " compareTo(byte[])=" + r4,
                r3==r4
                );
        int r5int;
        int r3int;
        boolean cmpCanNotStoreOffsetInInteger;

        if( (r5 & 0xFFFFFFFF00000000L) != 0 ) {
            r3int = r3 & 0xFFFF0000;
            r5int = (int)(r5 & 0x000000000000FFFFL);
            cmpCanNotStoreOffsetInInteger = true;

            Assert.assertEquals(
                    "compareTo(byte[]) bad value:",
                    r3int | 0xFFFF0000,
                    r3
                    );
            }
        else {
            r3int = r3;
            r5int = (int)(r5 & 0x00000000FFFFFFFFL);
            cmpCanNotStoreOffsetInInteger = false;
            }

        Assert.assertEquals(
                String.format(
                        "cmpCanNotStoreOffsetInInteger not valid(1) %1$X - %2$X", r3, r5
                        ),
                (r5 & 0xFFFFFFFFFFFF0000L) != 0,
                cmpCanNotStoreOffsetInInteger
                );
        Assert.assertEquals(
                String.format(
                        "cmpCanNotStoreOffsetInInteger not valid(2) %1$X - %2$X", r3, r5
                        ),
                (r3 & 0xFFFFFFFFFFFF0000L) != 0,
                cmpCanNotStoreOffsetInInteger
                );


        Assert.assertEquals( "mismatch compareTo(BytesAccess)="
                    + r3 + " (" + r3int + ')'
                    + " compareTo(byte[],byte[])=" + r5 + " (" + r5int + ')',
                r3int,
                r5int
                );

        if( r1 == null ) { // Inconsistent
            Assert.assertTrue( "advanceCompareTo(BytesAccess), compareTo(BytesAccess) inconsistent ! null != " + r3, r3==0);
            Assert.assertTrue( "advanceCompareTo(BytesAccess), compareTo(byte[]) inconsistent ! null != " + r4, r4==0);
            Assert.assertTrue( "advanceCompareTo(BytesAccess), compareTo(byte[],byte[]) inconsistent ! null != " + r4, r4==0);
            }
        else {
            // TODO: build a "long" from r1 (not urgent)
            // long r1long = 0;
            // To-Do-Later: Compare r1long and r5
            }
    }

    private static Object getAnyObjectForFailTest()
    {
        return "getAnyObject";
    }

    private static void testEquals( BytesAccess ba, BytesAccess compareTo)
    {
        boolean res = ba.equals( getAnyObjectForFailTest() );

        Assert.assertFalse( "BytesAccess should not be equals to a String", res );

        ba.equals( compareTo );
        ba.equals( NULL_OBJECT );
    }

    private static void _test_todo_Or( BytesAccess ba, BytesAccess orBytesAccess )
    {//TODO test case
        byte[] orSomeBytes = orBytesAccess.getBytesCopy();

        byte[] r1 = ba.orOperator( orBytesAccess );
        byte[] r2 = ba.orOperator( orSomeBytes );
        byte[] r3 = BytesAccess.orOperator( ba.getBytesCopy(), orSomeBytes );

        Assert.assertEquals( "or r1r2", 0, BytesAccess.compare( r1, r2 ));
        Assert.assertEquals( "or r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    private static void _test_todo_And( BytesAccess ba, BytesAccess andBytesAccess )
    {//TODO test case
        byte[] andSomeBytes = andBytesAccess.getBytesCopy();

        byte[] r1 = ba.andOperator( andBytesAccess );
        byte[] r2 = ba.andOperator( andSomeBytes );
        byte[] r3 = BytesAccess.andOperator( ba.getBytesCopy(), andSomeBytes );

        Assert.assertEquals( "and r1r2", 0, BytesAccess.compare( r1, r2 ));
        Assert.assertEquals( "and r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    private static void _test_todo_Xor( BytesAccess ba, BytesAccess xorBytesAccess )
    {//TODO test case
        byte[] xorSomeBytes = xorBytesAccess.getBytesCopy();

        byte[] r1 = ba.xorOperator( xorBytesAccess );
        byte[] r2 = ba.xorOperator( xorSomeBytes );
        byte[] r3 = BytesAccess.xorOperator( ba.getBytesCopy(), xorSomeBytes );

        Assert.assertEquals( "xor r1r2", 0, BytesAccess.compare( r1, r2 ));
        Assert.assertEquals( "xor r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    private static void _test_todo_Basic( BytesAccess ba )
    {//TODO test case
        ba.getBytesCopy();
        ba.length(); // == ba.getBytesCopy().length
                     // == source length
    }

    private static void _test_todo_UInteger( BytesAccess ba )
    {//TODO test case
        byte mask = 0;
        byte mask_0 = 0;
        byte mask_1 = 0;
        int leftRot_ = 0;
        int offset = 0;
        int offset_ = 0;
        int rightRot = 0;
        int rightRot_ = 0;

        ba.getUInteger( offset, mask, rightRot );
        ba.getUInteger( offset_, mask_0, leftRot_, mask_1, rightRot_ );

        //int offset2set = 0;
        //byte[] bytes2set = null;
        //TODO: use a debug anonymous class! ba.setBytes( offset2set, bytes2set );
    }

    private static InputStream getRandomInputStream(final int maxLen)
    {
        final byte[] byte1 = new byte[1];
        return new InputStream()
        {
            private int c = 0;
            @Override
            public int read() throws IOException
            {
                if( c++ < maxLen ) {
                    RND.nextBytes( byte1 );
                    return 0x00FF & byte1[0];
                    }

                return -1;
            }
        };
    }

    private static InputStream getConstantInputStream()
    {
        return new InputStream()
        {
            private int i = 0;
            @Override
            public int read() throws IOException
            {
                if( i < BYTES.length ) {
                    return 0x00FF & BYTES[i++];
                    }
                return -1;
            }
        };
    }

    private static BytesAccess getConstantBytesAccess()
        throws NullPointerException,
               BytesAccessException,
               IOException
    {
        return new TstOnlyBytesAccess( getConstantInputStream(), BYTES.length );
    }
}
