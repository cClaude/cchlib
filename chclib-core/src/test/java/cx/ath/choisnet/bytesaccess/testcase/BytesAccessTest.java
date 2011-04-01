/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.bytesaccess.testcase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import org.apache.log4j.Logger;
import cx.ath.choisnet.bytesaccess.BytesAccess;
import cx.ath.choisnet.bytesaccess.BytesAccessException;
import junit.framework.TestCase;

public class BytesAccessTest extends TestCase
{
    private static Logger slogger = Logger.getLogger(BytesAccessTest.class);
    private static Object nullObject = null;
    private final static Random random = new Random();
    private final static byte[] BYTES = { 
            (byte)0xDE, (byte)0xAD, 
            (byte)0xBE, (byte)0xFF,
            (byte)0xBA, (byte)0xBE,
            0x00,       (byte)0xFF,
            (byte)0xAA, (byte)~0xAA,
    };

    public void testFileConstructor() throws IOException
    {
        // Create a File
        File f = File.createTempFile( '~' + getClass().getName(), ".tmp" );
        
        // Fill it
        final int length = 50;
        InputStream      is  = getRandomInputStream( length );
        FileOutputStream fos = new FileOutputStream( f );
        int c;
        while( (c = is.read()) >= 0 ) {
            fos.write( c );
        }
        is.close();
        fos.close();
        assertEquals( "File Bad length!", length, f.length() );

        //
        BytesAccess ba = new TstOnlyBytesAccess( f, length );
        assertEquals( "Bad length!", length, ba.getBytesCopy().length );

        ba = new TstOnlyBytesAccess( f, length / 2 );
        assertEquals( "Bad length!", length/2, ba.getBytesCopy().length );

        try {
            ba = new TstOnlyBytesAccess( f, length * 2 );
            fail( "Should fail here !" );
        }
        catch( BytesAccessException e ) {
            // OK
        }
      
        // It there any lock ?
        boolean isDelete = f.delete();
        
        assertTrue( "Can't delete file [" + f + ']', isDelete );
    }
    
    public void testCompareTo() throws NullPointerException, BytesAccessException, IOException
    {
        int           bytesLength = 100;
        BytesAccess[] tests       = buildBytesAccessArray( bytesLength );

        slogger.info( "testCompareTo() - step 1 : cmpToCount=" + tests.length );
        for( BytesAccess ba : tests ) {
            TstOnlyBytesAccess baCopy = new TstOnlyBytesAccess( ba );
            
            testCompareTo( ba, baCopy );
            testEquals( ba, baCopy );
            
            testCompareTo( ba, getConstantBytesAccess() );
            testEquals( ba, getConstantBytesAccess() );
        }
        
        slogger.info( "testCompareTo() - step 2" );
        testCompareTo( 
                new TstOnlyBytesAccess( BYTES, 0, BYTES.length ),
                getConstantBytesAccess()
                );
        slogger.info( "testCompareTo() - step 3" );
        testCompareTo( // not equal !
                new TstOnlyBytesAccess( BYTES, 0, BYTES.length ),
                new TstOnlyBytesAccess( getRandomInputStream(BYTES.length), BYTES.length ) 
                );
    }
    
