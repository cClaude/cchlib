package cx.ath.choisnet.lang.testcase;

import java.io.IOException;
import cx.ath.choisnet.io.SerializableHelper;
import cx.ath.choisnet.lang.ByteArrayBuilder;
import junit.framework.TestCase;

public class ByteArrayBuilderTest extends TestCase 
{
    private final static byte[] BYTES = {'a','b','c','d','e','f'};
    private final static byte[] OTHERBYTES = {'A','B','C','D','E','F', 'G'};
    private final static int    BIG_CAPACITY = 2048 * 100;
    
    public void testConstructor1()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        assertEquals("bad length",0,bab.length());
        assertEquals("bad array",0,bab.array().length);
    }
    
    public void testConstructor2()
    {
        int capacity = 10;
        ByteArrayBuilder bab = new ByteArrayBuilder(capacity);
        assertEquals("bad length",0,bab.length());
        assertEquals("bad array",0,bab.array().length);
        assertEquals("bad capacity()",capacity,bab.capacity());
    }
    
    public void testConstructor3()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        assertEquals("bad length",BYTES.length,bab.length());

        byte[] bytes = bab.array();
        
        for(int i = 0;i<bytes.length;i++) {
            assertEquals(
                    String.format("bad value [%d]",i),
                    BYTES[i],
                    bytes[i]
                          );
        }
    }
    
    public void testConstructor4()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(null);
        assertEquals("bad length",0,bab.length());
        assertEquals("bad array",0,bab.array().length);
    }

    public void test_reset()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        bab.reset();
        assertEquals("bad length",0,bab.length());
        assertEquals("bad array",0,bab.array().length);
    }
    
    public void test_setLength()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        final int len = 4;
        bab.setLength( len );
        assertEquals("bad length",len,bab.length());
        assertEquals("bad array",len,bab.array().length);
        
        byte[] bytes = bab.array();
        
        for(int i = 0;i<bytes.length;i++) {
            assertEquals(
                    String.format( "bad value [%d]",i),
                    BYTES[i],
                    bytes[i]
                    );
        }
        
        bab.setLength( BIG_CAPACITY );
        assertEquals("bad length",BIG_CAPACITY,bab.length());
        assertEquals("bad array",BIG_CAPACITY,bab.array().length);

        bytes = bab.array();
        
        for(int i = 0;i<bytes.length;i++) {
            if( i<len ) {
                assertEquals(
                        String.format( "bad value [%d]",i ),
                        BYTES[i],
                        bytes[i]
                        );
            }
            else {
                assertEquals(
                        String.format( "bad value [%d]",i ),
                        0,
                        bytes[i]
                        );
            }
        }
    }

    public void test_ensureCapacity()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        bab.ensureCapacity(BIG_CAPACITY);
        
        assertEquals("bad length",BYTES.length,bab.length());
        assertEquals("bad array",BYTES.length,bab.array().length);

        byte[] bytes = bab.array();
        
        for(int i = 0;i<bytes.length;i++) {
            assertEquals(
                    String.format( "bad value [%d]",i),
                    BYTES[i],
                    bytes[i]
                    );
        }
   }
    
    public void test_append_bytes()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();

        final int count = 1000;
        for(int i = 0; i<count; i++ ) {
            bab.append(BYTES);
        }
        
        assertEquals("bad length",BYTES.length * count,bab.length());
        assertEquals("bad array",BYTES.length * count,bab.array().length);
   }

    public void test_append_bytes2()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        final int offset = 2;
        final int len = 3;
        bab.append( BYTES, offset, len );
        
        assertEquals("bad length",len,bab.length());
        assertEquals("bad array",len,bab.array().length);
        
        byte[] bytes = bab.array();
        for( int i=0; i<bytes.length;i++) {
            assertEquals(
                    String.format( "bad value [%d]",i ),
                    BYTES[i + offset],
                    bytes[i]
                    );
        }
    }

    public void test_append_byte()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();

        for(int i=0;i<BYTES.length;i++) {
            bab.append( BYTES[i] );
        }
        
        assertEquals("bad length",BYTES.length,bab.length());
        assertEquals("bad array",BYTES.length,bab.array().length);
        
        byte[] bytes = bab.array();
        for( int i=0; i<bytes.length;i++) {
            assertEquals(
                    String.format( "bad value [%d]",i ),
                    BYTES[i],
                    bytes[i]
                    );
        }
    }
    
