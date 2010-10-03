package cx.ath.choisnet.util.base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;
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
    private final static String toEncodeStr = "This is a dummy message for this stupid test case!";

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
            slogger.info( "encode " + i + " Character String res = [" + new String( enc ) + "]" + enc.length );

            byte[] decBytes = Base64Decoder.decode( enc, 0, enc.length );

            boolean eg = true;
            for( int icmp = 0; icmp<i; icmp++ ) {
                if( toEncodeBytes[ icmp ] != decBytes[ icmp ] ) {
                    eg = false;
                    break;
                }
            }
            slogger.info( "dec = [" + new String(decBytes) + "]" + decBytes.length );

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
        slogger.info( "dec = [" + decStr + "]" + decStr.length() );

        assertTrue( "encoding to decoding mismatch !", s.equals(decStr) );
        }
}
    public void testBasic() throws UnsupportedEncodingException
    {
        slogger.info( "Encode => " + toEncodeStr );

        String encodedStr   = Base64Encoder.encode( toEncodeStr );

        slogger.info( "decode => " + encodedStr );
        byte[] decodedBytes = Base64Decoder.decode( encodedStr.toCharArray(), 0, encodedStr.length() );

        String decodedStr = new String( decodedBytes );
        slogger.info( "decoded => " + decodedStr );

        assertTrue( "testBasic() encoding to decoding mismatch !", toEncodeStr.equals(decodedStr) );
    }

    public void testEmpty()
        throws Base64FormatException, IOException
    {
        String emptyEncodedStr = Base64Encoder.encode( "" );
        slogger.info( "encode empty String res = [" + emptyEncodedStr + "]" );
        assertTrue( "emptyEncodedStr should length of 0", emptyEncodedStr.length() == 0);
    }


    public void testDecodeInputStreamOutputStream()
    throws Base64FormatException, IOException
    {
        String encodedStr = Base64Encoder.encode( toEncodeStr );

        @SuppressWarnings("deprecation")
        InputStream           in   = new java.io.StringBufferInputStream(encodedStr);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( in, out);

        byte[] dec   = out.toByteArray();
        String decStr = new String( dec );

        slogger.info( "testDecodeInputStreamOutputStream() - dec = [" + decStr + "]" );
        assertTrue( "encoding to decoding mismatch !", toEncodeStr.equals(decStr) );
    }

    public void testDecodeUsingOutputStream()
        throws Base64FormatException, IOException
    {
        String encodedStr = Base64Encoder.encode( toEncodeStr );
        String res        = staticTestDecodeUsingOutputStream( encodedStr );

        slogger.info( "testDecodeUsingOutputStream() [" + res + "]" );
        assertTrue( "encoding to decoding mismatch !", toEncodeStr.equals(res) );
    }

    public void testFile()
    {
        // TODO; staticTestFile( file, xxx );
    }

    public static String staticTestDecodeUsingOutputStream( String str2decode )
        throws Base64FormatException, IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        decoder.decode( str2decode.getBytes(), out );

        return out.toString();
    }

    public static void staticTestFile( String filename, OutputStream out )
        throws Base64FormatException, IOException
    {
        FileInputStream in = new FileInputStream( filename );
        Base64Decoder b = new Base64Decoder();

        b.decode( in, out );
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
