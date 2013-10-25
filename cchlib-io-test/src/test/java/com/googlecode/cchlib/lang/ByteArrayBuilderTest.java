package com.googlecode.cchlib.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.SerializableHelper;
import com.googlecode.cchlib.test.ArrayAssert;

/**
 *
 */
public class ByteArrayBuilderTest
{
    private final static Logger logger = Logger.getLogger( ByteArrayBuilderTest.class );

    private final static byte[] BYTES = {'a','b','c','d','e','f'};
    private final static byte[] OTHERBYTES = {'A','B','C','D','E','F', 'G'};
    private final static int    BIG_CAPACITY = 2048 * 100;

    private final static byte[] _SHARP_160_         = { '&', '#', '1', '6', '0', ';' };
    private final static byte[] _SHARP_160_ENCODED_ = { '$', '#', '1', '6', '0', ';' };

    @Test
    public void testConstructor1()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();

        assertEquals("bad length",0,bab.length());
        assertEquals("bad array",0,bab.array().length);
    }

    @Test
    public void testConstructor2()
    {
        int capacity = 10;
        ByteArrayBuilder bab = new ByteArrayBuilder(capacity);

        assertEquals("bad length",0,bab.length());
        assertEquals("bad array",0,bab.array().length);
        assertEquals("bad capacity()",capacity,bab.capacity());
    }

    @Test
    public void testConstructor3()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        assertEquals("bad length",BYTES.length,bab.length());

        ArrayAssert.assertEquals("mitchmatch",BYTES,bab.array());
    }

    @Test
    public void testConstructor4()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(null);

        assertEquals("bad length",0,bab.length());
        assertEquals("bad array",0,bab.array().length);
    }

    @Test
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

    @Test
    public void test_ensureCapacity()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);
        bab.ensureCapacity(BIG_CAPACITY);

        assertEquals("bad length",BYTES.length,bab.length());
        assertEquals("bad array",BYTES.length,bab.array().length);

        ArrayAssert.assertEquals("mitchmatch",BYTES,bab.array());
   }

    @Test
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

    @Test
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

    @Test
    public void test_append_byte()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder();

        for(int i=0;i<BYTES.length;i++) {
            bab.append( BYTES[i] );
        }

        assertEquals("bad length",BYTES.length,bab.length());
        assertEquals("bad array",BYTES.length,bab.array().length);

        ArrayAssert.assertEquals("mitchmatch",BYTES,bab.array());
    }

    @Test
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
        assertEquals( "tmp file bad size",size,file.length());

        ByteArrayBuilder bab = new ByteArrayBuilder( 5 );
        FileInputStream  fis = new FileInputStream(file );

        try {
            ReadableByteChannel fileChannel = fis.getChannel();
            
            try {
                 bab.append( fileChannel );
                }
            finally {
                fileChannel.close();
                }
            }
        finally {
            fis.close();
            }

        assertEquals("bab bad len",size,bab.length());

        byte[] bytes = bab.array();

        assertEquals("array bad len",size,bytes.length);

        //clean up
        file.delete();
    }


    @Test
    public void test_AppendInputStream() throws IOException
    {
        InputStream is = new ByteArrayInputStream( BYTES );
        ByteArrayBuilder bab = new ByteArrayBuilder();
        bab.append( is );

        ArrayAssert.assertEquals("mitchmatch",BYTES,bab.array());
    }

    @Test
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

    @Test
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

    @Test
    public void test_compareTo()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        assertEquals("Should be equals",0,bab.compareTo( new ByteArrayBuilder(BYTES) ));
        assertTrue("Should be diffents",0!=bab.compareTo( new ByteArrayBuilder(OTHERBYTES) ));
    }

    @Test
    public void test_equals()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        assertTrue("Should be equals",bab.equals( new ByteArrayBuilder(BYTES) ));
        assertFalse("Should be diffents",bab.equals( new ByteArrayBuilder(OTHERBYTES) ));

        ByteArrayBuilder nullBab = null;

        assertFalse("Should be diffents",bab.equals( nullBab ));
        assertFalse("Should be diffents",bab.equals( new Object() ));
    }

    @Test
    public void test_toString()
    {
        ByteArrayBuilder bab = new ByteArrayBuilder(BYTES);

        final byte[] bytes      = bab.array();
        final String expected   = new String( bytes );

        String str = bab.toString();

        assertEquals("Should be equals",expected, str);
    }

    @Test
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

    @Test
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

    @Test
    public void test_replaceAll()
    {
        ByteArrayBuilder bab0 = new ByteArrayBuilder();
        bab0.append( createBytes() );
        logger.info( "bab0 = " + new String(bab0.array()) );

        ByteArrayBuilder bab1 = bab0.replaceAll( _SHARP_160_, _SHARP_160_ENCODED_ );
        logger.info( "bab1 = " + new String(bab1.array()) );

        ByteArrayBuilder bab2 = bab1.replaceAll( _SHARP_160_ENCODED_, _SHARP_160_ );
        logger.info( "bab2 = " + new String(bab2.array()) );

        int cmp1 = bab0.compareTo( bab2 );
        int cmp2 = bab2.compareTo( bab0 );

        assertEquals( 0, cmp1 );
        assertEquals( 0, cmp2 );
    }

    @Test
    public void test_createInputStream() throws IOException
    {
        ByteArrayBuilder bab0 = new ByteArrayBuilder();
        bab0.append( createBytes() );
        logger.info( "test_createInputStream(): bab0 = " + new String(bab0.array()) );

        InputStream is = bab0.createInputStream();
        ByteArrayBuilder bab1 = new ByteArrayBuilder();

        try {
            bab1.append( is );
            }
        finally {
            is.close();
            }

        logger.info( "test_createInputStream(): bab1 = " + new String(bab1.array()) );

        int cmp0 = bab0.compareTo( bab1 );
        int cmp1 = bab0.compareTo( bab0 );

        assertEquals( 0, cmp1 );
        assertEquals( 0, cmp0 );
    }
    
    @Test
    public void test_copyTo() throws IOException
    {
        ByteArrayBuilder bab0 = new ByteArrayBuilder();
        bab0.append( createBytes() );
        logger.info( "test_copyTo(): bab0 = " + new String(bab0.array()) );

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            bab0.copyTo( out );
            }
        finally {
            out.close();
            }
        
        ByteArrayBuilder bab1 = new ByteArrayBuilder();
        bab1.append( out.toByteArray() );
        logger.info( "test_copyTo(): bab1 = " + new String(bab1.array()) );

        int cmp0 = bab0.compareTo( bab1 );
        int cmp1 = bab0.compareTo( bab0 );

        assertEquals( 0, cmp1 );
        assertEquals( 0, cmp0 );
    }

    private static String getRefString()
    {
        return "This a text with something to escape inside &#160;";
    }

    private static byte[] createBytes()
    {
        return getRefString().getBytes();
    }
}