/*
 * TODO
    public ByteArrayBuilder append(ReadableByteChannel channel)
        throws java.io.IOException
    {
        return append(channel, DEFAULT_SIZE);
    }
TODO
    public ByteArrayBuilder append(ReadableByteChannel channel, int bufferSize)
        throws java.io.IOException
    {
        byte[] byteBuffer = new byte[bufferSize];

        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(byteBuffer);

        int len;
        while((len = channel.read(buffer)) != -1) {
            buffer.flip();

            append(byteBuffer, 0, len);
            buffer.clear();
        }

        return this;
    }
*/

    public void test_startsWith()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        bab.append( BYTES );
        
        for(int i = 0;i<100;i++) {
            bab.append( OTHERBYTES );
        }
        
        boolean sw0 = bab.startsWith( new ByteArrayBuilder(BYTES) );
        assertTrue("Should start with !",sw0);

        boolean sw1 = bab.startsWith( BYTES );
        assertTrue("Should start with !",sw1);
    }

    public void test_endsWith()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        
        for(int i = 0;i<100;i++) {
            bab.append( OTHERBYTES );
        }
        bab.append( BYTES );
        
        boolean sw0 = bab.endsWith( new ByteArrayBuilder(BYTES) );
        assertTrue("Should end with !",sw0);

        boolean sw1 = bab.endsWith( BYTES );
        assertTrue("Should end with !",sw1);
    }

    public void test_compareTo()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        assertEquals("Should be equals",0,bab.compareTo( new ByteArrayBuilder(BYTES) ));
        assertTrue("Should be diffents",0!=bab.compareTo( new ByteArrayBuilder(OTHERBYTES) ));
    }

    public void test_equals()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        assertTrue("Should be equals",bab.equals( new ByteArrayBuilder(BYTES) ));
        assertFalse("Should be diffents",bab.equals( new ByteArrayBuilder(OTHERBYTES) ));
        ByteArrayBuilder nullBab = null;
        assertFalse("Should be diffents",bab.equals( nullBab ));
        assertFalse("Should be diffents",bab.equals( new Object() ));
    }

//TODO    @Override
//    public String toString()
//    {
//        return new String(buffer, 0, lastPos);
//    }


    public void test_clone() throws CloneNotSupportedException
    {
        ByteArrayBuilder bab    = new ByteArrayBuilder(BYTES);
        ByteArrayBuilder clone  = bab.clone();
        
        assertTrue("Should be equals",bab.equals( clone ));
        assertEquals("bad length",BYTES.length,bab.length());
        assertEquals("bad array",BYTES.length,bab.array().length);

        byte[] bytes = bab.array();
        for( int i=0; i<bytes.length;i++) {
            assertEquals(
                    String.format( "bad value [%d]",i ),
                    BYTES[i],
                    bytes[i]
                    );
        }
    }

    public void test_writeObject_readObject() throws IOException, ClassNotFoundException
    {
        ByteArrayBuilder bab    = new ByteArrayBuilder(BYTES);
        ByteArrayBuilder clone  = SerializableHelper.clone( bab, ByteArrayBuilder.class );

        assertTrue("Should be equals",bab.equals( clone ));
        assertEquals("bad length",BYTES.length,bab.length());
        assertEquals("bad array",BYTES.length,bab.array().length);

        byte[] bytes = bab.array();
        for( int i=0; i<bytes.length;i++) {
            assertEquals(
                    String.format( "bad value [%d]",i ),
                    BYTES[i],
                    bytes[i]
                    );
        }
    }
}
