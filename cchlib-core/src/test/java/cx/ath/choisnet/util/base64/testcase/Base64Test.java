package cx.ath.choisnet.util.base64.testcase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;
import cx.ath.choisnet.io.IO; // TEST CASE ONLY
import cx.ath.choisnet.util.base64.Base64Decoder;
import cx.ath.choisnet.util.base64.Base64Encoder;
import cx.ath.choisnet.util.base64.Base64FormatException;

/**
 * Test {@link Base64Encoder} and {@link Base64Decoder}
 */
public class Base64Test
{
    final private static Logger slogger = Logger.getLogger(Base64Test.class);

    //
    private final static int BUFFER_SIZE = 5;
    //private final static int BUFFER_SIZE = 1024*1024;

    private final Base64Encoder encoder = new Base64Encoder( BUFFER_SIZE );
    private final Base64Decoder decoder = new Base64Decoder( BUFFER_SIZE );
    private final static String TEST_STRING = "This is a dummy message for this stupid test case!";

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testEncodeDecode_encodeToChar()
        throws UnsupportedEncodingException
    {
        byte[] toEncodeBytes = new byte[ 10 ];

        for( int i = 0; i<toEncodeBytes.length; i++)  {
            toEncodeBytes[ i ] = (byte)('A' + i);

            char[] enc   = Base64Encoder.encodeToChar( toEncodeBytes, 0, i );
            slogger.info( "encode " + i + " Character String res = [" + new String( enc ) + ']' + enc.length );

            byte[] decBytes = Base64Decoder.decode( enc, 0, enc.length );

            boolean eg = true;
            for( int icmp = 0; icmp<i; icmp++ ) {
                if( toEncodeBytes[ icmp ] != decBytes[ icmp ] ) {
                    eg = false;
                    break;
                }
            }
            slogger.info( "dec = [" + new String(decBytes) + ']' + decBytes.length );

            org.junit.Assert.assertTrue( "encoding to decoding mismatch !", eg );
            }
    }

    @Test
    public void testEncodeDecode_encode()
        throws UnsupportedEncodingException
    {
        StringBuilder encodeCharSB = new StringBuilder();

        for( int i = 0; i<9; i++)  {
            encodeCharSB.append( Character.valueOf( (char)('A' + i) ) );
            String s     = encodeCharSB.toString();
            String enc   = Base64Encoder.encode( s );
            slogger.info( "encode " + s.length() + " Character String res = [" + enc + "] " + enc.length() );

            String decStr = Base64Decoder.decode( enc );
            //String decStr    = new String( decBytes, "UTF8" );
            slogger.info( "dec = [" + decStr + "] " + decStr.length() );

            org.junit.Assert.assertTrue( "encoding to decoding mismatch !", s.equals(decStr) );
            }
    }

    @Test
    public void testBasic() throws UnsupportedEncodingException
    {
        slogger.info( "Encode => " + TEST_STRING );

        String encodedStr   = Base64Encoder.encode( TEST_STRING );

        slogger.info( "decode => " + encodedStr );
        byte[] decodedBytes = Base64Decoder.decode( encodedStr.toCharArray(), 0, encodedStr.length() );

        String decodedStr = new String( decodedBytes );
        slogger.info( "decoded => " + decodedStr );

        org.junit.Assert.assertTrue( "testBasic() encoding to decoding mismatch !", TEST_STRING.equals(decodedStr) );
    }

    @Test
    public void testEmpty()
        throws Base64FormatException, IOException
    {
        String emptyEncodedStr = Base64Encoder.encode( "" );
        slogger.info( "encode empty String res = [" + emptyEncodedStr + ']' );

        org.junit.Assert.assertTrue( "emptyEncodedStr should length of 0", emptyEncodedStr.length() == 0);
    }

    @Test
    public void testMiscCompare2Sun() throws IOException
    {
        testAndCompare2SunBASE64( TEST_STRING.getBytes() );

        {
            byte[] array1 = new byte[ 128 ];

            for( int i=0; i<128; i++ ) { array1[ i ] = (byte)i; }

            testAndCompare2SunBASE64( array1 );
        }
        {
            byte[] array2 = new byte[ 256 ];

            for( int i=0; i<256; i++ ) { array2[ i ] = (byte)i; }

            testAndCompare2SunBASE64( array2 );
        }

        {
            byte[] bytes = IOHelper.toByteArray( IO.getPNGFile() );

            testAndCompare2SunBASE64( bytes );
        }
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDecodeInputStreamOutputStream()
        throws Base64FormatException, IOException
    {
        String encodedStr = Base64Encoder.encode( TEST_STRING );

        InputStream           in  = new java.io.StringBufferInputStream(encodedStr);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( in, out);

        byte[] dec   = out.toByteArray();
        String decStr = new String( dec );

        slogger.info( "testDecodeInputStreamOutputStream() - dec = [" + decStr + ']' );
        org.junit.Assert.assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(decStr) );
    }

