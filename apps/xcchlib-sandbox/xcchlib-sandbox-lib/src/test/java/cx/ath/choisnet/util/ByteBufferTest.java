package cx.ath.choisnet.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.googlecode.cchlib.io.SerializableHelper;

public class ByteBufferTest extends TestCase
{
    protected ByteBuffer testByteBuffer;
    protected byte[]     bytes;
    protected byte[]     bytes10 = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

    @Override
    protected void setUp() // -------------------------------------------------
    {
        this.bytes = new byte[1024 << (2 + 7)];

        for( int i = 0; i < this.bytes.length; i++ ) {
            this.bytes[ i ] = (byte)(i % 256);
        }

        this.testByteBuffer = new ByteBuffer( this.bytes );
    }

    public static Test suite() // ---------------------------------------------
    {
        return new TestSuite( ByteBufferTest.class );
    }

//    public void testAppend()
//    {
//        final Iterator<String> iter = new ArrayIterator<String>( arrayOfString );
//
//        int size = 0;
//
//          while( iter.hasNext() ) { final String s = iter.next();
//
//          size++; }
//
//          assertEquals( size, arrayOfString.length );
//    }

    static void cmp( final ByteBuffer bb1, final ByteBuffer bb2 ) // ----------
    {
        assertEquals( bb1.length(), bb2.length() );

        final byte[] b1 = bb1.array();
        final byte[] b2 = bb2.array();

        assertEquals( b1.length, bb1.length() );
        assertEquals( b2.length, bb2.length() );
        assertEquals( b1.length, b2.length );

        assertNotSame( b1, b2 );

        for( int i = 0; i < b1.length; i++ ) {
            assertEquals( b1[ i ], b2[ i ] );
        }

        assertEquals( 0, bb1.compareTo( bb1 ) );
        assertEquals( 0, bb1.compareTo( bb2 ) );
        assertEquals( 0, bb2.compareTo( bb1 ) );
        assertEquals( 0, bb2.compareTo( bb2 ) );

        assertTrue( bb1.equals( bb1 ) );
        assertTrue( bb1.equals( bb2 ) );
        assertTrue( bb2.equals( bb1 ) );
        assertTrue( bb2.equals( bb2 ) );
    }

    public void testClone() // ------------------------------------------------
            throws CloneNotSupportedException
    {
        final ByteBuffer bufferClone = this.testByteBuffer.clone();

        cmp( bufferClone, this.testByteBuffer );

        assertTrue( bufferClone.length() == this.testByteBuffer.length() );

        bufferClone.append( this.bytes10 );

        assertFalse( bufferClone.length() == this.testByteBuffer.length() );
    }

    public void testSerializable() // -----------------------------------------
            throws java.io.IOException, ClassNotFoundException
    {
        final byte[]     serialization = SerializableHelper.toByteArray( this.testByteBuffer );
        final ByteBuffer byteBuffer    = SerializableHelper.toObject( serialization, ByteBuffer.class );

        cmp( byteBuffer, this.testByteBuffer );
    }
}
