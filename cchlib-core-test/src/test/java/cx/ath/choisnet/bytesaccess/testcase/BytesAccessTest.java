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

@SuppressWarnings("resource")
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
        catch( final BytesAccessException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
            // OK
            }

        // It there any lock ?
        final boolean isDelete = f.delete();

        Assert.assertTrue( "Can't delete file [" + f + ']', isDelete );
    }

    @Test
    public void testCompareTo() throws NullPointerException, BytesAccessException, IOException
    {
        final int           bytesLength = 100;
        final BytesAccess[] tests       = buildBytesAccessArray( bytesLength );

        LOGGER.info( "testCompareTo() - step 1 : cmpToCount=" + tests.length );
        for( final BytesAccess ba : tests ) {
            final TstOnlyBytesAccess baCopy = new TstOnlyBytesAccess( ba );

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
            final int bytesLength
             )
        throws  NullPointerException,
                BytesAccessException,
                IOException
    {
    final BytesAccess test = new TstOnlyBytesAccess(
            getRandomInputStream(bytesLength),
            bytesLength
            );
    final BytesAccess[] tests = {
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

    @SuppressWarnings("boxing")
    private static void testCompareTo( final BytesAccess ba, final BytesAccess compareTo )
    {
        final byte[] compareToBytes = compareTo.getBytesCopy();

        final String r1 = ba.advanceCompareTo( compareTo );
        final String r2 = ba.advanceCompareTo( compareToBytes );

        final StringBuilder debug = new StringBuilder();

        debug.append( "ba1:" );
        for( final Byte b : ba.getBytesCopy()) {
            debug.append( String.format( "%1$02X ", b ) );
            }
        LOGGER.info( debug );
        debug.setLength( 0 );
        debug.append( "ba2:" );
        for( final Byte b : compareToBytes) {
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
                final int  r3 = ba.compareTo( compareTo );
                Assert.fail("Should fail here : r3 = ba.compareTo( compareTo ): " + r3 );
                }
            catch( final IllegalArgumentException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                }
            try {
                final int r4 = ba.compareTo( compareToBytes );
                Assert.fail("Should fail here : r4 = ba.compareTo( compareToBytes ): " + r4 );
                }
            catch( final IllegalArgumentException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                }
            try {
                final long r5 =BytesAccess.compare( ba.getBytesCopy(), compareToBytes );
                Assert.fail("Should fail here : r5 = BytesAccess.compare( ba.getBytesCopy(), compareToBytes ): " + r5);
                }
            catch( final IllegalArgumentException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                }
            return;
        }

        final int    r3 = ba.compareTo( compareTo );
        final int    r4 = ba.compareTo( compareToBytes );
        final long   r5 = BytesAccess.compare( ba.getBytesCopy(), compareToBytes );
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

    private static void testEquals( final BytesAccess ba, final BytesAccess compareTo)
    {
        final boolean res = ba.equals( getAnyObjectForFailTest() );

        Assert.assertFalse( "BytesAccess should not be equals to a String", res );

        ba.equals( compareTo );
        ba.equals( NULL_OBJECT );
    }

    @SuppressWarnings("unused")
    private static void _test_todo_Or( final BytesAccess ba, final BytesAccess orBytesAccess )
    {//TODO test case
        final byte[] orSomeBytes = orBytesAccess.getBytesCopy();

        final byte[] r1 = ba.orOperator( orBytesAccess );
        final byte[] r2 = ba.orOperator( orSomeBytes );
        final byte[] r3 = BytesAccess.orOperator( ba.getBytesCopy(), orSomeBytes );

        Assert.assertEquals( "or r1r2", 0, BytesAccess.compare( r1, r2 ));
        Assert.assertEquals( "or r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    @SuppressWarnings("unused")
    private static void _test_todo_And( final BytesAccess ba, final BytesAccess andBytesAccess )
    {//TODO test case
        final byte[] andSomeBytes = andBytesAccess.getBytesCopy();

        final byte[] r1 = ba.andOperator( andBytesAccess );
        final byte[] r2 = ba.andOperator( andSomeBytes );
        final byte[] r3 = BytesAccess.andOperator( ba.getBytesCopy(), andSomeBytes );

        Assert.assertEquals( "and r1r2", 0, BytesAccess.compare( r1, r2 ));
        Assert.assertEquals( "and r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    @SuppressWarnings("unused")
    private static void _test_todo_Xor( final BytesAccess ba, final BytesAccess xorBytesAccess )
    {//TODO test case
        final byte[] xorSomeBytes = xorBytesAccess.getBytesCopy();

        final byte[] r1 = ba.xorOperator( xorBytesAccess );
        final byte[] r2 = ba.xorOperator( xorSomeBytes );
        final byte[] r3 = BytesAccess.xorOperator( ba.getBytesCopy(), xorSomeBytes );

        Assert.assertEquals( "xor r1r2", 0, BytesAccess.compare( r1, r2 ));
        Assert.assertEquals( "xor r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    @SuppressWarnings("unused")
    private static void _test_todo_Basic( final BytesAccess ba )
    {//TODO test case
        ba.getBytesCopy();
        ba.length(); // == ba.getBytesCopy().length
                     // == source length
    }

    @SuppressWarnings("unused")
    private static void _test_todo_UInteger( final BytesAccess ba )
    {//TODO test case
        final byte mask = 0;
        final byte mask_0 = 0;
        final byte mask_1 = 0;
        final int leftRot_ = 0;
        final int offset = 0;
        final int offset_ = 0;
        final int rightRot = 0;
        final int rightRot_ = 0;

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
                if( this.c++ < maxLen ) {
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
                if( this.i < BYTES.length ) {
                    return 0x00FF & BYTES[this.i++];
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