    @Test
    public void testDecodeReaderOutputStream()
        throws Base64FormatException, IOException
    {
        String encodedStr   = Base64Encoder.encode( TEST_STRING );
        char[] encodedChars = encodedStr.toCharArray();

        Reader                in  = new CharArrayReader( encodedChars );
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( in, out );

        byte[] dec   = out.toByteArray();
        String decStr = new String( dec );

        slogger.info( "testDecodeInputStreamOutputStream() - dec = [" + decStr + ']' );
        org.junit.Assert.assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(decStr) );
    }

    @Test
    public void testDecodeUsingOutputStream()
        throws Base64FormatException, IOException
    {
        String encodedStr = Base64Encoder.encode( TEST_STRING );
        String res        = staticTestDecodeUsingOutputStream( encodedStr );

        slogger.info( "testDecodeUsingOutputStream() [" + res + ']' );
        org.junit.Assert.assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(res) );
    }

    @Test
    public void testEncodeStatic() throws Base64FormatException, IOException
    {
        byte[] array = new byte[ 256 ];

        for( int i=0; i<256; i++ ) { array[ i ] = (byte)i; }

        String encodedString = Base64Encoder.encode( array );

        System.out.println( "Encoded: " + encodedString );
        System.out.println( "Encoded length=" + encodedString.length() );

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decodeToOutputStream( encodedString.toCharArray(), out );

        out.flush();
        out.close();

        byte[] result = out.toByteArray();

        org.junit.Assert.assertArrayEquals( "Bad result", array, result );
        cx.ath.choisnet.test.Assert.assertEquals( "Bad result", array, result );
    }

    @Test
    @Ignore // TODO DID NOT WORK if BUFFER_SIZE = 5
    public void testEncode() throws Base64FormatException, IOException
    {
        byte[] array = new byte[ 256 ];

        for( int i=0; i<256; i++ ) { array[ i ] = (byte)i; }

        InputStream in      = new ByteArrayInputStream( array );
        char[]      chars   = encodeInputStreamToCharArray( in );
        in.close();

        //FIXME int fixme;
        char[] encodedChars = Base64Encoder.encodeToChar( array );

        System.out.println( "chars v1 len = " + chars.length );
        System.out.println( "chars V2 len = " + encodedChars.length );

        System.out.println( "chars v1 = " + new String( chars ) );
        System.out.println( "chars V2 = " + new String( encodedChars ) );

        org.junit.Assert.assertArrayEquals( "Bad encode", chars, encodedChars );
        cx.ath.choisnet.test.Assert.assertEquals( "Bad encode", chars, encodedChars );

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decodeToOutputStream( chars, out );

        out.flush();
        out.close();

        byte[] result = out.toByteArray();

        org.junit.Assert.assertArrayEquals( "Bad result", array, result );
        cx.ath.choisnet.test.Assert.assertEquals( "Bad result", array, result );
    }

    @Test
    public void testFile() throws Base64FormatException, IOException
    {
        // ENCODE InputStream
        InputStream in      = IO.getPNGFile();
        char[]      chars   = encodeInputStreamToCharArray( in );
        in.close();

        System.out.println( "chars len = " + chars.length );
        System.out.println( "chars = " + new String( chars ) );
        System.out.println( "chars = " + new String( chars ).substring( 0, 344 ) );

        // DECODE using SUN Version
        {
            byte[]  sunDecode   = sunDecode( chars );
            boolean same        = IOHelper.isEquals( IO.getPNGFile(), sunDecode );

            System.out.println( "decode with sun - R=" + same );
            org.junit.Assert.assertTrue( "Error while decoding using SUN B64 classes", same );
        }

        // DECODE OutputStream
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decodeToOutputStream( chars, out );

        out.flush();
        out.close();

        // Check results
        in              = IO.getPNGFile();
        InputStream is  = new ByteArrayInputStream( out.toByteArray() );

        boolean same = IOHelper.isEquals( in, is );

        org.junit.Assert.assertTrue( same );

        in.close();
        is.close();
    }

    @Test
    public void testFileDeprecated() throws Base64FormatException, IOException
    {
        // ENCODE & DECODE
        InputStream             in  = IO.getPNGFile();
        ByteArrayOutputStream   out = new ByteArrayOutputStream();

        D_decodeToOutputStream( in, out ); // use decode deprecated method

        in.close();
        out.flush();
        out.close();

        in              = IO.getPNGFile();
        InputStream is  = new ByteArrayInputStream( out.toByteArray() );

        boolean same = IOHelper.isEquals( in, is );

        org.junit.Assert.assertTrue( same );
    }

