/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5.java
** Description   :
** Encodage      : ANSI
**
**  2.00.005 2005.09.29 Claude CHOISNET - Version initiale
**  2.01.021 2005.10.20 Claude CHOISNET
**                      Ajout de la méthode : computeMessageDigest(String)
**  2.01.036 2005.12.27 Claude CHOISNET
**                      Ajout de update( java.nio.ByteBuffer )
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5
**
*/
package cx.ath.choisnet.util.checksum;

import java.security.MessageDigest;

/**
** Classe permettant de calculer plusieurs MD5 consécutif é moindre coup
**
** @author Claude CHOISNET
** @version 2.00.005
** @since   2.01.036
**
** @see MessageDigest
*/
public class MD5
{
/** */
private MessageDigest md5;

/**
**
*/
public MD5() // -----------------------------------------------------------
{
 try {
    this.md5 = MessageDigest.getInstance( "MD5" );
    }
 catch( java.security.NoSuchAlgorithmException e ) {
    throw new RuntimeException( "MD5:NoSuchAlgorithmException", e );
    }
}

/**
**
*/
public void reset() // ----------------------------------------------------
{
 this.md5.reset();
}

/**
** Use computeHEX(String) to encode md5 current value.
**
** @return a String value of current MD5
** @see #computeHEX
*/
public String getStringValue() // -----------------------------------------
{
 return computeHEX( getValue() );
}

/**
** Fini le calcul pour la valeur MD5 courante et retourne le résultat
*/
public byte[] getValue() // -----------------------------------------------
{
 return this.md5.digest();
}

/**
** Fini le calcul pour la valeur MD5 courante, la compléte é le tableau
** de byte donnée et retourne le résultat
*/
public byte[] getValue( byte[] key ) // -----------------------------------
{
 return this.md5.digest( key );
}

/**
**
*/
public void update( byte[] b, int off, int len ) // -----------------------
{
 this.md5.update( b, off, len );
}

/**
**
** @since 2.01.036
*/
public void update( final java.nio.ByteBuffer buffer ) // -----------------
{
 this.md5.update( buffer );
}

/**
**
** @since 2.01.036
public void compute( final java.io.File file ) // -------------------------
{
 reset();

 final java.io.FileInputStream          fis         = new java.io.FileInputStream( file );
 final java.nio.channels.FileChannel    fchannel    = fis.getChannel();
 final byte[]                           bytes       = new byte[ 8192 ];
 final java.nio.ByteBuffer              buffer      = java.nio.ByteBuffer.wrap( bytes );

 while( fchannel.read( buffer ) != -1 || buffer.position() > 0 ) {
    buffer.flip();

    this.md5.update( buffer );

    buffer.compact();
    }

 fchannel.close();
 fis.close();
}
*/

/**
**
*/
public static String computeHEX( byte[] bytes ) // ------------------------
{
 StringBuilder sb = new StringBuilder();

 for( byte b :bytes ) {
    sb.append(
        Integer.toHexString(
                0x0000FF00 | ( b & 0x000000FF )
                ).substring( 2, 4 ).toUpperCase()
        );
    }

 return sb.toString();
}

/**
**
*/
public static byte[] computeMessageDigest( String hexKey ) // -------------
    throws NumberFormatException
{
 final int len = hexKey.length() / 2;

 if( len * 2 != hexKey.length() ) {
    throw new NumberFormatException( "key error * bad length()" );
    }

 byte[] mdBytes = new byte[ len ];

 for( int i = 0; i<len; i++ ) {
    int     pos     = i<<1;
    String  digit   = hexKey.substring( pos, pos + 2 );

    mdBytes[ i ] = Integer.valueOf( digit, 16 ).byteValue();
    }

 return mdBytes;
}

} // class

/**
**
public static String computeHEX( byte b ) // ------------------------------
{
 return Integer.toHexString( 0x0000FF00 | ( b & 0x000000FF ) ).substring( 2, 4 ).toUpperCase();
}
*/

