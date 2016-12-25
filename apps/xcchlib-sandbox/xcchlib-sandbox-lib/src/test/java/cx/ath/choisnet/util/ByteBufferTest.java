package cx.ath.choisnet.util;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.SerializableHelper;

public class ByteBufferTest
{
    protected ByteBuffer testByteBuffer;
    protected byte[]     bytes;
    protected byte[]     bytes10 = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

    @Before
    protected void setUp()
    {
        this.bytes = new byte[ 1024 << (2 + 7) ];

        for( int i = 0; i < this.bytes.length; i++ ) {
            this.bytes[ i ] = (byte)(i % 256);
        }

        this.testByteBuffer = new ByteBuffer( this.bytes );
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
        assertThat( bb2.length() ).isEqualTo( bb1.length() );

        final byte[] b1 = bb1.array();
        final byte[] b2 = bb2.array();

        assertThat( bb1.length() ).isEqualTo( b1.length );
        assertThat( bb2.length() ).isEqualTo( b2.length );
        assertThat( b2.length ).isEqualTo( b1.length );

        assertThat( b2 ).isNotEqualTo( b1 );

        for( int i = 0; i < b1.length; i++ ) {
            assertThat( (int)b2[ i ] ).isEqualTo( b1[ i ] );
        }

        assertThat( bb1.compareTo( bb1 ) ).isEqualTo( 0 );
        assertThat( bb1.compareTo( bb2 ) ).isEqualTo( 0 );
        assertThat( bb2.compareTo( bb1 ) ).isEqualTo( 0 );
        assertThat( bb2.compareTo( bb2 ) ).isEqualTo( 0 );

        assertThat( bb1.equals( bb1 ) ).isTrue();
        assertThat( bb1.equals( bb2 ) ).isTrue();
        assertThat( bb2.equals( bb1 ) ).isTrue();
        assertThat( bb2.equals( bb2 ) ).isTrue();
    }

    @Test
    public void testClone() throws CloneNotSupportedException
    {
        final ByteBuffer bufferClone = this.testByteBuffer.clone();

        cmp( bufferClone, this.testByteBuffer );

        assertThat( bufferClone.length() == this.testByteBuffer.length() ).isTrue();

        bufferClone.append( this.bytes10 );

        assertThat( bufferClone.length() == this.testByteBuffer.length() ).isFalse();
    }


    public void testSerializable() throws IOException, ClassNotFoundException
    {
        final byte[]     serialization = SerializableHelper.toByteArray( this.testByteBuffer );
        final ByteBuffer byteBuffer    = SerializableHelper.toObject( serialization, ByteBuffer.class );

        cmp( byteBuffer, this.testByteBuffer );
    }
}
