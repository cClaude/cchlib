package cx.ath.choisnet.lang.testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import cx.ath.choisnet.io.SerializableHelper;
import cx.ath.choisnet.lang.ByteArrayBuilder;
import cx.ath.choisnet.test.Assert;
import java.io.ByteArrayInputStream;
import junit.framework.TestCase;

/**
 * 
 */
public class ByteArrayBuilderTest extends TestCase 
{
    private final static byte[] BYTES = {'a','b','c','d','e','f'};
    private final static byte[] OTHERBYTES = {'A','B','C','D','E','F', 'G'};
    private final static int    BIG_CAPACITY = 2048 * 100;
    
    public void testConstructor1()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        TestCase.assertEquals("bad length",0,bab.length());
        TestCase.assertEquals("bad array",0,bab.array().length);
    }
    
    public void testConstructor2()
    {
        int capacity = 10;
        ByteArrayBuilder bab = new ByteArrayBuilder(capacity);
        TestCase.assertEquals("bad length",0,bab.length());
        TestCase.assertEquals("bad array",0,bab.array().length);
        TestCase.assertEquals("bad capacity()",capacity,bab.capacity());
    }
    
    public void testConstructor3()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        TestCase.assertEquals("bad length",BYTES.length,bab.length());

        Assert.assertEquals("mitchmatch",BYTES,bab.array());
