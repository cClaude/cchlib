/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/WakeOnLan.java
** Description   :
** Encodage      : ANSI
**
**  1.01.001 2007.01.16 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.WakeOnLan
**
*/
package cx.ath.choisnet.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
**
*/
public class WakeOnLan
{
public static final int PORT = 7;

/** */
private final int port;

/**
** Pr�pare une requ�te WakeOnLan sur le port par défaut
*/
public WakeOnLan() // ----------------------------------------------------
{
 this( PORT );
}

/**
** Prépare une requête WakeOnLan sur le port avec le port spécifié.
*/
public WakeOnLan( int port ) // -------------------------------------------
{
 this.port = port;
}

/**
** Envoit une notification de reveil à une adresse MAC donnée àtravers
** tout le réseaux.
**
** @param macAddress adresse MAC de la machine à reveillée
*/
public void notify( final String macAddress ) // --------------------------
    throws
        java.net.UnknownHostException,
        java.net.SocketException,
        IllegalArgumentException,
        java.io.IOException
{
 this.notify( null, macAddress );
}

/**
** Envoit une notification de reveilàune adresse MAC donnée à travers
** le réseaux défini par broadcastAddress
**
** @param broadcastAddress  adresse de broadcast.
** @param macAddress        adresse MAC de la machine à reveillée
*/
public void notify( // ----------------------------------------------------
    String          broadcastAddress,
    final String    macAddress
    )
    throws
        java.net.UnknownHostException,
        java.net.SocketException,
        IllegalArgumentException,
        java.io.IOException
{
 if( broadcastAddress != null ) {
    broadcastAddress = broadcastAddress.trim();
    }

 if( broadcastAddress == null || broadcastAddress.length() == 0 ) {
    broadcastAddress = "255.255.255.255";
    }

 final byte[] macBytes  = getMacAddressBytes( macAddress );
 final byte[] bytes     = new byte[ 6 + 16 * macBytes.length ];

 for( int i = 0; i < 6; i++ ) {
    bytes[i] = (byte) 0xff;
    }

 for( int i = 6; i < bytes.length; i += macBytes.length ) {
    System.arraycopy( macBytes, 0, bytes, i, macBytes.length );
    }

 final InetAddress      address = InetAddress.getByName( broadcastAddress );
 final DatagramPacket   packet  = new DatagramPacket( bytes, bytes.length, address, this.port );
 final DatagramSocket   socket  = new DatagramSocket();

 socket.send( packet );
 socket.close();
}

/**
**
*/
protected static byte[] getMacAddressBytes( String macAddress ) // --------
    throws IllegalArgumentException
{
 final byte[]   bytes   = new byte[6];
 final String[] hex     = macAddress.split("(\\:|\\-)");

 if( hex.length != 6 ) {
    throw new IllegalArgumentException( "Invalid MAC address '" + macAddress + "'." );
    }

 try {
    for( int i = 0; i < 6; i++ ) {
        bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }
    }
 catch( NumberFormatException e ) {
    throw new IllegalArgumentException( "Invalid hex digit in MAC address '" + macAddress + "'." );
    }

 return bytes;
}

} // class
