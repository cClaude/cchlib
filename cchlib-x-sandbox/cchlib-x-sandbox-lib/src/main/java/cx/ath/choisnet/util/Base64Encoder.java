/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/Base64Encoder.java
** Description   :
** Encodage      : ANSI
**
**  1.54.003 2005.09.07 Claude CHOISNET
**                      Version initiale.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.Base64Encoder
**
*/
package cx.ath.choisnet.util;

import java.io.UnsupportedEncodingException;

/**
**
** @author Claude CHOISNET
** @version 1.00
*/
public class Base64Encoder
    implements Base64
{

/**
**
*/
public static String encode( String s ) // --------------------------------
    throws UnsupportedEncodingException
{
 return encode( s, "8859_1" );
}

/**
**
*/
public static String encode( String str, String charsetName ) // ----------
    throws UnsupportedEncodingException
{
 return encode( str.getBytes( charsetName ) );
}

/**
**
*/
public static String encode( byte[] bytes ) // ----------------------------
    throws UnsupportedEncodingException
{
 try {
    int     k           = 0;
    int     length      = bytes.length;
    char[]  ac          = new char[ (length / 3) * 4 + 4 ];
    int     b           = (length / 3) * 3;
    int     i;

    for(i = 0; i < b; i += 3) {
        int j = bytes[i] << 16 | bytes[i + 1] << 8 | bytes[i + 2];

        ac[k + 3] = base64[j & 0x3f];
        ac[k + 2] = base64[j >>> 6 & 0x3f];
        ac[k + 1] = base64[j >>> 12 & 0x3f];
        ac[k] = base64[j >>> 18 & 0x3f];
        k += 4;
        }

    if( i < length ) {
        ac[ k ]     = base64[ bytes[ i ] >>> 2 ];
        ac[ k + 3]  = '=';

        int l = (bytes[i] & 0x3) << 4;

        ac[k + 1] = base64[l];
        ac[k + 2] = '=';

        if(i != length - 1) {
            ac[k + 1] = base64[l | (bytes[i + 1] & 0xf0) >>> 4];
            ac[k + 2] = base64[(bytes[i + 1] & 0xf) << 2];
            }
        }

    return new String(ac);
    }
 catch( Exception e ) {
    UnsupportedEncodingException uee
        = new UnsupportedEncodingException( "Base64Encode" );

    uee.initCause( e );

    throw uee;
    }
}

} // class