//        byte[] bytes = bab.array();
//        
//        for(int i = 0;i<bytes.length;i++) {
//            assertEquals(
//                    String.format("bad value [%d]",i),
//                    BYTES[i],
//                    bytes[i]
//                          );
//        }
    }
    
    public void testConstructor4()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(null);
        TestCase.assertEquals("bad length",0,bab.length());
        TestCase.assertEquals("bad array",0,bab.array().length);
    }

    public void test_reset()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        bab.reset();
        TestCase.assertEquals("bad length",0,bab.length());
        TestCase.assertEquals("bad array",0,bab.array().length);
    }
    
    public void test_setLength()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        final int len = 4;
        bab.setLength( len );
        TestCase.assertEquals("bad length",len,bab.length());
        TestCase.assertEquals("bad array",len,bab.array().length);
        
        byte[] bytes = bab.array();
        
        for(int i = 0;i<bytes.length;i++) {
            TestCase.assertEquals(
                    String.format( "bad value [%d]",i),
                    BYTES[i],
                    bytes[i]
                    );
        }
        
        bab.setLength( BIG_CAPACITY );
        TestCase.assertEquals("bad length",BIG_CAPACITY,bab.length());
        TestCase.assertEquals("bad array",BIG_CAPACITY,bab.array().length);

        bytes = bab.array();
        
        for(int i = 0;i<bytes.length;i++) {
            if( i<len ) {
                TestCase.assertEquals(
                        String.format( "bad value [%d]",i ),
                        BYTES[i],
                        bytes[i]
                        );
            }
            else {
                TestCase.assertEquals(
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
        
        TestCase.assertEquals("bad length",BYTES.length,bab.length());
        TestCase.assertEquals("bad array",BYTES.length,bab.array().length);

        Assert.assertEquals("mitchmatch",BYTES,bab.array());
//        byte[] bytes = bab.array();
//        
//        for(int i = 0;i<bytes.length;i++) {
//            assertEquals(
//                    String.format( "bad value [%d]",i),
//                    BYTES[i],
//                    bytes[i]
//                    );
//        }
   }
    
    public void test_append_bytes()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();

        final int count = 1000;
        for(int i = 0; i<count; i++ ) {
            bab.append(BYTES);
        }
        
        TestCase.assertEquals("bad length",BYTES.length * count,bab.length());
        TestCase.assertEquals("bad array",BYTES.length * count,bab.array().length);
   }

    public void test_append_bytes2()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        final int offset = 2;
        final int len = 3;
        bab.append( BYTES, offset, len );
        
        TestCase.assertEquals("bad length",len,bab.length());
        TestCase.assertEquals("bad array",len,bab.array().length);
        
        byte[] bytes = bab.array();
        for( int i=0; i<bytes.length;i++) {
            TestCase.assertEquals(
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
        
        TestCase.assertEquals("bad length",BYTES.length,bab.length());
        TestCase.assertEquals("bad array",BYTES.length,bab.array().length);
        
        Assert.assertEquals("mitchmatch",BYTES,bab.array());
//        byte[] bytes = bab.array();
//        for( int i=0; i<bytes.length;i++) {
//            assertEquals(
//                    String.format( "bad value [%d]",i ),
//                    BYTES[i],
//                    bytes[i]
//                    );
//        }
    }

    public void test_AppendReadableByteChannel() throws IOException
    {
        final File              file    = File.createTempFile( getClass().getName(), "tmp" );
        final int               count   = 20;
        final int               size    = BYTES.length * count;

        {
            //Create the File
            FileOutputStream fos = new FileOutputStream( file );
            
            for( int i=0; i<count; i++ ) {
                fos.write( BYTES );
            }
            
            fos.close();
        }
        TestCase.assertEquals( "tmp file bad size",size,file.length());

        FileInputStream     fis         = new FileInputStream(file );
        ReadableByteChannel fileChannel = fis.getChannel();

        ByteArrayBuilder bab = new ByteArrayBuilder( 5 );
        
        bab.append( fileChannel );
        fis.close();
        
        TestCase.assertEquals("bab bad len",size,bab.length());

        byte[] bytes = bab.array();

        TestCase.assertEquals("array bad len",size,bytes.length);

        //clean up
        file.delete();
    }
    
    
    public void test_AppendInputStream() 
        throws IOException
    {
        InputStream is = new ByteArrayInputStream( BYTES );
//        {
//            int i = 0;
//            @Override
//            public int read() throws IOException
//            {
//                if( i< BYTES.length ) {
//                    return BYTES[i++];
//                }
//                return -1;
//            }
//        };
        ByteArrayBuilder bab = new ByteArrayBuilder();
        bab.append( is );
        Assert.assertEquals("mitchmatch",BYTES,bab.array());
    }

    public void test_startsWith()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        bab.append( BYTES );
        
        for(int i = 0;i<100;i++) {
            bab.append( OTHERBYTES );
        }
        
        boolean sw0 = bab.startsWith( new ByteArrayBuilder(BYTES) );
        TestCase.assertTrue("Should start with !",sw0);

        boolean sw1 = bab.startsWith( BYTES );
        TestCase.assertTrue("Should start with !",sw1);
    }

    public void test_endsWith()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();
        
        for(int i = 0;i<100;i++) {
            bab.append( OTHERBYTES );
        }
        bab.append( BYTES );
        
        boolean sw0 = bab.endsWith( new ByteArrayBuilder(BYTES) );
        TestCase.assertTrue("Should end with !",sw0);

        boolean sw1 = bab.endsWith( BYTES );
        TestCase.assertTrue("Should end with !",sw1);
    }

    public void test_compareTo()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        TestCase.assertEquals("Should be equals",0,bab.compareTo( new ByteArrayBuilder(BYTES) ));
        TestCase.assertTrue("Should be diffents",0!=bab.compareTo( new ByteArrayBuilder(OTHERBYTES) ));
    }

    public void test_equals()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        TestCase.assertTrue("Should be equals",bab.equals( new ByteArrayBuilder(BYTES) ));
        TestCase.assertFalse("Should be diffents",bab.equals( new ByteArrayBuilder(OTHERBYTES) ));
        ByteArrayBuilder nullBab = null;
        TestCase.assertFalse("Should be diffents",bab.equals( nullBab ));
        TestCase.assertFalse("Should be diffents",bab.equals( new Object() ));
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
        
        TestCase.assertTrue("Should be equals",bab.equals( clone ));
        TestCase.assertEquals("bad length",BYTES.length,bab.length());
        TestCase.assertEquals("bad array",BYTES.length,bab.array().length);

        byte[] bytes = bab.array();
        for( int i=0; i<bytes.length;i++) {
            TestCase.assertEquals(
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

        TestCase.assertTrue("Should be equals",bab.equals( clone ));
        TestCase.assertEquals("bad length",BYTES.length,bab.length());
        TestCase.assertEquals("bad array",BYTES.length,bab.array().length);

        byte[] bytes = bab.array();
        for( int i=0; i<bytes.length;i++) {
            TestCase.assertEquals(
                    String.format( "bad value [%d]",i ),
                    BYTES[i],
                    bytes[i]
                    );
        }
    }
}