    public BytesAccess[] buildBytesAccessArray(
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

    public void testCompareTo( final BytesAccess ba, final BytesAccess compareTo )
    {
        byte[] compareToBytes = compareTo.getBytesCopy();

        String r1 = ba.advanceCompareTo( compareTo );
        String r2 = ba.advanceCompareTo( compareToBytes );
        
        StringBuilder debug = new StringBuilder();
        
        debug.append( "ba1:" );
        for( Byte b : ba.getBytesCopy()) {
            debug.append( String.format( "%1$02X ", b ) );
        }
        slogger.info( debug );
        debug.setLength( 0 );
        debug.append( "ba2:" );
        for( Byte b : compareToBytes) {
            debug.append( String.format( "%1$02X ", b ) );
        }
        slogger.info( debug );
        
        slogger.info( "r1: " + r1  );
        slogger.info( "r2: " + r2  );
        
        // Verify than r(n) give similar result
        assertTrue( "mismatch advanceCompareTo(BytesAccess)=" + r1 
                    + " advanceCompareTo(byte[])=" + r2,
                r1==null?
                        (r2==null?true:r2.equals( r1 ))
                        :
                        r1.equals( r2 )
                );
        
        if( ba.getBytesCopy().length != compareToBytes.length ) {
            // Can't Continue !
            try {
                int  r3 = ba.compareTo( compareTo );
                fail("Should fail here : r3 = ba.compareTo( compareTo ): " + r3 );
            }
            catch( IllegalArgumentException ok ) {
                
            }
            try {
                int r4 = ba.compareTo( compareToBytes );
                fail("Should fail here : r4 = ba.compareTo( compareToBytes ): " + r4 );
            }
            catch( IllegalArgumentException ok ) {
                
            }
            try {
                long r5 =BytesAccess.compare( ba.getBytesCopy(), compareToBytes );;
                fail("Should fail here : r5 = BytesAccess.compare( ba.getBytesCopy(), compareToBytes ): " + r5);
            }
            catch( IllegalArgumentException ok ) {
                
            }
            return;
        }
        
        int    r3 = ba.compareTo( compareTo );
        int    r4 = ba.compareTo( compareToBytes );
        long   r5 = BytesAccess.compare( ba.getBytesCopy(), compareToBytes );
        slogger.info( String.format( "r3: %1$08X", r3) );
        slogger.info( String.format( "r4: %1$08X", r4) );
        slogger.info( String.format( "r5: %1$016X", r5) );

        assertTrue( "mismatch compareTo(BytesAccess)=" + r3 
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
            
            assertEquals(
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

        assertEquals(
                String.format(
                        "cmpCanNotStoreOffsetInInteger not valid(1) %1$X - %2$X", r3, r5 
                        ),
                (r5 & 0xFFFFFFFFFFFF0000L) != 0,
                cmpCanNotStoreOffsetInInteger
                );
        assertEquals(
                String.format(
                        "cmpCanNotStoreOffsetInInteger not valid(2) %1$X - %2$X", r3, r5 
                        ),
                (r3 & 0xFFFFFFFFFFFF0000L) != 0,
                cmpCanNotStoreOffsetInInteger
                );

        
        assertEquals( "mismatch compareTo(BytesAccess)="
                    + r3 + " (" + r3int + ')'
                    + " compareTo(byte[],byte[])=" + r5 + " (" + r5int + ')', 
                r3int,
                r5int 
                ); 
        
        if( r1 == null ) { // Inconsistent
            assertTrue( "advanceCompareTo(BytesAccess), compareTo(BytesAccess) inconsistent ! null != " + r3, r3==0);
            assertTrue( "advanceCompareTo(BytesAccess), compareTo(byte[]) inconsistent ! null != " + r4, r4==0);
            assertTrue( "advanceCompareTo(BytesAccess), compareTo(byte[],byte[]) inconsistent ! null != " + r4, r4==0);
        }
        else {
            // TODO: build a "long" from r1 (not urgent)
            // long r1long = 0;
            // To-Do-Later: Compare r1long and r5
        }
    }
    
    public void testEquals( BytesAccess ba, BytesAccess compareTo)
    {
        boolean res = ba.equals( "fake" );
        
        assertFalse( "BytesAccess should not be equals to a String", res );
        
        ba.equals( compareTo );
        ba.equals( nullObject );
    }
    
    public void _test_todo_Or( BytesAccess ba, BytesAccess orBytesAccess )
    {//TODO
        byte[] orSomeBytes = orBytesAccess.getBytesCopy();
        
        byte[] r1 = ba.orOperator( orBytesAccess );
        byte[] r2 = ba.orOperator( orSomeBytes );
        byte[] r3 = BytesAccess.orOperator( ba.getBytesCopy(), orSomeBytes );

        assertEquals( "or r1r2", 0, BytesAccess.compare( r1, r2 ));
        assertEquals( "or r1r3", 0, BytesAccess.compare( r1, r3 ));
    }
    
    public void _test_todo_And( BytesAccess ba, BytesAccess andBytesAccess )
    {//TODO
        byte[] andSomeBytes = andBytesAccess.getBytesCopy();
        
        byte[] r1 = ba.andOperator( andBytesAccess );
        byte[] r2 = ba.andOperator( andSomeBytes );
        byte[] r3 = BytesAccess.andOperator( ba.getBytesCopy(), andSomeBytes );

        assertEquals( "and r1r2", 0, BytesAccess.compare( r1, r2 ));
        assertEquals( "and r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    public void _test_todo_Xor( BytesAccess ba, BytesAccess xorBytesAccess )
    {//TODO
        byte[] xorSomeBytes = xorBytesAccess.getBytesCopy();

        byte[] r1 = ba.xorOperator( xorBytesAccess );
        byte[] r2 = ba.xorOperator( xorSomeBytes );
        byte[] r3 = BytesAccess.xorOperator( ba.getBytesCopy(), xorSomeBytes );

        assertEquals( "xor r1r2", 0, BytesAccess.compare( r1, r2 ));
        assertEquals( "xor r1r3", 0, BytesAccess.compare( r1, r3 ));
    }

    public void _test_todo_Basic( BytesAccess ba )
    {//TODO
        ba.getBytesCopy();
        ba.length(); // == ba.getBytesCopy().length
                     // == source length
    }
    
    public void _test_todo_UInteger( BytesAccess ba )
    {//TODO
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

    public static InputStream getRandomInputStream(final int maxLen)
    {
        final byte[] byte1 = new byte[1];
        return new InputStream()
        {
            private int c = 0;
            @Override
            public int read() throws IOException
            {
                if( c++ < maxLen ) {
                    random.nextBytes( byte1 );
                    return 0x00FF & byte1[0];
                }
                
                return -1;
            }
        };
    }
    
    public static InputStream getConstantInputStream()
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
    public static BytesAccess getConstantBytesAccess()
        throws NullPointerException, 
               BytesAccessException,
               IOException
    {
        return new TstOnlyBytesAccess( getConstantInputStream(), BYTES.length );
    }
}