//    @SuppressWarnings("deprecation")
//    private String D_staticTestDecodeUsingOutputStream( String str2decode )
//        throws Base64FormatException, IOException
//    {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        decoder.decode( str2decode.getBytes(), out );
//
//        return out.toString();
//    }

    private String staticTestDecodeUsingOutputStream( String str2decode )
        throws Base64FormatException, IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( str2decode.toCharArray(), out );

        return out.toString();
    }


    private char[] encodeInputStreamToCharArray( InputStream inBinary )
        throws Base64FormatException, IOException
    {
        CharArrayWriter out64 = new CharArrayWriter();
        {
            encoder.encode( inBinary, out64 );
            inBinary.close();
        }
        out64.close();

        return out64.toCharArray();
    }

    private void decodeToOutputStream( final char[] encoded, OutputStream outBinary )
        throws Base64FormatException, IOException
    {
        CharArrayReader in64 = new CharArrayReader( encoded );
        {
            decoder.decode( in64, outBinary );
            outBinary.flush();
        }
        in64.close();
    }

    @SuppressWarnings("deprecation")
    private void D_decodeToOutputStream( InputStream inBinary, OutputStream outBinary )
        throws Base64FormatException, IOException
    {
        File tmp = File.createTempFile( "B64", ".tmp" );
        tmp.deleteOnExit();

        {
            Writer  out64 = new FileWriter( tmp );

            encoder.encode( inBinary, out64 );
            inBinary.close();
        }

        InputStream in64 = new FileInputStream( tmp );
        {
            decoder.decode( in64, outBinary );
            outBinary.flush();
        }
        in64.close();
        tmp.delete();
    }

    private static void testAndCompare2SunBASE64( final byte[] bytes )
        throws IOException
    {
        String sunEncode = sunEncode( bytes );
        String encode    = Base64Encoder.encode( bytes );

        org.junit.Assert.assertEquals( "bad encoding", sunEncode, encode );

        {
            byte[] sunDecode = sunDecode( sunEncode );
            byte[] decode    = Base64Decoder.decode( sunEncode.toCharArray() );

            org.junit.Assert.assertArrayEquals( "bad decoding", sunDecode, decode );
            cx.ath.choisnet.test.Assert.assertEquals( "bad decoding", sunDecode, decode );
        }
        {
            byte[] sunDecode = sunDecode( encode );
            byte[] decode    = Base64Decoder.decode( encode.toCharArray() );

            org.junit.Assert.assertArrayEquals( "bad decoding", sunDecode, decode );
            cx.ath.choisnet.test.Assert.assertEquals( "bad decoding", sunDecode, decode );
        }
    }

    private static String sunEncode( final byte[] bytes ) throws IOException
    {
        @SuppressWarnings("restriction")
        String sunEncode = (new sun.misc.BASE64Encoder()).encode( bytes );

        // Remove EOL characters
        StringBuilder sb = new StringBuilder();

        for( char c : sunEncode.toCharArray() ) {
            if( c > ' ' ) {
                sb.append( c );
                }
            }

        sunEncode = sb.toString();

        return sunEncode;
    }

    private static byte[] sunDecode( final char[] b64encoded ) throws IOException
    {
        final Reader    r  = new CharArrayReader( b64encoded );
        InputStream     is = new InputStream() {
                @Override
                public int read() throws IOException
                {
                    return r.read();
                }
            };

        @SuppressWarnings("restriction")
        byte[] sunDecode = (new sun.misc.BASE64Decoder()).decodeBuffer( is );

        return sunDecode;
    }

    private static byte[] sunDecode( final String b64encoded ) throws IOException
    {
        @SuppressWarnings("restriction")
        byte[] sunDecode = (new sun.misc.BASE64Decoder()).decodeBuffer( b64encoded );

        return sunDecode;
    }
}
