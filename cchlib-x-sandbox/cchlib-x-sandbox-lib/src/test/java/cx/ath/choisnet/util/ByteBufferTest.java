/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ByteBufferTest.java
** Description   :
**
**  3.00.001 2006.01.31 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ByteBufferTest
**
*/
package cx.ath.choisnet.util;

// import cx.ath.choisnet.test.TestSerializable;
import cx.ath.choisnet.io.Serialization;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
**
*/
public class ByteBufferTest extends TestCase
{
/**
**
*/
protected ByteBuffer testByteBuffer;

/**
**
*/
protected byte[] bytes;

/**
**
*/
protected byte[] bytes10 = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

/**
**
*/
@Override
protected void setUp() // -------------------------------------------------
{
 this.bytes = new byte[ 1024<<2 + 7 ];

 for( int i = 0; i<this.bytes.length ; i++ ) {
    this.bytes[ i ] = (byte)( i % 256 );
    }

 this.testByteBuffer = new ByteBuffer( this.bytes );
}

/**
**
*/
public static Test suite() // ---------------------------------------------
{
 return new TestSuite( ByteBufferTest.class );
}

/**
**
*/
public void testAppend() // -----------------------------------------------
{
/*
 Iterator<String> iter  = new ArrayIterator<String>( arrayOfString );
 int              size  = 0;

 while( iter.hasNext() ) {
    String s = iter.next();

    size++;
    }

 assertEquals( size, arrayOfString.length );
 //assertTrue( size == arrayOfString.length );
*/
}

/**
**
*/
static void cmp( final ByteBuffer bb1, final ByteBuffer bb2 ) // ----------
{
 assertEquals( bb1.length(), bb2.length() );

 final byte[] b1 = bb1.array();
 final byte[] b2 = bb2.array();

 assertEquals( b1.length, bb1.length() );
 assertEquals( b2.length, bb2.length() );
 assertEquals( b1.length, b2.length );

 assertNotSame( b1, b2 );

 for( int i = 0; i<b1.length; i++ ) {
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

/**
**
*/
public void testClone() // ------------------------------------------------
    throws CloneNotSupportedException
{
 ByteBuffer bufferClone = this.testByteBuffer.clone();

 cmp( bufferClone, this.testByteBuffer );

 assertTrue( bufferClone.length() == this.testByteBuffer.length() );

 bufferClone.append( bytes10 );

 assertFalse( bufferClone.length() == this.testByteBuffer.length() );
}

/**
**
*/
public void testSerializable() // -----------------------------------------
    throws java.io.IOException, ClassNotFoundException
{
 byte[]         serialization   = Serialization.toByteArray( this.testByteBuffer, ByteBuffer.class );
 ByteBuffer     byteBuffer      = Serialization.newFromByteArray( serialization, ByteBuffer.class );

 cmp( byteBuffer, this.testByteBuffer );
}

} // class