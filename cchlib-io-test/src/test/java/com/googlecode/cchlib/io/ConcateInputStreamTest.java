package com.googlecode.cchlib.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class ConcateInputStreamTest
{
    public void test( final InputStream is ) throws Exception
    {
        is.available();
        is.close();
        //is.equals( obj );
        //is.hashCode();
        /*
        int readlimit;
        is.markSupported();
        is.mark( readlimit );
        is.reset();
        */

        /*
        is.read();
        byte[] b;
        is.read( b );
        int off;
        int len;
        is.read( b, off, len );
        long skiplen;
        is.skip( skiplen );
        */
    }

    @Test
    public void testConcateInputStreamTest1() throws IOException
    {
        final byte[] ba1 = { 1, 2, 3 };
        final byte[] ba2 = { 4, 5, 6, 7, 8, 9 };
        //final byte[] ba3 = { 10, 11, 12, 13, 14, 14, 16 };

        final byte[]     res     = new byte[ ba1.length + ba2.length ];
        int                count     = 0;

        for( final byte b : ba1 ) {
            res[ count++ ] = b;
            }
        for( final byte b : ba2 ) {
            res[ count++ ] = b;
            }

        final InputStream is1 = new ByteArrayInputStream( ba1  );
        final InputStream is2 = new ByteArrayInputStream( ba2  );
        //InputStream is3 = new ByteArrayInputStream( ba3  );
        try (ConcateInputStream cis = new ConcateInputStream( is1, is2 )) {
            int                    c;

            count = 0;

            while( (c = cis.read()) >= 0 ) {
                final byte b = (byte)c;

                Assert.assertEquals( "Error at # value:" + count, res[count], b );

                count++;
            }

            Assert.assertEquals( "Bad size !", res.length, count );
        }
    }

}
