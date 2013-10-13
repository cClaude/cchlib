/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/SimpleMD5.java
** Description   :
** Encodage      : ANSI
**
**  2.01.036 2005.12.27 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.SimpleMD5
**
*/
package cx.ath.choisnet.util.checksum;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
** Classe permettant de calculer le MD5 de différents fichiers à moindre
** coup.
**
** @author Claude CHOISNET
** @version 2.01.036
** @since   2.01.036
**
** @see java.security.MessageDigest
*/
public class SimpleMD5 extends MD5
{
/** */
private final byte[] bytesBuffer;

/**
**
*/
public SimpleMD5( final int bufferSize ) // -------------------------------
{
 super();

 this.bytesBuffer = new byte[ bufferSize ];
}

/**
**
*/
public SimpleMD5() // -----------------------------------------------------
{
 this( 8192 );
}

/**
** <p>
** Prend complètement en charge le calcul MD5 du fichier donné.
** </p>
** @param file  fichier pour lequel on souhaite calculer la valeur MD5
**
** @see #getValue()
*/
public void compute( final java.io.File file ) // -------------------------
    throws
        java.io.FileNotFoundException,
        java.io.IOException
{
 reset();

 final FileInputStream fis = new FileInputStream( file );

 try {
    final FileChannel fchannel = fis.getChannel();

    try {
        final ByteBuffer buffer = ByteBuffer.wrap( bytesBuffer );

        while( fchannel.read( buffer ) != -1 || buffer.position() > 0 ) {
            buffer.flip();

            update( buffer );

            buffer.compact();
            }

        }
    finally {
        try { fchannel.close(); } catch( Exception ignore ) {}
        }
    }
 finally {
    try { fis.close(); } catch( Exception ignore ) {}
    }
}

} // class
