package com.googlecode.cchlib.util.base64;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; // TEST CASE ONLY
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.IOTestHelper;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.test.ArrayAssert;

/**
 * Test {@link Base64Encoder} and {@link Base64Decoder}
 */
@SuppressWarnings("resource")
public class Base64Test
{
    private static final Logger LOGGER = Logger.getLogger(Base64Test.class);

    private static final String TEST_STRING = "This is a dummy message for this stupid test case!";

    private static final int TEST_BUFFER_SIZE = 1024;
    private final String bigTestString;

    public Base64Test()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( TEST_STRING );

        for( int i = 0; i<128; i++ ) {
            sb.append( " - " );
            sb.append( TEST_STRING );
            }

        this.bigTestString = sb.toString();
    }

    @Test
    public void testEncodeDecode_encodeToChar()
        throws UnsupportedEncodingException
    {
        final byte[] toEncodeBytes = new byte[ 10 ];

        for( int i = 0; i<toEncodeBytes.length; i++)  {
            toEncodeBytes[ i ] = (byte)('A' + i);

            final char[] enc   = Base64Encoder.encodeToChar( toEncodeBytes, 0, i );
            LOGGER.info( "encode " + i + " Character String res = [" + toString( enc ) + ']' + enc.length );

            final byte[] decBytes = Base64Decoder.decode( enc, 0, enc.length );

            boolean eg = true;
            for( int icmp = 0; icmp<i; icmp++ ) {
                if( toEncodeBytes[ icmp ] != decBytes[ icmp ] ) {
                    eg = false;
                    break;
                }
            }
            LOGGER.info( "dec = [" + toString(decBytes) + ']' + decBytes.length );

            assertTrue( "encoding to decoding mismatch !", eg );
            }
    }

    private String toString( final byte[] bytes )
    {
        return new String( bytes );
    }

    private String toString( final char[] chars )
    {
        return new String( chars );
    }

    @Test
    public void testBigString() throws UnsupportedEncodingException
    {
        final String encoded = Base64Encoder.encode( this.bigTestString );
        final String decoder = Base64Decoder.decode( encoded );

        assertEquals( this.bigTestString, decoder);
    }

    @Test
    public void testEncodeDecode_encode()
        throws UnsupportedEncodingException
    {
        final StringBuilder encodeCharSB = new StringBuilder();

        for( int i = 0; i<9; i++)  {
            encodeCharSB.append( Character.valueOf( (char)('A' + i) ) );
            final String s     = encodeCharSB.toString();
            final String enc   = Base64Encoder.encode( s );
            LOGGER.info( "encode " + s.length() + " Character String res = [" + enc + "] " + enc.length() );

            final String decStr = Base64Decoder.decode( enc );
            //String decStr    = new String( decBytes, "UTF8" );
            LOGGER.info( "dec = [" + decStr + "] " + decStr.length() );

            assertTrue( "encoding to decoding mismatch !", s.equals(decStr) );
            }
    }

    @Test
    public void testBasic() throws UnsupportedEncodingException
    {
        LOGGER.info( "Encode => " + TEST_STRING );

        final String encodedStr = Base64Encoder.encode( TEST_STRING );
        LOGGER.info( "encode result => " + encodedStr );

        final byte[] decodedBytes = Base64Decoder.decode( encodedStr.toCharArray(), 0, encodedStr.length() );
        final String decodedStr = new String( decodedBytes );

        LOGGER.info( "decoded result => " + decodedStr );

        assertTrue( "testBasic() encoding to decoding mismatch !", TEST_STRING.equals(decodedStr) );
    }

    @Test
    public void testBasic1Char() throws UnsupportedEncodingException
    {
        final byte[] bytes = new byte[1];

        for( int currentByte = Byte.MIN_VALUE; currentByte<= Byte.MAX_VALUE; currentByte++ ) {
            bytes[ 0 ] = (byte)(0x00FF | currentByte);

            LOGGER.info( "Encode byte = " + toString( bytes ) );

            final char[] encodedChars = Base64Encoder.encodeToChar( bytes );
            LOGGER.info( "encode result => " + toString( encodedChars ) );

            final byte[] decodedBytes = Base64Decoder.decode( encodedChars );

            LOGGER.info( "decoded result => " +  toString( decodedBytes ) );

            assertArrayEquals(
                "testBasic1Char() encoding to decoding mismatch !",
                bytes,
                decodedBytes
                );
            }
    }

    @Test
    public void testBasicNChars() throws UnsupportedEncodingException
    {
        for( int len = 0; len<256; len++ ) {
            final byte[] bytes = new byte[ len ]; // $codepro.audit.disable avoidInstantiationInLoops

            for( int currentByte = 0; currentByte<len; currentByte++ ) {
                bytes[ currentByte ] = (byte)(0x00FF | currentByte);
                }

            final char[] encodedChars = Base64Encoder.encodeToChar( bytes );
            final byte[] decodedBytes = Base64Decoder.decode( encodedChars );

            assertArrayEquals(
                "testBasic1Char() encoding to decoding mismatch !",
                bytes,
                decodedBytes
                );
            }
    }

    @Test
    public void testEmpty()
        throws Base64FormatException, IOException
    {
        final String emptyEncodedStr = Base64Encoder.encode( StringHelper.EMPTY );
        LOGGER.info( "encode empty String res = [" + emptyEncodedStr + ']' );

        assertTrue( "emptyEncodedStr should length of 0", emptyEncodedStr.length() == 0);
    }

    @Test
    public void testMiscCompare2Sun() throws IOException
    {
        testAndCompare2SunBASE64( TEST_STRING.getBytes() );

        {
            final byte[] array1 = new byte[ 128 ];

            for( int i=0; i<128; i++ ) { array1[ i ] = (byte)i; }

            testAndCompare2SunBASE64( array1 );
        }
        {
            final byte[] array2 = new byte[ 256 ];

            for( int i=0; i<256; i++ ) { array2[ i ] = (byte)i; }

            testAndCompare2SunBASE64( array2 );
        }

        try (InputStream pngIS = IOTestHelper.createPNGInputStream()) {
            final byte[] bytes = IOHelper.toByteArray( pngIS );

            testAndCompare2SunBASE64( bytes );
            }
    }

    @Test
    public void testDecodeReaderOutputStream()
        throws Base64FormatException, IOException
    {
        final Base64Decoder decoder = new Base64Decoder( TEST_BUFFER_SIZE );

        final String encodedStr   = Base64Encoder.encode( TEST_STRING );
        final char[] encodedChars = encodedStr.toCharArray();

        final Reader                in  = new CharArrayReader( encodedChars );
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( in, out );

        final byte[] dec   = out.toByteArray();
        final String decStr = new String( dec );

        LOGGER.info( "testDecodeInputStreamOutputStream() - dec = [" + decStr + ']' );
        assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(decStr) );
    }

    @Test
    public void testDecodeUsingOutputStream()
        throws Base64FormatException, IOException
    {
        final String encodedStr = Base64Encoder.encode( TEST_STRING );
        final String res        = staticTestDecodeUsingOutputStream( encodedStr );

        LOGGER.info( "testDecodeUsingOutputStream() [" + res + ']' );
        assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(res) );
    }

    @Test
    public void testEncodeStatic() throws Base64FormatException, IOException
    {
        final byte[] array = new byte[ 256 ];

        for( int i=0; i<256; i++ ) { array[ i ] = (byte)i; }

        final String encodedString = Base64Encoder.encode( array );

        LOGGER.info( "Encoded: " + encodedString );
        LOGGER.info( "Encoded length=" + encodedString.length() );

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        decodeToOutputStream( encodedString.toCharArray(), out );

        out.flush();
        out.close();

        final byte[] result = out.toByteArray();

        assertArrayEquals( "Bad result", array, result );
        ArrayAssert.assertEquals( "Bad result", array, result );
    }

    @Test
    public void testEncodeDecodeBinary() throws Base64FormatException, IOException
    {
        final int prev = 0;

        for( int i = 0; i < 2048; i++ ) {
            final int bufferSize = Base64.computeEncoderBufferSize( i );

            if( bufferSize > prev ) {
                testEncodeDecodeBinary( bufferSize );
                }
            }
    }

    private static void testEncodeDecodeBinary(
        final int encoderBufferSize
        )
        throws Base64FormatException, IOException
    {
        LOGGER.info(
            "Current Base64Encoder bufferSize : "
                + encoderBufferSize
                );

        final byte[] arraySource = new byte[ 256 ];

        for( int i=0; i<arraySource.length; i++ ) {
            arraySource[ i ] = (byte)i;
            }

        final Base64Encoder encoder = new Base64Encoder( encoderBufferSize );

        // Build result REF
        final String        strResultRef = iharderEncodeToString( arraySource );

        // Build result 1 using an InputStream
        final InputStream   is = new ByteArrayInputStream( arraySource );
        final String        strResult1;

        try {
            final CharArrayWriter writer = new CharArrayWriter();

            try {
                encoder.encode( is, writer );
                }
            finally {
                writer.close();
                }

            strResult1 = writer.toString();
            }
        finally {
            is.close();
            }

        // Build result 2 using an array of bytes
        final String strResult2;
        {
            final char[] charsResult2 = Base64Encoder.encodeToChar( arraySource );

            strResult2 = new String( charsResult2 );
        }

        LOGGER.info( "ref len = " + strResultRef.length() );
        LOGGER.info( "v1  len = " + strResult1.length()   );
        LOGGER.info( "V2  len = " + strResult2.length()   );

        LOGGER.info( "ref = " + strResultRef );
        LOGGER.info( "v1  = " + strResult1   );
        LOGGER.info( "V2  = " + strResult2   );

        assertEquals( "error encode1", strResultRef, strResult1 );
        assertEquals( "error encode2", strResultRef, strResult2 );

        // Restore original value
        final byte[] decodedResult;
        {
            final ByteArrayOutputStream out     = new ByteArrayOutputStream();

            try {
                final StringReader          in      = new StringReader( strResult1 );
                final Base64Decoder         decoder = new Base64Decoder( TEST_BUFFER_SIZE );

                try {
                    decoder.decode( in, out );
                    out.flush();
                    }
                finally {
                    in.close();
                    }
                }
            finally {
                out.close();
                }

            decodedResult = out.toByteArray();
        }

        ArrayAssert.assertEquals( "Bad final result", arraySource, decodedResult );
        assertArrayEquals( "Bad final result", arraySource, decodedResult );
    }

    @Test
    public void testFile() throws Base64FormatException, IOException
    {
        final Base64Encoder encoder = new Base64Encoder( TEST_BUFFER_SIZE * 1000 );
        // ENCODE InputStream
        char[]      encodedPNGFileInB64CharArray;

        try( InputStream in = IOTestHelper.createPNGInputStream() ) {
            final CharArrayWriter caw = new CharArrayWriter();

            try {
                encoder.encode( in, caw );
                }
            finally {
                caw.close();
                }

            encodedPNGFileInB64CharArray = caw.toCharArray();
            }

        LOGGER.info( "chars len = " + encodedPNGFileInB64CharArray.length );
        LOGGER.info( "chars = " + new String( encodedPNGFileInB64CharArray ) );
        LOGGER.info( "chars = " + new String( encodedPNGFileInB64CharArray ).substring( 0, 344 ) );

        // DECODE using SUN Version
        {
            final byte[] sunDecode = iharderDecode( encodedPNGFileInB64CharArray );

            try( InputStream pngIS = IOTestHelper.createPNGInputStream() ) {
                final boolean same        = IOHelper.isEquals( pngIS, sunDecode );

                LOGGER.info( "decode with sun - R=" + same );

                assertTrue( "Error while decoding using SUN B64 classes", same );
                }
        }

        // DECODE OutputStream
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        decodeToOutputStream( encodedPNGFileInB64CharArray, out );

        out.flush();
        out.close();

        // Check results
        try( final InputStream in = IOTestHelper.createPNGInputStream() ) {
            try( final InputStream is = new ByteArrayInputStream( out.toByteArray() ) ) {
                final boolean same = IOHelper.isEquals( in, is );

                assertTrue( same );
            }
        }
    }

    private static String staticTestDecodeUsingOutputStream( final String str2decode )
        throws Base64FormatException, IOException
    {
        final Base64Decoder         decoder = new Base64Decoder( TEST_BUFFER_SIZE );
        final ByteArrayOutputStream out     = new ByteArrayOutputStream();

        decoder.decode( str2decode.toCharArray(), out );

        return out.toString();
    }

    private static void decodeToOutputStream(
        final char[]        encoded,
        final OutputStream  out
        )
        throws Base64FormatException, IOException
    {
        ;
        final Base64Decoder decoder = new Base64Decoder( TEST_BUFFER_SIZE );

        try( final CharArrayReader in = new CharArrayReader( encoded ) ) {
            decoder.decode( in, out );
            out.flush();
            }
    }

    private static void testAndCompare2SunBASE64( final byte[] bytes )
        throws IOException
    {
        //String sunEncode = sunEncodeToString( bytes );
        final String sunEncode = iharderEncodeToString( bytes );
        final String encode    = Base64Encoder.encode( bytes );

        assertEquals( "bad encoding", sunEncode, encode );

        {
            //byte[] sunDecode = sunDecode( sunEncode );
            final byte[] sunDecode = iharderDecode( sunEncode );

            final byte[] decode    = Base64Decoder.decode( sunEncode.toCharArray() );

            assertArrayEquals( "bad decoding", sunDecode, decode );
            ArrayAssert.assertEquals( "bad decoding", sunDecode, decode );
        }
        {
            final byte[] sunDecode = iharderDecode( encode );

            final byte[] decode    = Base64Decoder.decode( encode.toCharArray() );

            assertArrayEquals( "bad decoding", sunDecode, decode );
            ArrayAssert.assertEquals( "bad decoding", sunDecode, decode );
        }
    }

    private static String iharderEncodeToString( final byte[] bytes )
    {
        return net.iharder.Base64.encodeBytes( bytes );
    }

    private static byte[] iharderDecode( final String b64encoded )
        throws IOException
    {
        return net.iharder.Base64.decode( b64encoded );
    }

    private static byte[] iharderDecode( final char[] b64encoded )
        throws IOException
    {
        return iharderDecode( new String( b64encoded ) );
    }
}
