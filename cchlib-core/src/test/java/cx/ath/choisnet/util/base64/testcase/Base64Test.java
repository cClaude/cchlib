package cx.ath.choisnet.util.base64.testcase;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;
import cx.ath.choisnet.util.base64.Base64Decoder;
import cx.ath.choisnet.util.base64.Base64Encoder;
import cx.ath.choisnet.util.base64.Base64FormatException;
import junit.framework.TestCase;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class Base64Test extends TestCase
{
    final private static Logger slogger = Logger.getLogger(Base64Test.class);
    //private final static Base64Encoder encoder = new Base64Encoder();
    private final static Base64Decoder decoder = new Base64Decoder( 5 );
    private final static String TEST_STRING = "This is a dummy message for this stupid test case!";

    public void setUp()
    {

    }

    public void testEncodeDecode_encodeToChar()
        throws UnsupportedEncodingException
    {
        byte[] toEncodeBytes = new byte[ 10 ];

        for( int i = 0; i<toEncodeBytes.length; i++)  {
            toEncodeBytes[ i ] = (byte)('A' + i);
//            String s     = encodeCharSB.toString();
//            char[] enc   = Base64Encoder.encodeToChar( s.getBytes( "UTF8" ) );
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

            assertTrue( "encoding to decoding mismatch !", eg );
            }
    }

    public void testEncodeDecode_encode()
        throws UnsupportedEncodingException
    {
    StringBuilder encodeCharSB = new StringBuilder();

    for( int i = 0; i<9; i++)  {
        encodeCharSB.append( new Character( (char)('A' + i) ) );
        String s     = encodeCharSB.toString();
        String enc   = Base64Encoder.encode( s );
        slogger.info( "encode " + s.length() + " Character String res = [" + enc + "] " + enc.length() );

        String decStr = Base64Decoder.decode( enc );
        //String decStr    = new String( decBytes, "UTF8" );
        slogger.info( "dec = [" + decStr + "] " + decStr.length() );

        assertTrue( "encoding to decoding mismatch !", s.equals(decStr) );
        }
}
    public void testBasic() throws UnsupportedEncodingException
    {
        slogger.info( "Encode => " + TEST_STRING );

        String encodedStr   = Base64Encoder.encode( TEST_STRING );

        slogger.info( "decode => " + encodedStr );
        byte[] decodedBytes = Base64Decoder.decode( encodedStr.toCharArray(), 0, encodedStr.length() );

        String decodedStr = new String( decodedBytes );
        slogger.info( "decoded => " + decodedStr );

        assertTrue( "testBasic() encoding to decoding mismatch !", TEST_STRING.equals(decodedStr) );
    }

    public void testEmpty()
        throws Base64FormatException, IOException
    {
        String emptyEncodedStr = Base64Encoder.encode( "" );
        slogger.info( "encode empty String res = [" + emptyEncodedStr + ']' );
        assertTrue( "emptyEncodedStr should length of 0", emptyEncodedStr.length() == 0);
    }

    public void testMiscCompare2Sun() throws IOException
    {
        testAndCompare2SunBASE64( TEST_STRING );
    }

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
        assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(decStr) );
    }

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
        assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(decStr) );
    }

    public void testDecodeUsingOutputStream()
        throws Base64FormatException, IOException
    {
        String encodedStr = Base64Encoder.encode( TEST_STRING );
        String res        = staticTestDecodeUsingOutputStream( encodedStr );

        slogger.info( "testDecodeUsingOutputStream() [" + res + ']' );
        assertTrue( "encoding to decoding mismatch !", TEST_STRING.equals(res) );
    }

//    public void testFile()
//    {
//        File            file;
//        OutputStream    out;
//
//        staticTestFile( file, out );
//    }

    @SuppressWarnings("deprecation")
    public static String staticTestDecodeUsingOutputStreamOld( String str2decode )
        throws Base64FormatException, IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( str2decode.getBytes(), out );

        return out.toString();
    }

    public static String staticTestDecodeUsingOutputStream( String str2decode )
        throws Base64FormatException, IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( str2decode.toCharArray(), out );

        return out.toString();
    }

    @SuppressWarnings("deprecation")
    private static void staticTestFileOld( File file, OutputStream out )
        throws Base64FormatException, IOException
    {
        InputStream     in  = new FileInputStream( file );
        Base64Decoder   b   = new Base64Decoder();

        b.decode( in, out );
     }

    private static void staticTestFile( File file, OutputStream out )
        throws Base64FormatException, IOException
    {
        Reader          in  = new FileReader( file );
        Base64Decoder   b   = new Base64Decoder();

        b.decode( in, out );
     }

    public static void testAndCompare2SunBASE64( final String str2code )
        throws IOException
    {
        final byte[] bytes = str2code.getBytes();

        @SuppressWarnings("restriction")
        String sunEncode = (new sun.misc.BASE64Encoder()).encode( bytes );
        String encode    = Base64Encoder.encode( str2code );

        assertEquals( "bad encoding", sunEncode, encode );

        @SuppressWarnings("restriction")
        byte[] sunDecode = (new sun.misc.BASE64Decoder()).decodeBuffer( encode );
        String decode    = Base64Decoder.decode( encode );

        String sunDecodeStr = new String( sunDecode );

        assertEquals( "bad use Sun Base 64", str2code, sunDecodeStr );
        assertEquals( "bad decoding", str2code, decode );
    }

//    public static void main( String[] args )
//    {
//        if( args.length == 1 ) {
//            try {
//                String res = staticTestDecodeUsingOutputStream( args[ 0 ] );
//
//                slogger.info( (new StringBuilder())
//                        .append( "[" )
//                        .append( res )
//                        .append( "]" )
//                        .toString()
//                        );
//                }
//            catch( Exception e ) {
//                System.out.println( "Invalid Base64 format !" + e );
//
//                e.printStackTrace( System.err );
//            }
//        } else if( args.length == 2 && args[ 0 ].equals( "-f" ) ) {
//            try {
//                staticTestFile( args[ 1 ], System.out );
//            }
//            catch( Exception e ) {
//                System.out.println( (new StringBuilder())
//                        .append( "error: " )
//                        .append( e.getMessage() )
//                        .toString()
//                        );
//
//                e.printStackTrace( System.err );
//            }
//        } else {
//            System.out.println( "Base64Decoder [strong] [-f file]" );
//        }
//    }
}
